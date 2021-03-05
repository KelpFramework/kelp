package de.pxav.kelp.core.world.region;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import org.bukkit.util.Vector;

import java.util.Set;

public class CuboidRegion extends KelpRegion {

  public static CuboidRegion create(KelpLocation pos1, KelpLocation pos2) {
    CuboidRegion region = new CuboidRegion();
    region.setBoundingPositions(pos1, pos2);
    region.setWorldName(pos1.getWorldName());
    return region;
  }

  public static CuboidRegion create() {
    return new CuboidRegion();
  }

  @Override
  public void move(Vector vector) {
    this.minPos.add(vector);
    this.maxPos.add(vector);
  }

  @Override
  public void move(double dx, double dy, double dz) {
    move(new Vector(dx, dy, dz));
  }

  @Override
  public double getVolume() {
    double[] dimensions = getDimensions();
    return dimensions[0] * dimensions[1] * dimensions[2];
  }

  @Override
  public int getBlockVolume() {
    int[] blockDimensions = getBlockDimensions();
    return blockDimensions[0] * blockDimensions[1] * blockDimensions[2];
  }

  @Override
  public boolean contains(KelpLocation location) {
    if (!worldName.equalsIgnoreCase(location.getWorldName())) {
      return false;
    }

    // X
    double maxX = Math.max(this.minPos.getX(), this.maxPos.getX());
    double minX = Math.min(this.minPos.getX(), this.maxPos.getX());

    // Y
    double maxY = Math.max(this.minPos.getY(), this.maxPos.getY());
    double minY = Math.min(this.minPos.getY(), this.maxPos.getY());

    // Z
    double maxZ = Math.max(this.minPos.getZ(), this.maxPos.getZ());
    double minZ = Math.min(this.minPos.getZ(), this.maxPos.getZ());

    if(location.getX() <= maxX && location.getX() >= minX) {
      if(location.getY() <= maxY && location.getY() >= minY) {
        return location.getZ() <= maxZ && location.getZ() >= minZ;
      }
    }
    return false;
  }

  @Override
  public KelpLocation getCenter() {
    return this.minPos.clone().add(this.maxPos).multiply(0.5);
  }

  public boolean hasCuboidIntersection(CuboidRegion region) {
    if (!region.getWorldName().equalsIgnoreCase(worldName)) {
      return false;
    }

    return (!(minPos.getX() > region.getMaxPos().getX() || region.getMinPos().getX() > maxPos.getX()
      || minPos.getY() > region.getMaxPos().getY() || region.getMinPos().getY() > maxPos.getY()
      || minPos.getZ() > region.getMaxPos().getZ() || region.getMinPos().getZ() > maxPos.getZ()));
  }

  public CuboidRegion cuboidIntersection(CuboidRegion region) {
    if (!hasCuboidIntersection(region)) {
      return null;
    }

    return CuboidRegion.create(
      getMinPos().getMaximalLocation(region.getMinPos()),
      getMaxPos().getMinimalLocation(region.getMaxPos()));
  }

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

  public Set<KelpBlock> getFaceBlocks(KelpBlockFace direction) {
    return getFace(direction).getBlocks();
  }

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

  public CuboidRegion getFace(KelpBlockFace direction) {
    CuboidRegion output = this.clone();
    output.expand(direction.getOppositeFace(), -output.measureBlocks(direction) + 1);
    return output;
  }

  @Override
  public void expand(double amount) {
    expand(amount, amount, amount, amount, amount, amount);
  }

  @Override
  public void expand(KelpBlockFace direction, double amount) {
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

  @Override
  public void expand(double negativeX, double positiveX, double negativeY, double positiveY, double negativeZ, double positiveZ) {
    this.minPos.subtract(negativeX, negativeY, negativeZ);
    this.maxPos.add(positiveX, positiveY, positiveZ);
    setBoundingPositions(minPos, maxPos);
  }

  @Override
  public CuboidRegion clone() {
    return CuboidRegion.create(this.minPos, this.maxPos);
  }

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
