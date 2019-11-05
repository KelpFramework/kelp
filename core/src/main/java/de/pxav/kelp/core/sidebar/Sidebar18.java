package de.pxav.kelp.core.sidebar;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.sidebar.version.VersionedSidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class Sidebar18 extends VersionedSidebar {

  private JavaPlugin javaPlugin;

  public Sidebar18(JavaPlugin javaPlugin) {
    this.javaPlugin = javaPlugin;
  }

  @Override
  public Scoreboard createScoreboard() {
    Preconditions.checkNotNull(Bukkit.getScoreboardManager());
    return Bukkit.getScoreboardManager().getNewScoreboard();
  }

  @Override
  public Objective createObjective(Scoreboard parent,
                                   String identifier,
                                   String title) {
    Objective objective = parent.registerNewObjective(identifier, "dummy");
    objective.setDisplayName(title);
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    return objective;
  }

  @Override
  public void openScoreboard(Scoreboard scoreboard, Player player) {
    player.setScoreboard(scoreboard);
  }

  @Override
  public void closeScoreboard(Player player) {
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Preconditions.checkNotNull(manager);
    Scoreboard emptyScoreboard = manager.getNewScoreboard();
    player.setScoreboard(emptyScoreboard);
  }

}
