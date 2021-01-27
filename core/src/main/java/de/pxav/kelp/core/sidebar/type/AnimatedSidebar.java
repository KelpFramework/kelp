package de.pxav.kelp.core.sidebar.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.animation.StaticTextAnimation;
import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.SidebarUtils;
import de.pxav.kelp.core.sidebar.component.SidebarComponent;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class AnimatedSidebar extends KelpSidebar<AnimatedSidebar> {

  private TextAnimation title;
  private int titleAnimationInterval = 200;
  private String clusterId = null;

  private SidebarUtils sidebarUtils;
  private SidebarVersionTemplate sidebarVersionTemplate;

  public AnimatedSidebar(SidebarUtils sidebarUtils, SidebarVersionTemplate sidebarVersionTemplate) {
    this.sidebarUtils = sidebarUtils;
    this.sidebarVersionTemplate = sidebarVersionTemplate;
  }

  public static AnimatedSidebar create() {
    return new AnimatedSidebar(
      KelpPlugin.getInjector().getInstance(SidebarUtils.class),
      KelpPlugin.getInjector().getInstance(SidebarVersionTemplate.class)
    );
  }

  public AnimatedSidebar title(TextAnimation title) {
    this.title = title;
    return this;
  }

  public AnimatedSidebar titleAnimationInterval(int titleAnimationInterval) {
    this.titleAnimationInterval = titleAnimationInterval;
    return this;
  }

  public AnimatedSidebar clusterId(String clusterId) {
    this.clusterId = clusterId;
    return this;
  }

  public String getClusterId() {
    return clusterId;
  }

  public TextAnimation getTitle() {
    return title;
  }

  public int getTitleAnimationInterval() {
    return titleAnimationInterval;
  }

  @Override
  public void render(KelpPlayer player) {
    sidebarVersionTemplate.renderSidebar(this, player);
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
