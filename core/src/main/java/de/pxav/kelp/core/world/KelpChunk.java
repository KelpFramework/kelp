package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.version.ChunkVersionTemplate;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Chunk;

import java.util.Collection;
import java.util.Set;

/**
 * A chunk is a 16x16 area of blocks used by Minecraft
 * to generate the world and load a world dynamically.
 *
 * This class is a version-independent alternative to the
 * normal {@link Chunk} class provided by bukkit.
 *
 * @author pxav
 */
public class KelpChunk {

  private Chunk bukkitChunk;
  private ChunkVersionTemplate versionTemplate;

  KelpChunk(Chunk bukkitChunk, ChunkVersionTemplate versionTemplate) {
    this.bukkitChunk = bukkitChunk;
    this.versionTemplate = versionTemplate;
  }

  /**
   * Converts the bukkit chunk into a {@link KelpChunk}.
   *
   * @param bukkitChunk The bukkit chunk you want to convert.
   * @return The final {@link KelpChunk}
   */
  public static KelpChunk from(Chunk bukkitChunk) {
    return new KelpChunk(
      bukkitChunk,
      KelpPlugin.getInjector().getInstance(ChunkVersionTemplate.class)
    );
  }

  /**
   * Gets the world the given chunk is located in.
   *
   * @return The world the given chunk is located in.
   */
  public KelpWorld getWorld() {
    return versionTemplate.getWorld(this);
  }

  /**
   * Gets the X-coordinate in the chunk's world of the given
   * chunk. This can be used to compare and identify chunks.
   *
   * Note that this value does not return the absolute X-block-
   * coordinate where the chunk begins, but it returns the X
   * block value divided by 16. If you are at x=-35, then this
   * would return 2, because it is bigger than 32 (16*2) but smaller
   * than 48 (16*3).
   *
   * @return The X coordinate on the world's chunk grid.
   */
  public int getX() {
    return versionTemplate.getX(this);
  }

  /**
   * Gets the Z-coordinate in the chunk's world of the given
   * chunk. This can be used to compare and identify chunks.
   *
   * Note that this value does not return the absolute Z-block-
   * coordinate where the chunk begins, but it returns the Z
   * block value divided by 16. If you are at z=-35, then this
   * would return 2, because it is bigger than 32 (16*2) but smaller
   * than 48 (16*3).
   *
   * @return The Z coordinate on the world's chunk grid.
   */
  public int getZ() {
    return versionTemplate.getZ(this);
  }

  /**
   * Gets a block at the given location inside the chunk. The passed location
   * may not be from outside the chunk!
   *
   * @param location  The location where the block is located.
   * @return The block at the given location.
   */
  public KelpBlock getBlockAt(KelpLocation location) {
    return versionTemplate.getBlockAt(this, location);
  }

  /**
   * Determines whether the given location is inside the given chunk.
   *
   * @param location  The location that should be located in the chunk.
   * @return {@code true} if the location is inside the chunk.
   */
  public boolean contains(KelpLocation location) {
    return this.versionTemplate.contains(this, location);
  }

  /**
   * Determines whether the given block is inside the given chunk.
   *
   * @param block  The block that should be located in the chunk.
   * @return {@code true} if the block is inside the chunk.
   */
  public boolean contains(KelpBlock block) {
    return this.versionTemplate.contains(this, block.getLocation());
  }

  /**
   * Determines whether slime entities can spawn in the given chunk.
   *
   * @return {@code true} if slime entities can spawn in that chunk.
   */
  public boolean isSlimeChunk() {
    return versionTemplate.isSlimeChunk(this);
  }

  /**
   * Gets a collection of all players that are currently inside the chunk.
   *
   * @return A collection of all players that are currently inside the chunk.
   */
  public Collection<KelpPlayer> getPlayers() {
    return versionTemplate.getPlayers(this);
  }

  /**
   * Gets a collection of all players that are currently inside the chunk.
   *
   * @return A collection of all players that are currently inside the chunk.
   */
  public Collection<KelpEntity<?>> getEntities() {
    return versionTemplate.getEntities(this);
  }

  /**
   * Gets the approximate center block of this chunk at the given height.
   * As a chunk is 16x16 blocks, there is no exact center block, which
   * is why this method can only return an approximation.
   *
   * @param height The height of the desired center block location.
   * @return The approximate center of the given chunk.
   */
  public KelpLocation getCenter(int height) {
    return KelpLocation.from(getWorld().getName(), getX() << 4, height, getZ() << 4).add(7, 0, 7);
  }

  /**
   * Gets the chunk which is west of the current chunk.
   *
   * @return The chunk west of the current chunk.
   */
  public KelpChunk getWestEnclosedChunk() {
    return getNorthWesternBlock(10).getLocation().add(-2, 0, 2).getChunk();
  }

  /**
   * Gets the chunk which is north of the current chunk.
   *
   * @return The chunk north of the current chunk.
   */
  public KelpChunk getNorthEnclosedChunk() {
    return getNorthEasternBlock(10).getLocation().add(-2, 0, -2).getChunk();
  }

  /**
   * Gets the chunk which is north of the current chunk.
   *
   * @return The chunk north of the current chunk.
   */
  public KelpChunk getEastEnclosedChunk() {
    return getNorthEasternBlock(10).getLocation().add(2, 0, 2).getChunk();
  }

  /**
   * Gets the chunk which is south of the current chunk.
   *
   * @return The chunk south of the current chunk.
   */
  public KelpChunk getSouthEnclosedChunk() {
    return getSouthWesternBlock(10).getLocation().add(2, 0, 2).getChunk();
  }

