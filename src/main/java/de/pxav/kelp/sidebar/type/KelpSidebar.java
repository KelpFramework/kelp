package de.pxav.kelp.sidebar.type;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class KelpSidebar {

  public abstract Scoreboard renderSidebar(Player player);

  public abstract Scoreboard renderAndOpenSidebar(Player player);

  public abstract Scoreboard update(Player player);

  public abstract void hideSidebar(Player player);

}
