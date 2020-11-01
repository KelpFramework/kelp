package de.pxav.kelp.implementation1_8.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.sign.SignPromptResponseHandler;
import de.pxav.kelp.core.player.prompt.sign.SignPromptVersionTemplate;
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
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
@Singleton
public class VersionedSignPrompt extends SignPromptVersionTemplate {

  private final ConcurrentMap<UUID, Block> originalBlocks = Maps.newConcurrentMap();
  private final ConcurrentMap<UUID, SignPromptResponseHandler> promptHandlers = Maps.newConcurrentMap();

  @Override
  public void openSignPrompt(KelpPlayer player, List<String> initialText, SignPromptResponseHandler responseHandler) {
    CraftPlayer craftPlayer = ((CraftPlayer)player.getBukkitPlayer());

    Location signLocation = craftPlayer.getLocation().clone();
    signLocation.setY(255);
    originalBlocks.put(player.getUUID(), signLocation.getBlock());

    BlockPosition blockPosition = new BlockPosition(signLocation.getBlockX(), signLocation.getBlockY(), signLocation.getBlockZ());

    PacketPlayOutBlockChange blockChange = new PacketPlayOutBlockChange(((CraftWorld)signLocation.getWorld()).getHandle(), blockPosition);
    blockChange.block = CraftMagicNumbers.getBlock(68).fromLegacyData((byte) 0);
    craftPlayer.getHandle().playerConnection.sendPacket(blockChange);

    initialText = this.addMissingLines(Lists.newArrayList(initialText));

    PacketPlayOutUpdateSign updateSignPacket = new PacketPlayOutUpdateSign(((CraftWorld)signLocation.getWorld()).getHandle(),
      blockPosition,
      new IChatBaseComponent[] {
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(0) + "\"}"),
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(1) + "\"}"),
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(2) + "\"}"),
        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + initialText.get(3) + "\"}")
      });
    craftPlayer.getHandle().playerConnection.sendPacket(updateSignPacket);

    PacketPlayOutOpenSignEditor signEditorPacket = new PacketPlayOutOpenSignEditor(blockPosition);
    craftPlayer.getHandle().playerConnection.sendPacket(signEditorPacket);

    this.promptHandlers.put(player.getUUID(), responseHandler);
  }

  public boolean isChecked(UUID player) {
    return originalBlocks.containsKey(player);
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
