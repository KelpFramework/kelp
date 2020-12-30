package de.pxav.kelp.implementation1_8.player;

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
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * This is the {@code 1.8} version implementation of the sign prompt.
 * This class contains the method used for opening the prompt, but it also
 * does the input processing.
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

  /**
   * Opens the sign editor to a specific player. If the player clicks {@code Done} in this editor,
   * the response handler is called automatically.
   *
   * @param player          The player you want the prompt to show to.
   * @param initialText     Pre-defined input. When the editor opens, there won't be an empty sign, but
   *                        already some lines written to it providing help to the player on which input
   *                        format is expected for example. The list follows a chronological order, so the
   *                        0th list item is the 1st line and so on. If you do not want a default input to
   *                        be displayed, simply pass an empty list here.
   * @param timeout         If you want to enable a timeout, you can configure it here. Detailed information on
   *                        how to do that can be found in {@link PromptTimeout}. If you want to disable timeout,
   *                        pass {@code null} here.
   * @param responseHandler The code handling the input by the player. If the player submits their input by pressing
   *                        {@code Done}, the {@link SignPromptResponseHandler#accept(List)} method is executed.
   *                        More information about handling the response can be found in {@link SignPromptResponseHandler}.
   */
  @Override
  public void openSignPrompt(Player player, List<String> initialText, PromptTimeout timeout, SignPromptResponseHandler responseHandler) {
    CraftPlayer craftPlayer = ((CraftPlayer)player);

    // in order to be able to open a sign prompt, there really has to be a sign block. So by sending the open editor packet
    // we cannot directly open an editor. So we change a block on the client side of the player to a sign. In order to revert
    // these changes later, we have to save the original block.
    Location signLocation = craftPlayer.getLocation().clone();
    signLocation.setY(255);
    originalBlocks.put(player.getUniqueId(), signLocation.getBlock());

    BlockPosition blockPosition = new BlockPosition(signLocation.getBlockX(), signLocation.getBlockY(), signLocation.getBlockZ());

    // change the block defined above to a sign on the client side
    PacketPlayOutBlockChange blockChange = new PacketPlayOutBlockChange(((CraftWorld)signLocation.getWorld()).getHandle(), blockPosition);
    blockChange.block = CraftMagicNumbers.getBlock(68).fromLegacyData((byte) 0);
    craftPlayer.getHandle().playerConnection.sendPacket(blockChange);

    // if there are not enough lines / some lines are null in the default text, add those
    // missing lines to the list
    initialText = this.addMissingLines(Lists.newArrayList(initialText));

    // the spawned sign is updated with the default text. So that this text is visible
    // later in the editor
    PacketPlayOutUpdateSign updateSignPacket = new PacketPlayOutUpdateSign(((CraftWorld)signLocation.getWorld()).getHandle(),
      blockPosition,
      new IChatBaseComponent[] {
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(0) + "\"}"),
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(1) + "\"}"),
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(2) + "\"}"),
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(3) + "\"}")
      });
    craftPlayer.getHandle().playerConnection.sendPacket(updateSignPacket);

    // prepare the editor packet and then actually send it.
    PacketPlayOutOpenSignEditor signEditorPacket = new PacketPlayOutOpenSignEditor(blockPosition);
    craftPlayer.getHandle().playerConnection.sendPacket(signEditorPacket);

    // add player to cache to be able to know which player to handle later
    this.promptHandlers.put(player.getUniqueId(), responseHandler);

    // apply timeout data if a timeout was specified
    if (timeout != null) {
      UUID task = schedulerFactory.newDelayedScheduler()
        .withDelayOf(timeout.getTimeout())
        .timeUnit(timeout.getTimeUnit())
        .async()
        .run((taskId -> {

          // if the player times out, the changed block has to be reset.
          // sending block change packets is not thread-safe, therefore
          // we jump on the main thread for this action.
          ServerMainThread.RunParallel.run(() -> {
            this.resetBlockAndRemove(player.getUniqueId());

            // if the timeout is sync, we execute the timeout code
            // right here on the main thread as well.
            if (timeout.getOnTimeout() != null && !timeout.isAsync()) {
              timeout.getOnTimeout().run();
            }
          });

          // jump back to the async thread and execute the
          // timeout code if it is async.
          if (timeout.getOnTimeout() != null && timeout.isAsync()) {
            timeout.getOnTimeout().run();
          }

      }));

      timeout.setTaskId(task);
      this.playerTimeouts.put(player.getUniqueId(), timeout);
    }
  }

  /**
   * Checks whether the player is currently in a prompt.
   *
   * @param player the uuid of the player you want to check.
   * @return whether the player is currently in a prompt.
   */
  public boolean isChecked(UUID player) {
    return originalBlocks.containsKey(player);
  }

  /**
   * Gets the timeout information of a specific player. If the player
   * has no timeout for their prompt, it will return {@code null}.
   *
   * @param player The uuid of the player you want to get the timeout from.
   * @return The timeout information as {@link PromptTimeout}.
   */
  public PromptTimeout getTimeout(UUID player) {
    return this.playerTimeouts.get(player);
  }

  /**
   * Gets the block which was present before a temporary sign was placed at
   * that location and restores this block. Furthermore the player is removed
   * from the cache.
   *
   * This method uses the block change packet and has to be executed on the main
   * thread!
   *
   * @param player The uuid of the player you want to remove from the cache
   *               and whose block you want to reset.
   */
  public void resetBlockAndRemove(UUID player) {
    Block block = originalBlocks.get(player);
    BlockPosition blockPosition = new BlockPosition(block.getLocation().getBlockX(),
      block.getLocation().getBlockY(),
      block.getLocation().getBlockZ());

    // revert block changes on client side.
    PacketPlayOutBlockChange blockChange = new PacketPlayOutBlockChange(((CraftWorld)block.getWorld()).getHandle(), blockPosition);
    blockChange.block = CraftMagicNumbers.getBlock(block.getType().getId()).fromLegacyData((byte) 0);
    ((CraftPlayer)Bukkit.getPlayer(player)).getHandle().playerConnection.sendPacket(blockChange);

    // remove from cache.
    this.originalBlocks.remove(player);
    this.promptHandlers.remove(player);
  }

  /**
   * Gets the {@link SignPromptResponseHandler} class that is currently handling the input of the player.
   *
   * @param player The player whose prompt handler you want to get.
   * @return The handler of the player's prompt.
   */
  public SignPromptResponseHandler getHandler(UUID player) {
    return this.promptHandlers.get(player);
  }

  /**
   * If the player quits before a timeout or before they entered
   * a valid input, they are removed from the cache to prevent
   * useless data and null pointers when the timeout code is executed.
   *
   * @param event The event you want to listen for.
   */
  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    if (isChecked(player.getUniqueId())) {
      // interrupt timeout schedulers
      UUID taskId = getTimeout(player.getUniqueId()).getTaskId();
      schedulerRepository.interruptScheduler(taskId);

      // remove from cache.
      originalBlocks.remove(player.getUniqueId());
    }
  }

  /**
   * If a default text list for a sign is incomplete / does not
   * contain 4 lines, this method corrects those issues in order
   * to be readable by the sign update packet {@link PacketPlayOutUpdateSign}
   *
   * @param lines The original list of sign lines.
   * @return The new list of lines.
   */
  private List<String> addMissingLines(List<String> lines) {
    // calculate the amount of lines missing
    int lineDifference = 4 - lines.size();

    // if there is nothing to correct, return.
    if (lineDifference == 0) {
      return lines;
    }

    // add as many empty lines as needed
    for (int i = 0; i < lineDifference; i++) {
      lines.add("");
    }

    return lines;
  }

}
