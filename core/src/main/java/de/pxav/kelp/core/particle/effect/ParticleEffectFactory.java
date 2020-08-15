package de.pxav.kelp.core.particle.effect;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class ParticleEffectFactory {

  private ParticleEffectRepository particleEffectRepository;

  @Inject
  public ParticleEffectFactory(ParticleEffectRepository particleEffectRepository) {
    this.particleEffectRepository = particleEffectRepository;
  }

  public ParticleLineEffect newParticleLineEffect() {
    return new ParticleLineEffect(this.particleEffectRepository);
  }

}
