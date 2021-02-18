package de.pxav.kelp.core.world;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Collection;

@KelpVersionTemplate
public abstract class ChunkVersionTemplate {

  public abstract boolean contains(KelpChunk chunk, KelpBlock block);

  public abstract KelpBlock getBlockAt(KelpChunk chunk, KelpLocation location);

  //todo getEntities()

  public abstract Collection<KelpPlayer> getPlayers(KelpChunk chunk, KelpLocation location);

  public abstract KelpWorld getWorld(KelpChunk chunk);

  public abstract int getX(KelpChunk chunk);

  public abstract int getZ(KelpChunk chunk);

  public abstract boolean isLoaded(KelpChunk chunk);

  public abstract void load(KelpChunk chunk);

  public abstract void unload(KelpChunk chunk);

  public abstract boolean isSlimeChunk(KelpChunk chunk);

}
