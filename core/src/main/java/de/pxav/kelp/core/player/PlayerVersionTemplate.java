package de.pxav.kelp.core.player;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.sound.KelpSound;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class PlayerVersionTemplate {

  public abstract void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut);

  public abstract void sendActionBar(Player player, String message);

  public abstract void sendTabHeaderAndFooter(Player player, String header, String footer);

  public abstract void playSound(Player player, KelpSound sound, Location location, float volume, float pitch);

  public abstract void setHealth(Player player, int health);

  public abstract UUID getUniqueId(Player player);

  public abstract Location getLocation(Player player);

  public abstract void teleport(Player player, Location location);

}
