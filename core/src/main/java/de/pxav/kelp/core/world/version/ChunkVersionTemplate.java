package de.pxav.kelp.core.world.version;

import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;

import java.util.Collection;
import java.util.Set;

/**
 * This version template is used to handle version-dependent operations
 * of {@link KelpChunk}s.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ChunkVersionTemplate {

  /**
   * Determines whether the given location is inside the given chunk.
   *
   * @param chunk     The chunk you want to check.
   * @param location  The location that should be located in the chunk.
   * @return {@code true} if the location is inside the chunk.
   */
  public abstract boolean contains(KelpChunk chunk, KelpLocation location);

  /**
   * Gets a block at the given location inside the chunk. The passed location
   * may not be from outside the chunk!
   *
   * @param chunk     The chunk you want to get a block of.
   * @param location  The location where the block is located.
   * @return The block at the given location.
   */
  public abstract KelpBlock getBlockAt(KelpChunk chunk, KelpLocation location);

  //todo getEntities()

  /**
   * Gets a collection of all players that are currently inside the chunk.
   *
   * @param chunk The chunk you want to get the players of.
   * @return A collection of all players that are currently inside the chunk.
   */
  public abstract Collection<KelpPlayer> getPlayers(KelpChunk chunk);

  /**
   * Gets the world the given chunk is located in.
   *
   * @param chunk The chunk you want to get the world of.
   * @return The world the given chunk is located in.
   */
  public abstract KelpWorld getWorld(KelpChunk chunk);

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
   * @param chunk The chunk you want to get the X-coordinate of.
   * @return The X coordinate on the world's chunk grid.
   */
  public abstract int getX(KelpChunk chunk);

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
   * @param chunk The chunk you want to get the Z-coordinate of.
   * @return The Z coordinate on the world's chunk grid.
   */
  public abstract int getZ(KelpChunk chunk);

  /**
   * Checks whether the given chunk is currently loaded. That
   * means it checks whether tick operations are currently ran on
   * this chunk.
   *
   * @param chunk The chunk you want to check if its loaded.
   * @return {@code true} if the chunk is currently loaded.
   */
  public abstract boolean isLoaded(KelpChunk chunk);

  /**
   * Loads the chunk. This will make the chunk passable for players and
   * tick operations such as redstone clocks or crop growing will be performed
   * again. To save performance, chunks are unloaded by bukkit if they are not used.
   * If you need to prevent that you can use {@link #addForceLoadFlag(KelpChunk, Class)}
   * to keep the chunk loaded until you manually unload it again. But please
   * keep in mind that this might have bad performance impact.
   *
   * @param chunk The chunk to be loaded. If the chunk has not been loaded before,
   *              the world will be generated at that point.
   */
  public abstract void load(KelpChunk chunk);

  /**
   * Unloads the chunk. That means tick operations in this
   * chunk will no longer be performed. Redstone clocks will stop running
   * and crops will stop growing. This can be reversed at any time using
   * {@link #load(KelpChunk)}.
   *
   * @param chunk The chunk you want to unload.
   */
  public abstract void unload(KelpChunk chunk);

  /**
   * Adds a force load flag to the given chunk. A force load flag means that
   * the chunk cannot be unloaded by bukkit randomly, but keeps loaded until you
   * revert that action by yourself ({@link #removeForceLoadFlag(KelpChunk, Class)}).
   *
   * If you call this method, {@link #unload(KelpChunk)} won't have an effect
   * as Kelp will immediately load the chunk again if there is a flag to keep
   * it loaded. So if you want to unload the chunk, call {@link #removeForceLoadFlag(KelpChunk, Class)}
   * first.
   *
   * @param chunk   The chunk you want to keep loaded.
   * @param plugin  The plugin that should keep the chunk loaded.
   *                This is important when you remove your flag,
   *                but another plugin still relies on the chunk to be loaded,
   *                the chunk will be kept loaded until the other plugin(s)
   *                unload the chunk as well.
   */
  public abstract void addForceLoadFlag(KelpChunk chunk, Class<? extends KelpApplication> plugin);

  /**
   * Removes a force load flag from the given chunk again. A force load flag means that
   * the chunk cannot be unloaded by bukkit randomly, but keeps loaded until you
   * revert that action by yourself using this method. Such a flag can be assigned
   * to a chunk using {@link #addForceLoadFlag(KelpChunk, Class)}.
   *
   * @param chunk   The chunk to remove the force load flag of.
   * @param plugin  The plugin that removes their flag. If another plugin has still
   *                loaded this chunk, it won't be unloaded until all plugins have removed
   *                their flag.
   */
  public abstract void removeForceLoadFlag(KelpChunk chunk, Class<? extends KelpApplication> plugin);

  /**
   * Gets all {@link KelpApplication}s that are currently forcing this chunk to keep loaded.
   *
   * @param chunk The chunk you want to get the plugins of.
   * @return A set of all plugin main classes that force the chunk to keep loaded.
   */
  public abstract Set<Class<? extends KelpApplication>> getForceLoadFlagPlugins(KelpChunk chunk);

  /**
   * Determines whether slime entities can spawn in the given chunk.
   *
   * @param chunk The chunk you want to check.
   * @return {@code true} if slime entities can spawn in that chunk.
   */
  public abstract boolean isSlimeChunk(KelpChunk chunk);

  public abstract Collection<KelpEntity<?>> getEntities(KelpChunk kelpChunk);
}
