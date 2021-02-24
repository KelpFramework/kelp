package de.pxav.kelp.core.world;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.io.Serializable;

public class KelpLocation implements Serializable, Cloneable {

  private String worldName = "world";

  private double x = 0.0D;
  private double y = 0.0D;
  private double z = 0.0D;
  private float yaw = 0.0F;
  private float pitch = 0.0F;

  public static KelpLocation from(Location location) {
    KelpLocation kelpLocation = new KelpLocation();

    kelpLocation.setX(location.getX());
    kelpLocation.setY(location.getY());
    kelpLocation.setZ(location.getZ());
    kelpLocation.setYaw(location.getYaw());
    kelpLocation.setPitch(location.getPitch());

    kelpLocation.setWorldName(location.getWorld().getName());

    return kelpLocation;
  }

  public static KelpLocation from(String worldName, double x, double y, double z) {
    Preconditions.checkNotNull(worldName);
    KelpLocation location = new KelpLocation();

    location.setX(x);
    location.setY(y);
    location.setZ(z);
    location.setWorldName(worldName);

    return location;
  }

  public static KelpLocation from(String worldName, double x, double y, double z, float yaw, float pitch) {
    Preconditions.checkNotNull(worldName);
    KelpLocation location = new KelpLocation();

    location.setX(x);
    location.setY(y);
    location.setZ(z);
    location.setYaw(yaw);
    location.setPitch(pitch);
    location.setWorldName(worldName);

    return location;
  }

  public static KelpLocation create() {
    return new KelpLocation();
  }

  public String getWorldName() {
    return worldName;
  }

