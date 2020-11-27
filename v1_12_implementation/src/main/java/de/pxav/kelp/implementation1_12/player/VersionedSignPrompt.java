package de.pxav.kelp.implementation1_12.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.sign.SignPromptResponseHandler;
import de.pxav.kelp.core.player.prompt.sign.SignPromptVersionTemplate;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.synchronize.ServerMainThread;
import de.pxav.kelp.core.scheduler.type.SchedulerFactory;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
@Singleton
public class VersionedSignPrompt extends SignPromptVersionTemplate {

  private final ConcurrentMap<UUID, Block> originalBlocks = Maps.newConcurrentMap();
  private final ConcurrentMap<UUID, SignPromptResponseHandler> promptHandlers = Maps.newConcurrentMap();
  private final ConcurrentMap<UUID, PromptTimeout> playerTimeouts = Maps.newConcurrentMap();

  private SchedulerFactory schedulerFactory;
  private KelpSchedulerRepository schedulerRepository;

  @Inject
  public VersionedSignPrompt(SchedulerFactory schedulerFactory, KelpSchedulerRepository schedulerRepository) {
    this.schedulerFactory = schedulerFactory;
    this.schedulerRepository = schedulerRepository;
  }

  @Override
  public void openSignPrompt(Player player, List<String> initialText, PromptTimeout timeout, SignPromptResponseHandler responseHandler) {
    CraftPlayer craftPlayer = ((CraftPlayer)player);

    Location signLocation = craftPlayer.getLocation().clone();
    signLocation.setY(255);
    originalBlocks.put(player.getUniqueId(), signLocation.getBlock());

    BlockPosition blockPosition = new BlockPosition(signLocation.getBlockX(), signLocation.getBlockY(), signLocation.getBlockZ());

    PacketPlayOutBlockChange blockChange = new PacketPlayOutBlockChange(((CraftWorld)signLocation.getWorld()).getHandle(), blockPosition);
    blockChange.block = CraftMagicNumbers.getBlock(68).fromLegacyData((byte) 0);
    craftPlayer.getHandle().playerConnection.sendPacket(blockChange);

    initialText = this.addMissingLines(Lists.newArrayList(initialText));
    player.sendSignChange(signLocation, new String[]{initialText.get(0), initialText.get(1), initialText.get(2), initialText.get(3)});

    PacketPlayOutOpenSignEditor signEditorPacket = new PacketPlayOutOpenSignEditor(blockPosition);
    craftPlayer.getHandle().playerConnection.sendPacket(signEditorPacket);

    this.promptHandlers.put(player.getUniqueId(), responseHandler);

    if (timeout != null) {
      UUID task = schedulerFactory.newDelayedScheduler()
        .withDelayOf(timeout.getTimeout())
        .timeUnit(timeout.getTimeUnit())
        .async()
        .run((taskId -> {

          ServerMainThread.RunParallel.run(() -> {
            this.resetBlockAndRemove(player.getUniqueId());
            if (timeout.getOnTimeout() != null && !timeout.isAsync()) {
              timeout.getOnTimeout().run();
            }
          });

          if (timeout.getOnTimeout() != null && timeout.isAsync()) {
            timeout.getOnTimeout().run();
          }

      }));

      timeout.setTaskId(task);
      this.playerTimeouts.put(player.getUniqueId(), timeout);
    }
  }

  public boolean isChecked(UUID player) {
    return originalBlocks.containsKey(player);
  }

  public PromptTimeout getTimeout(UUID player) {
    return this.playerTimeouts.get(player);
  }

  public void resetBlockAndRemove(UUID player) {
    Block block = originalBlocks.get(player);
    BlockPosition blockPosition = new BlockPosition(block.getLocation().getBlockX(),
      block.getLocation().getBlockY(),
      block.getLocation().getBlockZ());

    PacketPlayOutBlockChange blockChange = new PacketPlayOutBlockChange(((CraftWorld)block.getWorld()).getHandle(), blockPosition);
    blockChange.block = CraftMagicNumbers.getBlock(block.getType().getId()).fromLegacyData((byte) 0);
    ((CraftPlayer)Bukkit.getPlayer(player)).getHandle().playerConnection.sendPacket(blockChange);

    this.originalBlocks.remove(player);
    this.promptHandlers.remove(player);
  }

  public SignPromptResponseHandler getHandler(UUID player) {
    return this.promptHandlers.get(player);
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    if (isChecked(player.getUniqueId())) {
      UUID taskId = getTimeout(player.getUniqueId()).getTaskId();
      schedulerRepository.interruptScheduler(taskId);

      originalBlocks.remove(player.getUniqueId());
    }
  }

  private List<String> addMissingLines(List<String> lines) {
    int lineDifference = 4 - lines.size();

    if (lineDifference == 0) {
      return lines;
    }

    for (int i = 0; i < lineDifference; i++) {
      lines.add("");
    }

    return lines;
  }

}
