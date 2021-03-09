package de.pxav.kelp.core.world.region;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.Set;

public class EllipsoidRegion extends KelpRegion {

  private static final double FOUR_THIRDS = 1.33333333333333333333d;

  private KelpLocation center;
  private double xRadius;
  private double yRadius;
  private double zRadius;
  private double limitX = 0;
  private double limitY = 0;
  private double limitZ = 0;

  public static EllipsoidRegion create(KelpLocation center, double radius) {
    EllipsoidRegion region = new EllipsoidRegion();
    region.setCenter(center);
    region.setRadius(radius);
    region.minPos = center.clone().subtract(radius);
    region.maxPos = center.clone().add(radius);
    return region;
  }

  public static EllipsoidRegion create(KelpLocation center, double xRadius, double yRadius, double zRadius) {
    EllipsoidRegion region = new EllipsoidRegion();
    region.setCenter(center);
    region.setXRadius(xRadius);
    region.setYRadius(yRadius);
    region.setZRadius(zRadius);
    region.minPos = center.clone().subtract(xRadius, yRadius, zRadius);
    region.maxPos = center.clone().add(xRadius, yRadius, zRadius);
    return region;
  }

  public static EllipsoidRegion create(KelpLocation pos1, KelpLocation pos2) {
    EllipsoidRegion region = new EllipsoidRegion();
    region.maxPos = pos1.getMaximalLocation(pos2);
    region.minPos = pos1.getMinimalLocation(pos2);

    region.setCenter(pos1.findMidpoint(pos2));
    region.setXRadius(Math.abs(region.minPos.getX() - region.maxPos.getX()) * 0.5);
    region.setYRadius(Math.abs(region.minPos.getY() - region.maxPos.getY()) * 0.5);
    region.setZRadius(Math.abs(region.minPos.getZ() - region.maxPos.getZ()) * 0.5);
    return region;
  }

  public void limitRadius(double limiter) {
    this.limitX = limiter;
    this.limitY = limiter;
    this.limitZ = limiter;
  }

  public void limitXRadius(double limiter) {
    this.limitX = limiter;
  }

  public void limitYRadius(double limiter) {
    this.limitY = limiter;
  }

  public void limitZRadius(double limiter) {
    this.limitZ = limiter;
  }

  @Override
  public void move(Vector vector) {
    center.add(vector);
  }

  @Override
  public void move(double dx, double dy, double dz) {
    center.add(new Vector(dx, dy, dz));
  }

  @Override
  public double getVolume() {
    return FOUR_THIRDS * Math.PI * xRadius * yRadius * zRadius;
  }

  @Override
  public int getBlockVolume() {
    return NumberConversions.floor(getVolume());
  }

  public EllipsoidRegion setCenter(KelpLocation center) {
    this.center = center;
    return this;
  }

  @Override
  public KelpLocation getCenter() {
    return this.center;
  }

  @Override
  public Set<KelpBlock> getSurfaceBlocks() {
    return getBlocks(true);
  }

  @Override
  public Set<KelpBlock> getBlocks() {
    return getBlocks(false);
  }

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


