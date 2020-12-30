package de.pxav.kelp.implementation1_8.player;

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
 * This is the {@code 1.8} version implementation of the chat prompt.
 * This class contains the method to open the prompt to the player
 * but also listens to the input and calls the corresponding input handler.
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

    // configure timeout scheduler
    if (timeout != null) {
      UUID task = schedulerFactory.newDelayedScheduler()
        .withDelayOf(timeout.getTimeout())
        .timeUnit(timeout.getTimeUnit())
        .async()
        .run((taskId -> {
          this.removeFromCache(player.getUniqueId());

          // if the timeout code should be handled sync, jump back to the main thread
          // as soon as the timeout happens. Until then, stay on async thread.
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

    // add it to the cache so that the event handler is able to notice
    // the player is in a prompt.
    this.chatPrompts.put(player.getUniqueId(), simpleChatPrompt);
  }

  /**
   * This method is triggered when a player sends a chat message.
   * It calls the message handler of the prompt and prompts the player
   * again if needed. It also checks if the player wants to escape from
   * the prompt.
   *
   * @param event The event to listen for.
   */
  @EventHandler
  public void handleAsyncChatInput(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();

    if (!this.chatPrompts.containsKey(player.getUniqueId())) {
      return;
    }

    // cancel the event so that the prompt input is invisible for other players
    event.setCancelled(true);
    SimpleChatPrompt chatPrompt = this.chatPrompts.get(player.getUniqueId());

    // if the message contains a % sign, handling the message will cause exceptions
    // caused by bukkit. In order to display the % char correctly we have to replace it.
    String message = event.getMessage().replace("%", "%%");

    // send echo if enabled
    if (chatPrompt.isEchoEnabled()) {
      StringBuilder echoMessage = new StringBuilder();
      if (chatPrompt.getEchoPrefix() != null) {
        echoMessage.append(chatPrompt.getEchoPrefix());
      }
      echoMessage.append(message);
      if (chatPrompt.getEchoSuffix() != null) {
        echoMessage.append(chatPrompt.getEchoSuffix());
      }
      player.sendMessage(echoMessage.toString());
    }

    // allow the player to escape from the prompt if they enter the
    // exit flag.
    if (message.equalsIgnoreCase(chatPrompt.getCancelFlag())) {
      UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
      this.schedulerRepository.interruptScheduler(taskId);
      chatPrompt.getOnCancel().run();
      this.removeFromCache(player.getUniqueId());
      return;
    }

    // let the prompt handler handle the input. Later the plugin checks whether
    // the input was valid and delegates the player to another prompt.
    // if not, the prompt will close.
    PromptResponseType responseType = chatPrompt.getResponseHandler().accept(message);

    // no timeout will happen anymore as the response is already present.
    // so timeout schedulers can be canceled. If the player is prompted
    // again, the timeout will be started again automatically by this method.
    UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
    this.schedulerRepository.interruptScheduler(taskId);

    // if the handler stated the response is invalid, open the prompt again
    if (responseType == PromptResponseType.TRY_AGAIN) {
      this.simpleChatPrompt(chatPrompt);
      return;
    }

    this.removeFromCache(player.getUniqueId());

  }

  /**
   * If the player quits the server while being in a prompt, they
   * have to get removed from the cache to avoid the plugin to
   * wait for a response infinitely if no timeout is specified.
   *
   * @param event The event to listen for.
   */
  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    if (!this.chatPrompts.containsKey(player.getUniqueId())) {
      return;
    }

    // cancel timeout scheduler. If the scheduler refers to an offline player
    // it will throw null pointer exceptions
    UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
    this.schedulerRepository.interruptScheduler(taskId);

    this.removeFromCache(player.getUniqueId());
  }

  /**
   * Removes the chat prompt data from the given player from
   * the cache. This data includes the timeout information as
   * well as the chat prompt object (containing information about
   * echo, cancel flag, etc.)
   *
   * @param player The player to be removed.
   */
  private void removeFromCache(UUID player) {
    this.playerTimeouts.remove(player);
    this.chatPrompts.remove(player);
  }

}
