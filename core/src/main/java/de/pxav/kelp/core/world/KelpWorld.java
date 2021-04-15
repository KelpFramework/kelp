package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.type.general.ItemDropType;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.util.ExplosionPower;
import de.pxav.kelp.core.world.util.WorldType;
import de.pxav.kelp.core.world.version.WorldVersionTemplate;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;

import java.util.Collection;
import java.util.UUID;

/**
 * This class represents a world on the server in which entities and players
 * can move, blocks can be spawned and generated and chunks can be loaded
 * and unloaded.
 *
 * A world is usually saved in the server's main folder and has to
 * be loaded manually before it can be used (except the default {@code 'world'} of
 * bukkit).
 *
 * This class is a version-independent replacement for the normal {@link World}
 * provided by bukkit.
 *
 * @author pxav
 */
public class KelpWorld {

  private String name;
  private WorldType worldType;
  private World bukkitWorld;

  private WorldVersionTemplate versionTemplate;

  public KelpWorld(World bukkitWorld, WorldVersionTemplate versionTemplate) {
    this.bukkitWorld = bukkitWorld;
    this.versionTemplate = versionTemplate;

    this.name = bukkitWorld.getName();
  }

  public static KelpWorld from(String worldName) {
    World world = Bukkit.getWorld(worldName);
    if (world == null) {
      return null;
    }
    return KelpWorld.from(world);
  }

  public static KelpWorld from(World world) {
    return new KelpWorld(
      world,
      KelpPlugin.getInjector().getInstance(WorldVersionTemplate.class)
    );
  }

  /**
   * Converts this world into a bukkit {@link World} instance.
   *
   * @return The bukkit instance of this world.
   */
  public World getBukkitWorld() {
    return bukkitWorld;
  }

  public WorldType getWorldType() {
    if (this.worldType == null) {
      this.worldType = versionTemplate.getWorldType(this);
    }
    return worldType;
  }

  /**
   * Gets the {@link KelpBlock} at the given location of the world.
   *
   * @param location  The exact location of the block you want to get.
   * @return The {@link KelpBlock} object at the given location.
   *         If the block's material is {@link de.pxav.kelp.core.inventory.material.KelpMaterial#AIR},
   *         the block won't be {@code null} but of type {@code AIR}
   */
  public KelpBlock getBlockAt(KelpLocation location) {
    return versionTemplate.getBlockAt(this, location);
  }

  /**
   * Gets the {@link KelpBlock} at the given location of the world.
   *
   * @param x  The x-coordinate of the location you want to get the block at.
   * @param y  The y-coordinate of the location you want to get the block at.
   * @param z  The z-coordinate of the location you want to get the block at.
   * @return The {@link KelpBlock} object at the given location.
   *         If the block's material is {@link de.pxav.kelp.core.inventory.material.KelpMaterial#AIR},
   *         the block won't be {@code null} but of type {@code AIR}
   */
  public KelpBlock getBlockAt(double x, double y, double z) {
    return versionTemplate.getBlockAt(this, KelpLocation.from(getName(), x, y, z));
  }

  /**
   * Gets the chunk at the given location in this world.
   * A chunk is a 16x16x256 (320 if you are on 1.17+)
   * cuboid region minecraft uses to generate and handle the world. More information
   * can be found in {@link KelpChunk}.
   *
   * @param location  The location where the chunk is located.
   * @return The chunk at the given location.
   */
  public KelpChunk getChunkAt(KelpLocation location) {
    return versionTemplate.getChunkAt(this, location);
  }

  /**
   * Gets the chunk at the given location in this world.
   * More information can be found in {@link KelpChunk}.
   *
   * @param x  The x-coordinate of the location to get the chunk at.
   * @param y  The y-coordinate of the location to get the chunk at.
   * @param z  The z-coordinate of the location to get the chunk at.
   * @return The chunk at the given location.
   */
  public KelpChunk getChunkAt(double x, double y, double z) {
    return versionTemplate.getChunkAt(this, KelpLocation.from(getName(), x, y, z));
  }

