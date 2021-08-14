package de.pxav.kelp.core.world.util;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import de.pxav.kelp.core.particle.visualize.ParticleVisualizable;
import de.pxav.kelp.core.particle.visualize.ParticleVisualizerProfile;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class Vector3 implements Cloneable, ParticleVisualizable {

  private static final double EPSILON = 1.0E-6D;
  protected double x = 0.0D;
  protected double y = 0.0D;
  protected double z = 0.0D;

  public static Vector3 create() {
    return new Vector3();
  }

  public static Vector3 create(double x, double y, double z) {
    return new Vector3().setX(x).setY(y).setZ(z);
  }

  public static Vector3 from(Vector bukkitVector) {
    return Vector3.create(bukkitVector.getX(), bukkitVector.getY(), bukkitVector.getZ());
  }

  public Vector3 add(Vector3 vector) {
    this.x += vector.x;
    this.y += vector.y;
    this.z += vector.z;
    return this;
  }

  public Vector3 subtract(Vector3 vector) {
    this.x -= vector.x;
    this.y -= vector.y;
    this.z -= vector.z;
    return this;
  }

  public Vector3 multiply(Vector3 vector) {
    this.x *= vector.x;
    this.y *= vector.y;
    this.z *= vector.z;
    return this;
  }

  public Vector3 divide(Vector3 vector) {
    this.x /= vector.x;
    this.y /= vector.y;
    this.z /= vector.z;
    return this;
  }

  public Vector3 copyFrom(Vector3 vector) {
    this.x = vector.x;
    this.y = vector.y;
    this.z = vector.z;
    return this;
  }

  public double magnitude() {
    return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
  }

  public double magnitudeSquared() {
    return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
  }

  public double distance(Vector3 to) {
    return Math.sqrt(
      NumberConversions.square(this.x - to.x)
      + NumberConversions.square(this.y - to.y)
      + NumberConversions.square(this.z - to.z)
    );
  }

  public double distanceSquared(Vector3 to) {
    return NumberConversions.square(this.x - to.x)
      + NumberConversions.square(this.y - to.y)
      + NumberConversions.square(this.z - to.z);
  }

  public float angle(Vector3 to) {
    double dot = Doubles.constrainToRange(this.dot(to) / (this.magnitude() * to.magnitude()), -1.0D, 1.0D);
    return (float)Math.acos(dot);
  }

  public Vector3 midpoint(Vector3 other) {
    this.x = (this.x + other.x) / 2.0D;
    this.y = (this.y + other.y) / 2.0D;
    this.z = (this.z + other.z) / 2.0D;
    return this;
  }

  public Vector3 getMidpoint(Vector3 other) {
    double x = (this.x + other.x) / 2.0D;
    double y = (this.y + other.y) / 2.0D;
    double z = (this.z + other.z) / 2.0D;
    return Vector3.create(x, y, z);
  }

  public Vector3 multiply(int m) {
    this.x *= m;
    this.y *= m;
    this.z *= m;
    return this;
  }

  public Vector3 multiply(double m) {
    this.x *= m;
    this.y *= m;
    this.z *= m;
    return this;
  }

  public Vector3 multiply(float m) {
    this.x *= m;
    this.y *= m;
    this.z *= m;
    return this;
  }

  public double dot(Vector3 other) {
    return this.x * other.x + this.y * other.y + this.z * other.z;
  }

  public Vector3 crossProduct(Vector3 other) {
    Vector3 crossProduct = getCrossProduct(other);
    this.x = crossProduct.x;
    this.y = crossProduct.y;
    this.z = crossProduct.z;
    return this;
  }

  public Vector3 getCrossProduct(Vector3 other) {
    double x = this.y * other.z - other.y * this.z;
    double y = this.z * other.x - other.z * this.x;
    double z = this.x * other.y - other.x * this.y;
    return Vector3.create(x, y, z);
  }

  public Vector3 normalize() {
    double length = this.magnitude();
    this.x /= length;
    this.y /= length;
    this.z /= length;
    this.normalizeZeros();
    return this;
  }

  public Vector3 zero() {
    this.x = 0.0D;
    this.y = 0.0D;
    this.z = 0.0D;
    return this;
  }

  private Vector3 normalizeZeros() {
    if (this.x == -0.0D) {
      this.x = 0.0D;
    }

    if (this.y == -0.0D) {
      this.y = 0.0D;
    }

    if (this.z == -0.0D) {
      this.z = 0.0D;
    }

    return this;
  }

  public boolean isInAABB(Vector3 min, @NotNull Vector3 max) {
    return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
  }

  public boolean isInSphere(Vector3 origin, double radius) {
    return NumberConversions.square(origin.x - this.x) + NumberConversions.square(origin.y - this.y) + NumberConversions.square(origin.z - this.z) <= NumberConversions.square(radius);
  }

  public boolean isNormalized() {
    return Math.abs(this.magnitudeSquared() - 1.0D) < EPSILON;
  }

  public Vector3 rotateAroundX(double angle) {
    double angleCos = Math.cos(angle);
    double angleSin = Math.sin(angle);
    double y = angleCos * this.getY() - angleSin * this.getZ();
    double z = angleSin * this.getY() + angleCos * this.getZ();
    return this.setY(y).setZ(z);
  }

  public Vector3 rotateAroundY(double angle) {
    double angleCos = Math.cos(angle);
    double angleSin = Math.sin(angle);
    double x = angleCos * this.getX() + angleSin * this.getZ();
    double z = -angleSin * this.getX() + angleCos * this.getZ();
    return this.setX(x).setZ(z);
  }

  public Vector3 rotateAroundZ(double angle) {
    double angleCos = Math.cos(angle);
    double angleSin = Math.sin(angle);
    double x = angleCos * this.getX() - angleSin * this.getY();
    double y = angleSin * this.getX() + angleCos * this.getY();
    return this.setX(x).setY(y);
  }

  public Vector3 rotateAroundAxis(Vector3 axis, double angle) {
    Preconditions.checkArgument(axis != null, "The provided axis vector was null");
    return this.rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.clone().normalize(), angle);
  }

  public Vector3 rotateAroundNonUnitAxis(Vector3 axis, double angle) {
    Preconditions.checkArgument(axis != null, "The provided axis vector was null");
    double x = this.getX();
    double y = this.getY();
    double z = this.getZ();
    double x2 = axis.getX();
    double y2 = axis.getY();
    double z2 = axis.getZ();
    double cosTheta = Math.cos(angle);
    double sinTheta = Math.sin(angle);
    double dotProduct = this.dot(axis);
    double xPrime = x2 * dotProduct * (1.0D - cosTheta) + x * cosTheta + (-z2 * y + y2 * z) * sinTheta;
    double yPrime = y2 * dotProduct * (1.0D - cosTheta) + y * cosTheta + (z2 * x - x2 * z) * sinTheta;
    double zPrime = z2 * dotProduct * (1.0D - cosTheta) + z * cosTheta + (-y2 * x + x2 * y) * sinTheta;
    return this.setX(xPrime).setY(yPrime).setZ(zPrime);
  }

  // current vector instance used as origin
  public Vector3 getPosition(Vector3 direction, double blocksAway) {
    return this.clone().add(direction.clone().multiply(blocksAway));
  }

  // current vector instance used as origin
  public boolean isOnLine(Vector3 direction, Vector position) {
    double t = (position.getX() - this.getX()) / direction.getX();
    return position.getBlockY() == this.getY() + (t * direction.getY()) && position.getBlockZ() == this.getZ() + (t * direction.getZ());
  }

  public Vector3 setX(double x) {
    this.x = x;
    return this;
  }

  public Vector3 setY(double y) {
    this.y = y;
    return this;
  }

  public Vector3 setZ(double z) {
    this.z = z;
    return this;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public int getBlockX() {
    return (int) Math.floor(this.x);
  }

  public int getBlockY() {
    return (int) Math.floor(this.y);
  }

  public int getBlockZ() {
    return (int) Math.floor(this.z);
  }

  public KelpLocation toLocation(KelpWorld world) {
    return KelpLocation.from(world, this.x, this.y, this.z);
  }

  public KelpLocation toLocation(String worldName) {
    return KelpLocation.from(worldName, this.x, this.y, this.z);
  }

  public Vector toBukkitVector() {
    return new Vector(this.x, this.y, this.z);
  }

  @Override
  public void visualize(KelpPlayer player, ParticleVisualizerProfile visualizerProfile) {

  }

  @Override
  public String toString() {
    return "x=" + this.x + ", y=" + this.y + ", z=" + this.z;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object){
      return true;
    }

    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    Vector3 vector3 = (Vector3) object;
    return this.x == vector3.x && this.y == vector3.y && this.z == vector3.z;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(this.x)
      .append(this.y)
      .append(this.z)
      .toHashCode();
  }

  @Override
  public Vector3 clone() {
    return Vector3.create(getX(), getY(), getZ());
  }

}
