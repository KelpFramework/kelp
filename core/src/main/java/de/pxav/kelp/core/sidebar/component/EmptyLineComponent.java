package de.pxav.kelp.core.sidebar.component;

import de.pxav.kelp.core.sidebar.SidebarUtils;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * This sidebar component can be used to generate empty lines.
 * You could also write them manually as a {@code SimpleTextComponent},
 * but this way makes it more maintainable, because you don't
 * have to choose color codes manually:
 *
 * To display an empty line in a scoreboard you have to use invisible
 * characters like formatting codes (§6, §l) or spaces.
 * But you may not use one line twice, so when you use '§6§l' for
 * one line, you have to use §7§l for the next one and so on.
 *
 * This makes it more difficult to add new empty lines which
 * is why it's recommended to simply use this component.
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
