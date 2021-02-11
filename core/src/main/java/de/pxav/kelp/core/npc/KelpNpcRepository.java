package de.pxav.kelp.core.npc;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.scheduler.type.RepeatingScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This repository class administrates the player NPCs.
 * It sorts them in lists to keep track of NPC owners and
 * abilities. It controls the NPC heartbeat, which is responsible
 * for updating for example the sneak state or head
 * rotation.
 *
 * @author pxav
 */
@Singleton
public class KelpNpcRepository {

  // saves all spawned NPCs for each player
  // Player UUID => List of all NPCs spawned for this player
  private ConcurrentHashMap<UUID, Set<KelpNpc>> spawnedNpcs;

  private KelpPlayerRepository playerRepository;
  private KelpLogger logger;

  @Inject
  public KelpNpcRepository(KelpPlayerRepository playerRepository, KelpLogger logger) {
    this.spawnedNpcs = new ConcurrentHashMap<>();
    this.playerRepository = playerRepository;
    this.logger = logger;
  }

  /**
   * This method is triggered when a player leaves the server.
   * It removes the player's NPCs to keep the cache tidied.
   */
  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    this.spawnedNpcs.remove(player.getUniqueId());
  }

  /**
   * Adds a new NPC to the cache of currently spawned NPCs.
   *
   * @param npc     The object of the NPC you want to spawn.
   * @param player  The player for whom the NPC is visible / The player
   *                who receives the spawn packet.
   */
  public void addNpc(KelpNpc npc, KelpPlayer player) {
    Set<KelpNpc> npcs = spawnedNpcs.getOrDefault(player.getUUID(), new HashSet<>());
    npcs.add(npc);
    spawnedNpcs.put(player.getUUID(), npcs);
  }

  /**
   * Removes an NPC from the cache of spawned NPCs. This
   * method should be called whenever you despawn an NPC.
   *
   * @param npc     The instance of the NPC you want to despawn.
   * @param player  The player for whom the NPC was visible, when it was visible.
   */
  public void removeNpc(KelpNpc npc, KelpPlayer player) {
    Set<KelpNpc> npcs = spawnedNpcs.getOrDefault(player.getUUID(), new HashSet<>());
    npcs.remove(npc);
    spawnedNpcs.put(player.getUUID(), npcs);
  }

  /**
   * Starts the scheduler scheduling the NPC heartbeat.
   * This heartbeat handles for example the head rotation or
   * sneak state of the NPC.
   */
  public void startScheduler() {
    logger.log("[NPC] Starting NPC heartbeat schedulers.");
    RepeatingScheduler.create()
      .async()
      .every(100)
      .milliseconds()
      .waitForTaskCompletion(true)
      .run(taskId -> {
        try {

          // iterate all players with spawned NPCs.
          spawnedNpcs.forEach((uuid, npcList) -> {

            KelpPlayer player = playerRepository.getKelpPlayer(uuid);
            Preconditions.checkNotNull(player);
            player.sendActionbar("§eTotal Npcs: §7" + npcList.size() + " §8|| §eSpawned NPCs: §7" + npcList.stream().filter(npc -> npc.isSpawned()).count());

            // iterate all NPCs of an individual player
            for (KelpNpc kelpNpc : npcList) {
              kelpNpc.triggerHeartbeatTick();
            }

          });
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
  }

}
