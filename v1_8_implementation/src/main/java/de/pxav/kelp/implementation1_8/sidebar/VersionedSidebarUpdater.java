package de.pxav.kelp.implementation1_8.sidebar;

import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.version.SidebarUpdaterVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@Versioned
public class VersionedSidebarUpdater extends SidebarUpdaterVersionTemplate {

  /**
   * Updates the title of sidebar of the given player to the given string.
   * @param to          The title to update to.
   * @param kelpPlayer  The player whose sidebar's title should be updated.
   */
  @Override
  public void updateTitleOnly(String to, KelpPlayer kelpPlayer) {
    Scoreboard scoreboard = kelpPlayer.getBukkitPlayer().getScoreboard();
    Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

    objective.setDisplayName(to);
  }

}
