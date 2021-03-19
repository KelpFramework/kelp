package de.pxav.kelp.core.world.util;

import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

/**
 * An approximate location is an object to save rough locations in a world.
 * It is similar to a chunk, which covers a 16x16 space with the main difference
 * being that this location type has a scale of 100x100.
 *
 * So if you get the x and z of an approximate location, {@code x=1} will mean
 * that the exact location is {@code 200 > x >= 100}. This is useful if you want to execute
 * certain operations for players in a certain area without having to use performance-heavy(er)
 * {@code distance()} functions. This is used in the built-in region system for example.
 * The region enter and exit event don't check each region on a world to check if a player
 * is in there, but only for regions with the same approximate location. This saves lots of
 * performance.
 *
 * @author pxav
 */
public class ApproximateLocation {

  private int x;
  private int z;
  private String worldName;

  /**
   * Converts the given {@link KelpLocation} into an {@link ApproximateLocation}.
   * This does not have an effect on the given source location.
   *
   * @param location The source location to create the approximate location from.
   * @return The approximate location equivalent to the given {@link KelpLocation}.
   */
  public static ApproximateLocation from(KelpLocation location) {
    return new ApproximateLocation(location.getWorldName(), location.getBlockX(), location.getBlockZ());
  }

  /**
   * Converts the given {@link KelpLocation} into an {@link ApproximateLocation}.
   * This does not have an effect on the given source location.
   *
   * @param location The source location to create the approximate location from.
   * @return The approximate location equivalent to the given {@link Location bukkit location}.
   */
  public static ApproximateLocation from(Location location) {
    return new ApproximateLocation(location.getWorld().getName(), location.getBlockX(), location.getBlockZ());
  }

  /**
   * Creates a new approximate location based on the given world and x and z axis.
   *
   * @param worldName The name of the world for the desired location.
   * @param x         The exact x axis of the original location.
   * @param z         The exact z axis of the original location.
   * @return The final approximate location equivalent to the given location.
   */
  public static ApproximateLocation fromExact(String worldName, int x, int z) {
    return new ApproximateLocation(worldName, x, z);
  }

  /**
   * Creates a new approximate location based on the given world and x and z axis.
   *
   * @param world The world of the desired location.
   * @param x     The exact x axis of the original location.
   * @param z     The exact z axis of the original location.
   * @return The final approximate location equivalent to the given location.
   */
  public static ApproximateLocation fromExact(KelpWorld world, int x, int z) {
    return fromExact(world.getName(), x, z);
  }

  /**
   * Creates a new approximate location based on a world name and the
   * approximate x and z values. If you want to use exact values to calculate
   * the approximate ones, use {@link #fromExact(String, int, int)} instead.
   *
   * @param worldName The name of the world for this location.
   * @param x         The approximate x value to use (won't be further converted)
   * @param z         The approximate x value to use (won't be further converted)
   * @return The final approximate location based on the given data.
   */
  public static ApproximateLocation create(String worldName, int x, int z) {
    return new ApproximateLocation(worldName, x, z);
  }

  private ApproximateLocation(String worldName, int x, int z) {
    this.x =  x / 100;
    this.z =  z / 100;
    this.worldName = worldName;
  }

  /**
   * Gets the x-value in an approximate grid, which is equal to
   * the exact location's X divided by 100.
   *
   * @return The exact x axis divided by 100.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the z-value in an approximate grid, which is equal to
   * the exact location's Z divided by 100.
   *
   * @return The exact z axis divided by 100.
   */
  public int getZ() {
    return z;
  }

  /**
   * Gets the name of the world of this location.
   *
   * @return This location's world name.
   */
  public String getWorldName() {
    return worldName;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(this.x)
      .append(this.z)
      .append(this.worldName)
      .toHashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ApproximateLocation)) {
      return false;
    }
    ApproximateLocation location = (ApproximateLocation) object;

    return location.getWorldName().equalsIgnoreCase(this.worldName)
      && location.getX() == this.getX()
      && location.getZ() == this.getZ();
  }

  @Override
  public String toString() {
    return "ApproximateLocation{" +
      "x=" + x +
      ", z=" + z +
      ", worldName='" + worldName + '\'' +
      '}';
  }
}
