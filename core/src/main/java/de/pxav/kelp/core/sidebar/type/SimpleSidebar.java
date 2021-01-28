package de.pxav.kelp.core.sidebar.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.version.SidebarUpdaterVersionTemplate;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;

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

  /**
   * Sets the title of the sidebar. This method takes a {@link Supplier}, which
   * allows you to create dynamic titles that can update every time you call
   * {@link #updateTitleOnly(KelpPlayer)}.
   *
   * If you rather want a static title, choose {@link #staticTitle(String)}
   *
   * @param title The title do display at the top of the sidebar.
   * @return Instance of the current component for more fluent builder design.
   */
  public SimpleSidebar title(Supplier<String> title) {
    this.title = title;
    return this;
  }

  /**
   * Sets the title of the sidebar. This method takes a normal {@code String}, which
   * means that the title is static and won't change if you call {@link #updateTitleOnly(KelpPlayer)}
   *
   * If you rather want a dynamic title, choose {@link #title(Supplier)} instead.
   *
   * @param title The title do display at the top of the sidebar.
   * @return Instance of the current component for more fluent builder design.
   */
  public SimpleSidebar staticTitle(String title) {
    this.title = () -> title;
    return this;
  }

  /**
   * Gets the {@link Supplier} holding the current title - no matter if static or dynamic.
   * @return The current title of the sidebar.
   */
  public Supplier<String> getTitle() {
    return title;
  }

  @Override
  public void render(KelpPlayer player) {
    sidebarVersionTemplate.renderSidebar(this, player);
  }

  /**
   * Updates the title of the sidebar without loading to changing any
   * of its components.
   *
   * @param player The player you want to show the title update to.
   */
  public void updateTitleOnly(KelpPlayer player) {
    updaterVersionTemplate.updateTitleOnly(title.get(), player);
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
