package de.pxav.kelp.implementation1_12.player;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.PromptResponseType;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import de.pxav.kelp.core.player.prompt.chat.ChatPromptVersionTemplate;
import de.pxav.kelp.core.player.prompt.chat.SimpleChatPrompt;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.synchronize.ServerMainThread;
import de.pxav.kelp.core.scheduler.type.SchedulerFactory;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
@Singleton
public class VersionedChatPrompt extends ChatPromptVersionTemplate {

  private SchedulerFactory schedulerFactory;
  private KelpSchedulerRepository schedulerRepository;

  private ConcurrentMap<UUID, SimpleChatPrompt> chatPrompts = Maps.newConcurrentMap();
  private ConcurrentMap<UUID, PromptTimeout> playerTimeouts = Maps.newConcurrentMap();

  @Inject
  public VersionedChatPrompt(SchedulerFactory schedulerFactory, KelpSchedulerRepository schedulerRepository) {
    this.schedulerFactory = schedulerFactory;
    this.schedulerRepository = schedulerRepository;
  }

  @Override
  public void simpleChatPrompt(SimpleChatPrompt simpleChatPrompt) {
    Player player = simpleChatPrompt.getPlayer();
    PromptTimeout timeout = simpleChatPrompt.getTimeout();

    if (timeout != null) {
      UUID task = schedulerFactory.newDelayedScheduler()
        .withDelayOf(timeout.getTimeout())
        .timeUnit(timeout.getTimeUnit())
        .async()
        .run((taskId -> {
          this.removeFromCache(player.getUniqueId());

          if (timeout.getOnTimeout() != null && !timeout.isAsync()) {
            ServerMainThread.RunParallel.run(() -> {
              timeout.getOnTimeout().run();
            });
          }

          if (timeout.getOnTimeout() != null && timeout.isAsync()) {
            timeout.getOnTimeout().run();
          }
        }));

      timeout.setTaskId(task);
      this.playerTimeouts.put(player.getUniqueId(), timeout);
    }

    this.chatPrompts.put(player.getUniqueId(), simpleChatPrompt);
  }

  @EventHandler
  public void handleAsyncChatInput(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();

    if (!this.chatPrompts.containsKey(player.getUniqueId())) {
      return;
    }

    event.setCancelled(true);
    SimpleChatPrompt chatPrompt = this.chatPrompts.get(player.getUniqueId());
    String message = event.getMessage().replace("%", "%%");

    if (chatPrompt.isEchoEnabled()) {
      String echoMessage = chatPrompt.getEchoPrefix() + message + chatPrompt.getEchoSuffix();
      player.sendMessage(echoMessage);
    }

    if (message.equalsIgnoreCase(chatPrompt.getCancelFlag())) {
      UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
      this.schedulerRepository.interruptScheduler(taskId);
      chatPrompt.getOnCancel().run();
      this.removeFromCache(player.getUniqueId());
      return;
    }

    PromptResponseType responseType = chatPrompt.getResponseHandler().accept(message);

    UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
    this.schedulerRepository.interruptScheduler(taskId);

    if (responseType == PromptResponseType.TRY_AGAIN) {
      this.simpleChatPrompt(chatPrompt);
      return;
    }

    this.removeFromCache(player.getUniqueId());

  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    if (!this.chatPrompts.containsKey(player.getUniqueId())) {
      return;
    }

    UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
    this.schedulerRepository.interruptScheduler(taskId);

    this.removeFromCache(player.getUniqueId());
  }

  private void removeFromCache(UUID player) {
    this.playerTimeouts.remove(player);
    this.chatPrompts.remove(player);
  }

}
