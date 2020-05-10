package de.pxav.kelp.implementation1_8.player;

import de.pxav.kelp.core.player.PlayerVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedPlayer extends PlayerVersionTemplate {

  @Override
  public void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
    final IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
    final IChatBaseComponent subComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
    final PacketPlayOutTitle titleOut = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, fadeIn, stay, fadeOut);
    final PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, subComponent);
    final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
    final PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subComponent);

    this.sendPacket(titleOut, player);
    this.sendPacket(subtitle, player);
    this.sendPacket(titlePacket, player);
    this.sendPacket(subtitlePacket, player);
  }

  @Override
  public void sendActionBar(Player player, String message) {
    PacketPlayOutChat packet =
            new PacketPlayOutChat(
                    IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte) 2);
    sendPacket(packet, player);
  }

  @Override
  public void sendTabHeaderAndFooter(Player player, String header, String footer) {
    if (header == null) header = "";
    if (footer == null) footer = "";

    IChatBaseComponent tabHeader =
            IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
    IChatBaseComponent tabFooter =
            IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabHeader);

    try {
      Field f = packet.getClass().getDeclaredField("b");
      f.setAccessible(true);
      f.set(packet, tabFooter);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }

    sendPacket(packet, player);
  }

  @Override
  public void setHealth(Player player, int health) {
    player.setHealth(health);
  }

  @Override
  public void teleport(Player player, Location location) {
    player.teleport(location);
  }

  public UUID getUniqueId(Player player) {
    return player.getUniqueId();
  }

  private void sendPacket(Packet packet, Player player) {
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
  }

}
