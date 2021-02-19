package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.world.version.ChunkVersionTemplate;
import org.bukkit.Chunk;

import java.util.Set;

public class KelpChunk {

  private Chunk bukkitChunk;
  private ChunkVersionTemplate versionTemplate;

  KelpChunk(Chunk bukkitChunk, ChunkVersionTemplate versionTemplate) {
    this.bukkitChunk = bukkitChunk;
    this.versionTemplate = versionTemplate;
  }

  public static KelpChunk from(Chunk bukkitChunk) {
    return new KelpChunk(
      bukkitChunk,
      KelpPlugin.getInjector().getInstance(ChunkVersionTemplate.class)
    );
  }

  public KelpWorld getWorld() {
    return versionTemplate.getWorld(this);
  }

  public int getX() {
    return versionTemplate.getX(this);
  }

  public int getZ() {
    return versionTemplate.getZ(this);
  }

  public KelpLocation getCenter(int height) {
    return KelpLocation.from(getWorld().getName(), getX() << 4, height, getZ() << 4).add(7, 0, 7);
  }

  public KelpBlock getNorthEasternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16 + 15,
      height,
      getZ() * 16
    );
    return getWorld().getBlockAt(location);
  }

  public KelpBlock getNorthWesternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16,
      height,
      getZ() * 16
    );
    return getWorld().getBlockAt(location);
  }

  public KelpBlock getSouthEasternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16 + 15,
      height,
      getZ() * 16 + 15
    );
    return getWorld().getBlockAt(location);
  }

  public KelpBlock getSouthWesternBlock(int height) {
    KelpLocation location = KelpLocation.from(
      getWorld().getName(),
      getX() * 16,
      height,
      getZ() * 16 + 15
    );
    return getWorld().getBlockAt(location);
  }

  public void load() {
    versionTemplate.load(this);
  }

  public void unload() {
    versionTemplate.unload(this);
  }

  public boolean isLoaded() {
    return versionTemplate.isLoaded(this);
  }

  public void addForceLoadFlag(Class<? extends KelpApplication> plugin) {
    versionTemplate.addForceLoadFlag(this, plugin);
  }

  public void removeForceLoadFlag(Class<? extends KelpApplication> plugin) {
    versionTemplate.removeForceLoadFlag(this, plugin);
  }

  public Set<Class<? extends KelpApplication>> getForceLoadingPlugins() {
    return versionTemplate.getForceLoadFlagPlugins(this);
  }

  public boolean equals(KelpChunk compareTo) {
    return compareTo.getX() == getX() && compareTo.getZ() == getZ();
  }

  public boolean equals(Chunk compareTo) {
    return compareTo.getX() == getX() && compareTo.getZ() == getZ();
  }

  public Chunk getBukkitChunk() {
    return bukkitChunk;
  }
}
