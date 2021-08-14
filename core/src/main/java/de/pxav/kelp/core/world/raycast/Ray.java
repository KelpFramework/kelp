package de.pxav.kelp.core.world.raycast;

import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Ray {

  protected KelpLocation startLocation;
  protected Vector direction;

  protected double maxLength = 20.0d;
  protected int maxCollisions = 1;

  public Ray maxLength(double maxLength) {
    this.maxLength = maxLength;
    return this;
  }

  public Ray startLocation(KelpLocation startLocation) {
    this.startLocation = startLocation;
    return this;
  }

  public Ray direction(Vector direction) {
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

  public Vector getDirection() {
    return direction;
  }

  public double getMaxLength() {
    return maxLength;
  }

}