  /**
   * Gets the chunk which is south west of the current chunk.
   *
   * @return The chunk south west of the current chunk.
   */
  public KelpChunk getSouthWestEnclosedChunk() {
    return getSouthWesternBlock(10).getLocation().add(-2, 0, 2).getChunk();
  }

  /**
   * Gets the chunk which is north west of the current chunk.
   *
   * @return The chunk north west of the current chunk.
   */
  public KelpChunk getNorthWestEnclosedChunk() {
    return getNorthWesternBlock(10).getLocation().add(-2, 0, -2).getChunk();
  }

  /**
   * Gets the chunk which is north east of the current chunk.
   *
   * @return The chunk north east of the current chunk.
   */
  public KelpChunk getNorthEastEnclosedChunk() {
    return getNorthEasternBlock(10).getLocation().add(2, 0, -2).getChunk();
  }

  /**
   * Gets the chunk which is south east of the current chunk.
   *
   * @return The chunk south east of the current chunk.
   */
  public KelpChunk getSouthEastEnclosedChunk() {
    return getSouthEasternBlock(10).getLocation().add(2, 0, 2).getChunk();
  }

  /**
   * Gets the block which is most north east in this chunk.
   *
   * @param height The height of the desired block.
   * @return The most north eastern block of this chunk.
   */
  public KelpBlock getNorthEasternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16 + 15,
      height,
      getZ() * 16
    );
    return getWorld().getBlockAt(location);
  }

  /**
   * Gets the block which is most north west in this chunk.
   *
   * @param height The height of the desired block.
   * @return The most north western block of this chunk.
   */
  public KelpBlock getNorthWesternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16,
      height,
      getZ() * 16
    );
    return getWorld().getBlockAt(location);
  }

  /**
   * Gets the block which is most south east in this chunk.
   *
   * @param height The height of the desired block.
   * @return The most south eastern block of this chunk.
   */
  public KelpBlock getSouthEasternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16 + 15,
      height,
      getZ() * 16 + 15
    );
    return getWorld().getBlockAt(location);
  }

  /**
   * Gets the block which is most south west in this chunk.
   *
   * @param height The height of the desired block.
   * @return The most south western block of this chunk.
   */
  public KelpBlock getSouthWesternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16,
      height,
      getZ() * 16 + 15
    );
    return getWorld().getBlockAt(location);
  }

  /**
   * Loads the chunk. This will make the chunk passable for players and
   * tick operations such as redstone clocks or crop growing will be performed
   * again. To save performance, chunks are unloaded by bukkit if they are not used.
   * If you need to prevent that you can use {@link #addForceLoadFlag(Class)}
   * to keep the chunk loaded until you manually unload it again. But please
   * keep in mind that this might have bad performance impact.
   */
  public void load() {
    versionTemplate.load(this);
  }

  /**
   * Unloads the chunk. That means tick operations in this
   * chunk will no longer be performed. Redstone clocks will stop running
   * and crops will stop growing. This can be reversed at any time using
   * {@link #load()}.
   */
  public void unload() {
    versionTemplate.unload(this);
  }

  /**
   * Checks whether the given chunk is currently loaded. That
   * means it checks whether tick operations are currently ran on
   * this chunk.
   *
   * @return {@code true} if the chunk is currently loaded.
   */
  public boolean isLoaded() {
    return versionTemplate.isLoaded(this);
  }

  /**
   * Adds a force load flag to the given chunk. A force load flag means that
   * the chunk cannot be unloaded by bukkit randomly, but keeps loaded until you
   * revert that action by yourself ({@link #removeForceLoadFlag(Class)}).
   *
   * If you call this method, {@link #unload()} won't have an effect
   * as Kelp will immediately load the chunk again if there is a flag to keep
   * it loaded. So if you want to unload the chunk, call {@link #removeForceLoadFlag(Class)}
   * first.
   *
   * @param plugin  The plugin that should keep the chunk loaded.
   *                This is important when you remove your flag,
   *                but another plugin still relies on the chunk to be loaded,
   *                the chunk will be kept loaded until the other plugin(s)
   *                unload the chunk as well.
   */
  public void addForceLoadFlag(Class<? extends KelpApplication> plugin) {
    versionTemplate.addForceLoadFlag(this, plugin);
  }

  /**
   * Removes a force load flag from the given chunk again. A force load flag means that
   * the chunk cannot be unloaded by bukkit randomly, but keeps loaded until you
   * revert that action by yourself using this method. Such a flag can be assigned
   * to a chunk using {@link #addForceLoadFlag(Class)}.
   *
   * @param plugin  The plugin that removes their flag. If another plugin has still
   *                loaded this chunk, it won't be unloaded until all plugins have removed
   *                their flag.
   */
  public void removeForceLoadFlag(Class<? extends KelpApplication> plugin) {
    versionTemplate.removeForceLoadFlag(this, plugin);
  }

  /**
   * Gets all {@link KelpApplication}s that are currently forcing this chunk to keep loaded.
   *
   * @return A set of all plugin main classes that force the chunk to keep loaded.
   */
  public Set<Class<? extends KelpApplication>> getForceLoadingPlugins() {
    return versionTemplate.getForceLoadFlagPlugins(this);
  }

  /**
   * Gets the bukkit chunk instance of this KelpChunk.
   *
   * @return The bukkit instance of this chunk.
   */
  public Chunk getBukkitChunk() {
    return bukkitChunk;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof KelpChunk)) {
      return false;
    }

    KelpChunk chunk = (KelpChunk) object;
    return chunk.getZ() == this.getZ()
      && chunk.getX() == this.getX()
      && chunk.getWorld().getName().equalsIgnoreCase(this.getWorld().getName());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(this.getWorld().getName())
      .append(this.getX())
      .append(this.getZ())
      .toHashCode();
  }
}
