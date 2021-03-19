package de.pxav.kelp.core.world.region;

import de.pxav.kelp.core.common.ConcurrentMultimap;
import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.event.kelpevent.PlayerEnterRegionEvent;
import de.pxav.kelp.core.event.kelpevent.PlayerLeaveRegionEvent;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.type.RepeatingScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class KelpRegionRepository {

  private ConcurrentSetMultimap<ApproximateLocation, KelpRegion> nearRegions;

  // player -> region id
  private ConcurrentMultimap<UUID, UUID> containedBy;

  private UUID task;

  private KelpSchedulerRepository schedulerRepository;

  @Inject
  public KelpRegionRepository(KelpSchedulerRepository schedulerRepository) {
    this.containedBy = ConcurrentSetMultimap.create();
    this.nearRegions = ConcurrentSetMultimap.create();

    this.schedulerRepository = schedulerRepository;
  }

  public void listenTo(KelpRegion region) {
    ApproximateLocation maxPos = ApproximateLocation.from(region.getMaxPos());
    ApproximateLocation minPos = ApproximateLocation.from(region.getMinPos());

    for (int x = region.getMinPos().getBlockX(); x <= region.getMaxPos().getBlockX(); x++) {
      for (int z = region.getMinPos().getBlockZ(); z <= region.getMaxPos().getBlockZ(); z++) {
        ApproximateLocation current = ApproximateLocation.fromExact(maxPos.getWorldName(), x, z);
        nearRegions.put(current, region);
      }
    }

    if (!isListenerRunning()) {
      startListenerTasks();
    }
  }

  public void stopListeningTo(KelpRegion region) {
    ApproximateLocation maxPos = ApproximateLocation.from(region.getMaxPos());
    ApproximateLocation minPos = ApproximateLocation.from(region.getMinPos());

    for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
      for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
        ApproximateLocation current = ApproximateLocation.fromExact(maxPos.getWorldName(), x, z);
        nearRegions.remove(current, region);
      }
    }
  }

  public boolean isListeningTo(KelpRegion region) {
    return nearRegions.containsValue(region);
  }

  @EventHandler
  public void handlePlayerJoin(PlayerJoinEvent event) {
    if (nearRegions.isEmpty() || this.isListenerRunning()) {
      return;
    }

    this.startListenerTasks();

  }

  private void startListenerTasks() {
    task = RepeatingScheduler.create()
      .async()
      .every(100)
      .milliseconds()
      .waitForTaskCompletion(true)
      .run(taskId -> {

        // if there are no regions to listen to anymore, cancel the scheduler
        if (nearRegions.isEmpty() || Bukkit.getOnlinePlayers().size() == 0) {
          stopListenerTasks();
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
          nearRegions.getOrEmpty(ApproximateLocation.from(player.getLocation())).forEach(region -> {

            if (!region.getWorldName().equalsIgnoreCase(player.getWorld().getName())) {
              return;
            }

            boolean contains = region.contains(
              player.getLocation().getX(),
              player.getLocation().getY(),
              player.getLocation().getZ()
            );

            if (contains && !containedBy.containsEntry(player.getUniqueId(), region.getRegionId())) {
              this.containedBy.put(player.getUniqueId(), region.getRegionId());
              Bukkit.getPluginManager().callEvent(new PlayerEnterRegionEvent(region, KelpPlayer.from(player)));
            } else if (!contains && containedBy.containsEntry(player.getUniqueId(), region.getRegionId())) {
              this.containedBy.remove(player.getUniqueId(), region.getRegionId());
              Bukkit.getPluginManager().callEvent(new PlayerLeaveRegionEvent(region, KelpPlayer.from(player)));
            }
          });
        }
    });
  }

  private void stopListenerTasks() {
    if (task != null) {
      schedulerRepository.interruptScheduler(task);
      task = null;
    }
  }

  private boolean isListenerRunning() {
    return task != null;
  }

}
