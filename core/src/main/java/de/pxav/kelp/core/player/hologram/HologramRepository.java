package de.pxav.kelp.core.player.hologram;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.type.RepeatingScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class HologramRepository {

  private ConcurrentMap<UUID, Set<KelpHologram>> playerHolograms = Maps.newConcurrentMap();
  private UUID taskId;

  private KelpSchedulerRepository schedulerRepository;

  @Inject
  public HologramRepository(KelpSchedulerRepository schedulerRepository) {
    this.schedulerRepository = schedulerRepository;
  }

  public void addHologram(KelpHologram hologram) {
    Set<KelpHologram> holograms = playerHolograms.getOrDefault(hologram.getPlayer().getUUID(), Sets.newHashSet());
    holograms.add(hologram);
    this.playerHolograms.put(hologram.getPlayer().getUUID(), holograms);
    this.startScheduler();
  }

  public void removeHologram(KelpHologram hologram) {
    Set<KelpHologram> holograms = playerHolograms.getOrDefault(hologram.getPlayer().getUUID(), Sets.newHashSet());
    holograms.remove(hologram);
    if (holograms.isEmpty()) {
      this.playerHolograms.remove(hologram.getPlayer().getUUID());
    } else {
      this.playerHolograms.put(hologram.getPlayer().getUUID(), holograms);
    }
  }

  @EventHandler
  public void handleHologramQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    playerHolograms.remove(player.getUniqueId());
  }

  private void startScheduler() {

    // if scheduler is already running, don't start another one
    if (this.taskId != null) {
      return;
    }

    taskId = RepeatingScheduler.create()
      .async()
      .waitForTaskCompletion(true)
      .every(200)
      .milliseconds()
      .run(task -> {

        if (playerHolograms.isEmpty()) {
          schedulerRepository.interruptScheduler(taskId);
          this.taskId = null;
        }

        for (Map.Entry<UUID, Set<KelpHologram>> entry : playerHolograms.entrySet()) {

          for (KelpHologram hologram : entry.getValue()) {
            hologram.doTick();
          }

        }

      });
  }

}
