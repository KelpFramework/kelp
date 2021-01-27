package de.pxav.kelp.core.sidebar.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.component.SidebarComponent;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class SidebarVersionTemplate {

  public abstract void renderSidebar(KelpSidebar sidebar, KelpPlayer player);

  public abstract void lazyUpdate(KelpSidebar sidebar, KelpPlayer kelpPlayer);

  public abstract void updateSidebar(KelpSidebar sidebar, KelpPlayer player);

}
