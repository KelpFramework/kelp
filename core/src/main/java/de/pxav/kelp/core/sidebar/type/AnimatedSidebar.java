package de.pxav.kelp.core.sidebar.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;

/**
 * This is a specific type of sidebar which enables you
 * to animate titles. Those animations can be clustered, which means
 * that if you have the same update interval for different sidebars,
 * you can cluster them into a group like {@code lobby_scoreboard} for
 * example to reduce the total amount of schedulers required.
 *
 * Apart from that it is the same as {@link SimpleSidebar}, so you can
 * also hold normal sidebar components and update them, etc.
 *
 * @author pxav
 */
public class AnimatedSidebar extends KelpSidebar<AnimatedSidebar> {

  private TextAnimation title;
  private int titleAnimationInterval = 200;
  private String clusterId = null;

  private SidebarVersionTemplate sidebarVersionTemplate;

  public AnimatedSidebar(SidebarVersionTemplate sidebarVersionTemplate) {
    this.sidebarVersionTemplate = sidebarVersionTemplate;
  }

  public static AnimatedSidebar create() {
    return new AnimatedSidebar(
      KelpPlugin.getInjector().getInstance(SidebarVersionTemplate.class)
    );
  }

  /**
   * Sets the title animation to be displayed at the top of the sidebar.
   *
   * @param title The title animation you want to display.
   * @return Instance of the current component for more fluent builder design.
   */
  public AnimatedSidebar title(TextAnimation title) {
    this.title = title;
    return this;
  }

  /**
   * Sets the interval of the title animation. The given value is the period
   * in milliseconds it takes between each update to the next animation state.
   *
   * @param titleAnimationInterval The update interval in milliseconds.
   * @return Instance of the current component for more fluent builder design.
   */
  public AnimatedSidebar titleAnimationInterval(int titleAnimationInterval) {
    this.titleAnimationInterval = titleAnimationInterval;
    return this;
  }

  /**
   * Sets the cluster id for the current sidebar. This is an optional
   * step, but it is recommended doing that if your sidebar is displayed
   * to more than 2 or 3 players. It wraps all player sidebars into a
   * single scheduler to save server performance instead of creating an
   * individual scheduler for each player.
   *
   * If the given cluster does not exist, it will be created automatically by Kelp.
   *
   * @param clusterId The id of the cluster you want to add the sidebar to.
   * @return Instance of the current component for more fluent builder design.
   */
  public AnimatedSidebar clusterId(String clusterId) {
    this.clusterId = clusterId;
    return this;
  }

  /**
   * Gets the cluster id of the current scoreboard.
   *
   * @return The current cluster id. {@code null} if no cluster id has been set.
   */
  public String getClusterId() {
    return clusterId;
  }

  /**
   * Gets the title of the sidebar as a {@link TextAnimation}.
   *
   * @return The sidebar's title animation.
   */
  public TextAnimation getTitle() {
    return title;
  }

  /**
   * Gets the interval in which the sidebar's title is updated in milliseconds.
   *
   * @return The update interval of the sidebar's title.
   */
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

  /**
   * Performs a lazy update on the sidebar. A lazy update does not remove all entries/lines
   * from a scoreboard first, like it is done by {@link #update(KelpPlayer)}. It only used the
   * existing entries in the sidebar, which means that you cannot use it if you know that the amount
   * of lines in the sidebar might change with an update.
   *
   * However this update method is completely free from any flickering effects and it is
   * not as performance heavy as a normal update. So if you can, you should prefer this update
   * method over a normal update.
   *
   * @param player The player who should see the updated sidebar.
   */
  public void lazyUpdate(KelpPlayer player) {
    sidebarVersionTemplate.lazyUpdate(this, player);
  }

  @Override
  public void remove(KelpPlayer player) {

  }

}