  /**
   * Gets the name of this world in the bukkit world registration.
   *
   * @return This world's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the spawn location of the given world. The spawn location is the
   * center of a radius in which players can spawn when initially joining a
   * new world.
   *
   * @return The world's spawn location.
   */
  public KelpLocation getSpawnLocation() {
    return versionTemplate.getSpawnLocation(this);
  }

  /**
   * Checks whether this world is able to generate structures such as temples or villages.
   *
   * @return {@code true} if the world can generate structures
   */
  public boolean canGenerateStructures() {
    return versionTemplate.canGenerateStructures(this);
  }

  /**
   * Creates an explosion at the given location. This explosion will be
   * destructive, which means it will actually destroy blocks, but not
   * ignite any fire.
   *
   * @param location      The center of the explosion
   * @param power         The power of the explosion. More information on explosion powers can be
   *                      found in {@link ExplosionPower}. This has no effect on the fact whether
   *                      fire is ignited or blocks are broken.
   * @return
   */
  public KelpWorld createDestructiveExplosion(KelpLocation location, ExplosionPower power) {
    versionTemplate.createExplosion(this, location, power, true, false);
    return this;
  }

  /**
   * Creates an explosion at the given location. This explosion is only
   * an effect, which means it won't break any blocks nor ignite fire.
   *
   * @param location      The center of the explosion
   * @param power         The power of the explosion. More information on explosion powers can be
   *                      found in {@link ExplosionPower}. This has no effect on the fact whether
   *                      fire is ignited or blocks are broken.
   * @return
   */
  public KelpWorld createEffectExplosion(KelpLocation location, ExplosionPower power) {
    versionTemplate.createExplosion(this, location, power, false, false);
    return this;
  }

  /**
   * Creates an explosion at the given location. This explosion will be
   * destructive, which means it will break blocks. It will furthermore ignite fire
   * at some locations within the explosion radius.
   *
   * @param location      The center of the explosion
   * @param power         The power of the explosion. More information on explosion powers can be
   *                      found in {@link ExplosionPower}. This has no effect on the fact whether
   *                      fire is ignited or blocks are broken.
   * @return
   */
  public KelpWorld createDestructiveFireExplosion(KelpLocation location, ExplosionPower power) {
    versionTemplate.createExplosion(this, location, power, true, true);
    return this;
  }

  /**
   * Creates an explosion at the given location. This explosion will not
   * break any blocks but ignite fire within a specific radius of the center
   * location.
   *
   * @param location      The center of the explosion
   * @param power         The power of the explosion. More information on explosion powers can be
   *                      found in {@link ExplosionPower}. This has no effect on the fact whether
   *                      fire is ignited or blocks are broken.
   * @return
   */
  public KelpWorld createFireExplosion(KelpLocation location, ExplosionPower power) {
    versionTemplate.createExplosion(this, location, power, false, true);
    return this;
  }

  /**
   * Drops an item on the given world at the given location. This item will be
   * visible for all players on the world. The drop will land on the given
   * location as there won't be any natural offset by default. If you want that,
   * use {@link #dropItemNaturally(KelpLocation, KelpItem)} instead.
   *
   * @param location  The location from where the item should drop
   * @param item      The item you want to drop
   * @return The item entity that has been spawned at the given location.
   */
  public DroppedItemEntity dropItem(KelpLocation location, KelpItem item) {
    return versionTemplate.dropItem(this, location, item, ItemDropType.NORMAL);
  }

  /**
   * Drops an item on the given world at the given location. This item will be
   * visible for all players on the world. The drop will land on the given
   * location as there won't be any natural offset by default. If you want that,
   * use
   *
   * @param location  The location from where the item should drop
   * @param item      The item you want to drop
   * @return The item entity that has been spawned at the given location.
   */
  public DroppedItemEntity dropItemNaturally(KelpLocation location, KelpItem item) {
    return versionTemplate.dropItem(this, location, item, ItemDropType.NATURAL);
  }

