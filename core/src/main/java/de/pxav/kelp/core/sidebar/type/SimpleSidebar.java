package de.pxav.kelp.core.sidebar.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.SidebarUtils;
import de.pxav.kelp.core.sidebar.component.SidebarComponentFactory;
import de.pxav.kelp.core.sidebar.component.SimpleSidebarComponentOld;
import de.pxav.kelp.core.sidebar.version.SidebarUpdaterVersionTemplate;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;
import de.pxav.kelp.core.logger.KelpLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * This class represents the most simple type
 * of a sidebar implementation.
 *
 * It can basically hold simple components without animation
 * as well as a static title.
 *
 * @author pxav
 */
public class SimpleSidebar extends KelpSidebar<SimpleSidebar> {

  private Supplier<String> title;

  private SidebarVersionTemplate sidebarVersionTemplate;
  private SidebarUpdaterVersionTemplate updaterVersionTemplate;

  public SimpleSidebar(SidebarVersionTemplate sidebarVersionTemplate,
                       SidebarUpdaterVersionTemplate updaterVersionTemplate) {
    this.sidebarVersionTemplate = sidebarVersionTemplate;
    this.updaterVersionTemplate = updaterVersionTemplate;
  }

  public static SimpleSidebar create() {
    return new SimpleSidebar(
      KelpPlugin.getInjector().getInstance(SidebarVersionTemplate.class),
      KelpPlugin.getInjector().getInstance(SidebarUpdaterVersionTemplate.class)
    );
  }

  public SimpleSidebar title(Supplier<String> title) {
    this.title = title;
    return this;
  }

  public SimpleSidebar staticTitle(String title) {
    this.title = () -> title;
    return this;
  }

  public Supplier<String> getTitle() {
    return title;
  }

  @Override
  public void render(KelpPlayer player) {
    sidebarVersionTemplate.renderSidebar(this, player);
  }

  public void updateTitleOnly(KelpPlayer player) {
    updaterVersionTemplate.updateTitleOnly(title.get(), player);
  }

  @Override
  public void update(KelpPlayer player) {
    sidebarVersionTemplate.updateSidebar(this, player);
  }

  public void lazyUpdate(KelpPlayer player) {
    sidebarVersionTemplate.lazyUpdate(this, player);
  }

  @Override
  public void remove(KelpPlayer player) {

  }

}
