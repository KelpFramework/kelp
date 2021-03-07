package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.version.ChunkVersionTemplate;
import org.bukkit.Chunk;

import java.util.Collection;
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

  public KelpBlock getBlockAt(KelpLocation location) {
    return versionTemplate.getBlockAt(this, location);
  }

  public boolean contains(KelpLocation location) {
    return this.versionTemplate.contains(this, location);
  }

  public boolean contains(KelpBlock block) {
    return this.versionTemplate.contains(this, block.getLocation());
  }

  public boolean isSlimeChunk() {
    return versionTemplate.isSlimeChunk(this);
  }

  public Collection<KelpPlayer> getPlayers() {
    return versionTemplate.getPlayers(this);
  }

  public KelpLocation getCenter(int height) {
    return KelpLocation.from(getWorld().getName(), getX() << 4, height, getZ() << 4).add(7, 0, 7);
  }

  public KelpChunk getWestEnclosedChunk() {
    return getNorthWesternBlock(10).getLocation().add(-2, 0, 2).getChunk();
  }

  public KelpChunk getNorthEnclosedChunk() {
    return getNorthEasternBlock(10).getLocation().add(-2, 0, -2).getChunk();
  }

  public KelpChunk getEastEnclosedChunk() {
    return getNorthEasternBlock(10).getLocation().add(2, 0, 2).getChunk();
  }

  public KelpChunk getSouthEnclosedChunk() {
    return getSouthWesternBlock(10).getLocation().add(2, 0, 2).getChunk();
  }

  public KelpChunk getSouthWestEnclosedChunk() {
    return getSouthWesternBlock(10).getLocation().add(-2, 0, 2).getChunk();
  }

  public KelpChunk getNorthWestEnclosedChunk() {
    return getNorthWesternBlock(10).getLocation().add(-2, 0, -2).getChunk();
  }

  public KelpChunk getNorthEastEnclosedChunk() {
    return getNorthEasternBlock(10).getLocation().add(2, 0, -2).getChunk();
  }

  public KelpChunk getSouthEastEnclosedChunk() {
    return getSouthEasternBlock(10).getLocation().add(2, 0, 2).getChunk();
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

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof KelpChunk)) {
      return false;
    }

    KelpChunk chunk = (KelpChunk) object;
    return chunk.hashCode() == this.hashCode();
  }

  @Override
  public int hashCode() {
    int hash = 19 * 3 + (getWorld() != null ? getWorld().hashCode() : 0);
    hash *= getX() * getX();
    hash *= getZ() * getZ();

    return hash;
  }
}
