package de.pxav.kelp.sidebar.version;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * This basically is a template for version-specific implementations.
 * If you want to create a version specific implementation of scoreboards
 * you need to make your class inherit from this abstract class
 * and type the content of the given methods.
 *
 * Kelp always detects if there is an implementation installed and
 * automatically binds it to this class so that it can be injected
 * in your constructor. So you can always depend on this class if
 * you need version specific sidebar code.
 *
 * @author pxav
 */
public abstract class VersionedSidebar {

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

}
