package de.pxav.kelp.sidebar.component;

import de.pxav.kelp.sidebar.SidebarUtils;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class EmptyLineComponent implements SimpleSidebarComponent {

  private int line;

  private SidebarUtils sidebarUtils;

  public EmptyLineComponent(SidebarUtils sidebarUtils) {
    this.sidebarUtils = sidebarUtils;
  }

  public EmptyLineComponent score(int line) {
    this.line = line;
    return this;
  }

  @Override
  public void render(Scoreboard parent) {
    String entry = sidebarUtils.randomEmptyEntry(parent);
    Objective objective = parent.getObjective(DisplaySlot.SIDEBAR);

    objective.getScore(entry).setScore(line);

    Team team = parent.registerNewTeam("entry_" + line);
    team.addEntry(entry);
    sidebarUtils.setTeamData(sidebarUtils.randomEmptyEntry(parent), team);
  }

  @Override
  public void update(Scoreboard parent) {}

}
