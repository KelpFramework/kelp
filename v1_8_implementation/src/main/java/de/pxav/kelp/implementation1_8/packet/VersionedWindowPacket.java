package de.pxav.kelp.implementation1_8.packet;

import de.pxav.kelp.core.inventory.version.WindowPacketTemplate;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedWindowPacket extends WindowPacketTemplate {

  @Override
  public void updateWindowTitle(Player player, String newTitle) {
    EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();

    PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(
            entityPlayer.activeContainer.windowId, "minecraft:chest",
            new ChatMessage(newTitle),
            player.getOpenInventory().getTopInventory().getSize());
    entityPlayer.playerConnection.sendPacket(packet);
    entityPlayer.updateInventory(entityPlayer.activeContainer);
  }

}
