package de.pxav.kelp.implementation1_8.world;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.util.ItemDropType;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.*;
import de.pxav.kelp.core.world.util.ExplosionPower;
import de.pxav.kelp.core.world.util.WorldType;
import de.pxav.kelp.core.world.version.WorldVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * This class implements all version-dependent methods
 * of {@link KelpWorld}.
 *
 * @author pxav
 */
@Versioned
public class VersionedWorld extends WorldVersionTemplate {

  /**
   * Gets the spawn location of the given world. The spawn location is the
   * center of a radius in which players can spawn when initially joining a
   * new world.
   *
   * @param world The world you want to get the spawn location of.
   * @return The world's spawn location.
   */
  @Override
  public KelpLocation getSpawnLocation(KelpWorld world) {
    return KelpLocation.from(world.getBukkitWorld().getSpawnLocation());
  }

  /**
   * Gets the chunk at the given location. A chunk is a 16x16x(256 or 320 depending on your version)
   * cuboid region minecraft uses to generate and handle the world. More information
   * can be found in {@link KelpChunk}.
   *
   * @param world     The world from which you want to get the chunk of.
   * @param location  The location where the chunk is located.
   * @return The chunk in the given world at the given location.
   */
  @Override
  public KelpChunk getChunkAt(KelpWorld world, KelpLocation location) {
    return KelpChunk.from(world.getBukkitWorld().getChunkAt(location.getBukkitLocation()));
  }

