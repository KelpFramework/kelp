package de.pxav.kelp.implementation1_8.particle;

import com.google.inject.Inject;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.particle.type.ParticleTypeRepository;
import de.pxav.kelp.core.particle.type.ParticleTypeVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.EnumParticle;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedParticleType extends ParticleTypeVersionTemplate {

  private ParticleTypeRepository particleTypeRepository;
  private KelpLogger logger;

  @Inject
  public VersionedParticleType(ParticleTypeRepository particleTypeRepository, KelpLogger logger) {
    this.particleTypeRepository = particleTypeRepository;
    this.logger = logger;
  }

  @Override
  public void defineDefaults() {
    long startedAt = System.currentTimeMillis();

    add(ParticleType.FLAME, EnumParticle.FLAME.toString());
    add(ParticleType.HEART, EnumParticle.HEART.toString());

    long elapsed = System.currentTimeMillis() - startedAt;
    logger.log("[VERSION-1.8] Successfully defined particle names (took " + elapsed + "ms)");
  }

  private void add(ParticleType particleType, String bukkitType) {
    particleTypeRepository.addParticleType(particleType, bukkitType);
  }

}
