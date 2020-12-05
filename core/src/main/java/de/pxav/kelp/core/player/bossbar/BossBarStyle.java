package de.pxav.kelp.core.player.bossbar;

import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.SinceKelpVersion;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum BossBarStyle {

  @SinceKelpVersion(KelpVersion.MC_1_8_0)
  SOLID,

  SEGMENTS_6,
  SEGMENTS_10,
  SEGMENTS_12,
  SEGMENTS_20;
  
}
