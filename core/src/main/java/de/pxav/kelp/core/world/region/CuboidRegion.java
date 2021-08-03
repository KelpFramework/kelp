package de.pxav.kelp.core.world.region;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.util.Vector;

import java.util.Set;

/**
 * A CuboidRegion is a region representing a cubic space in a 3d-world.
 * This means it has two opposite points and calculates the cuboid in between
 * those points.
 *
 * @author pxav
 */
public class CuboidRegion extends KelpRegion {

  /**
   * Creates a new {@link CuboidRegion} based on two opposite points.
   * This method automatically calculates which point is the upper and
   * which one the lower point.
   *
   * @param pos1 The first point
   * @param pos2 The second point.
   * @return The cuboid region between those points
   */
  public static CuboidRegion create(KelpLocation pos1, KelpLocation pos2) {
    CuboidRegion region = new CuboidRegion(getRegionRepository());
    region.setBoundingPositions(pos1, pos2);
    region.setWorldName(pos1.getWorldName());
    return region;
  }

  /**
   * Creates a new {@link CuboidRegion} instance without calculating any points.
   *
   * @return The fresh and empty cuboid region instance.
   */
  public static CuboidRegion create() {
    return new CuboidRegion(getRegionRepository());
  }

  private CuboidRegion(KelpRegionRepository regionRepository) {
    super(regionRepository);
  }

  /**
   * Moves the region into a certain direction defined by
   * the different axis values.
   *
   * @param dx How far the region should be moved on the x-axis
   * @param dy How far the region should be moved on the y-axis
   * @param dz How far the region should be moved on the z-axis
   */
  @Override
  protected void moveIgnoreListeners(double dx, double dy, double dz) {
    move(new Vector(dx, dy, dz));
  }

  /**
   * Moves the region into the direction of the given
   * {@link Vector}.
   *
   * @param vector The vector providing the direction and
   *               power of the movement.
   */
  @Override
  protected void moveIgnoreListeners(Vector vector) {
    this.minPos.add(vector);
    this.maxPos.add(vector);
  }

  /**
   * Gets the total volume of this region, which is defined
   * by the length on the {@code x * y * z} axis.
   *
   * This method returns the exact volume of this region.
   * If you only want the block count, use {@link #getBlockVolume()}
   * instead.
   *
   * @return The exact volume of this location in blocks.
   */
  @Override
  public double getVolume() {
    double[] dimensions = getDimensions();
    return dimensions[0] * dimensions[1] * dimensions[2];
  }

  /**
   * Gets the total volume of this region, which is defined
   * by the length on the {@code x * y * z} axis.
   *
   * This method approximates the volume to whole blocks.
   *
   * @return The approximate volume of this location in blocks.
   */
  @Override
  public int getBlockVolume() {
    int[] blockDimensions = getBlockDimensions();
    return blockDimensions[0] * blockDimensions[1] * blockDimensions[2];
  }

  /**
   * Determines whether the given location is contained by
   * this region. This method does not check whether the world
   * is equal but only the axis values.
   *
   * @param x The x-coordinate of the location to check.
   * @param y The y-coordinate of the location to check.
   * @param z The z-coordinate of the location to check.
   * @return {@code true} if the location is contained by the region.
   */
  @Override
  public boolean contains(double x, double y, double z) {
    if(x <= maxPos.getX() && x >= minPos.getX()) {
      if(y <= maxPos.getY() && y >= minPos.getY()) {
        return z <= maxPos.getZ() && z >= minPos.getZ();
      }
    }
    return false;
  }

  /**
   * Gets the center of the cuboid, which is defined by the center of the
   * diagonal line between the two opposite points.
   *
   * @return The center of this cuboid.
   */
  @Override
  public KelpLocation getCenter() {
    return this.minPos.clone().add(this.maxPos).multiply(0.5);
  }

  /**
   * Checks if this region intersects with another {@link CuboidRegion}.
   *
   * @param region The region to check the intersection with.
   * @return {@code true} whether both regions intersect with each other.
   */
  public boolean hasCuboidIntersection(CuboidRegion region) {
    // if the worlds differ, the regions cannot intersect
    if (!region.getWorldName().equalsIgnoreCase(worldName)) {
      return false;
    }

    return (!(minPos.getX() > region.getMaxPos().getX() || region.getMinPos().getX() > maxPos.getX()
      || minPos.getY() > region.getMaxPos().getY() || region.getMinPos().getY() > maxPos.getY()
      || minPos.getZ() > region.getMaxPos().getZ() || region.getMinPos().getZ() > maxPos.getZ()));
  }

