package de.pxav.kelp.core.world.region;

import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

public class ApproximateLocation {

  private int x;
  private int z;
  private String worldName;

  public static ApproximateLocation from(KelpLocation location) {
    return new ApproximateLocation(location.getWorldName(), location.getBlockX(), location.getBlockZ());
  }

  public static ApproximateLocation from(Location location) {
    return new ApproximateLocation(location.getWorld().getName(), location.getBlockX(), location.getBlockZ());
  }

  public static ApproximateLocation create(String worldName, int x, int z) {
    return new ApproximateLocation(worldName, x, z);
  }

  public static ApproximateLocation create(KelpWorld world, int x, int z) {
    return create(world.getName(), x, z);
  }

  private ApproximateLocation(String worldName, int x, int z) {
    this.x =  x / 100;
    this.z =  z / 100;
    this.worldName = worldName;
  }

  public int getX() {
    return x;
  }

  public int getZ() {
    return z;
  }

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
