package de.pxav.kelp.core.world.region;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.particle.visualize.ParticleVisualizerProfile;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import de.pxav.kelp.core.world.util.Vector3;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.Set;

/**
 * An {@code EllipsoidRegion} represents an ellipsoid shape in a 3d-world.
 * An ellipsoid is the 3d-equivalent of an ellipse and has different cases:
 * - a sphere: all radius (xRadius, yRadius and zRadius) are equal to each
 * other and every outer point of the shape has an equal distance to the center.
 * - a spheroid: an ellipse has been rotated around itself. At least two
 * radius values have to be equal to each other.
 * - a triaxial ellipsoid: all radius values are different from each other
 * and there is no symmetry. (most general)
 *
 * You can check the special cases using {@link #isSphere()} or
 * {@link #isSpheroid()}.
 *
 * An ellipsoid can have a limited radius, which means that it has an
 * ellipsoid shape, but is interrupted at some point. Then it has a
 * straight border at the given axis as if you would cut it with a 1000° hot
 * knife. You can enable those limiters for specific axis using
 * {@link #limitXRadius(double)} for example.
 *
 * @author pxav
 */
public class EllipsoidRegion extends KelpRegion {

  // four thirds are needed to calculate the volume of an ellipsoid,
  // so it is cached here in order to save performance.
  private static final double FOUR_THIRDS = 1.33333333333333333333d;

  // the center of the ellipsoid. If the ellipsoid is a sphere,
  // all surface locations will have the same distance to this block.
  private KelpLocation center;

  // radius in different directions
  private double xRadius;
  private double yRadius;
  private double zRadius;

  // the radius limiters
  private double limitX = 0;
  private double limitY = 0;
  private double limitZ = 0;

  /**
   * Creates a new {@link EllipsoidRegion} with a given center and
   * a radius in all directions. So this ellipsoid will be a sphere.
   *
   * @param center The center of the sphere.
   * @param radius The sphere's radius.
   * @return The sphere as an {@link EllipsoidRegion}
   */
  public static EllipsoidRegion create(KelpLocation center, double radius) {
    EllipsoidRegion region = new EllipsoidRegion(getRegionRepository());
    region.setCenter(center);
    region.setRadius(radius);
    region.minPos = center.clone().subtract(radius);
    region.maxPos = center.clone().add(radius);
    region.worldName = center.getWorldName();
    return region;
  }

  /**
   * Creates a new {@link EllipsoidRegion} with a given center and
   * different radius values in each direction. So this ellipsoid will
   * either be a sphreid or triaxial.
   *
   * @param center    The center of your ellipsoid.
   * @param xRadius   The radius on the x axis.
   * @param yRadius   The radius on the x axis.
   * @param zRadius   The radius on the x axis.
   * @return The final {@link EllipsoidRegion}
   */
  public static EllipsoidRegion create(KelpLocation center, double xRadius, double yRadius, double zRadius) {
    EllipsoidRegion region = new EllipsoidRegion(getRegionRepository());
    region.setCenter(center);
    region.setXRadius(xRadius);
    region.setYRadius(yRadius);
    region.setZRadius(zRadius);
    region.minPos = center.clone().subtract(xRadius, yRadius, zRadius);
    region.maxPos = center.clone().add(xRadius, yRadius, zRadius);
    region.worldName = center.getWorldName();
    return region;
  }