  /**
   * Gets the cuboid intersection between to regions. If two
   * {@link CuboidRegion}s intersect with each other, the
   * intersecting blocks will be covered by this region.
   *
   * @param region The region you want to get the intersection with.
   * @return The region representing the intersection of the two regions.
   */
  public CuboidRegion cuboidIntersection(CuboidRegion region) {
    // if the regions do not intersect, return null
    if (!hasCuboidIntersection(region)) {
      return null;
    }

    return CuboidRegion.create(
      getMinPos().getMaximalLocation(region.getMinPos()),
      getMaxPos().getMinimalLocation(region.getMaxPos()));
  }

  /**
   * Gets a set of all blocks which are on the regions surface.
   *
   * @return A set of blocks containing all blocks on the regions surface.
   */
  @Override
  public Set<KelpBlock> getSurfaceBlocks() {
    Set<KelpBlock> output = Sets.newConcurrentHashSet();
    output.addAll(getFaceBlocks(KelpBlockFace.UP));
    output.addAll(getFaceBlocks(KelpBlockFace.DOWN));
    output.addAll(getFaceBlocks(KelpBlockFace.EAST));
    output.addAll(getFaceBlocks(KelpBlockFace.WEST));
    output.addAll(getFaceBlocks(KelpBlockFace.NORTH));
    output.addAll(getFaceBlocks(KelpBlockFace.SOUTH));
    return output;
  }

  /**
   * Gets all blocks contained by this region.
   * This can be used to visualize its shape.
   *
   * @return A set of all blocks contained by this region.
   */
  @Override
  public Set<KelpBlock> getBlocks() {
    Set<KelpBlock> output = Sets.newConcurrentHashSet();
    for (int y = minPos.getBlockY(); y <= maxPos.getBlockY(); y++) {
      for (int x = minPos.getBlockX(); x <= maxPos.getBlockX(); x++) {
        for (int z = minPos.getBlockZ(); z <= maxPos.getBlockZ(); z++) {
          output.add(KelpLocation.from(getWorldName(), x, y, z).getBlock());
        }
      }
    }
    return output;
  }

  /**
   * Gets all blocks of a specific face of this cuboid.
   *
   * @param direction The direction of the face you want to get the blocks of.
   * @return A set of blocks of the face in the given direction.
   */
  public Set<KelpBlock> getFaceBlocks(KelpBlockFace direction) {
    return getFace(direction).getBlocks();
  }

  /**
   * Gets the length of the region in the given direction.
   * This method gets the exact length, use {@link #measureBlocks(KelpBlockFace)}
   * if you only need the block count instead.
   *
   * @param direction The direction to measure the length of.
   * @return The length of this cuboid in a specific direction.
   */
  public double measure(KelpBlockFace direction) {
    switch (direction) {
      case UP:
      case DOWN:
        return getDimensions()[1];
      case EAST:
      case WEST:
        return getDimensions()[0];
      case NORTH:
      case SOUTH:
        return getDimensions()[2];
    }
    throw new IllegalArgumentException("Cannot measure region axis '" + direction + "'. Only use UP, DOWN, EAST, WEST, NORTH, SOUTH");
  }

  /**
   * Gets the length of the region in the given direction.
   * This method approximates the output to an integer
   * block count.
   *
   * @param direction The direction to measure the length of.
   * @return The length of this cuboid in a specific direction.
   */
  public int measureBlocks(KelpBlockFace direction) {
    switch (direction) {
      case UP:
      case DOWN:
        return getBlockDimensions()[1];
      case EAST:
      case WEST:
        return getBlockDimensions()[0];
      case NORTH:
      case SOUTH:
        return getBlockDimensions()[2];
    }
    throw new IllegalArgumentException("Cannot measure region axis '" + direction + "'. Only use UP, DOWN, EAST, WEST, NORTH, SOUTH");
  }

  /**
   * Gets all chunks covered by this region no matter if they
   * are loaded or not. If you only want loaded chunks,
   * use {@link #getLoadedChunks()} instead.
   *
   * @return A set of all chunks covered by this region.
   */
  @Override
  public Set<KelpChunk> getChunks() {
    Set<KelpChunk> output = Sets.newHashSet();
    KelpChunk minChunk = getMinPos().getChunk();
    KelpChunk maxChunk = getMaxPos().getChunk();

    for (int cx = minChunk.getX(); cx <= maxChunk.getX(); cx++) {
      for (int cz = minChunk.getZ(); cz <= maxChunk.getZ(); cz++) {
        output.add(getWorld().getChunkAt(cx, 0, cz));
      }
    }
    return output;
  }

