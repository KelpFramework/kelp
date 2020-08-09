package de.pxav.kelp.core.particle.effect;

import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ParticleLineEffect extends ParticleEffect {

  private ScheduledExecutorService scheduledExecutorService;
  private ExecutorService executorService;

  private ParticleType particleType;
  private Location firstPoint;
  private Location secondPoint;
  private double particleDensity;

  public ParticleLineEffect() {
    this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    this.executorService = Executors.newCachedThreadPool();
  }

  public ParticleLineEffect particleType(ParticleType particleType) {
    this.particleType = particleType;
    return this;
  }

  public ParticleLineEffect firstPoint(Location firstPoint) {
    this.firstPoint = firstPoint;
    return this;
  }

  public ParticleLineEffect secondPoint(Location secondPoint) {
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
  public void play(Collection<KelpPlayer> player) {
    if (this.playOnce) {
      this.executorService.execute(() -> this.playAnimationOnce(player));
      return;
    }

    scheduledExecutorService.scheduleAtFixedRate(() -> {
      if (currentIterations.get() == maxIterations) {
        scheduledExecutorService.shutdownNow();
        return;
      }
      System.out.println("played particle effect");
      currentIterations.set(currentIterations.get() + 1);
      playAnimationOnce(player);
    }, 0, intervalInMillis, TimeUnit.MILLISECONDS);

  }

  @Override
  public void play(KelpPlayer... player) {
    play(Arrays.asList(player));
  }

  private void playAnimationOnce(Collection<KelpPlayer> player) {
    Location firstPointBackup = firstPoint.clone();

    Vector line = secondPoint.clone().toVector().subtract(firstPointBackup.toVector());
    double length = line.length();
    line.normalize();

    double x = line.getX();
    double y = line.getY();
    double z = line.getZ();

    for (double d = 0.5D; d < length; d += particleDensity) {
      firstPointBackup.add(x * d, y * d, z * d);

      for (KelpPlayer kelpPlayer : player) {
        kelpPlayer.spawnParticle(particleType, true, firstPointBackup, 0, 0, 0, 1, 0, null);
      }

      firstPointBackup.subtract(x * d, y * d, z * d);
    }
  }

}
