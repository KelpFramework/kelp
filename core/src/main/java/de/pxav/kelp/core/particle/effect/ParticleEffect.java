package de.pxav.kelp.core.particle.effect;

import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class ParticleEffect {

  protected ParticleEffectRepository particleEffectRepository;
  protected ExecutorService executorService;
  protected ScheduledExecutorService scheduledExecutorService;

  protected boolean playOnce = true;
  protected int intervalInMillis = 200;
  protected int maxIterations = -1;
  protected AtomicInteger currentIterations = new AtomicInteger(0);

  ParticleEffect(ParticleEffectRepository particleEffectRepository) {
    this.particleEffectRepository = particleEffectRepository;
    this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    this.executorService = Executors.newCachedThreadPool();
  }

  public ParticleEffect maxIterations(int maxIntervals) {
    this.maxIterations = maxIntervals;
    return this;
  }

  public ParticleEffect intervalInMillis(int intervalInMillis) {
    this.intervalInMillis = intervalInMillis;
    return this;
  }

  public ParticleEffect playOnce(boolean playOnce) {
    this.playOnce = playOnce;
    return this;
  }

  public void play(Collection<KelpPlayer> players) {
    if (this.playOnce) {
      this.executorService.execute(() -> this.playAnimationOnce(players));
      return;
    }

    scheduledExecutorService.scheduleAtFixedRate(() -> {
      if (currentIterations.get() == maxIterations) {
        scheduledExecutorService.shutdownNow();
        return;
      }
      currentIterations.set(currentIterations.get() + 1);
      playAnimationOnce(players);
    }, 0, intervalInMillis, TimeUnit.MILLISECONDS);

    particleEffectRepository.addTimer(players, scheduledExecutorService);
  }

  public void play(KelpPlayer... player) {
    play(Arrays.asList(player));
  }

  protected abstract void playAnimationOnce(Collection<KelpPlayer> players);

}