  /**
   * Gets all chunks that are loaded and covered by this region.
   * If you want to include unloaded chunks as well, use {@link #getChunks()}
   * instead.
   * This method automatically applies the changes to the listener system
   * if it is enabled for this region.
   *
   * @return A set of all loaded chunks covered by this region.
   */
  @Override
  public Set<KelpChunk> getLoadedChunks() {
    Set<KelpChunk> output = Sets.newHashSet();
    KelpChunk minChunk = getMinPos().getChunk();
    KelpChunk maxChunk = getMaxPos().getChunk();

    for (int cx = minChunk.getX(); cx <= maxChunk.getX(); cx++) {
      for (int cz = minChunk.getZ(); cz <= maxChunk.getZ(); cz++) {
        KelpChunk toAdd = getWorld().getChunkAt(cx, 0, cz);
        if (toAdd.isLoaded()) {
          output.add(toAdd);
        }
      }
    }
    return output;
  }

  /**
   * Gets the cuboid region representing the outermost block-layer
   * at a given face. If you choose {@link KelpBlockFace} for example,
   *
   * @param direction The direction of the face you want to get.
   * @return The cuboid region representing the face in the given direction.
   */
  public CuboidRegion getFace(KelpBlockFace direction) {
    CuboidRegion output = this.clone();
    output.expand(direction.getOppositeFace(), -output.measureBlocks(direction) + 1);
    return output;
  }

  /**
   * Expands the current region by a certain multiplier.
   * This method does not update the listeners for the region.
   *
   * @param amount The amount of expansion in blocks.
   */
  @Override
  protected void expandIgnoreListeners(double amount) {
    expand(amount, amount, amount, amount, amount, amount);
  }

  /**
   * Expands the current region into a specific direction by
   * a given multiplier.
   * This method does not update the listeners for the region.
   *
   * @param direction The direction to expand the region in.
   * @param amount The amount of expansion in blocks.
   */
  @Override
  protected void expandIgnoreListeners(KelpBlockFace direction, double amount) {
    Vector vector = direction.getDirection();
    if (vector.getX() + vector.getY() + vector.getZ() > 0) {
      vector = vector.multiply(amount);
      maxPos.add(vector);
    } else {
      vector = vector.multiply(amount);
      minPos.add(vector);
    }
    setBoundingPositions(minPos, maxPos);
  }

  /**
   * Expands the current region in the given axis.
   * This method does not update the listeners for the region.
   *
   * @param negativeX The expansion on the negative x-axis (equal to west)
   * @param positiveX The expansion on the positive x-axis (equal to east)
   * @param negativeY The expansion on the negative y-axis (equal to down)
   * @param positiveY The expansion on the positive y-axis (equal to up)
   * @param negativeZ The expansion on the negative z-axis (equal to north)
   * @param positiveZ  The expansion on the positive z-axis (equal to south)
   */
  @Override
  protected void expandIgnoreListener(double negativeX, double positiveX, double negativeY, double positiveY, double negativeZ, double positiveZ) {
    this.minPos.subtract(negativeX, negativeY, negativeZ);
    this.maxPos.add(positiveX, positiveY, positiveZ);
    setBoundingPositions(minPos, maxPos);
  }

  @Override
  public CuboidRegion clone() {
    return CuboidRegion.create(this.minPos, this.maxPos);
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof CuboidRegion)) {
      return false;
    }

    CuboidRegion region = (CuboidRegion) object;
    return region.getWorldName().equalsIgnoreCase(worldName)
      && minPos.equals(region.getMinPos())
      && maxPos.equals(region.getMaxPos());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(this.worldName)
      .append("CUBOID")
      .append(maxPos.hashCode())
      .append(minPos.hashCode())
      .toHashCode();
  }

  /**
   * Takes two opposite points and checks which of those points
   * is higher and which one is lower and assigns them to {@code minPos}
   * and {@code maxPos} of this location accordingly.
   *
   * The order you provide the points in does not matter for this function.
   *
   * @param pos1 The first point
   * @param pos2 The second point
   */
  public void setBoundingPositions(KelpLocation pos1, KelpLocation pos2) {
    if (!pos1.getWorldName().equals(pos2.getWorldName())) {
      throw new IllegalArgumentException("Cannot build CuboidRegion from locations of differing worlds!");
    }

    double minX = Math.min(pos1.getX(), pos2.getX());
    double minY = Math.min(pos1.getY(), pos2.getY());
    double minZ = Math.min(pos1.getZ(), pos2.getZ());

    double maxX = Math.max(pos1.getX(), pos2.getX());
    double maxY = Math.max(pos1.getY(), pos2.getY());
    double maxZ = Math.max(pos1.getZ(), pos2.getZ());

    this.minPos = KelpLocation.from(pos1.getWorldName(), minX, minY, minZ);
    this.maxPos = KelpLocation.from(pos1.getWorldName(), maxX, maxY, maxZ);
  }

}
