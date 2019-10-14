package de.pxav.kelp.sidebar.component;

import org.bukkit.scoreboard.Scoreboard;

import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface SimpleSidebarComponent {

  void render(Scoreboard parent);

  void update(Scoreboard parent);

}
