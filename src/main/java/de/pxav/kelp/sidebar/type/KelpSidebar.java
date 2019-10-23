package de.pxav.kelp.sidebar.type;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

/**
 * This class basically is the template for each sidebar.
 * If you want to make a custom sidebar,
 * your class has to inherit from this class.
 *
 * It provides essential methods used by the repository.
 *
 * @author pxav
 */
public abstract class KelpSidebar {

  /**
   * Renders the sidebar and its components.
   * That means that no existing scoreboard is modified,
   * but a new one will be created as well as
   * a new objective.
   * This method does not open the sidebar automatically.
   *
   * @param player The player for which the sidebar should be rendered.
   * @return The final sidebar obeject.
   */
  public abstract Scoreboard renderSidebar(Player player);

  /**
   * Renders and opens the sidebar.
   * This method basically executes the {@code #renderSidebar(player)} method
   * above and directly sets the scoreboard for the given player.
   *
   * @param player The player who should see the scoreboard.
   * @return The render result.
   */
  public abstract Scoreboard renderAndOpenSidebar(Player player);

  /**
   * Updates the given scoreboard without creating a new
   * one.
   *
   * @param player The player whose scoreboard you want to update.
   * @return The final scoreboard with the updated data.
   */
  public abstract Scoreboard update(Player player);

  /**
   * Makes the sidebar disappear from the player's screen.
   *
   * @param player The player whose sidebar should disappear.
   */
  public abstract void hideSidebar(Player player);

}
