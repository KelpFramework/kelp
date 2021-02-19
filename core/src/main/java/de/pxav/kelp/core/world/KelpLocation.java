package de.pxav.kelp.core.world;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;

public class KelpLocation implements Serializable {

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

  public Location getBukkitLocation() {
    return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
  }

}
