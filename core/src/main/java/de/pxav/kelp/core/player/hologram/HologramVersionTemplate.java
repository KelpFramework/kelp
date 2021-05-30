package de.pxav.kelp.core.player.hologram;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;

@KelpVersionTemplate
public abstract class HologramVersionTemplate {

  public abstract void spawnHologram(KelpPlayer player, KelpHologram hologram);

  public abstract void despawnHologram(KelpPlayer player, KelpHologram hologram);

  public abstract void updateHologram(KelpPlayer player, KelpHologram hologram);

}
