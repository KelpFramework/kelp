package de.pxav.kelp.core.event.kelpevent.sidebar;

import de.pxav.kelp.core.event.kelpevent.KelpPlayerEvent;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import org.bukkit.event.HandlerList;

/**
 * This event is triggered when a {@link KelpSidebar} is rendered to a player,
 * which means that the sidebar is displayed to them for the first time.
 *
 * @author pxav
 */
public class KelpSidebarRenderEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();

  private KelpSidebar sidebar;

  public KelpSidebarRenderEvent(KelpPlayer who, KelpSidebar sidebar) {
    super(who);
    this.sidebar = sidebar;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  /**
   * Gets the instance of the sidebar that has been rendered to the player.
   *
   * @return The current scoreboard object.
   */
  public KelpSidebar getSidebar() {
    return sidebar;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

}
