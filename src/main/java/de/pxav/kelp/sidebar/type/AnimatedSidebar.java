package de.pxav.kelp.sidebar.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.animation.TextAnimation;
import de.pxav.kelp.sidebar.component.SimpleSidebarComponent;
import de.pxav.kelp.sidebar.component.SwitchingSidebarComponent;
import de.pxav.kelp.sidebar.version.VersionedSidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class AnimatedSidebar extends KelpSidebar {

  private TextAnimation titleAnimation;
  private List<SimpleSidebarComponent> simpleComponents;
  private List<SwitchingSidebarComponent> switchingComponents;

  private ScoreboardManager scoreboardManager;
  private VersionedSidebar versionedSidebar;

  AnimatedSidebar(VersionedSidebar versionedSidebar) {
    this.scoreboardManager = Bukkit.getScoreboardManager();
    this.versionedSidebar = versionedSidebar;
    this.simpleComponents = Lists.newArrayList();
    this.switchingComponents = Lists.newArrayList();
  }

  public AnimatedSidebar addComponent(SimpleSidebarComponent components) {
    this.simpleComponents.add(components);
    return this;
  }


  public AnimatedSidebar addComponent(SwitchingSidebarComponent components) {
    this.switchingComponents.add(components);
    return this;
  }

  public AnimatedSidebar withTitle(TextAnimation titleAnimation) {
    this.titleAnimation = titleAnimation;
    return this;
  }

  public void updateTitleOnly(Player player, int state) {
    Scoreboard scoreboard = player.getScoreboard();
    scoreboard.getObjective("main").setDisplayName(titleAnimation.states().get(state));
  }

  @Override
  public Scoreboard renderSidebar(Player player) {
    Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

    scoreboard.getTeams().forEach(current -> {
      if (current.getName().startsWith("entry_")) {
        current.unregister();
      }
    });

    if (scoreboard.getObjective("main") == null) {
      versionedSidebar.createObjective(scoreboard, "main", titleAnimation.states().get(0));
    }

    for (SimpleSidebarComponent component : this.simpleComponents) {
      component.render(scoreboard);
    }
    return scoreboard;
  }

  @Override
  public Scoreboard renderAndOpenSidebar(Player player) {
    Scoreboard scoreboard = renderSidebar(player);
    player.setScoreboard(scoreboard);
    return scoreboard;
  }

  @Override
  public Scoreboard update(Player player) {
    Scoreboard scoreboard = player.getScoreboard();

    for (SimpleSidebarComponent component : this.simpleComponents) {
      component.update(scoreboard);
    }
    return scoreboard;
  }

  @Override
  public void hideSidebar(Player player) {

  }

  public int maxStates() {
    return this.titleAnimation == null ? 0 : this.titleAnimation.states().size();
  }

}
