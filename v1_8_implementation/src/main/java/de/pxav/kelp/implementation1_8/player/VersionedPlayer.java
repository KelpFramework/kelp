package de.pxav.kelp.implementation1_8.player;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.PlayerVersionTemplate;
import de.pxav.kelp.core.sound.KelpSound;
import de.pxav.kelp.core.sound.SoundRepository;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 * @author Etrayed
 */
@Versioned
public class VersionedPlayer extends PlayerVersionTemplate {

  private SoundRepository soundRepository;

  @Inject
  public VersionedPlayer(SoundRepository soundRepository) {
    this.soundRepository = soundRepository;
  }

  /**
   * Sends a title to a player. A title is a big text displayed
   * right in the middle of the player's screen.
   *
   * @param player The player who should see the title.
   * @param title The upper title text (will be displayed slightly bigger than the sub title).
   * @param subTitle The lower title text (will be displayed slightly smaller than the main title).
   * @param fadeIn How long should it take to fade the title in? (in ticks)
   * @param stay How long should the title stay in 100% opacity? (in ticks)
   * @param fadeOut How long should it take to fade the title out? (in ticks)
   */
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

  /**
   * Sends an action bar message to the player.
   * The action bar is a line of text, which is displayed
   * above the player's hotbar.
   *
   * @param player  The player who should receive the message.
   * @param message The message you want to send.
   */
  @Override
  public void sendActionBar(Player player, String message) {
    PacketPlayOutChat packet =
            new PacketPlayOutChat(
                    IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte) 2);
    sendPacket(packet, player);
  }

  /**
   * Sets the tab header and footer of player. The tab header is
   * a text displayed above the player list in the tab, while
   * the tab footer is a message displayed below the player list.
   *
   * The messages may contain '\n' to create new lines inside the
   * message.
   *
   * @param player The player who should see the messages.
   * @param header The header message you want to send.
   * @param footer The footer message you want to send.
   */
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

  /**
   * Plays a sound to the player.
   *
   * @param player    The player who should hear the sound.
   * @param sound     The sound you want to play.
   * @param location  The location, where the sound should come from.
   * @param volume    How loud the sound should be.
   * @param pitch     How strong the sound should be pitched.
   */
  @Override
  public void playSound(Player player, KelpSound sound, Location location, float volume, float pitch) {
    Sound bukkitSound = Sound.valueOf(soundRepository.getSound(sound));
    player.playSound(player.getLocation(), bukkitSound, volume, pitch);
  }

  /**
   * Sets the player's health.
   *
   * @param player The player whose health you want to change.
   * @param health How many health points the player should have.
   *               2 health points equal 1 heart.
   *               So 20 health points equal the full 10 hearts.
   */
  @Override
  public void setHealth(Player player, int health) {
    player.setHealth(health);
  }

  /**
   * Teleports the player to the given location.
   *
   * @param player    The player you want to teleport.
   * @param location  The location you want to teleport the player to.
   */
  @Override
  public void teleport(Player player, Location location) {
    player.teleport(location);
  }

  /**
   * @param player The player whose UUID you want to get.
   * @return The player's uuid.
   */
  public UUID getUniqueId(Player player) {
    return player.getUniqueId();
  }

  /**
   * @param player The player whose location you want to get
   * @return The location the player is currently at.
   */
  @Override
  public Location getLocation(Player player) {
    return null;
  }

  /**
   * Checks if the player is currently located in water.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently in water.
   */
  @Override
  public boolean isInWater(Player player) {
    try {
      Field inWaterField = Entity.class.getDeclaredField("inWater");

      inWaterField.setAccessible(true);

      return inWaterField.getBoolean(((CraftEntity) player).getHandle());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * Checks if the player is currently stuck in a cobweb.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently stuck in a cobweb.
   */
  @Override
  public boolean isInCobweb(Player player) {
    try {
      Field hField = Entity.class.getDeclaredField("H");

      hField.setAccessible(true);

      return hField.getBoolean(((CraftEntity) player).getHandle());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * Sends the given packet to the given player.
   *
   * @param packet The packet you want to send.
   * @param player The player who should receive the packet.
   */
  private void sendPacket(Packet packet, Player player) {
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
  }

}
