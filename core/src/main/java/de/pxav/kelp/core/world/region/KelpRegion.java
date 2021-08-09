package de.pxav.kelp.core.world.region;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.event.kelpevent.region.PlayerEnterRegionEvent;
import de.pxav.kelp.core.particle.visualize.ParticleVisualizable;
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

/**
 * A KelpRegion can be any collection of blocks in a world that are grouped
 * together as one. This can be of different shapes: for example a cube or a sphere.
 *
 * A region can be expanded and moved in the 3D-space and you can do operations
 * with its blocks.
 *
 * You can also listen for region events, for example the {@link PlayerEnterRegionEvent},
 * which is triggered each time a player enters a registered region. For a region to
 * be recognized by listeners, you have to enable listeners for it using
 * {@link #enableListeners()}, which can be disabled at every time using
 * {@link #disableListeners()}.
 *
 * There are different sub-types of KelpRegions, each of which represent different
 * shapes and implement own features such as the simple {@link CuboidRegion} or the
 * {@link EllipsoidRegion}.
 *
 * @author pxav
 */
@Singleton
public abstract class KelpRegion implements Cloneable, ParticleVisualizable {

  protected KelpRegionRepository regionRepository;

  // the region id used by the listener system for simplified
  protected UUID regionId = UUID.randomUUID();

  // the name of the world this region is located in
  protected String worldName;

  // the lowermost corner of the region with the lowest axis values
  protected KelpLocation minPos;

  // the uppermost corner of the region with the highest axis values
  protected KelpLocation maxPos;

