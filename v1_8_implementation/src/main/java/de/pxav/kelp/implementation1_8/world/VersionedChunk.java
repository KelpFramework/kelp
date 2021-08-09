package de.pxav.kelp.implementation1_8.world;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.*;
import de.pxav.kelp.core.world.version.ChunkVersionTemplate;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Implements all version-dependent operations of {@link KelpChunk}.
 *
 * @author pxav
 */
@Versioned
@Singleton
public class VersionedChunk extends ChunkVersionTemplate {

  @Inject private ForcedChunkLoader forcedChunkLoader;

  /**
   * Determines whether the given location is inside the given chunk.
   *
   * @param chunk     The chunk you want to check.
   * @param location  The location that should be located in the chunk.
   * @return {@code true} if the location is inside the chunk.
   */
  @Override
  public boolean contains(KelpChunk chunk, KelpLocation location) {
    KelpLocation firstPosition = chunk.getNorthEasternBlock(0).getLocation();
    KelpLocation secondPosition = chunk.getSouthWesternBlock(256).getLocation();

    double maxX = Math.max(firstPosition.getX(), secondPosition.getX());
    double minX = Math.min(firstPosition.getX(), secondPosition.getX());

    double maxY = Math.max(firstPosition.getY(), secondPosition.getY());
    double minY = Math.min(firstPosition.getY(), secondPosition.getY());

    double maxZ = Math.max(firstPosition.getZ(), secondPosition.getZ());
    double minZ = Math.min(firstPosition.getZ(), secondPosition.getZ());

    if(location.getX() <= maxX && location.getX() >= minX) {
      if(location.getY() <= maxY && location.getY() >= minY)
        return location.getZ() <= maxZ && location.getZ() >= minZ;
    }

    return false;
  }

  /**
   * Gets a block at the given location inside the chunk. The passed location
   * may not be from outside the chunk!
   *
   * @param chunk     The chunk you want to get a block of.
   * @param location  The location where the block is located.
   * @return The block at the given location.
   */
  @Override
  public KelpBlock getBlockAt(KelpChunk chunk, KelpLocation location) {
    return KelpBlock.from(chunk.getBukkitChunk().getBlock(
      location.getBlockX(),
      location.getBlockY(),
      location.getBlockZ())
    );
  }

  /**
   * Gets a collection of all players that are currently inside the chunk.
   *
   * @param chunk The chunk you want to get the players of.
   * @return A collection of all players that are currently inside the chunk.
   */
  @Override
  public Collection<KelpPlayer> getPlayers(KelpChunk chunk) {
    Collection<KelpPlayer> output = Lists.newArrayList();
    net.minecraft.server.v1_8_R3.Chunk nmsChunk = craftChunk(chunk).getHandle();

    List<Entity>[] entitySlices = (List<Entity>[]) ReflectionUtil.getValue(nmsChunk, "entitySlices");
    if (entitySlices == null) {
      return output;
    }

    Arrays.stream(entitySlices).forEach(entitySlice -> {
      Object[] entityArray = entitySlice.toArray();

      for (Object entityObject : entityArray) {
        if (entityObject instanceof EntityHuman) {
          EntityHuman entityHuman = (EntityHuman) entityObject;
          Player player = (Player) entityHuman.getBukkitEntity();
          KelpPlayer kelpPlayer = KelpPlayer.from(player);
          output.add(kelpPlayer);
        }
      }

    });

    return output;
  }

