package de.pxav.kelp.core.world.region;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import org.bukkit.util.Vector;

import javax.inject.Singleton;
import java.util.Set;
import java.util.UUID;

@Singleton
public abstract class KelpRegion implements Cloneable {

  protected KelpRegionRepository regionRepository;
  protected UUID regionId = UUID.randomUUID();
  protected String worldName;
  protected KelpLocation minPos;
  protected KelpLocation maxPos;

  public KelpRegion(KelpRegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  public abstract void move(Vector vector);

  public abstract void move(double dx, double dy, double dz);

  public abstract double getVolume();

  public abstract int getBlockVolume();

  public abstract KelpLocation getCenter();

  public abstract Set<KelpBlock> getSurfaceBlocks();

  public abstract Set<KelpBlock> getBlocks();

  public abstract Set<KelpChunk> getChunks();

  public abstract Set<KelpChunk> getLoadedChunks();

  public abstract void expand(double amount);

  public abstract void expand(KelpBlockFace direction, double amount);

  public abstract void expand(double negativeX,
                              double positiveX,
                              double negativeY,
                              double positiveY,
                              double negativeZ,
                              double positiveZ);

  public abstract boolean contains(KelpLocation location);

  public boolean contains(KelpPlayer player) {
    return contains(player.getLocation());
  }

  public boolean contains(KelpBlock block) {
    return contains(block.getLocation());
  }

  public boolean contains(double x, double y, double z) {
    if (worldName == null) {
      return false;
    }
    return contains(KelpLocation.from(worldName, x, y, z));
  }

  public int[] getBlockDimensions() {
    return new int[] {
      maxPos.getBlockX() - minPos.getBlockX() + 1,
      maxPos.getBlockY() - minPos.getBlockY() + 1,
      maxPos.getBlockZ() - minPos.getBlockZ() + 1
    };
  }

  public double[] getDimensions() {
    return new double[] {
      maxPos.getX() - minPos.getX() + 1,
      maxPos.getY() - minPos.getY() + 1,
      maxPos.getZ() - minPos.getZ() + 1
    };
  }

  public KelpLocation[] getOuterCorners() {
    return new KelpLocation[] {
      minPos,
      maxPos,
      KelpLocation.from(getWorld().getName(), minPos.getX(), minPos.getY(), maxPos.getZ()),
      KelpLocation.from(getWorld().getName(), minPos.getX(), maxPos.getY(), minPos.getZ()),
      KelpLocation.from(getWorld().getName(), maxPos.getX(), minPos.getY(), minPos.getZ()),
      KelpLocation.from(getWorld().getName(), minPos.getX(), maxPos.getY(), maxPos.getZ()),
      KelpLocation.from(getWorld().getName(), maxPos.getX(), maxPos.getY(), minPos.getZ()),
      KelpLocation.from(getWorld().getName(), maxPos.getX(), minPos.getY(), maxPos.getZ())
    };
  }

  public String getWorldName() {
    return worldName;
  }

  public void setWorldName(String worldName) {
    this.worldName = worldName;
  }

  public KelpWorld getWorld() {
    if (this.worldName == null) {
      return null;
    }
    return KelpWorld.from(worldName);
  }

  public KelpLocation getMinPos() {
    return minPos;
  }

  public KelpLocation getMaxPos() {
    return maxPos;
  }

  public CuboidRegion toCuboid() {
    return CuboidRegion.create(minPos, maxPos);
  }

  public EllipsoidRegion toEllipsoid() {
    return EllipsoidRegion.create(minPos, maxPos);
  }

  public UUID getRegionId() {
    return regionId;
  }

  @Override
  public abstract KelpRegion clone();

  public abstract boolean equals(Object object);

  public abstract int hashCode();

  protected static KelpRegionRepository getRegionRepository() {
    return KelpPlugin.getInjector().getInstance(KelpRegionRepository.class);
  }

}