  public KelpRegion(KelpRegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  /**
   * Moves the region into the direction of the given
   * {@link Vector}.
   *
   * @param vector The vector providing the direction and
   *               power of the movement.
   */
  public void move(Vector vector) {
    this.disableListeners();
    this.moveIgnoreListeners(vector);
    this.enableListeners();
  }

  /**
   * Moves the region into the direction of the given
   * {@link Vector}.
   *
   * @param vector The vector providing the direction and
   *               power of the movement.
   */
  protected abstract void moveIgnoreListeners(Vector vector);

  /**
   * Moves the region into a certain direction defined by
   * the different axis values.
   *
   * @param dx How far the region should be moved on the x-axis
   * @param dy How far the region should be moved on the y-axis
   * @param dz How far the region should be moved on the z-axis
   */
  public void move(double dx, double dy, double dz) {
    this.disableListeners();
    this.moveIgnoreListeners(dx, dy, dz);
    this.enableListeners();
  }

  /**
   * Moves the region into a certain direction defined by
   * the different axis values.
   *
   * @param dx How far the region should be moved on the x-axis
   * @param dy How far the region should be moved on the y-axis
   * @param dz How far the region should be moved on the z-axis
   */
  protected abstract void moveIgnoreListeners(double dx, double dy, double dz);

  /**
   * Gets the total volume of this region, which depends
   * on the region's shape. This method calculates the exact
   * value and should be used if your region is not exactly
   * based on block locations. Otherwise, use {@link #getBlockVolume()}.
   *
   * @return The exact volume of this region in blocks.
   */
  public abstract double getVolume();

  /**
   * Gets the total volume of this region, which depends
   * on the region's shape. This method uses the integer block
   * location values and is therefore an approximation of {@link #getVolume()}
   * in most cases.
   *
   * @return The approximate volume of this location in blocks.
   */
  public abstract int getBlockVolume();

  /**
   * Gets the center of the given region shape. The exact location
   * of the center depends on the implementation, but in most cases
   * it is the cubic center of a region.
   *
   * @return The center location of this region.
   */
  public abstract KelpLocation getCenter();

  /**
   * Gets a set of all blocks which are on the regions surface.
   *
   * @return A set of blocks containing all blocks on the regions surface.
   */
  public abstract Set<KelpBlock> getSurfaceBlocks();

  /**
   * Gets all blocks contained by this region.
   * This can be used to visualize its shape.
   *
   * @return A set of all blocks contained by this region.
   */
  public abstract Set<KelpBlock> getBlocks();

  /**
   * Gets all chunks covered by this region no matter if they
   * are loaded or not. If you only want loaded chunks,
   * use {@link #getLoadedChunks()} instead.
   *
   * @return A set of all chunks covered by this region.
   */
  public abstract Set<KelpChunk> getChunks();

  /**
   * Gets all chunks that are loaded and covered by this region.
   * If you want to include unloaded chunks as well, use {@link #getChunks()}
   * instead.
   * This method automatically applies the changes to the listener system
   * if it is enabled for this region.
   *
   * @return A set of all loaded chunks covered by this region.
   */
  public abstract Set<KelpChunk> getLoadedChunks();

  /**
   * Expands the current region by a certain multiplier.
   *
   * @param amount The amount of expansion in blocks.
   */
  public void expand(double amount) {
    this.disableListeners();
    this.expandIgnoreListeners(amount);
    this.enableListeners();
  }

  /**
   * Expands the current region by a certain multiplier.
   * This method does not update the listeners for the region.
   *
   * @param amount The amount of expansion in blocks.
   */
  protected abstract void expandIgnoreListeners(double amount);

  /**
   * Expands the current region into a specific direction by
   * a given multiplier.
   * This method automatically applies the changes to the listener system
   * if it is enabled for this region.
   *
   * @param direction The direction to expand the region in.
   * @param amount The amount of expansion in blocks.
   */
  public void expand(KelpBlockFace direction, double amount) {
    this.disableListeners();
    this.expandIgnoreListeners(direction, amount);
    this.enableListeners();
  }

  /**
   * Expands the current region into a specific direction by
   * a given multiplier.
   * This method does not update the listeners for the region.
   *
   * @param direction The direction to expand the region in.
   * @param amount The amount of expansion in blocks.
   */
  protected abstract void expandIgnoreListeners(KelpBlockFace direction, double amount);

  /**
   * Expands the current region in the given axis.
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
  public void expand(double negativeX,
                              double positiveX,
                              double negativeY,
                              double positiveY,
                              double negativeZ,
                              double positiveZ) {
    this.disableListeners();
    this.expandIgnoreListener(negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ);
    this.enableListeners();
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
  protected abstract void expandIgnoreListener(double negativeX,
                              double positiveX,
                              double negativeY,
                              double positiveY,
                              double negativeZ,
                              double positiveZ);

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
  public abstract boolean contains(double x, double y, double z);

  /**
   * Checks whether the given location is contained by this region.
   * This method also checks if the worlds are equal.
   *
   * @param location The location to check for.
   * @return {@code true} if the location is contained by this region.
   */
  public boolean contains(KelpLocation location) {
    if (!worldName.equalsIgnoreCase(location.getWorldName())) {
      return false;
    }
    return contains(location.getX(), location.getZ(), location.getZ());
  }

  /**
   * Checks whether this region contains the given
   * {@link KelpPlayer}.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is contained by this region.
   */
  public boolean contains(KelpPlayer player) {
    return contains(player.getLocation());
  }

  /**
   * Checks whether this region contains the given
   * {@link KelpBlock}.
   *
   * @param block The block you want to check.
   * @return {@code true} if the block is contained by this region.
   */
  public boolean contains(KelpBlock block) {
    return contains(block.getLocation());
  }

  /**
   * Gets the approximate x, y, and z dimensions of this region.
   * So it basically measures how long the region is on a
   * specific axis of the world in blocks without fraction digits.
   * If you want to get the exact dimensions, use {@link #getDimensions()}
   * instead.
   *
   * @return The dimensions of this region in the format [x, y, z]
   */
  public int[] getBlockDimensions() {
    return new int[] {
      maxPos.getBlockX() - minPos.getBlockX() + 1,
      maxPos.getBlockY() - minPos.getBlockY() + 1,
      maxPos.getBlockZ() - minPos.getBlockZ() + 1
    };
  }

  /**
   * Gets the exact x, y, and z dimensions of this region.
   * So it basically measures how long the region is on a
   * specific axis of the world in blocks.
   *
   * @return The dimensions of this region in the format [x, y, z]
   */
  public double[] getDimensions() {
    return new double[] {
      maxPos.getX() - minPos.getX() + 1,
      maxPos.getY() - minPos.getY() + 1,
      maxPos.getZ() - minPos.getZ() + 1
    };
  }

  /**
   * Gets the cubic outer corners of this region. It basically
   * interpolates the missing corners based on the uppermost
   * and lowermost corners ({@link #getMaxPos()} and {@link #getMinPos()}.
   *
   * @return An array containing all outer cubic corners of this region.
   */
  public KelpLocation[] getCuboidOuterCorners() {
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

  /**
   * Gets the name of the world this region is located in.
   * @return
   */
  public String getWorldName() {
    return worldName;
  }

  /**
   * Sets the name of the world this region is located in.
   *
   * @param worldName The name of this region's world.
   */
  public void setWorldName(String worldName) {
    this.worldName = worldName;
  }

  /**
   * Gets the {@link KelpWorld} this region is located in.
   *
   * @return The world this region is located in.
   *         {@code null} if the world has not been set.
   */
  public KelpWorld getWorld() {
    if (this.worldName == null) {
      return null;
    }
    return KelpWorld.from(worldName);
  }

  /**
   * Gets the lowermost point of this region with
   * the lowest axis values.
   *
   * @return The lowermost point of this region.
   */
  public KelpLocation getMinPos() {
    return minPos;
  }

  /**
   * Gets the uppermost point of this region with
   * the highest axis values.
   *
   * @return The uppermost point of this region.
   */
  public KelpLocation getMaxPos() {
    return maxPos;
  }

  /**
   * Converts and clones this region into a cuboid region using its min and max
   * pos as outer corners.
   *
   * @return The cuboid region based on the min and max pos of this region.
   */
  public CuboidRegion toCuboid() {
    return CuboidRegion.create(minPos, maxPos);
  }

  /**
   * Converts and clones this region into an ellipsoid region, which uses
   * the given min and max pos as outer corners, so that the ellipsoid
   * fits into the cube with maximum size.
   *
   * @return The {@link EllipsoidRegion} based on the min and max pos of this region.
   */
  public EllipsoidRegion toEllipsoid() {
    return EllipsoidRegion.create(minPos, maxPos);
  }

  /**
   * Gets the unique id for this region. This id can be
   * used to identify regions without having to compare dynamic values
   * such as their corners/etc. this ensures increased consistency.
   *
   * @return The region to get the unique id of.
   */
  public UUID getRegionId() {
    return regionId;
  }

  /**
   * Enables all listeners for this region.
   * This means that events such as {@link PlayerEnterRegionEvent}
   * will be triggered. Region listeners might become relatively
   * performance intensive if you have many of them, so they are
   * disabled by default.
   *
   * Always call this method first if you plan to use them.
   * They can be disabled at any time using {@link #disableListeners()}
   */
  public void enableListeners() {
    if (listenersEnabled()) {
      return;
    }
    regionRepository.listenTo(this);
  }

  /**
   * Disables all listeners for this region.
   * This means that events such as {@link PlayerEnterRegionEvent}
   * won't be triggered anymore, which saves performance.
   *
   * Disabled listeners are the default for all regions.
   */
  public void disableListeners() {
    if (!listenersEnabled()) {
      return;
    }
    regionRepository.stopListeningTo(this);
  }

  /**
   * Checks whether listeners are currently enabled for this region.
   * Listeners are responsible for triggering events such as
   * {@link PlayerEnterRegionEvent}
   *
   * @return {@code true} if listeners are enabled.
   */
  public boolean listenersEnabled() {
    return regionRepository.isListeningTo(this);
  }

  @Override
  public abstract KelpRegion clone();

  public abstract boolean equals(Object object);

  public abstract int hashCode();

  protected static KelpRegionRepository getRegionRepository() {
    return KelpPlugin.getInjector().getInstance(KelpRegionRepository.class);
  }

}
