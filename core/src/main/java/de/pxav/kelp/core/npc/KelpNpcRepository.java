package de.pxav.kelp.core.npc;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
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
  private ConcurrentHashMap<UUID, Collection<KelpNpc>> spawnedNpcs;
  private ScheduledExecutorService scheduledExecutorService;

  private KelpPlayerRepository playerRepository;

  @Inject
  public KelpNpcRepository(KelpPlayerRepository playerRepository) {
    this.spawnedNpcs = new ConcurrentHashMap<>();
    this.playerRepository = playerRepository;
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
    Collection<KelpNpc> npcs = spawnedNpcs.getOrDefault(player.getUUID(), new ArrayList<>());
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
    Collection<KelpNpc> npcs = spawnedNpcs.getOrDefault(player.getUUID(), new ArrayList<>());
    npcs.remove(npc);
    spawnedNpcs.put(player.getUUID(), npcs);
  }

  /**
   * Starts the scheduler scheduling the NPC heartbeat.
   * This heartbeat handles for example the head rotation or
   * sneak state of the NPC.
   */
  public void startScheduler() {
    scheduledExecutorService = Executors.newScheduledThreadPool(0);
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      try {

        // iterate all players with spawned NPCs.
        spawnedNpcs.forEach((uuid, npcList) -> {

          KelpPlayer player = playerRepository.getKelpPlayer(uuid);
          Preconditions.checkNotNull(player);

          // iterate all NPCs of an individual player
          npcList.forEach(currentNpc -> {

            // un-sneak if necessary.
            if (currentNpc.shouldImitateSneaking()
                    && currentNpc.isSneaking()
                    && !player.isSneaking()) {
              currentNpc.unSneak();
              currentNpc.refresh(player);

            // sneak if necessary.
            } else if(currentNpc.shouldImitateSneaking()
                    && !currentNpc.isSneaking()
                    && player.isSneaking()) {
              currentNpc.sneak();
              currentNpc.refresh(player);
            }

            // check if the NPC should always look at the player
            // it true, update the head rotation of the npc.
            if (currentNpc.shouldFollowHeadRotation()) {
              currentNpc.lookTo(player.getBukkitPlayer().getLocation());
              currentNpc.refresh(player);
            }

          });
        });
      } catch (Exception e) {
        e.printStackTrace();
      }

    }, 0, 100, TimeUnit.MILLISECONDS);
  }

  /**
   * Stops the scheduler updating the NPC heartbeat.
   * So the head rotation, sneaking and spawning
   * won't be updated anymore.
   */
  public void stopScheduler() {
    if(scheduledExecutorService == null) {
      return;
    }

    scheduledExecutorService.shutdownNow();
  }

}