  /**
   * Checks whether animals can spawn naturally on this world.
   *
   * @return {@code true} if animals can spawn by default.
   */
  public boolean animalsAllowed() {
    return versionTemplate.animalsAllowed(this);
  }

  /**
   * Checks whether monsters such as creepers or zombies
   * can spawn naturally on this world.
   *
   * @return {@code true} if monsters can spawn by default.
   */
  public boolean monstersAllowed() {
    return versionTemplate.monstersAllowed(this);
  }

  /**
   * Gets the highest block at a given location.
   *
   * @param location  The location containing the y-axis for the block check.
   * @return The highest {@link KelpBlock} at the provided location of the world.
   */
  public KelpBlock getHighestBlockAt(KelpLocation location) {
    return versionTemplate.getHighestBlockAt(this, location);
  }

  /**
   * Gets a collection of all {@link KelpChunk}s that are currently loaded in
   * this world. This includes forced loaded chunks as well as naturally loaded chunks.
   *
   * @return The collection of all chunks currently loaded in this world.
   */
  public Collection<KelpChunk> getLoadedChunks() {
    return versionTemplate.getLoadedChunks(this);
  }

  /**
   * Gets the difficulty of this current world. The difficulty gives an
   * indication of how difficult survival gameplay is. {@code Peaceful} means
   * players cannot become hungry and monsters won't spawn, while {@code hard}
   * says that lots of monsters will spawn and players lose saturation really
   * fast.
   *
   * @return The current difficulty of the world.
   */
  public Difficulty getDifficulty() {
    return versionTemplate.getDifficulty(this);
  }

  /**
   * Gets the humidity at a specific location in the world.
   * The humidity is dependent on the biome at the given location.
   *
   * @param location  The location to check the humidity at.
   * @return The humidity value at the given location in the given world.
   */
  public double getHumidityAt(KelpLocation location) {
    return versionTemplate.getHumidityAt(this, location);
  }

  /**
   * Checks whether the spawn chunks should be kept in memory although
   * no players are currently in the spawn area.
   *
   * @return {@code true} if the spawn chunks are kept in memory.
   */
  public boolean shouldKeepSpawnInMemory() {
    return versionTemplate.shouldKeepSpawnInMemory(this);
  }

  /**
   * Changes whether the spawn chunks of a world should be kept
   * loaded/in memory although no players are currently in the spawn
   * area. This might improve performance if you are commonly spawning
   * new players to a world (such as in a server lobby).
   *
   * @param keep {@code true} if the spawn chunks should be kept loaded over the server runtime.
   */
  public KelpWorld keepSpawnInMemory(boolean keep) {
    versionTemplate.setKeepSpawnInMemory(this, keep);
    return this;
  }

  /**
   * Gets the full time of a world. This is the full absolute
   * in-game daytime.
   *
   * @return The full (absolute) time of the given world.
   */
  public long getFullTime() {
    return versionTemplate.getFullTime(this);
  }

  /**
   * Gets the full in-game time of this world from the initial world generation.
   *
   * @return The game time that has passed on the given world.
   */
  public long getGameTime() {
    return versionTemplate.getGameTime(this);
  }

  /**
   * Gets the relative in-game time of the given world.
   * This time is calculated from the world's full time:
   * {@code #getFullTime() % 24000L}.
   *
   * This time cannot be negative. If a negative value is returned from
   * the above operation, a full day will be added to this value.
   *
   * @return The relative in-game time of the given world.
   */
  public long getTime() {
    return versionTemplate.getTime(this);
  }

  /**
   * Gets all players that are currently on the given world.
   *
   * @return A collection of all players that are currently on this world.
   */
  public Collection<KelpPlayer> getPlayers() {
    return versionTemplate.getPlayers(this);
  }

  /**
   * Gets the current pvp mode of the world. If players are allowed to
   * fight each other, this will return {@code true}.
   *
   * @return {@code true} if pvp is enabled on this world.
   */
  public boolean pvpEnabled() {
    return versionTemplate.isPvPEnabled(this);
  }

