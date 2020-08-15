package de.pxav.kelp.core.particle.type;

import de.pxav.kelp.core.version.KelpVersion;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum ParticleType {

  FLAME(KelpVersion.MC_1_8_0),
  HEART(KelpVersion.MC_1_8_0),
  ;

  private KelpVersion since;

  ParticleType(KelpVersion since) {
    this.since = since;
  }

  public KelpVersion since() {
    return since;
  }

  @Override
  public String toString() {
    return super.toString();
  }

}
