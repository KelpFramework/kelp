package de.pxav.kelp.core.world.region;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.Set;

public class EllipsoidRegion extends KelpRegion {

  private KelpLocation center;
  private double xRadius;
  private double yRadius;
  private double zRadius;

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
    return (4d / 3d) * Math.PI * xRadius * yRadius * zRadius;
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
    Set<KelpBlock> output = Sets.newConcurrentHashSet();
    this.clone().toCuboid()
      .getBlocks()
      .parallelStream()
      .filter(this::contains)
      .filter(b -> getCostAt(b.getLocation()) > 0.55)
      .forEach(output::add);
    return output;
  }

  @Override
  public Set<KelpBlock> getBlocks() {
    Set<KelpBlock> output = Sets.newConcurrentHashSet();
    this.clone().toCuboid()
      .getBlocks()
      .parallelStream()
      .filter(this::contains)
      .forEach(output::add);
    return output;
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

  @Override
  public void expand(double negativeX, double positiveX, double negativeY, double positiveY, double negativeZ, double positiveZ) {
    this.xRadius = positiveX + negativeX;
    this.yRadius = positiveY + negativeY;
    this.zRadius = positiveZ + negativeZ;
  }

  @Override
  public boolean contains(KelpLocation location) {
    return getCostAt(location) <= 1;
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
}
