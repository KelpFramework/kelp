package de.pxav.kelp.sidebar.component;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface SwitchingSidebarComponent {

  SimpleSidebarComponent[] states();

  void render();

  void update(int state);

}
