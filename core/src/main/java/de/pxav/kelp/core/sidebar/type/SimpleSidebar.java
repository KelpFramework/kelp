package de.pxav.kelp.core.sidebar.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.sidebar.component.SidebarComponentFactory;
import de.pxav.kelp.core.sidebar.component.SimpleSidebarComponent;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;
import de.pxav.kelp.core.logger.KelpLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Collection;

/**
 * This class represents the most simple type
 * of a sidebar implementation.
 *
 * It can basically hold simple components without animation
 * as well as a static title.
 *
 * @author pxav
 */
public class SimpleSidebar extends KelpSidebar {

  // the title of the scoreboard (cannot be animated)
  private String title;

  // the components that should be displayed.
  private Collection<SimpleSidebarComponent> components;

  private KelpLogger logger;
  private JavaPlugin javaPlugin;
  private SidebarVersionTemplate sidebarVersionTemplate;
  private SidebarComponentFactory sidebarComponentFactory;

  private ScoreboardManager scoreboardManager;

  SimpleSidebar(KelpLogger logger,
                JavaPlugin javaPlugin,
                SidebarVersionTemplate sidebarVersionTemplate,
                SidebarComponentFactory sidebarComponentFactory) {
    this.logger = logger;
    this.javaPlugin = javaPlugin;
    this.sidebarVersionTemplate = sidebarVersionTemplate;
    this.sidebarComponentFactory = sidebarComponentFactory;

    this.components = Lists.newArrayList();
    this.scoreboardManager = Bukkit.getScoreboardManager();
  }

  public SimpleSidebar withTitle(String title) {
    this.title = title;
    return this;
  }

  public SimpleSidebar insertLine(int line, String text) {
    this.components.add(sidebarComponentFactory.simpleTextComponent(text, line));
    return this;
  }

  public SimpleSidebar addComponent(SimpleSidebarComponent sidebarComponent) {
    this.components.add(sidebarComponent);
    return this;
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
      sidebarVersionTemplate.createObjective(scoreboard, "main", this.title);
    }

    for (SimpleSidebarComponent component : this.components) {
      component.render(scoreboard);
    }

    return scoreboard;
  }

  @Override
  public Scoreboard renderAndOpenSidebar(Player player) {
    Scoreboard scoreboard = this.renderSidebar(player);
    player.setScoreboard(scoreboard);
    return scoreboard;
  }

  @Override
  public Scoreboard update(Player player) {
    Scoreboard scoreboard = player.getScoreboard();

    for (SimpleSidebarComponent component : this.components) {
      component.update(scoreboard);
    }

    return scoreboard;
  }

  @Override
  public void hideSidebar(Player player) {
    Bukkit.getScheduler().runTaskLater(javaPlugin, () -> {
      Scoreboard emptyScoreboard = scoreboardManager.getNewScoreboard();
      player.setScoreboard(emptyScoreboard);
    }, 1L);
  }

}
