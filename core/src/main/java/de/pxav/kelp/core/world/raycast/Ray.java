package de.pxav.kelp.core.world.raycast;

import de.pxav.kelp.core.particle.visualize.ParticleVisualizable;
import de.pxav.kelp.core.particle.visualize.ParticleVisualizerProfile;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.Vector3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Ray<T extends Ray<?>> implements ParticleVisualizable {

  protected KelpLocation startLocation;
  protected Vector3 direction;

  protected double maxLength = 20.0d;
  protected int maxCollisions = 1;

  protected KelpPlayer[] visualizeTo;
  protected boolean visualize;
  protected ParticleVisualizerProfile visualizerProfile;

  public T maxLength(double maxLength) {
    this.maxLength = maxLength;
    return (T) this;
  }

  public T startLocation(KelpLocation startLocation) {
    this.startLocation = startLocation;
    return (T) this;
  }

  public T direction(Vector3 direction) {
    this.direction = direction;
    return (T) this;
  }

  public T maxCollisions(int maxCollisions) {
    this.maxCollisions = maxCollisions;
    return (T) this;
  }

  @NotNull
  public abstract List<RaycastHit> compute();

  public KelpLocation getStartLocation() {
    return startLocation;
  }

  public Vector3 getDirection() {
    return direction;
  }

  public double getMaxLength() {
    return maxLength;
  }

  @Override
  public void visualize(KelpPlayer player, ParticleVisualizerProfile visualizerProfile) {
    this.visualize = true;
    this.visualizeTo = new KelpPlayer[] {player};
    this.visualizerProfile = visualizerProfile;
  }

}
