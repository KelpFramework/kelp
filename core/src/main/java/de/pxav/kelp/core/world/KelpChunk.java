package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.world.version.ChunkVersionTemplate;
import org.bukkit.Chunk;

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
