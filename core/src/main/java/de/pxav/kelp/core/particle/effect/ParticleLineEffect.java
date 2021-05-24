package de.pxav.kelp.core.particle.effect;

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
public class ParticleLineEffect extends ParticleEffect {

  private ParticleType particleType;
  private KelpLocation firstPoint;
  private KelpLocation secondPoint;
  private double particleDensity;

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

  public ParticleLineEffect changeLocationBy(double x, double y, double z) {

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
        kelpPlayer.spawnParticle(particleType, firstPointBackup, 1, 0);
      }

      firstPointBackup.subtract(x * d, y * d, z * d);
    }
  }

}
