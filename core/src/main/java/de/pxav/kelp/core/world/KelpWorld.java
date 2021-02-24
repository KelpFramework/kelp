package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.world.util.WorldType;
import de.pxav.kelp.core.world.version.WorldVersionTemplate;
import org.bukkit.Bukkit;
import org.bukkit.World;

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

  public World getBukkitWorld() {
    return bukkitWorld;
  }

  public WorldType getWorldType() {
    if (this.worldType == null) {
      this.worldType = versionTemplate.getWorldType(this);
    }
    return worldType;
  }

  public KelpBlock getBlockAt(KelpLocation location) {
    return versionTemplate.getBlockAt(this, location);
  }

  public KelpChunk getChunkAt(KelpLocation location) {
    return versionTemplate.getChunkAt(this, location);
  }

  public String getName() {
    return name;
  }

  public KelpLocation getSpawnLocation() {
    return versionTemplate.getSpawnLocation(this);
  }

}