  /**
   * Creates a new {@link EllipsoidRegion} which fits into the cuboid
   * area defined by the two given points. So the outermost points
   * will touch the same axis as the limiter blocks, while the sphere
   * wont exceed its area.
   *
   * @param pos1 The first point
   * @param pos2 The second point.
   * @return The ellipsoid fitting into the cuboid defined by pos1 and pos2.
   */
  public static EllipsoidRegion create(KelpLocation pos1, KelpLocation pos2) {
    if (!pos1.getWorldName().equalsIgnoreCase(pos2.getWorldName())) {
      throw new IllegalArgumentException("Cannot create region from locations of differing worlds!");
    }
    EllipsoidRegion region = new EllipsoidRegion(getRegionRepository());
    region.maxPos = pos1.getMaximalLocation(pos2);
    region.minPos = pos1.getMinimalLocation(pos2);

    region.setCenter(pos1.findMidpoint(pos2));
    region.worldName = pos1.getWorldName();

    double radius = Math.abs(region.minPos.getX() - region.maxPos.getX()) * 0.5;
    region.setXRadius(radius);
    region.setYRadius(radius);
    region.setZRadius(radius);

    return region;
  }

  private EllipsoidRegion(KelpRegionRepository regionRepository) {
    super(regionRepository);
  }

  /**
   * Limits the ellipsoid radius in the all axis.
   *
   * Limiting the radius means that if the radius is set to
   * 10, while the limiter is 5, the ellipsoid will be calculated
   * as a normal 10-radius-ellipsoid, but it will be cut off straight
   * as soon as the limiter is exceeded. This will look
   * like someone cut with a knife through the ellipsoid.
   *
   * @param limiter The value to limit all radius values to.
   */
  public void limitRadius(double limiter) {
    this.limitX = limiter;
    this.limitY = limiter;
    this.limitZ = limiter;
  }

  /**
   * Limits the ellipsoid radius in the x axis.
   *
   * Limiting the radius means that if the radius is set to
   * 10, while the limiter is 5, the ellipsoid will be calculated
   * as a normal 10-radius-ellipsoid, but it will be cut off straight
   * as soon as the limiter is exceeded. This will look
   * like someone cut with a knife through the ellipsoid.
   *
   * @param limiter The value to limit all radius values to.
   */
  public void limitXRadius(double limiter) {
    this.limitX = limiter;
  }

  /**
   * Limits the ellipsoid radius in the x axis.
   *
   * Limiting the radius means that if the radius is set to
   * 10, while the limiter is 5, the ellipsoid will be calculated
   * as a normal 10-radius-ellipsoid, but it will be cut off straight
   * as soon as the limiter is exceeded. This will look
   * like someone cut with a knife through the ellipsoid.
   *
   * @param limiter The value to limit all radius values to.
   */
  public void limitYRadius(double limiter) {
    this.limitY = limiter;
  }

  /**
   * Limits the ellipsoid radius in the x axis.
   *
   * Limiting the radius means that if the radius is set to
   * 10, while the limiter is 5, the ellipsoid will be calculated
   * as a normal 10-radius-ellipsoid, but it will be cut off straight
   * as soon as the limiter is exceeded. This will look
   * like someone cut with a knife through the ellipsoid.
   *
   * @param limiter The value to limit all radius values to.
   */
  public void limitZRadius(double limiter) {
    this.limitZ = limiter;
  }

  /**
   * Moves the region into the direction of the given
   * {@link Vector}.
   *
   * @param vector The vector providing the direction and
   *               power of the movement.
   */
  @Override
  protected void moveIgnoreListeners(Vector3 vector) {
    center.add(vector);
    minPos.add(vector);
    maxPos.add(vector);
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
    Vector3 vector = Vector3.create(dx, dy, dz);
    center.add(vector);
    minPos.add(vector);
    maxPos.add(vector);
  }

  /**
   * Gets the exact total volume of this region, which is defined
   * by {@code (4/3) * PI * xRadius * yRadius * zRadius}
   * for all ellipsoid.
   *
   * @return The exact volume of this region in blocks.
   */
  @Override
  public double getVolume() {
    return FOUR_THIRDS * Math.PI * xRadius * yRadius * zRadius;
  }

  /**
   * Gets the approximate total volume of this region, which is defined
   * by {@code (4/3) * PI * xRadius * yRadius * zRadius}
   * for all ellipsoid.
   *
   * This method approximates the volume in whole blocks.
   *
   * @return The approximate volume of this region in blocks.
   */
  @Override
  public int getBlockVolume() {
    return NumberConversions.floor(getVolume());
  }

