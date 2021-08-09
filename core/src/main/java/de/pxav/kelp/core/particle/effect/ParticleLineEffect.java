package de.pxav.kelp.core.particle.effect;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.util.Vector;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ParticleLineEffect extends ParticleEffect implements Cloneable {

  private ParticleType particleType = ParticleType.FLAME;
  private KelpLocation firstPoint;
  private KelpLocation secondPoint;
  private double particleDensity = 0.1d;

  public static ParticleLineEffect create() {
    return new ParticleLineEffect(KelpPlugin.getInjector().getInstance(ParticleEffectRepository.class));
  }

  ParticleLineEffect(ParticleEffectRepository particleEffectRepository) {
    super(particleEffectRepository);
  }

  public ParticleLineEffect particleType(ParticleType particleType) {
    this.particleType = particleType;
    return this;
  }

  public ParticleLineEffect firstPoint(KelpLocation firstPoint) {
    this.firstPoint = firstPoint;
    return this;
  }

  public ParticleLineEffect secondPoint(KelpLocation secondPoint) {
    this.secondPoint = secondPoint;
    return this;
  }

  public ParticleLineEffect particleDensity(double particleDensity) {
    this.particleDensity = particleDensity;
    return this;
  }

  @Override
  protected void playAnimationOnce(Collection<KelpPlayer> player) {
    KelpLocation firstPointBackup = firstPoint.clone();

    Vector line = secondPoint.clone().toVector().subtract(firstPointBackup.toVector());
    double length = line.length();
    line.normalize();

    double x = line.getX();
    double y = line.getY();
    double z = line.getZ();

    for (double d = 0.5D; d < length; d += particleDensity) {
      firstPointBackup.add(x * d, y * d, z * d);

      for (KelpPlayer kelpPlayer : player) {
        kelpPlayer.spawnParticle(particleType, firstPointBackup);
      }

      firstPointBackup.subtract(x * d, y * d, z * d);
    }
  }

}
