package de.pxav.kelp.sidebar.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.logger.KelpLogger;
import de.pxav.kelp.sidebar.component.SidebarComponentFactory;
import de.pxav.kelp.sidebar.component.SimpleSidebarComponent;
import de.pxav.kelp.sidebar.version.VersionedSidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SimpleSidebar extends KelpSidebar {

  private String title;
  private Collection<SimpleSidebarComponent> components;

  private KelpLogger logger;
  private JavaPlugin javaPlugin;
  private VersionedSidebar versionedSidebar;
  private SidebarComponentFactory sidebarComponentFactory;

  private ScoreboardManager scoreboardManager;

  SimpleSidebar(KelpLogger logger,
                JavaPlugin javaPlugin,
                VersionedSidebar versionedSidebar,
                SidebarComponentFactory sidebarComponentFactory) {
    this.logger = logger;
    this.javaPlugin = javaPlugin;
    this.versionedSidebar = versionedSidebar;
    this.sidebarComponentFactory = sidebarComponentFactory;

    this.components = Lists.newArrayList();
    this.scoreboardManager = Bukkit.getScoreboardManager();
  }

  public SimpleSidebar withTitle(String title) {
    this.title = title;
    return this;
  }

  public SimpleSidebar insertLine(int line, String text) {
    this.components.add(sidebarComponentFactory.newSimpleTextComponent());
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
      versionedSidebar.createObjective(scoreboard, "main", this.title);
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
