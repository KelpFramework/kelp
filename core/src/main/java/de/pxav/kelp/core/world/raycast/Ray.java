package de.pxav.kelp.core.world.raycast;

import de.pxav.kelp.core.particle.visualize.ParticleVisualizable;
import de.pxav.kelp.core.particle.visualize.ParticleVisualizerProfile;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.Vector3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Ray implements ParticleVisualizable {

  protected KelpLocation startLocation;
  protected Vector3 direction;

  protected double maxLength = 20.0d;
  protected int maxCollisions = 1;

  protected KelpPlayer[] visualizeTo;
  protected boolean visualize;
  protected ParticleVisualizerProfile visualizerProfile;

  public Ray maxLength(double maxLength) {
    this.maxLength = maxLength;
    return this;
  }

  public Ray startLocation(KelpLocation startLocation) {
    this.startLocation = startLocation;
    return this;
  }

  public Ray direction(Vector3 direction) {
    this.direction = direction;
    return this;
  }

  public Ray maxCollisions(int maxCollisions) {
    this.maxCollisions = maxCollisions;
    return this;
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
