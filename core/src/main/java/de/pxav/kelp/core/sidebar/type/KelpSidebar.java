package de.pxav.kelp.core.sidebar.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.component.SidebarComponent;

import java.util.Collection;

/**
 * This class represents a template for Kelp sidebars.
 *
 *
 * @param <T> The type of the current sidebar. This is needed to keep
 *            up a more fluent builder design as methods such as {@link #addComponent(SidebarComponent)}
 *            do not return a general sidebar anymore but the specific type of sidebar you are
 *            currently working with.
 * @author pxav
 */
public abstract class KelpSidebar<T extends KelpSidebar> {

  protected Collection<SidebarComponent> components = Lists.newArrayList();

  /**
   * Adds a new component to the sidebar. When you add it,
   * it should already contain all its properties.
   *
   * @param component The component to be added.
   * @return
   */
  public T addComponent(SidebarComponent component) {
    this.components.add(component);
    return (T) this;
  }

  /**
   * Removes a specific component from the sidebar permanently.
   * It won't be displayed anymore until you add it again or
   * display another component instead.
   *
   * @param component The component to be removed.
   * @return
   */
  public T removeComponent(SidebarComponent component) {
    this.components.remove(component);
    return (T) this;
  }

  /**
   * Removes all existing components of the sidebar permanently.
   *
   * @return
   */
  public T clearComponents() {
    this.components.clear();
    return (T) this;
  }

  /**
   * Gets a collection of all components the sidebar is currently holding.
   *
   * @return A collection containing all components.
   */
  public Collection<SidebarComponent> getComponents() {
    return this.components;
  }

  /**
   * Renders the sidebar to a specific player. This means it displays
   * it for the first time (so only use this method if the player does
   * not already see this sidebar to avoid flicker effects or similar behaviour).
   *
   * @param player The player to render the sidebar to.
   */
  public abstract void render(KelpPlayer player);

  /**
   * Updates all components of the sidebar. That means that all existing entries/lines
   * are removed and the components are added to an empty scoreboard again. So you
   * should only use this method if you know that the amount of lines will change or
   * certain components might (dis)appear after an update. If you do a simple update,
   * that only updates some text values you might want to use a {@code lazyUpdate} as this
   * is safe from any flicker effects unlike this method, which can cause flicker on heavily
   * loaded servers.
   *
   * @param player The player who should see the updated sidebar.
   */
  public abstract void update(KelpPlayer player);

  /**
   * Removes the sidebar from the given player, which means that the player
   * won't be able to see it anymore.
   *
   * @param player The player you want to hide the sidebar from.
   */
  public abstract void remove(KelpPlayer player);

}
