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
   * @return A new, empty scoreboard.
   */
  public abstract Scoreboard createScoreboard();

  /**
   * Creates a scoreboard objective.
   *
   * @param parent The scoreboard in which the objective should be wrapped.
   * @param identifier The name/identifier of the objective.
   * @param title The display name/title of the objective.
   * @return The final scoreboard objective.
   */
  public abstract Objective createObjective(Scoreboard parent,
                                            String identifier,
                                            String title);

  /**
   * Open the screboard for a player.
   *
   * @param scoreboard The actual scoreboard you want to open.
   * @param player The player who should see the scoreboard.
   */
  public abstract void openScoreboard(Scoreboard scoreboard, Player player);

  /**
   * Close / hide the scoreboard for the player again.
   *
   * @param player The player whose scoreboard
   *               should become invisible.
   */
  public abstract void closeScoreboard(Player player);

  public abstract void renderSidebar(KelpSidebar sidebar, KelpPlayer player);

  public abstract void lazyUpdate(KelpSidebar sidebar, KelpPlayer kelpPlayer);

  public abstract void updateSidebar(KelpSidebar sidebar, KelpPlayer player);

}
