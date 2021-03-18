package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.world.region.KelpRegion;
import org.bukkit.event.Event;

public abstract class KelpRegionEvent extends Event {

  protected KelpRegion region;

  public KelpRegionEvent(KelpRegion region) {
    this.region = region;
  }

  public KelpRegion getRegion() {
    return region;
  }

}
