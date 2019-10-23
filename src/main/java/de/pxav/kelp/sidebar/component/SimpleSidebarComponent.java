package de.pxav.kelp.sidebar.component;

import org.bukkit.scoreboard.Scoreboard;

/**
 * This interface is the template for all sidebar
 * components. It provides methods to render and update
 * the component.
 *
 * @author pxav
 */
public interface SimpleSidebarComponent {

  /**
   * This method creates and renders the component
   * to the given scoreboard.
   * So it will create a new team and set the content.
   *
   * @param parent The scoreboard you want to render the component on.
   */
  void render(Scoreboard parent);

  /**
   * Updates the component, which means that no
   * new team will be created, but the existing one
   * will be updated to avoid flickering.
   *
   * @param parent The scoreboard on which the component should be rendered.
   */
  void update(Scoreboard parent);

}