  /**
   * Allows PVP in this world. This allows players to fight
   * against each other.
   *
   * @return
   */
  public KelpWorld enablePVP() {
    versionTemplate.setPVP(this, true);
    return this;
  }

  /**
   * Allows PVP in this world. This prohibits players to fight
   * against each other.
   *
   * @return
   */
  public KelpWorld disablePVP() {
    versionTemplate.setPVP(this, false);
    return this;
  }

  /**
   * Sets the current pvp mode of the world. If players should be allowed
   * to fight each other, set this to {@code true}.
   *
   * @param allow   {@code true} if players should be able to fight each other.
   */
  public KelpWorld allowPVP(boolean allow) {
    versionTemplate.setPVP(this, allow);
    return this;
  }

  /**
   * Gets the seed of the world generator of this world. A seed is a constant value
   * used by the {@code Perlin noise} world generation algorithm to randomize
   * biomes and structures. Seeds are usually compatible across different versions, but
   * might not output the same result in differing versions due to new structures and
   * biomes that have been added over time.
   *
   * @return The world's seed.
   */
  public long getSeed() {
    return versionTemplate.getSeed(this);
  }

  /**
   * Gets the sea level of this world.
   *
   * @return The world's sea level.
   */
  public int getSeaLevel() {
    return versionTemplate.getSeaLevel(this);
  }

  /**
   * Gets the temperature of a world at a specific location.
   * Temperatures are depending on the biome and exact location (height specifically).
   * A temperature indicates whether it can snow or rain at a specific location
   * or whether there is any precipitation at all. The desert biome for example
   * has a relatively high temperature of {@code 2.0d}, while Snowy Beach has {@code 0.05}
   * for example.
   *
   * @param location  The location you want to get the temperature at.
   * @return The exact temperature at the given location in the current world.
   */
  public double getTemperatureAt(KelpLocation location) {
    return versionTemplate.getTemperatureAt(this, location);
  }

  /**
   * Gets the duration of the current thunder in the world in ticks.
   *
   * @return The thunder duration in ticks.
   */
  public int getThunderDuration() {
    return versionTemplate.getThunderDuration(this);
  }

  /**
   * Gets the unique id of this world.
   *
   * @return The world's {@link UUID}.
   */
  public UUID getUUID() {
    return versionTemplate.getUUID(this);
  }

  /**
   * Gets the duration of the current weather state in ticks.
   * This counts for all weather types (including clear weather)
   *
   * @return The current weather duration in ticks.
   */
  public int getWeatherDuration() {
    return versionTemplate.getWeatherDuration(this);
  }

  /**
   * Checks whether there is a storm in the given world.
   *
   * @return {@code true} whether there is currently a storm in the given world.
   */
  public boolean hasStorm() {
    return versionTemplate.hasStorm(this);
  }

  /**
   * Checks if auto-save is enabled in this world.
   * This would save the world to the server folder in a regular interval.
   *
   * @return {@code true} if auto-save is enabled.
   */
  public boolean hasAutoSave() {
    return versionTemplate.hasAutoSave(this);
  }

  /**
   * Checks whether there is clear weather in the given world.
   * If there is clear whether, methods such as {@link #hasStorm()}
   * are automatically {@code false}.
   *
   * @return {@code true} whether there is clear weather, {@code false} if there is any thunder or precipitation.
   */
  public boolean isWeatherClear() {
    return versionTemplate.isWeatherClear(this);
  }

  /**
   * Checks if there currently is a thunderstorm in the given world.
   *
   * @return {@code true} weather there currently is a thunder in the world.
   */
  public boolean isThundering() {
    return versionTemplate.isThundering(this);
  }

