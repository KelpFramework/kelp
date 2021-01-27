package de.pxav.kelp.core.sidebar.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.component.SidebarComponent;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class SidebarVersionTemplate {

  /**
   * Renders the sidebar to a specific player. This means it displays
   * it for the first time (so only use this method if the player does
   * not already see this sidebar to avoid flicker effects or similar behaviour).
   *
   * @param sidebar The sidebar to render to the given player.
   * @param player  The player to render the sidebar to.
   */
  public abstract void renderSidebar(KelpSidebar sidebar, KelpPlayer player);

  /**
   * Performs a lazy update on the sidebar. A lazy update does not remove all entries/lines
   * from a scoreboard first, like it is done by {@link #updateSidebar(KelpSidebar, KelpPlayer)}. It only uses the
   * existing entries in the sidebar, which means that you cannot use it if you know that the amount
   * of lines in the sidebar might change with an update.
   *
   * However this update method is completely free from any flickering effects and it is
   * not as performance heavy as a normal update. So if you can, you should prefer this update
   * method over a normal update.
   *
   * @param sidebar     The sidebar to update.
   * @param kelpPlayer  The player who should see the updated sidebar.
   */
  public abstract void lazyUpdate(KelpSidebar sidebar, KelpPlayer kelpPlayer);

  /**
   * Performs a full-update on the given sidebar, which means that all existing
   * contents are removed and then new contents are applied. This method is safe
   * against changing amounts of lines.
   *
   * @param sidebar   The sidebar you want to update.
   * @param player    The player who should see the updates.
   */
  public abstract void updateSidebar(KelpSidebar sidebar, KelpPlayer player);

}
