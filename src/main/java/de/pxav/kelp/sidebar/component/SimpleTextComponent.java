package de.pxav.kelp.sidebar.component;

import de.pxav.kelp.logger.KelpLogger;
import de.pxav.kelp.logger.LogLevel;
import de.pxav.kelp.sidebar.SidebarUtils;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * This scoreboard component is used to display
 * custom text on your sidebar.
 *
 * @author pxav
 */
public class SimpleTextComponent implements SimpleSidebarComponent {

  private String text;
  private int line;

  private SidebarUtils sidebarUtils;
  private KelpLogger logger;

  public SimpleTextComponent(SidebarUtils sidebarUtils, KelpLogger logger) {
    this.sidebarUtils = sidebarUtils;
    this.logger = logger;
  }

  public SimpleTextComponent text(String text) {
    this.text = text;
    return this;
  }

  public SimpleTextComponent line(int line) {
    this.line = line;
    return this;
  }

  /**
   * This method creates and renders the component
   * to the given scoreboard.
   * So it will create a new team and set the content.
   *
   * @param parent The scoreboard you want to render the component on.
   */
  @Override
  public void render(Scoreboard parent) {
    String entry = sidebarUtils.randomEmptyEntry(parent);
    Objective objective = parent.getObjective(DisplaySlot.SIDEBAR);

    objective.getScore(entry).setScore(line);

    Team team = parent.registerNewTeam("entry_" + line);
    team.addEntry(entry);
    update(parent);
  }

  /**
   * Updates the component, which means that no
   * new team will be created, but the existing one
   * will be updated to avoid flickering.
   *
   * @param parent The scoreboard on which the component should be rendered.
   */
  @Override
  public void update(Scoreboard parent) {
    String teamName = "entry_" + line;
    Team team = parent.getTeam(teamName);

    if (team == null) {
      logger.log(LogLevel.ERROR, "Cannot update component at score " + line + ", "
              + "because there is no entry assigned to this score.");
      return;
    }
    sidebarUtils.setTeamData(text, team);
  }

}