  /**
   * Sets the center location of this ellipsoid.
   *
   * @param center The new center.
   * @return An instance of this region for fluent builder design.
   */
  public EllipsoidRegion setCenter(KelpLocation center) {
    this.center = center;
    return this;
  }

  /**
   * Gets the center of this ellipsoid.
   *
   * @return The center point of this ellipsoid.
   */
  @Override
  public KelpLocation getCenter() {
    return this.center;
  }

  /**
   * Gets a set of all blocks which are on the regions surface.
   *
   * @return A set of blocks containing all blocks on the regions surface.
   */
  @Override
  public Set<KelpBlock> getSurfaceBlocks() {
    return getBlocks(true);
  }

  /**
   * Gets all blocks contained by this region.
   * This can be used to visualize its shape.
   *
   * @return A set of all blocks contained by this region.
   */
  @Override
  public Set<KelpBlock> getBlocks() {
    return getBlocks(false);
  }

  /**
   * Gets all blocks contained by this region and optionally
   * only the blocks contained by the surface. This method
   * obeys all criteria set by the radius limiter.
   *
   * @param surfaceOnly Whether only surface blocks should be returned.
   * @return All (surface) blocks of this region.
   */
  private Set<KelpBlock> getBlocks(boolean surfaceOnly) {
    Set<KelpBlock> output = Sets.newConcurrentHashSet();
    double rX = xRadius + 0.5;
    double rY = yRadius + 0.5;
    double rZ = zRadius + 0.5;

    final double invRadiusX = 1 / rX;
    final double invRadiusY = 1 / rY;
    final double invRadiusZ = 1 / rZ;

    final int ceilRadiusX = (int) Math.ceil(rX);
    final int ceilRadiusY = (int) Math.ceil(rY);
    final int ceilRadiusZ = (int) Math.ceil(rZ);

    double nextXn = 0;
    forX: for (int x = 0; x <= ceilRadiusX; ++x) {
      final double xn = nextXn;
      nextXn = (x + 1) * invRadiusX;
      double nextYn = 0;
      forY: for (int y = 0; y <= ceilRadiusY; ++y) {
        final double yn = nextYn;
        nextYn = (y + 1) * invRadiusY;
        double nextZn = 0;
        forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
          final double zn = nextZn;
          nextZn = (z + 1) * invRadiusZ;

          double magnitude = KelpLocation.magnitude(xn, yn, zn);
          if (magnitude > 1) {
            if (z == 0) {
              if (y == 0) {
                break forX;
              }
              break forY;
            }
            break forZ;
          }

          if (surfaceOnly) {
            if (KelpLocation.magnitude(nextXn, yn, zn) <= 1
              && KelpLocation.magnitude(xn, nextYn, zn) <= 1
              && KelpLocation.magnitude(xn, yn, nextZn) <= 1) {
              continue;
            }
          }

          output.add(center.clone().add(x, y, z).getBlock());
          output.add(center.clone().add(-x, y, z).getBlock());
          output.add(center.clone().add(x, -y, z).getBlock());
          output.add(center.clone().add(x, y, -z).getBlock());
          output.add(center.clone().add(-x, -y, z).getBlock());
          output.add(center.clone().add(x, -y, -z).getBlock());
          output.add(center.clone().add(-x, y, -z).getBlock());
          output.add(center.clone().add(-x, -y, -z).getBlock());
        }
      }
    }

