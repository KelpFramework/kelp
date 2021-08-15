package de.pxav.kelp.core.event.kelpevent.hologram;

import de.pxav.kelp.core.player.hologram.KelpHologram;
import org.bukkit.event.Event;

public abstract class HologramEvent extends Event {

  private KelpHologram hologram;

  public HologramEvent(KelpHologram hologram) {
    this.hologram = hologram;
  }

  public KelpHologram getHologram() {
    return hologram;
  }

}