  /**
   * Sets the difficulty of the current world. The difficulty gives an
   * indication of how difficult survival gameplay is. {@code Peaceful} means
   * players cannot become hungry and monsters won't spawn, while {@code hard}
   * says that lots of monsters will spawn and players lose saturation really
   * fast.
   *
   * @param difficulty  The difficulty you want to set.
   */
  public KelpWorld setDifficulty(Difficulty difficulty) {
    versionTemplate.setDifficulty(this, difficulty);
    return this;
  }

  /**
   * Sets the full time of the current world. The full time is the absolute in-game
   * daytime of a world. With this method you can also rewind the time, which could
   * eventually break redstone clocks or other scheduled events, which is why
   * you might want to use {@link #setTime(long)} depending on your use-case.
   *
   * @param fullTime  The full time to set for this world.
   */
  public KelpWorld setFullTime(long fullTime) {
    versionTemplate.setFullTime(this, fullTime);
    return this;
  }

  /**
   * Sets the spawn location of this world. This is the location, where
   * players will spawn when they first join the world.
   *
   * By default, there is a 30x30 radius around the location, where players
   * spawn randomly.
   *
   * @param spawnLocation  The spawn location you want to set.
   */
  public KelpWorld setSpawnLocation(KelpLocation spawnLocation) {
    versionTemplate.setSpawnLocation(this, spawnLocation);
    return this;
  }

  /**
   * Sets the relative in-game time of the given world.
   * If you set a value that is smaller than the current time value,
   * the server will automatically skip to the next day. Rewind
   * is not possible with this method for safety reasons. If you need
   * to do it though, use {@link #setFullTime(long)}.
   *
   * @param time   The relative in-game time to set.
   */
  public KelpWorld setTime(long time) {
    versionTemplate.setTime(this, time);
    return this;
  }

  /**
   * Sets the duration of the current thunderstorm in the world in ticks.
   *
   * @param duration    The duration in ticks the thunder should take.
   */
  public KelpWorld setThunderDuration(int duration) {
    versionTemplate.setThunderDuration(this, duration);
    return this;
  }

  /**
   * Sets the thunder state of a world. When set to {@code true} a new thunder
   * will start or the current storm will continue.
   *
   * @param thundering {@code true} if you want to start a thunderstorm. {@code false} if you want to end the thunder.
   */
  public KelpWorld setThundering(boolean thundering) {
    versionTemplate.setThundering(this, thundering);
    return this;
  }

  /**
   * Sets the storm state of a world. When set to {@code true} a new storm
   * will start or the current storm will continue.
   *
   * @param storm {@code true} if you want to start a storm. {@code false} if you want to end the storm.
   */
  public KelpWorld setStorm(boolean storm) {
    versionTemplate.setStorm(this, storm);
    return this;
  }

  /**
   * Spawns a lightning in the given world at the given location. This
   * lightning will be visible for all players who are in the world and in range.
   *
   * This method will cause block and entity damage and might ignite fires at
   * the point where the lightning strikes. If you just want to spawn the effect,
   * use {@link #strikeLightningEffect(KelpLocation)}.
   *
   * @param location    The exact location where the lightning should strike.
   */
  public KelpWorld strikeLightning(KelpLocation location) {
    versionTemplate.strikeLightning(this, location, false);
    return this;
  }

  /**
   * Spawns a strike lightning in the given world at the given location. This
   * lightning will be visible for all players who are in the world and in range.
   *
   * This method does not spawn fire at the location where the lightning
   * strikes, nor does it damage or transform any entities (from pig to pigman
   * for example). It simply spawns the effect of a lightning. If you want the
   * lightning to make damange, use {@link #strikeLightning(KelpLocation)}
   *
   * @param location    The exact location where the lightning should strike.
   */
  public KelpWorld strikeLightningEffect(KelpLocation location) {
    versionTemplate.strikeLightning(this, location, true);
    return this;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof KelpWorld)) {
      return false;
    }

    KelpWorld world = (KelpWorld) object;
    return world.getName().equalsIgnoreCase(this.getName());
  }

  @Override
  public int hashCode() {
    return getUUID().hashCode();
  }

}