    // if limiter is enabled, remove all blocks out of the allowed radius
    if (limitX > 0 || limitY > 0 || limitZ > 0) {
      output.parallelStream()
        .filter(block -> {
          // TRUE if the location is excluded due to a limited radius
          boolean limitCriteriaX = false, limitCriteriaY = false, limitCriteriaZ = false;

          if (limitX > 0) {
            limitCriteriaX = block.getX() > (center.getX() + limitX) || block.getX() < (center.getX() - limitX);
          }

          if (limitY > 0) {
            limitCriteriaY = block.getY() > (center.getY() + limitY) || block.getY() < (center.getY() - limitY);
          }

          if (limitZ > 0) {
            limitCriteriaZ = block.getZ() > (center.getZ() + limitZ) || block.getZ() < (center.getZ() - limitZ);
          }

          return limitCriteriaX || limitCriteriaY || limitCriteriaZ;
        })
        .forEach(output::remove);

      // if only the surface is desired, the region would
      // have a big hole on the cut sides, which is why a slice is
      // added back
      if (surfaceOnly) {
        if (limitX > 0) {
          output.addAll(getZSliceAt(center.getBlockX() + limitX));
          output.addAll(getZSliceAt(center.getBlockX() - limitX));
        }
        if (limitY > 0) {
          output.addAll(getYSliceAt(center.getBlockY() + limitY));
          output.addAll(getYSliceAt(center.getBlockY() - limitY));
        }
        if (limitZ > 0) {
          output.addAll(getXSliceAt(center.getBlockZ() + limitZ));
          output.addAll(getXSliceAt(center.getBlockZ() - limitZ));
        }
      }

    }

