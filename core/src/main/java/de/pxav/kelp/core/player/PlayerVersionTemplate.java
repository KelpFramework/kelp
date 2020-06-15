package de.pxav.kelp.core.player;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.sound.KelpSound;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This is a version template for the {@code KelpPlayer}.
 * It contains template methods for all version specific
 * stuff a player can do.
 *
 * @see KelpPlayer
 * @author pxav
 * @author Etrayed
 */
@KelpVersionTemplate
public abstract class PlayerVersionTemplate {

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
  public abstract void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut);

  /**
   * Sends an action bar message to the player.
   * The action bar is a line of text, which is displayed
   * above the player's hotbar.
   *
   * @param player  The player who should receive the message.
   * @param message The message you want to send.
   */
  public abstract void sendActionBar(Player player, String message);

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
  public abstract void sendTabHeaderAndFooter(Player player, String header, String footer);

  /**
   * Plays a sound to the player.
   *
   * @param player    The player who should hear the sound.
   * @param sound     The sound you want to play.
   * @param location  The location, where the sound should come from.
   * @param volume    How loud the sound should be.
   * @param pitch     How strong the sound should be pitched.
   */
  public abstract void playSound(Player player, KelpSound sound, Location location, float volume, float pitch);

  /**
   * Sets the player's health.
   *
   * @param player The player whose health you want to change.
   * @param health How many health points the player should have.
   *               2 health points equal 1 heart.
   *               So 20 health points equal the full 10 hearts.
   */
  public abstract void setHealth(Player player, int health);

  /**
   * @param player The player whose UUID you want to get.
   * @return The player's uuid.
   */
  public abstract UUID getUniqueId(Player player);

  /**
   * @param player The player whose location you want to get
   * @return The location the player is currently at.
   */
  public abstract Location getLocation(Player player);

  /**
   * Teleports the player to the given location.
   *
   * @param player    The player you want to teleport.
   * @param location  The location you want to teleport the player to.
   */
  public abstract void teleport(Player player, Location location);

  /**
   * Checks if the player is currently stuck in a cobweb.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently stuck in a cobweb.
   */
  public abstract boolean isInCobweb(Player player);

  /**
   * Checks if the player is currently located in water.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently in water.
   */
  public abstract boolean isInWater(Player player);
}
