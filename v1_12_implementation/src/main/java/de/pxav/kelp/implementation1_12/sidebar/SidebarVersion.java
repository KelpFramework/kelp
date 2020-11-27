package de.pxav.kelp.implementation1_12.sidebar;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class SidebarVersion extends SidebarVersionTemplate {

  @Override
  public Scoreboard createScoreboard() {
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
