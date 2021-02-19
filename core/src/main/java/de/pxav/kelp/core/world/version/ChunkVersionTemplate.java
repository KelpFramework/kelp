package de.pxav.kelp.core.world.version;

import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;

import java.util.Collection;
import java.util.Set;

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

  public abstract void addForceLoadFlag(KelpChunk chunk, Class<? extends KelpApplication> plugin);

  public abstract void removeForceLoadFlag(KelpChunk chunk, Class<? extends KelpApplication> plugin);

  public abstract Set<Class<? extends KelpApplication>> getForceLoadFlagPlugins(KelpChunk chunk);

  public abstract boolean isSlimeChunk(KelpChunk chunk);

}
