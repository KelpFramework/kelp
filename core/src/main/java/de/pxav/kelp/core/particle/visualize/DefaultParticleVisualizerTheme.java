package de.pxav.kelp.core.particle.visualize;

import de.pxav.kelp.core.particle.type.ParticleType;

public class DefaultParticleVisualizerTheme implements ParticleVisualizerProfile {
  @Override
  public ParticleType primary() {
    return ParticleType.FLAME;
  }

  @Override
  public ParticleType secondary() {
    return ParticleType.HEART;
  }

  @Override
  public ParticleType tertiary() {
    return ParticleType.FLAME;
  }
}
