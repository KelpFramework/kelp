package de.pxav.kelp.core.event.kelpevent.region;

import de.pxav.kelp.core.world.region.KelpRegion;
import org.bukkit.event.Event;

/**
 * A region event is any event that is related to a specific {@link KelpRegion}.
 *
 * In most cases you first have to enable listeners for your region using
 * {@link KelpRegion#enableListeners()} to work with this region.
 *
 * @author pxav
 */
public abstract class KelpRegionEvent extends Event {

  // the region handled by this event
  protected KelpRegion region;

  public KelpRegionEvent(KelpRegion region) {
    this.region = region;
  }

  /**
   * Gets the region involved in the current event.
   *
   * @return The region involved in this event.
   */
  public KelpRegion getRegion() {
    return region;
  }

}
