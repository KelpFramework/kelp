package de.pxav.kelp.implementation1_8.sidebar;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import de.pxav.kelp.core.sidebar.SidebarUtils;
import de.pxav.kelp.core.sidebar.component.SidebarComponent;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import de.pxav.kelp.core.sidebar.type.SimpleSidebar;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import javax.inject.Inject;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedSidebar extends SidebarVersionTemplate {

  private SidebarUtils sidebarUtils;
  private KelpLogger logger;
  private SidebarRepository sidebarRepository;

  @Inject
  public VersionedSidebar(SidebarUtils sidebarUtils, KelpLogger logger, SidebarRepository sidebarRepository) {
    this.sidebarUtils = sidebarUtils;
    this.logger = logger;
    this.sidebarRepository = sidebarRepository;
  }

  @Override
  public void renderSidebar(KelpSidebar sidebar, KelpPlayer kelpPlayer) {
    Player player = kelpPlayer.getBukkitPlayer();
    Scoreboard scoreboard;
    Objective objective;

    if (kelpPlayer.hasScoreboard()) {
      scoreboard = player.getScoreboard();
    } else {
      scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    if (scoreboard.getObjective(DisplaySlot.SIDEBAR) == null) {
      objective = scoreboard.registerNewObjective("kelpObj", "dummy");
    } else {
      objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
    }
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    player.setScoreboard(scoreboard);
    this.updateSidebar(sidebar, kelpPlayer);

    if (sidebar.getClass().isAssignableFrom(AnimatedSidebar.class)) {
      AnimatedSidebar animatedSidebar = (AnimatedSidebar) sidebar;
      objective.setDisplayName(animatedSidebar.getTitle().states().get(0));
      sidebarRepository.addAnimatedSidebar(animatedSidebar, kelpPlayer);
    } else if (sidebar.getClass().isAssignableFrom(SimpleSidebar.class)) {
      SimpleSidebar simpleSidebar = (SimpleSidebar) sidebar;
      objective.setDisplayName(simpleSidebar.getTitle().get());
    }

  }

  @Override
  public void lazyUpdate(KelpSidebar sidebar, KelpPlayer kelpPlayer) {
    Scoreboard scoreboard = kelpPlayer.getBukkitPlayer().getScoreboard();

    for (Object object : sidebar.getComponents()) {
      if (!(object instanceof SidebarComponent)) {
        continue;
      }

      SidebarComponent component = (SidebarComponent) object;

      component.render().forEach((line, text) -> {
        String teamName = "entry_" + line;
        Team team = scoreboard.getTeam(teamName);

        if (team == null) {
          logger.log(LogLevel.ERROR, "Cannot update component at score " + line + ", "
            + "because there is no entry assigned to this score. Are you sure 'lazyUpdate' " +
            "is the appropriate updating method for your case?");
          return;
        }

        sidebarUtils.setTeamData(text, team);
      });
    }
  }

  @Override
  public void updateSidebar(KelpSidebar sidebar, KelpPlayer kelpPlayer) {
    Scoreboard scoreboard = kelpPlayer.getBukkitPlayer().getScoreboard();
    Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

    if (objective == null) {
      renderSidebar(sidebar, kelpPlayer);
      return;
    }

    for (String entry : scoreboard.getEntries()) {
      Score score = objective.getScore(entry);

      if (score == null) {
        continue;
      }

      scoreboard.resetScores(entry);
    }

    scoreboard.getTeams().forEach(Team::unregister);

    for (Object object : sidebar.getComponents()) {
      if (!(object instanceof SidebarComponent)) {
        continue;
      }

      SidebarComponent component = (SidebarComponent) object;

      component.render().forEach((line, text) -> {
        String entry = sidebarUtils.randomEmptyEntry(scoreboard);
        objective.getScore(entry).setScore(line);
        Team team = scoreboard.registerNewTeam("entry_" + line);
        team.addEntry(entry);

        sidebarUtils.setTeamData(text, team);
      });

    }
  }

}