    if (limitX > 0 || limitY > 0 || limitZ > 0) {
      output.parallelStream()
        .filter(block -> {
          if (limitX > 0) {
            return block.getX() > center.clone().addX(limitX).getX() || block.getX() < center.clone().subtractX(limitX).getX();
          }
          if (limitY > 0) {
            return block.getY() > center.clone().addY(limitY).getY() || block.getY() < center.clone().subtractY(limitY).getY();
          }
          if (limitZ > 0) {
            return block.getZ() > center.clone().addZ(limitZ).getZ() || block.getZ() < center.clone().subtractZ(limitZ).getZ();
          }

          return false;
        })
        .forEach(output::remove);

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

  public Set<KelpBlock> getYSliceAt(double y) {
    Set<KelpBlock> blocks = Sets.newConcurrentHashSet();
    getBlocks().parallelStream()
      .filter(block -> block.getY() == Location.locToBlock(y))
      .forEach(blocks::add);
    return blocks;
  }

  public Set<KelpBlock> getXSliceAt(double x) {
    Set<KelpBlock> blocks = Sets.newConcurrentHashSet();
    getBlocks().parallelStream()
      .filter(block -> block.getX() == Location.locToBlock(x))
      .forEach(blocks::add);
    return blocks;
  }

  public Set<KelpBlock> getZSliceAt(double z) {
    Set<KelpBlock> blocks = Sets.newConcurrentHashSet();
    getBlocks().parallelStream()
      .filter(block -> block.getZ() == Location.locToBlock(z))
      .forEach(blocks::add);
    return blocks;
  }

  @Override
  public Set<KelpChunk> getChunks() {
    return toCuboid().getChunks();
  }

  @Override
  public Set<KelpChunk> getLoadedChunks() {
    return toCuboid().getLoadedChunks();
  }

  @Override
  public void expand(double amount) {
    xRadius += amount;
    yRadius += amount;
    zRadius += amount;
  }

  @Override
  public void expand(KelpBlockFace direction, double amount) {
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
  }

  public void expandAndMove(KelpBlockFace direction, double amount) {
    expand(direction, amount);
    move(direction.getDirection().multiply(amount / 2));
  }

  @Override
  public void expand(double negativeX, double positiveX, double negativeY, double positiveY, double negativeZ, double positiveZ) {
    this.xRadius = positiveX + negativeX;
    this.yRadius = positiveY + negativeY;
    this.zRadius = positiveZ + negativeZ;
  }

  public void expandAndMove(double negativeX, double positiveX, double negativeY, double positiveY, double negativeZ, double positiveZ) {
    expand(negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ);
    move(positiveX - negativeX, positiveY - negativeY, positiveZ - negativeZ);
  }

  @Override
  public boolean contains(KelpLocation location) {

    // if the location is excluded due to a limited radius
    boolean limitCriteria = false;

    if (limitX > 0) {
      limitCriteria = location.getX() > center.clone().addX(limitX).getX() || location.getX() < center.clone().subtractX(limitX).getX();
    }
    if (limitY > 0) {
      limitCriteria = location.getY() > center.clone().addY(limitY).getY() || location.getY() < center.clone().subtractY(limitY).getY();
    }
    if (limitZ > 0) {
      limitCriteria = location.getZ() > center.clone().addZ(limitZ).getZ() || location.getZ() < center.clone().subtractZ(limitZ).getZ();
    }

    return getCostAt(location) <= 1 && !limitCriteria;
  }

  public double getCostAt(KelpLocation location) {
    return ((location.getX() - center.getX()) / xRadius) * ((location.getX() - center.getX()) / xRadius)
      + ((location.getY() - center.getY()) / yRadius) * ((location.getY() - center.getY()) / yRadius)
      + ((location.getZ() - center.getZ()) / zRadius) * ((location.getZ() - center.getZ()) / zRadius);
  }

  public boolean isSphere() {
    return xRadius == yRadius && xRadius == zRadius;
  }

  public boolean isSpheroid() {
    return xRadius == yRadius || xRadius == zRadius || yRadius == zRadius;
  }

  public EllipsoidRegion setRadius(double radius) {
    this.xRadius = radius;
    this.yRadius = radius;
    this.zRadius = radius;
    return this;
  }

  public EllipsoidRegion setXRadius(double xRadius) {
    this.xRadius = xRadius;
    return this;
  }

  public EllipsoidRegion setYRadius(double yRadius) {
    this.yRadius = yRadius;
    return this;
  }

  public EllipsoidRegion setZRadius(double zRadius) {
    this.zRadius = zRadius;
    return this;
  }

  public double getXRadius() {
    return xRadius;
  }

  public double getYRadius() {
    return yRadius;
  }

  public double getZRadius() {
    return zRadius;
  }

  public String getWorldName() {
    return center.getWorldName();
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
}
