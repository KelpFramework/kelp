package de.pxav.kelp.core.event.kelpevent.region;

import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.region.KelpRegion;
import org.bukkit.event.HandlerList;

/**
 * This event is triggered when a player leaves a {@link KelpRegion}
 * that has listeners enabled.
 *
 * The opposite of this event would be {@link PlayerEnterRegionEvent}
 *
 * @author pxav
 */
public class PlayerLeaveRegionEvent extends KelpRegionEvent {

  private static final HandlerList handlers = new HandlerList();

  private KelpPlayer player;

  public PlayerLeaveRegionEvent(KelpRegion region, KelpPlayer player) {
    super(region);
    this.player = player;
  }

  /**
   * Gets the player who left the region.
   *
   * @return The player who left the region.
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
