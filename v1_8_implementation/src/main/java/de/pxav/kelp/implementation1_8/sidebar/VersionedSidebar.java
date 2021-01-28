package de.pxav.kelp.implementation1_8.sidebar;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.event.kelpevent.sidebar.KelpSidebarRenderEvent;
import de.pxav.kelp.core.event.kelpevent.sidebar.KelpSidebarUpdateEvent;
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

  /**
   * Renders the sidebar to a specific player. This means it displays
   * it for the first time (so only use this method if the player does
   * not already see this sidebar to avoid flicker effects or similar behaviour).
   *
   * @param sidebar     The sidebar to render to the given player.
   * @param kelpPlayer  The player to render the sidebar to.
   */
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
    Bukkit.getPluginManager().callEvent(new KelpSidebarRenderEvent(kelpPlayer, sidebar));

    // apply all contents to the sidebar.
    this.updateSidebar(sidebar, kelpPlayer);

    if (sidebar.getClass().isAssignableFrom(AnimatedSidebar.class)) {
      // if the sidebar is animated start the animation schedulers in
      // the sidebar repository and set the first animation state as default.
      AnimatedSidebar animatedSidebar = (AnimatedSidebar) sidebar;
      objective.setDisplayName(animatedSidebar.getTitle().states().get(0));
      sidebarRepository.addAnimatedSidebar(animatedSidebar, kelpPlayer);
    } else if (sidebar.getClass().isAssignableFrom(SimpleSidebar.class)) {
      // if the sidebar is a simple sidebar, simply set the default title.
      SimpleSidebar simpleSidebar = (SimpleSidebar) sidebar;
      objective.setDisplayName(simpleSidebar.getTitle().get());
    }

    kelpPlayer.setSidebarInternally(sidebar);

  }

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

    Bukkit.getPluginManager().callEvent(new KelpSidebarUpdateEvent(kelpPlayer, sidebar, true));
  }

  /**
   * Performs a full-update on the given sidebar, which means that all existing
   * contents are removed and then new contents are applied. This method is safe
   * against changing amounts of lines.
   *
   * @param sidebar       The sidebar you want to update.
   * @param kelpPlayer    The player who should see the updates.
   */
  @Override
  public void updateSidebar(KelpSidebar sidebar, KelpPlayer kelpPlayer) {
    Scoreboard scoreboard = kelpPlayer.getBukkitPlayer().getScoreboard();
    Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

    if (objective == null) {
      renderSidebar(sidebar, kelpPlayer);
      return;
    }

    // remove all old entries
    for (String entry : scoreboard.getEntries()) {
      Score score = objective.getScore(entry);

      if (score == null) {
        continue;
      }

      scoreboard.resetScores(entry);
    }

    // unregister all teams
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

    Bukkit.getPluginManager().callEvent(new KelpSidebarUpdateEvent(kelpPlayer, sidebar, false));
  }

}
