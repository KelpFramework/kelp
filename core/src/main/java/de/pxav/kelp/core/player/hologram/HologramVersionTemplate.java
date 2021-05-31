package de.pxav.kelp.core.player.hologram;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;

@KelpVersionTemplate
public abstract class HologramVersionTemplate {

  public abstract void spawnHologram(KelpHologram hologram);

  public abstract void despawnHologram(KelpHologram hologram);

  public abstract void updateHologram(KelpHologram hologram);

}