  /**
   * Gets the world the given chunk is located in.
   *
   * @param chunk The chunk you want to get the world of.
   * @return The world the given chunk is located in.
   */
  @Override
  public KelpWorld getWorld(KelpChunk chunk) {
    return KelpWorld.from(chunk.getBukkitChunk().getWorld());
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
   * @param chunk The chunk you want to get the X-coordinate of.
   * @return The X coordinate on the world's chunk grid.
   */
  @Override
  public int getX(KelpChunk chunk) {
    return chunk.getBukkitChunk().getX();
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
   * @param chunk The chunk you want to get the Z-coordinate of.
   * @return The Z coordinate on the world's chunk grid.
   */
  @Override
  public int getZ(KelpChunk chunk) {
    return chunk.getBukkitChunk().getZ();
  }

  /**
   * Checks whether the given chunk is currently loaded. That
   * means it checks whether tick operations are currently ran on
   * this chunk.
   *
   * @param chunk The chunk you want to check if its loaded.
   * @return {@code true} if the chunk is currently loaded.
   */
  @Override
  public boolean isLoaded(KelpChunk chunk) {
    return chunk.getBukkitChunk().isLoaded();
  }

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
  @Override
  public void load(KelpChunk chunk) {
    chunk.getBukkitChunk().load();
  }

  /**
   * Unloads the chunk. That means tick operations in this
   * chunk will no longer be performed. Redstone clocks will stop running
   * and crops will stop growing. This can be reversed at any time using
   * {@link #load(KelpChunk)}.
   *
   * @param chunk The chunk you want to unload.
   */
  @Override
  public void unload(KelpChunk chunk) {
    chunk.getBukkitChunk().unload();
  }

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
  @Override
  public void addForceLoadFlag(KelpChunk chunk, Class<? extends KelpApplication> plugin) {
    forcedChunkLoader.forceLoadChunk(plugin, chunk);
  }

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
  @Override
  public void removeForceLoadFlag(KelpChunk chunk, Class<? extends KelpApplication> plugin) {
    forcedChunkLoader.removeForceLoadFlag(plugin, chunk);
  }

  /**
   * Gets all {@link KelpApplication}s that are currently forcing this chunk to keep loaded.
   *
   * @param chunk The chunk you want to get the plugins of.
   * @return A set of all plugin main classes that force the chunk to keep loaded.
   */
  @Override
  public Set<Class<? extends KelpApplication>> getForceLoadFlagPlugins(KelpChunk chunk) {
    return forcedChunkLoader.getForceLoadingPluginsOf(chunk);
  }

  /**
   * Determines whether slime entities can spawn in the given chunk.
   *
   * @param chunk The chunk you want to check.
   * @return {@code true} if slime entities can spawn in that chunk.
   */
  @Override
  public boolean isSlimeChunk(KelpChunk chunk) {
    long seed = chunk.getBukkitChunk().getWorld().getSeed();
    int x = chunk.getX();
    int y = chunk.getZ();
    return (new Random(
      seed
        + ((long) x * x * 4987142) + (x * 5947611L)
        + (long) y * y * 4392871L + (y * 389711L) ^ 987234911L)
    ).nextInt(10) == 0;
  }

  @Override
  public Collection<KelpEntity<?>> getEntities(KelpChunk kelpChunk) {
    Collection<KelpEntity<?>> output = Lists.newArrayList();
    net.minecraft.server.v1_8_R3.Chunk nmsChunk = craftChunk(kelpChunk).getHandle();

    List<Entity>[] entitySlices = (List<Entity>[]) ReflectionUtil.getValue(nmsChunk, "entitySlices");
    if (entitySlices == null) {
      return output;
    }

    for(int i = 0; i < 16; ++i) {
      Object[] var6;
      int var7 = (var6 = entitySlices[i].toArray()).length;


      for(int var8 = 0; var8 < var7; ++var8) {
        Object obj = var6[var8];
        if (obj instanceof net.minecraft.server.v1_8_R3.Entity) {
          KelpEntity<?> kelpEntity = KelpEntity.from(((net.minecraft.server.v1_8_R3.Entity)obj).getBukkitEntity());
          output.add(kelpEntity);
        }
      }
    }

    return output;
  }

  private CraftChunk craftChunk(KelpChunk chunk) {
    return (CraftChunk) chunk.getBukkitChunk();
  }

}
