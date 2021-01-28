package de.pxav.kelp.core.event.kelpevent.sidebar;

import de.pxav.kelp.core.event.kelpevent.KelpPlayerEvent;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.event.HandlerList;

/**
 * This event is triggered when any sidebar is removed from a player
 * and the corresponding animation schedulers, etc. are stopped. It
 * does only handle {@link de.pxav.kelp.core.sidebar.type.KelpSidebar<>} ano
 * no default bukkit scoreboards.
 *
 * @author pxav
 */
public class KelpSidebarRemoveEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();

  public KelpSidebarRemoveEvent(KelpPlayer who) {
    super(who);
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

}
