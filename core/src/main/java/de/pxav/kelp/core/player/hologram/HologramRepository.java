package de.pxav.kelp.core.player.hologram;

import de.pxav.kelp.core.common.ConcurrentMultimap;
import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.type.RepeatingScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Singleton
public class HologramRepository {

  private ConcurrentMultimap<UUID, KelpHologram> playerHolograms = ConcurrentSetMultimap.create();
  private UUID taskId;

  private KelpSchedulerRepository schedulerRepository;

  @Inject
  public HologramRepository(KelpSchedulerRepository schedulerRepository) {
    this.schedulerRepository = schedulerRepository;
  }

  public void addHologram(KelpHologram hologram) {
    this.playerHolograms.put(hologram.getPlayer().getUUID(), hologram);
    this.startScheduler();
  }

  public void removeHologram(KelpHologram hologram) {
    this.playerHolograms.remove(hologram.getPlayer().getUUID(), hologram);
  }

  public boolean playerHasHologram(UUID playerUUID) {
    return playerHolograms.containsKey(playerUUID);
  }

  @EventHandler
  public void handleHologramQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    playerHolograms.removeAll(player.getUniqueId());
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

        for (Map.Entry<UUID, Collection<KelpHologram>> entry : playerHolograms.asMap().entrySet()) {

          for (KelpHologram hologram : entry.getValue()) {
            hologram.doTick();
          }

        }

      });
  }

  public ConcurrentMultimap<UUID, KelpHologram> getPlayerHolograms() {
    return playerHolograms;
  }

}
