package de.pxav.kelp.core.player;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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

  public abstract void setHealth(Player player, int health);

  public abstract void teleport(Player player, Location location);

}
