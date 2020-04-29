package de.pxav.kelp.core.inventory.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.entity.Player;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class WindowPacketTemplate {

  /**
   * Sends a packet to the client of the given player which
   * updates the inventory title without a flicker effect.
   *
   * @param player    The player whose title should be updated
   * @param newTitle  The new title string you want to set
   */
  public abstract void updateWindowTitle(Player player, String newTitle);

}
