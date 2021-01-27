package de.pxav.kelp.core.sidebar.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * This version template is responsible for specific update operations
 * other than simply updating specific components. This includes updating
 * a sidebar's title for example.
 */
@KelpVersionTemplate
public abstract class SidebarUpdaterVersionTemplate {

  /**
   * Updates the title of sidebar of the given player to the given string.
   * @param to      The title to update to.
   * @param player  The player whose sidebar's title should be updated.
   */
  public abstract void updateTitleOnly(String to, KelpPlayer player);

}