    return output;
  }

  /**
   * Gets a slice of blocks of the ellipsoid
   * which are on a specific height/y-Axis. The
   * slice itself will be oriented along the x-axis.
   *
   * @param y The absolute y value of the blocks you want to get.
   * @return All blocks of the desired slice.
   */
  public Set<KelpBlock> getYSliceAt(double y) {
    Set<KelpBlock> blocks = Sets.newConcurrentHashSet();
    getBlocks().parallelStream()
      .filter(block -> block.getY() == Location.locToBlock(y))
      .forEach(blocks::add);
    return blocks;
  }

  /**
   * Gets a slice of blocks of the ellipsoid
   * which are on a specific x-Axis. The
   * slice itself will be oriented along the z-axis.
   *
   * @param x The absolute x value of the blocks you want to get.
   * @return All blocks of the desired slice.
   */
  public Set<KelpBlock> getXSliceAt(double x) {
    Set<KelpBlock> blocks = Sets.newConcurrentHashSet();
    getBlocks().parallelStream()
      .filter(block -> block.getX() == Location.locToBlock(x))
      .forEach(blocks::add);
    return blocks;
  }

  /**
   * Gets a slice of blocks of the ellipsoid
   * which are on a specific z-Axis. The
   * slice itself will be oriented along the x-axis.
   *
   * @param z The absolute z value of the blocks you want to get.
   * @return All blocks of the desired slice.
   */
  public Set<KelpBlock> getZSliceAt(double z) {
    Set<KelpBlock> blocks = Sets.newConcurrentHashSet();
    getBlocks().parallelStream()
      .filter(block -> block.getZ() == Location.locToBlock(z))
      .forEach(blocks::add);
    return blocks;
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
    return toCuboid().getChunks();
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
    return toCuboid().getLoadedChunks();
  }

  /**
   * Expands the current region by a certain multiplier.
   * This method does not update the listeners for the region.
   *
   * @param amount The amount of expansion in blocks.
   */
  @Override
  protected void expandIgnoreListeners(double amount) {
    xRadius += amount;
    yRadius += amount;
    zRadius += amount;
    updateMinMax();
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
    switch (direction) {
      case UP:
      case DOWN:
        yRadius += amount / 2;
        break;
      case EAST:
      case WEST:
        xRadius += amount / 2;
        break;
      case NORTH:
      case SOUTH:
        zRadius += amount / 2;
        break;
      default:
        throw new IllegalArgumentException("Error when expanding EllipsoidRegion: BlockFace must be one of UP, DOWN, NORTH, SOUTH, EAST, WEST");
    }
    updateMinMax();
  }

  /**
   * Expands the current region into a specific direction by
   * a given multiplier.
   *
   * This method automatically moves the ellipsoid up by the given
   * amount to avoid that there will be an expansion in the opposite
   * direction as well.
   *
   * This method automatically applies the changes to the listener system
   * if it is enabled for this region.
   *
   * @param direction The direction to expand the region in.
   * @param amount The amount of expansion in blocks.
   */
  public void expandAndMove(KelpBlockFace direction, double amount) {
    expand(direction, amount);
    move(direction.getDirection().multiply(amount / 2));
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
    this.xRadius = positiveX + negativeX;
    this.yRadius = positiveY + negativeY;
    this.zRadius = positiveZ + negativeZ;
    updateMinMax();
  }

  /**
   * Updates the {@code minPos} and {@code maxPos} according to the
   * radius values. This should be executed every time you expand the
   * region for example as the radius values are updated, but the
   * outer points remain the same, which might break the listener system.
   */
  private void updateMinMax() {
    minPos = center.clone().subtract(xRadius, yRadius, zRadius);
    maxPos = center.clone().add(xRadius, yRadius, zRadius);
  }

  /**
   * Expands the current region in the given axis.
   *
   * This method automatically moves the ellipsoid up by the given
   * amount to avoid that there will be an expansion in the opposite
   * direction as well.
   *
   * This method automatically applies the changes to the listener system
   * if it is enabled for this region.
   *
   * @param negativeX The expansion on the negative x-axis (equal to west)
   * @param positiveX The expansion on the positive x-axis (equal to east)
   * @param negativeY The expansion on the negative y-axis (equal to down)
   * @param positiveY The expansion on the positive y-axis (equal to up)
   * @param negativeZ The expansion on the negative z-axis (equal to north)
   * @param positiveZ  The expansion on the positive z-axis (equal to south)
   */
  public void expandAndMove(double negativeX, double positiveX, double negativeY, double positiveY, double negativeZ, double positiveZ) {
    expand(negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ);
    move(positiveX - negativeX, positiveY - negativeY, positiveZ - negativeZ);
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
    // if the location is excluded due to a limited radius
    boolean limitCriteriaX = false, limitCriteriaY = false, limitCriteriaZ = false;

    if (limitX > 0) {
      limitCriteriaX = x > (center.getX() + limitX) || x < (center.getX() - limitX);
    }

    if (limitY > 0) {
      limitCriteriaY = y > (center.getY() + limitY) || y < (center.getY() - limitY);
    }

    if (limitZ > 0) {
      limitCriteriaZ = z > (center.getZ() + limitZ) || z < (center.getZ() - limitZ);
    }

    return getCostAt(x, y, z) <= 1 && !limitCriteriaX && !limitCriteriaY && !limitCriteriaZ;
  }

  /**
   * Gets the "cost" of a block at a given location. A location's cost
   * is defined by {@code (x/xRadius)^2 + (y/yRadius)^2 + (z/zRadius)^2},
   * where x/y/z are defined by {@code x - center.x}, etc.
   *
   * This can be used to determine how far a block is away from the
   * ellipsoid or in which layer it is roughly located. If the cost
   * is smaller than or equal to 1, it is contained by the ellipsoid,
   * otherwise it's not.
   *
   * @param location The location you want to get the cost of.
   * @return The "cost" of the given location.
   */
  public double getCostAt(KelpLocation location) {
    return getCostAt(location.getX(), location.getY(), location.getZ());
  }

  /**
   * Gets the "cost" of a block at a given location. A location's cost
   * is defined by {@code (x/xRadius)^2 + (y/yRadius)^2 + (z/zRadius)^2},
   * where x/y/z are defined by {@code x - center.x}, etc.
   *
   * This can be used to determine how far a block is away from the
   * ellipsoid or in which layer it is roughly located. If the cost
   * is smaller than or equal to 1, it is contained by the ellipsoid,
   * otherwise it's not.
   *
   * @param x The x-coordinate of the location you want to get the cost of.
   * @param y The y-coordinate of the location you want to get the cost of.
   * @param z The z-coordinate of the location you want to get the cost of.
   * @return The "cost" of the given location.
   */
  public double getCostAt(double x, double y, double z) {
    return ((x - center.getX()) / xRadius) * ((x - center.getX()) / xRadius)
      + ((y - center.getY()) / yRadius) * ((y - center.getY()) / yRadius)
      + ((z - center.getZ()) / zRadius) * ((z - center.getZ()) / zRadius);
  }

  /**
   * Checks whether this ellipsoid is a sphere.
   * For this to be true, all radius values have to be the same:
   * {@code xRadius = yRadius = zRadius}
   *
   * @return {@code true} if all radius are equal.
   */
  public boolean isSphere() {
    return xRadius == yRadius && xRadius == zRadius;
  }

  /**
   * Checks whether this ellipsoid is a spheroid.
   * For this to be true, at least two radius values have to be equal to each other.
   *
   * @return {@code true} if all radius are equal.
   */
  public boolean isSpheroid() {
    return xRadius == yRadius || xRadius == zRadius || yRadius == zRadius;
  }

  /**
   * Sets the radius of all axis to the given value.
   *
   * @param radius The radius to apply for all axis.
   * @return An instance of the current region.
   */
  public EllipsoidRegion setRadius(double radius) {
    this.xRadius = radius;
    this.yRadius = radius;
    this.zRadius = radius;
    return this;
  }

  /**
   * Sets the radius of the x axis to the given value.
   *
   * @param xRadius The radius to apply on the x axis.
   * @return An instance of the current region.
   */
  public EllipsoidRegion setXRadius(double xRadius) {
    this.xRadius = xRadius;
    return this;
  }

  /**
   * Sets the radius of the y axis to the given value.
   *
   * @param yRadius The radius to apply on the y axis.
   * @return An instance of the current region.
   */
  public EllipsoidRegion setYRadius(double yRadius) {
    this.yRadius = yRadius;
    return this;
  }

  /**
   * Sets the radius of the z axis to the given value.
   *
   * @param zRadius The radius to apply on the z axis.
   * @return An instance of the current region.
   */
  public EllipsoidRegion setZRadius(double zRadius) {
    this.zRadius = zRadius;
    return this;
  }

  /**
   * Gets the radius of this ellipsoid along the x axis.
   * @return The radius along the x axis.
   */
  public double getXRadius() {
    return xRadius;
  }

  /**
   * Gets the radius of this ellipsoid along the y axis.
   * @return The radius along the y axis.
   */
  public double getYRadius() {
    return yRadius;
  }

  /**
   * Gets the radius of this ellipsoid along the z axis.
   * @return The radius along the z axis.
   */
  public double getZRadius() {
    return zRadius;
  }

  @Override
  public KelpRegion clone() {
    return EllipsoidRegion.create(this.center, xRadius, yRadius, zRadius);
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof EllipsoidRegion)) {
      return false;
    }

    EllipsoidRegion region = (EllipsoidRegion) object;
    return region.getWorldName().equalsIgnoreCase(worldName)
      && region.getXRadius() == this.getXRadius()
      && region.getYRadius() == this.getYRadius()
      && region.getZRadius() == this.getZRadius()
      && region.getCenter().equals(this.center);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(getWorldName())
      .append("ELLIPSOID")
      .append(xRadius)
      .append(xRadius)
      .append(yRadius)
      .append(center.hashCode())
      .toHashCode();
  }

  @Override
  public void visualize(KelpPlayer player, ParticleVisualizerProfile visualizerProfile) {
    throw new UnsupportedOperationException("It is not yet supported to visualize Ellipsoid Spheres. This feature will follow in the future.");
  }

}
