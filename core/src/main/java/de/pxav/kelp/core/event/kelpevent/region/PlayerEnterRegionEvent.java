package de.pxav.kelp.core.event.kelpevent.region;

import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.region.KelpRegion;
import org.bukkit.event.HandlerList;

/**
 * This event is triggered when a player enters a {@link KelpRegion}
 * that has listeners enabled.
 *
 * The opposite of this event would be {@link PlayerLeaveRegionEvent}
 *
 * @author pxav
 */
public class PlayerEnterRegionEvent extends KelpRegionEvent {

  private static final HandlerList handlers = new HandlerList();

  // player who entered the region
  private KelpPlayer player;

  public PlayerEnterRegionEvent(KelpRegion region, KelpPlayer player) {
    super(region);
    this.player = player;
  }

  /**
   * Gets the player who entered the region.
   *
   * @return The player who entered the region.
   */
  public KelpPlayer getPlayer() {
    return player;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