  public void setWorldName(String worldName) {
    this.worldName = worldName;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getZ() {
    return z;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public float getYaw() {
    return yaw;
  }

  public void setYaw(float yaw) {
    this.yaw = yaw;
  }

  public float getPitch() {
    return pitch;
  }

  public void setPitch(float pitch) {
    this.pitch = pitch;
  }

  public int getBlockX() {
    return Location.locToBlock(this.x);
  }

  public int getBlockY() {
    return Location.locToBlock(this.y);
  }

  public int getBlockZ() {
    return Location.locToBlock(this.z);
  }

  public void setBlockX() {
    this.x = Location.locToBlock(this.x);
  }

  public void setBlockY() {
    this.y = Location.locToBlock(this.y);
  }

  public void setBlockZ() {
    this.z = Location.locToBlock(this.z);
  }

  public KelpChunk getChunk() {
    return KelpChunk.from(getBukkitLocation().getChunk());
  }

  public KelpBlock getBlock() {
    return KelpBlock.from(getBukkitLocation().getBlock());
  }

  public KelpLocation addAndClone(double x, double y, double z) {
    return KelpLocation.from(
      this.worldName,
      this.x + x,
      this.y + y,
      this.z + z
    );
  }

  public KelpLocation add(double x, double y, double z) {
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
  }

  public KelpLocation addAndCloneX(double x) {
    return KelpLocation.from(
      this.worldName,
      this.x + x,
      this.y,
      this.z
    );
  }

  public KelpLocation addX(double x) {
    this.x += x;
    return this;
  }

  public KelpLocation addY(double y) {
    this.y += y;
    return this;
  }

  public KelpLocation addAndCloneY(double y) {
    return KelpLocation.from(
      this.worldName,
      this.x,
      this.y + y,
      this.z
    );
  }

  public KelpLocation addZ(double z) {
    this.z += z;
    return this;
  }

  public KelpLocation addAndCloneZ(double z) {
    return KelpLocation.from(
      this.worldName,
      this.x,
      this.y,
      this.z + z
    );
  }

  public KelpLocation subtractAndClone(double x, double y, double z) {
    return KelpLocation.from(
      this.worldName,
      this.x - x,
      this.y - y,
      this.z - z
    );
  }

  public KelpLocation subtract(double x, double y, double z) {
    this.x -= x;
    this.y -= y;
    this.z -= z;
    return this;
  }

  public KelpLocation subtractAndCloneX(double x) {
    return KelpLocation.from(
      this.worldName,
      this.x - x,
      this.y,
      this.z
    );
  }

  public KelpLocation subtractX(double x) {
    this.x -= x;
    return this;
  }

  public KelpLocation subtractAndCloneY(double y) {
    return KelpLocation.from(
      this.worldName,
      this.x,
      this.y - y,
      this.z
    );
  }

  public KelpLocation subtractY(double y) {
    this.y -= y;
    return this;
  }

  public KelpLocation subtractAndCloneZ(double z) {
    return KelpLocation.from(
      this.worldName,
      this.x,
      this.y,
      this.z - z
    );
  }

  public KelpLocation subtractZ(double z) {
    this.z -= z;
    return this;
  }

  public Vector getDirection() {
    Vector vector = new Vector();

    double rotY = this.getPitch();
    vector.setY(-Math.sin(Math.toRadians(rotY)));

    double xz = Math.cos(Math.toRadians(rotY));
    double rotX = this.getYaw();
    vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
    vector.setZ(xz * Math.cos(Math.toRadians(rotX)));

    return vector;
  }

  public KelpLocation getFrontLookLocation() {
    return this.clone().add(getDirection());
  }


  public KelpLocation getBackLookLocation() {
    return this.clone().subtract(getDirection());
  }

  public KelpLocation getBackLocationKeepHeight() {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.add(Math.sin(yawRadians), 0, -Math.cos(yawRadians));
  }

  public KelpLocation getFrontLocationKeepHeight() {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.subtract(Math.sin(yawRadians), 0, -Math.cos(yawRadians));
  }

  public KelpLocation getBackLocationKeepHeight(double distance) {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.add(distance * Math.sin(yawRadians), 0, -distance * Math.cos(yawRadians));
  }

  public KelpLocation getFrontLocationKeepHeight(double distance) {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.subtract(distance * Math.sin(yawRadians), 0, -distance * Math.cos(yawRadians));
  }

  public KelpLocation setDirection(Vector vector) {
    double _2PI = 6.283185307179586D;
    double x = vector.getX();
    double z = vector.getZ();
    if (x == 0.0D && z == 0.0D) {
      this.pitch = (float)(vector.getY() > 0.0D ? -90 : 90);
    } else {
      double theta = Math.atan2(-x, z);
      this.yaw = (float)Math.toDegrees((theta + _2PI) % _2PI);
      double x2 = NumberConversions.square(x);
      double z2 = NumberConversions.square(z);
      double xz = Math.sqrt(x2 + z2);
      this.pitch = (float)Math.toDegrees(Math.atan(-vector.getY() / xz));
    }
    return this;
  }

  public KelpLocation add(KelpLocation location) {
    Preconditions.checkNotNull(location, "Cannot add 'null' to a KelpLocation");
    if (!location.getWorldName().equalsIgnoreCase(getWorldName())) {
      throw new IllegalArgumentException("Cannot add two locations of differing worlds!");
    }

    this.x += location.getX();
    this.y += location.getY();
    this.z += location.getZ();
    return this;
  }

  public KelpLocation addAndClone(KelpLocation location) {
    Preconditions.checkNotNull(location, "Cannot add 'null' to a KelpLocation");
    if (!location.getWorldName().equalsIgnoreCase(getWorldName())) {
      throw new IllegalArgumentException("Cannot add two locations of differing worlds!");
    }

    return KelpLocation.from(
      worldName,
      this.x += location.getX(),
      this.y += location.getY(),
      this.z += location.getZ()
    );
  }

  public KelpLocation subtract(KelpLocation location) {
    Preconditions.checkNotNull(location, "Cannot subtract 'null' from a KelpLocation");
    if (!location.getWorldName().equalsIgnoreCase(getWorldName())) {
      throw new IllegalArgumentException("Cannot subtract two locations of differing worlds!");
    }

    this.x -= location.getX();
    this.y -= location.getY();
    this.z -= location.getZ();
    return this;
  }

  public KelpLocation subtractAndClone(KelpLocation location) {
    Preconditions.checkNotNull(location, "Cannot subtract 'null' from a KelpLocation");
    if (!location.getWorldName().equalsIgnoreCase(getWorldName())) {
      throw new IllegalArgumentException("Cannot subtract two locations of differing worlds!");
    }

    return KelpLocation.from(
      worldName,
      this.x -= location.getX(),
      this.y -= location.getY(),
      this.z -= location.getZ()
    );
  }

  public KelpLocation add(Vector vector) {
    this.x += vector.getX();
    this.y += vector.getY();
    this.z += vector.getZ();
    return this;
  }

  public KelpLocation addAndClone(Vector vector) {
    return KelpLocation.from(
      worldName,
      this.x += vector.getX(),
      this.y += vector.getY(),
      this.z += vector.getZ()
    );
  }

  public KelpLocation subtract(Vector vector) {
    this.x -= vector.getX();
    this.y -= vector.getY();
    this.z -= vector.getZ();
    return this;
  }

  public KelpLocation subtractAndClone(Vector vector) {
    return KelpLocation.from(
      worldName,
      this.x -= vector.getX(),
      this.y -= vector.getY(),
      this.z -= vector.getZ()
    );
  }

  public double length() {
    return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
  }

  public double lengthSquared() {
    return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
  }

  public double distanceSquared(KelpLocation to) {
    Preconditions.checkNotNull(to, "Cannot measure distance between 'null' and a KelpLocation");
    if (!to.getWorldName().equalsIgnoreCase(worldName)) {
      throw new IllegalArgumentException("Cannot measure distance between KelpLocations from differing worlds");
    }
    return NumberConversions.square(
      this.x - to.x)
      + NumberConversions.square(this.y - to.y)
      + NumberConversions.square(this.z - to.z
    );
  }

  public double distance(KelpLocation to) {
    return Math.sqrt(this.distanceSquared(to));
  }

  public KelpLocation multiply(double multiplier) {
    this.x *= multiplier;
    this.y *= multiplier;
    this.z *= multiplier;
    return this;
  }

  public KelpLocation multiplyX(double multiplier) {
    this.x *= multiplier;
    return this;
  }

  public KelpLocation multiplyZ(double multiplier) {
    this.z *= multiplier;
    return this;
  }

  public KelpLocation multiplyXZ(double multiplier) {
    this.x *= multiplier;
    this.z *= multiplier;
    return this;
  }

  public KelpLocation multiply(Vector multiplier) {
    this.x *= multiplier.getX();
    this.y *= multiplier.getY();
    this.z *= multiplier.getZ();
    return this;
  }

  public KelpLocation multiply(KelpLocation multiplier) {
    this.x *= multiplier.getX();
    this.y *= multiplier.getY();
    this.z *= multiplier.getZ();
    return this;
  }

  public KelpLocation zeroAxis() {
    setX(0.0D);
    setY(0.0D);
    setZ(0.0D);
    return this;
  }

  public KelpLocation zeroAll() {
    setX(0.0D);
    setY(0.0D);
    setZ(0.0D);
    setYaw(0.0F);
    setPitch(0.0F);
    return this;
  }

  public KelpLocation zeroLook() {
    setYaw(0.0F);
    setPitch(0.0F);
    return this;
  }

  @Override
  public KelpLocation clone() {
    try {
      return (KelpLocation) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Location getBukkitLocation() {
    return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
  }

}