  @Override
  public boolean isChunkLoaded(KelpWorld world, KelpLocation location) {
    WorldServer nmsWorld = craftWorld(world).getHandle();
    return nmsWorld.chunkProviderServer.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4);
  }

  /**
   * Checks whether this world is able to generate structures such as temples or villages.
   *
   * @param world The world you want to check.
   * @return {@code true} if the world can generate structures
   */
  @Override
  public boolean canGenerateStructures(KelpWorld world) {
    return world.getBukkitWorld().canGenerateStructures();
  }

  /**
   * Creates an explosion at the given location.
   *
   * @param world         The world to spawn the explosion at.
   * @param location      The center of the explosion
   * @param power         The power of the explosion. More information on explosion powers can be
   *                      found in {@link ExplosionPower}
   * @param breakBlocks   Whether the explosion should be able to break blocks.
   * @param igniteFire    Whether the explosion should be able to ignite a fire within its radius.
   */
  @Override
  public void createExplosion(KelpWorld world, KelpLocation location, ExplosionPower power, boolean breakBlocks, boolean igniteFire) {
    Location bukkitLocation = location.getBukkitLocation();
    world.getBukkitWorld().createExplosion(
      bukkitLocation.getX(),
      bukkitLocation.getY(),
      bukkitLocation.getZ(),
      power.getPower(),
      igniteFire,
      breakBlocks
    );
  }

  /**
   * Drops an item on the given world at the given location. This item will be
   * visible for all players on the world.
   *
   * @param world     The world to spawn the item on.
   * @param location  The location from where the item should drop
   * @param item      The item you want to drop
   * @param dropType  Whether the item should be dropped normally or naturally.
   *                  More information about the difference can be found in {@link ItemDropType}
   * @return The item entity that has been spawned at the given location.
   */
  @Override
  public DroppedItemEntity dropItem(KelpWorld world, KelpLocation location, KelpItem item, ItemDropType dropType) {
    if (dropType == ItemDropType.NORMAL) {
      return DroppedItemEntity.from(world.getBukkitWorld()
        .dropItem(
          location.getBukkitLocation(),
          item.getItemStack())
        );
    } else if (dropType == ItemDropType.NATURAL) {
      return DroppedItemEntity.from(world.getBukkitWorld()
        .dropItemNaturally(
          location.getBukkitLocation(),
          item.getItemStack())
      );
    }
    return null;
  }

  /**
   * Checks whether animals can spawn naturally on this world.
   *
   * @param world The world you want to check animal spawns for.
   * @return {@code true} if animals can spawn by default.
   */
  @Override
  public boolean animalsAllowed(KelpWorld world) {
    return world.getBukkitWorld().getAllowAnimals();
  }

  /**
   * Checks whether monsters such as creepers or zombies
   * can spawn naturally on this world.
   *
   * @param world The world you want to check monster spawns for.
   * @return {@code true} if monsters can spawn by default.
   */
  @Override
  public boolean monstersAllowed(KelpWorld world) {
    return world.getBukkitWorld().getAllowMonsters();
  }

  /**
   * Gets the {@link KelpBlock} at the given location of the world.
   *
   * @param world     The world you want to get the block of.
   * @param location  The exact location of the block you want to get.
   * @return The {@link KelpBlock} object at the given location.
   *         If the block's material is {@link de.pxav.kelp.core.inventory.material.KelpMaterial#AIR},
   *         the block won't be {@code null} but of type {@code AIR}
   */
  @Override
  public KelpBlock getBlockAt(KelpWorld world, KelpLocation location) {
    return KelpBlock.from(world.getBukkitWorld().getBlockAt(location.getBukkitLocation()));
  }

  /**
   * Gets the highest block at a given location.
   *
   * @param world     The world to check the location in.
   * @param location  The location containing the y-axis for the block check.
   * @return The highest {@link KelpBlock} at the provided location of the world.
   */
  @Override
  public KelpBlock getHighestBlockAt(KelpWorld world, KelpLocation location) {
    return KelpBlock.from(world.getBukkitWorld().getHighestBlockAt(location.getBukkitLocation()));
  }

  /**
   * Gets a collection of all {@link KelpChunk}s that are currently loaded in
   * the given world. This includes forced loaded chunks as well as naturally loaded chunks.
   *
   * @param world The world to get the loaded chunks of.
   * @return The collection of all chunks currently loaded in this world.
   */
  @Override
  public Collection<KelpChunk> getLoadedChunks(KelpWorld world) {
    Collection<KelpChunk> chunks = Lists.newArrayList();
    for (Chunk loadedChunk : world.getBukkitWorld().getLoadedChunks()) {
      chunks.add(KelpChunk.from(loadedChunk));
    }
    return chunks;
  }

  /**
   * Gets the difficulty of the current world. The difficulty gives an
   * indication of how difficult survival gameplay is. {@code Peaceful} means
   * players cannot become hungry and monsters won't spawn, while {@code hard}
   * says that lots of monsters will spawn and players lose saturation really
   * fast.
   *
   * @param world The world to get the difficulty of.
   * @return The current difficulty of the world.
   */
  @Override
  public Difficulty getDifficulty(KelpWorld world) {
    return world.getBukkitWorld().getDifficulty();
  }

  /**
   * Gets the environment/world type. The world type says which dimension
   * is currently used by the world generator. This also affects the fog effects
   * of the world. While in overworld ({@code NORMAL}) there won't be any
   * special background or fog, the background of an {@code END} world
   * will be purple for example.
   *
   * This is similar to the {@code #getEnvironment()} method of a normal
   * bukkit world.
   *
   * @param world The world to get the world type of.
   * @return The type of the given world.
   */
  @Override
  public WorldType getWorldType(KelpWorld world) {
    World.Environment environment = world.getBukkitWorld().getEnvironment();
    switch (environment) {
      case NETHER:
        return WorldType.THE_NETHER;
      case THE_END:
        return WorldType.THE_END;
      case NORMAL:
        return WorldType.NORMAL;
    }
    return WorldType.NORMAL;
  }

  /**
   * Gets the humidity at a specific location in the world.
   * The humidity is dependent on the biome at the given location.
   *
   * @param world     The world you want to check the humidity in.
   * @param location  The location to check the humidity at.
   * @return The humidity value at the given location in the given world.
   */
  @Override
  public double getHumidityAt(KelpWorld world, KelpLocation location) {
    return world.getBukkitWorld().getHumidity((int) location.getX(), (int) location.getZ());
  }

  /**
   * Checks whether the spawn chunks should be kept in memory although
   * no players are currently in the spawn area.
   *
   * @param world The world you want to check the spawn chunks of.
   * @return {@code true} if the spawn chunks are kept in memory.
   */
  @Override
  public boolean shouldKeepSpawnInMemory(KelpWorld world) {
    return world.getBukkitWorld().getKeepSpawnInMemory();
  }

  /**
   * Changes whether the spawn chunks of a world should be kept
   * loaded/in memory although no players are currently in the spawn
   * area. This might improve performance if you are commonly spawning
   * new players to a world (such as in a server lobby).
   *
   * @param world The world you want to set the spawn chunk settings of.
   * @param keep {@code true} if the spawn chunks should be kept loaded over the server runtime.
   */
  @Override
  public void setKeepSpawnInMemory(KelpWorld world, boolean keep) {
    world.getBukkitWorld().setKeepSpawnInMemory(keep);
  }

  /**
   * Gets the full time of a world. This is the full absolute
   * in-game daytime.
   *
   * @param world The world you want to get the time of.
   * @return The full (absolute) time of the given world.
   */
  @Override
  public long getFullTime(KelpWorld world) {
    return world.getBukkitWorld().getFullTime();
  }

  /**
   * Gets the full in-game time of this world from the initial world generation.
   *
   * @param world The world you want to get the game time of.
   * @return The game time that has passed on the given world.
   */
  @Override
  public long getGameTime(KelpWorld world) {
    return ((CraftWorld)world.getBukkitWorld()).getHandle().getWorldData().getTime();
  }

  /**
   * Gets the relative in-game time of the given world.
   * This time is calculated from the world's full time:
   * {@code #getFullTime() % 24000L}.
   *
   * This time cannot be negative. If a negative value is returned from
   * the above operation, a full day will be added to this value.
   *
   * @param world The world you want to get the relative time of.
   * @return The relative in-game time of the given world.
   */
  @Override
  public long getTime(KelpWorld world) {
    return world.getBukkitWorld().getTime();
  }

  /**
   * Gets all players that are currently on the given world.
   *
   * @param world The world you want to get the players of.
   * @return A collection of all players that are currently on this world.
   */
  @Override
  public Collection<KelpPlayer> getPlayers(KelpWorld world) {
    Collection<KelpPlayer> output = Lists.newArrayList();
    for (EntityHuman human : craftWorld(world).getHandle().players) {
      HumanEntity bukkitEntity = human.getBukkitEntity();
      if (bukkitEntity instanceof Player) {
        output.add(KelpPlayer.from((Player) bukkitEntity));
      }
    }
    return output;
  }

  /**
   * Gets all entities that are currently on the given world.
   * This includes players as well as normal entities. If you only
   * want to iterate players, you can also use {@link #getPlayers(KelpWorld)}
   * instead.
   *
   * @param world The world you want to get the players of.
   * @return A collection of all entities that are currently on this world.
   */
  @Override
  public Collection<KelpEntity<?>> getEntities(KelpWorld world) {
    Collection<KelpEntity<?>> output = Lists.newArrayList();
    for (Entity entity : craftWorld(world).getHandle().entityList) {
      org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();
      output.add(KelpEntity.from(bukkitEntity));
    }
    return output;
  }

  /**
   * Gets the current pvp mode of the world. If players are allowed to
   * fight each other, this will return {@code true}.
   *
   * @param world The world you want to check the pvp setting of.
   * @return {@code true} if pvp is enabled on this world.
   */
  @Override
  public boolean isPvPEnabled(KelpWorld world) {
    return world.getBukkitWorld().getPVP();
  }

  /**
   * Sets the current pvp mode of the world. If players should be allowed
   * to fight each other, set this to {@code true}.
   *
   * @param world The world you want to set the pvp setting of.
   * @param pvp   {@code true} if players should be able to fight each other.
   */
  @Override
  public void setPVP(KelpWorld world, boolean pvp) {
    world.getBukkitWorld().setPVP(pvp);
  }

  /**
   * Gets the seed of the world generator of this world. A seed is a constant value
   * used by the {@code Perlin noise} world generation algorithm to randomize
   * biomes and structures. Seeds are usually compatible across different versions, but
   * might not output the same result in differing versions due to new structures and
   * biomes that have been added over time.
   *
   * @param world The world you want to get the seed of.
   * @return The world's seed.
   */
  @Override
  public long getSeed(KelpWorld world) {
    return world.getBukkitWorld().getSeed();
  }

  /**
   * Gets the sea level of this world.
   *
   * @param world The world you want to get the sea level of.
   * @return The world's sea level.
   */
  @Override
  public int getSeaLevel(KelpWorld world) {
    return world.getBukkitWorld().getSeaLevel();
  }

  /**
   * Gets the temperature of a world at a specific location.
   * Temperatures are depending on the biome and exact location (height specifically).
   * A temperature indicates whether it can snow or rain at a specific location
   * or whether there is any precipitation at all. The desert biome for example
   * has a relatively high temperature of {@code 2.0d}, while Snowy Beach has {@code 0.05}
   * for example.
   *
   * @param world     The world you want to get the temperature in.
   * @param location  The location you want to get the temperature at.
   * @return The exact temperature at the given location in the current world.
   */
  @Override
  public double getTemperatureAt(KelpWorld world, KelpLocation location) {
    return world.getBukkitWorld().getTemperature((int) location.getX(), (int) location.getZ());
  }

  /**
   * Gets the duration of the current thunder in the world in ticks.
   *
   * @param world The world you want to get the thunder duration of.
   * @return The thunder duration in ticks.
   */
  @Override
  public int getThunderDuration(KelpWorld world) {
    return world.getBukkitWorld().getThunderDuration();
  }

  /**
   * Gets the unique id of this world.
   *
   * @param world This world's unique id.
   * @return The world's {@link UUID}.
   */
  @Override
  public UUID getUUID(KelpWorld world) {
    return world.getBukkitWorld().getUID();
  }

  /**
   * Gets the duration of the current weather state in ticks.
   * This counts for all weather types (including clear weather)
   *
   * @param world The world to get the weather duration of.
   * @return The current weather duration in ticks.
   */
  @Override
  public int getWeatherDuration(KelpWorld world) {
    return world.getBukkitWorld().getWeatherDuration();
  }

  /**
   * Checks whether there is a storm in the given world.
   *
   * @param world The world you want to check.
   * @return {@code true} whether there is currently a storm in the given world.
   */
  @Override
  public boolean hasStorm(KelpWorld world) {
    return world.getBukkitWorld().hasStorm();
  }

  /**
   * Checks if auto-save is enabled in this world.
   * This would save the world to the server folder in a regular interval.
   *
   * @param world The world you want to get the auto-save mode of.
   * @return {@code true} if auto-save is enabled.
   */
  @Override
  public boolean hasAutoSave(KelpWorld world) {
    return world.getBukkitWorld().isAutoSave();
  }

  /**
   * Checks whether there is clear weather in the given world.
   * If there is clear whether, methods such as {@link #hasStorm(KelpWorld)}
   * are automatically {@code false}.
   *
   * @param world The world you want to check the weather of.
   * @return {@code true} whether there is clear weather, {@code false} if there is any thunder or precipitation.
   */
  @Override
  public boolean isWeatherClear(KelpWorld world) {
    return !(world.getBukkitWorld().hasStorm() || world.getBukkitWorld().isThundering());
  }

  /**
   * Checks if there currently is a thunderstorm in the given world.
   *
   * @param world The world you want to check the weather of.
   * @return {@code true} weather there currently is a thunder in the world.
   */
  @Override
  public boolean isThundering(KelpWorld world) {
    return world.getBukkitWorld().isThundering();
  }

  /**
   * Sets the difficulty of the current world. The difficulty gives an
   * indication of how difficult survival gameplay is. {@code Peaceful} means
   * players cannot become hungry and monsters won't spawn, while {@code hard}
   * says that lots of monsters will spawn and players lose saturation really
   * fast.
   *
   * @param world       The world you want to set the difficulty of.
   * @param difficulty  The difficulty you want to set.
   */
  @Override
  public void setDifficulty(KelpWorld world, Difficulty difficulty) {
    world.getBukkitWorld().setDifficulty(difficulty);
  }

  /**
   * Sets the full time of the current world. The full time is the absolute in-game
   * daytime of a world. With this method you can also rewind the time, which could
   * eventually break redstone clocks or other scheduled events, which is why
   * you might want to use {@link #setTime(KelpWorld, long)} depending on your use-case.
   *
   * @param world     The world to set the full time of.
   * @param fullTime  The full time to set for this world.
   */
  @Override
  public void setFullTime(KelpWorld world, long fullTime) {
    world.getBukkitWorld().setFullTime(fullTime);
  }

  /**
   * Sets the spawn location of this world. This is the location, where
   * players will spawn when they first join the world.
   *
   * By default, there is a 30x30 radius around the location, where players
   * spawn randomly.
   *
   * @param world     The world you want to set the spawn location of.
   * @param location  The spawn location you want to set.
   */
  @Override
  public void setSpawnLocation(KelpWorld world, KelpLocation location) {
    world.getBukkitWorld().setSpawnLocation(
      location.getBukkitLocation().getBlockX(),
      location.getBukkitLocation().getBlockY(),
      location.getBukkitLocation().getBlockZ()
    );
  }

  /**
   * Sets the storm state of a world. When set to {@code true} a new storm
   * will start or the current storm will continue.
   *
   * @param world The world you want to set the storm state of.
   * @param storm {@code true} if you want to start a storm. {@code false} if you want to end the storm.
   */
  @Override
  public void setStorm(KelpWorld world, boolean storm) {
    world.getBukkitWorld().setStorm(storm);
  }

  /**
   * Sets the thunder state of a world. When set to {@code true} a new thunder
   * will start or the current storm will continue.
   *
   * @param world   The world you want to set the thunder state of.
   * @param thunder {@code true} if you want to start a thunderstorm. {@code false} if you want to end the thunder.
   */
  @Override
  public void setThundering(KelpWorld world, boolean thunder) {
    world.getBukkitWorld().setThundering(thunder);
  }

  /**
   * Sets the duration of the current thunderstorm in the world in ticks.
   *
   * @param world       The world you want to set the thunder duration of.
   * @param duration    The duration in ticks the thunder should take.
   */
  @Override
  public void setThunderDuration(KelpWorld world, int duration) {
    world.getBukkitWorld().setThunderDuration(duration);
  }

  /**
   * Sets the relative in-game time of the given world.
   * If you set a value that is smaller than the current time value,
   * the server will automatically skip to the next day. Rewind
   * is not possible with this method for safety reasons. If you need
   * to do it though, use {@link #setFullTime(KelpWorld, long)}.
   *
   * @param world  The world you want to set the time of.
   * @param time   The relative in-game time to set.
   */
  @Override
  public void setTime(KelpWorld world, long time) {
    world.getBukkitWorld().setTime(time);
  }

  /**
   * Spawns a lightning in the given world at the given location. This
   * lightning will be visible for all players who are in the world and in range.
   *
   * @param world       The world you want to spawn the lightning on.
   * @param location    The exact location where the lightning should strike.
   * @param effect      Whether the lightning should just be an effect or be a real lightning.
   *                    A lightning effect does not cause fire, nor damage, nor pigs are transformed to pigmen, etc.
   *                    If you set this to {@code false} a normal lightning will spawn and cause damage to players.
   */
  @Override
  public void strikeLightning(KelpWorld world, KelpLocation location, boolean effect) {
    if (effect) {
      world.getBukkitWorld().strikeLightningEffect(location.getBukkitLocation());
      return;
    }
    world.getBukkitWorld().strikeLightning(location.getBukkitLocation());
  }

  /**
   * Converts a given {@link KelpWorld} to a craft bukkit world.
   * This allows easier NMS integration into the implementation.
   *
   * @param world The world to convert.
   * @return The final craft world
   */
  private CraftWorld craftWorld(KelpWorld world) {
    return ((CraftWorld) world.getBukkitWorld());
  }

}
