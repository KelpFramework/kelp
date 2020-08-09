package de.pxav.kelp.core.particle.effect;

import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class ParticleEffect {

  protected boolean playOnce = true;
  protected int intervalInMillis = 200;
  protected int maxIterations = -1;
  protected AtomicInteger currentIterations = new AtomicInteger(0);

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

  public abstract void play(Collection<KelpPlayer> player);

  public abstract void play(KelpPlayer... player);

}
