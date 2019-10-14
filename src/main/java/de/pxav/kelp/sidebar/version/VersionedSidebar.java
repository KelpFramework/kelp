package de.pxav.kelp.sidebar.version;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class VersionedSidebar {

  public abstract Scoreboard createScoreboard();

  public abstract Objective createObjective(Scoreboard parent,
                                            String identifier,
                                            String title);

  public abstract Score createScore(Objective parent,
                                    String content,
                                    int index);

  public abstract void openScoreboard(Scoreboard scoreboard, Player player);

  public abstract void closeScoreboard(Player player);

}
