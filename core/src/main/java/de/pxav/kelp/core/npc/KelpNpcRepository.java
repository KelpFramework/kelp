package de.pxav.kelp.core.npc;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.KelpPlayer;
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
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpNpcRepository {

  private ConcurrentHashMap<UUID, Collection<KelpNpc>> spawnedNpcs;
  private ScheduledExecutorService scheduledExecutorService;

  @Inject
  public KelpNpcRepository() {
    this.spawnedNpcs = new ConcurrentHashMap<>();
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    this.spawnedNpcs.remove(player.getUniqueId());
  }

  public void addNpc(KelpNpc npc, Player player) {
    Collection<KelpNpc> npcs = spawnedNpcs.getOrDefault(player.getUniqueId(), new ArrayList<>());
    npcs.add(npc);
    spawnedNpcs.put(player.getUniqueId(), npcs);
  }

  public void removeNpc(KelpNpc npc, Player player) {
    Collection<KelpNpc> npcs = spawnedNpcs.getOrDefault(player.getUniqueId(), new ArrayList<>());
    npcs.remove(npc);
    spawnedNpcs.put(player.getUniqueId(), npcs);
  }

  public void startScheduler() {
    scheduledExecutorService = Executors.newScheduledThreadPool(0);
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      try {

        spawnedNpcs.forEach((uuid, npcList) -> {

          Player player = Bukkit.getPlayer(uuid);
          Preconditions.checkNotNull(player);

          npcList.forEach(currentNpc -> {

            if (currentNpc.shouldImitateSneaking()
                    && currentNpc.isSneaking()
                    && !player.isSneaking()) {
              currentNpc.unSneak();
              currentNpc.refresh(player);
            } else if(currentNpc.shouldImitateSneaking()
                    && !currentNpc.isSneaking()
                    && player.isSneaking()) {
              currentNpc.sneak();
              currentNpc.refresh(player);
            }

            if (currentNpc.shouldFollowHeadRotation()) {
              currentNpc.lookTo(player.getLocation());
              currentNpc.refresh(player);
            }

          });
        });
      } catch (Exception e) {
        e.printStackTrace();
      }

    }, 0, 100, TimeUnit.MILLISECONDS);
  }

  public void stopScheduler() {
    scheduledExecutorService.shutdownNow();
  }

}
