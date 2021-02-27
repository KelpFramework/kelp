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

/**
 * This class is responsible for keeping chunks loaded if
 * they have a flag assigned by {@link KelpChunk#addForceLoadFlag(Class)}.
 *
 * Minecraft unloads chunks by default to save performance, which prevents
 * crops from growing, etc. If you want to disable that, you can use this class.
 *
 * But you don't have to use it directly, it is recommended to use the
 * interface provided by {@link KelpChunk}.
 *
 * @author pxav
 */
@Singleton
public class ForcedChunkLoader {

  private ConcurrentMap<Chunk, Set<Class<? extends KelpApplication>>> forceLoadedChunks = new ConcurrentHashMap<>();

  private KelpLogger logger;

  @Inject
  public ForcedChunkLoader(KelpLogger logger) {
    this.logger = logger;
  }

  /**
   * Adds a chunk to the force load list. This will automatically load the
   * chunk if it is not already loaded.
   *
   * @param plugin      The plugin that added this loading flag.
   * @param kelpChunk   The chunk that should be kept loaded.
   */
  public void forceLoadChunk(Class<? extends KelpApplication> plugin, KelpChunk kelpChunk) {
    Chunk chunk = kelpChunk.getBukkitChunk();
    Set<Class<? extends KelpApplication>> plugins = forceLoadedChunks.getOrDefault(chunk, Sets.newHashSet());
    plugins.add(plugin);
    forceLoadedChunks.put(chunk, plugins);

    // load the plugin sync, as it is not thread-safe
    ServerMainThread.RunParallel.run(chunk::load);
  }

  /**
   * Removes a force load flag from the given chunk by a specific plugin.
   * The chunk might still be kept loaded if another plugin is loading it.
   *
   * @param plugin      The plugin that removed the load flag.
   * @param kelpChunk   The chunk that should be removed.
   */
  public void removeForceLoadFlag(Class<? extends KelpApplication> plugin, KelpChunk kelpChunk) {
    Chunk chunk = kelpChunk.getBukkitChunk();
    Set<Class<? extends KelpApplication>> plugins = forceLoadedChunks.getOrDefault(chunk, Sets.newHashSet());
    plugins.remove(plugin);

    // if this was the last plugin keeping that chunk, remove the chunk from the list entirely
    if (plugins.isEmpty()) {
      forceLoadedChunks.remove(chunk);
      return;
    }
    forceLoadedChunks.put(chunk, plugins);
  }

  /**
   * Removes all force load flags for a plugin. Then, this plugin won't load
   * any chunks anymore. But the chunks might still be loaded if another plugin
   * loads them.
   *
   * @param plugin The plugin to remove from the list and eventually unload the chunks of.
   */
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

  /**
   * Gets all chunks that are force loaded by a specific plugin.
   *
   * @param plugin The plugin you want to get the force loaded chunks of.
   * @return The set of chunks that are loaded by the given plugin.
   */
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

  /**
   * Gets all plugins force loading a specific {@link KelpChunk}.
   *
   * @param chunk The chunk whose plugin should be queried.
   * @return The plugins loading the given chunk.
   */
  public Set<Class<? extends KelpApplication>> getForceLoadingPluginsOf(KelpChunk chunk) {
    return this.forceLoadedChunks.getOrDefault(chunk.getBukkitChunk(), Sets.newHashSet());
  }

  /**
   * This listener method is triggered everytime the server
   * tries to unload a chunk.
   *
   * If that chunk is force loaded by any plugin, the unload event
   * is canceled and the chunk is kept loaded.
   *
   * @param event The detailed event data
   */
  @EventHandler
  public void handleChunkUnload(ChunkUnloadEvent event) {
    Chunk chunk = event.getChunk();

    // if chunk in list, then cancel the event
    for (Chunk current : forceLoadedChunks.keySet()) {
      if (current.getX() == chunk.getX() && current.getZ() == chunk.getZ()) {
        event.setCancelled(true);
        logger.log(LogLevel.DEBUG, "Chunk " + chunk.getX() + ":" + chunk.getZ() + " has been prevented from unloading.");
      }
    }

  }

}
