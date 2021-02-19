package de.pxav.kelp.implementation1_8.world;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.scheduler.synchronize.ServerMainThread;
import de.pxav.kelp.core.world.KelpChunk;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkUnloadEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class ForcedChunkLoader {

  private ConcurrentMap<Chunk, Set<Class<? extends KelpApplication>>> forceLoadedChunks = new ConcurrentHashMap<>();

  private KelpLogger logger;

  @Inject
  public ForcedChunkLoader(KelpLogger logger) {
    this.logger = logger;
  }

  public void forceLoadChunk(Class<? extends KelpApplication> plugin, KelpChunk kelpChunk) {
    Chunk chunk = kelpChunk.getBukkitChunk();
    Set<Class<? extends KelpApplication>> plugins = forceLoadedChunks.getOrDefault(chunk, Sets.newHashSet());
    plugins.add(plugin);
    forceLoadedChunks.put(chunk, plugins);
    ServerMainThread.RunParallel.run(chunk::load);
  }

  public void removeForceLoadFlag(Class<? extends KelpApplication> plugin, KelpChunk kelpChunk) {
    Chunk chunk = kelpChunk.getBukkitChunk();
    Set<Class<? extends KelpApplication>> plugins = forceLoadedChunks.getOrDefault(chunk, Sets.newHashSet());
    plugins.remove(plugin);
    if (plugins.isEmpty()) {
      System.out.println("removing chunk from force load list");
      forceLoadedChunks.remove(chunk);
      System.out.println("new size :" + forceLoadedChunks.size());
      return;
    }
    forceLoadedChunks.put(chunk, plugins);
  }

  public void removeForceLoadsFor(Class<? extends KelpApplication> plugin) {
    for (Map.Entry<Chunk, Set<Class<? extends KelpApplication>>> entry : forceLoadedChunks.entrySet()) {
      Set<Class<? extends KelpApplication>> plugins = entry.getValue();

      if (plugins.contains(plugin)) {
        plugins.remove(plugin);
        if (plugins.isEmpty()) {
          forceLoadedChunks.remove(entry.getKey());
        }
      }

    }
  }

  public Set<KelpChunk> getForceLoadedChunksFor(Class<? extends KelpApplication> plugin) {
    Set<KelpChunk> output = Sets.newHashSet();
    for (Map.Entry<Chunk, Set<Class<? extends KelpApplication>>> entry : forceLoadedChunks.entrySet()) {
      Set<Class<? extends KelpApplication>> plugins = entry.getValue();

      if (plugins.contains(plugin)) {
        output.add(KelpChunk.from(entry.getKey()));
      }

    }
    return output;
  }

  public Set<Class<? extends KelpApplication>> getForceLoadingPluginsOf(KelpChunk chunk) {
    return this.forceLoadedChunks.getOrDefault(chunk.getBukkitChunk(), Sets.newHashSet());
  }

  @EventHandler
  public void handleChunkUnload(ChunkUnloadEvent event) {
    Chunk chunk = event.getChunk();

    for (Chunk current : forceLoadedChunks.keySet()) {
      if (current.getX() == chunk.getX() && current.getZ() == chunk.getZ()) {
        event.setCancelled(true);
        logger.log(LogLevel.DEBUG, "Chunk " + chunk.getX() + ":" + chunk.getZ() + " has been prevented from unloading.");
      }
    }

  }

}
