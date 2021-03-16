package de.pxav.kelp.core.world.region;

import com.google.common.collect.*;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.type.RepeatingScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.UUID;

@Singleton
public class KelpRegionRepository {

  // region id -> region object
  private Map<UUID, KelpRegion> listenedRegions;

  // player -> region id
  private Multimap<UUID, UUID> containedBy;

  private boolean listenerRunning = false;

  private KelpSchedulerRepository schedulerRepository;

  @Inject
  public KelpRegionRepository(KelpSchedulerRepository schedulerRepository) {
    this.listenedRegions = Maps.newConcurrentMap();
    this.containedBy = ArrayListMultimap.create();

    this.schedulerRepository = schedulerRepository;
  }

  public void listenTo(KelpRegion region) {
    this.listenedRegions.put(region.getRegionId(), region);
    if (!listenerRunning) {
      startListenerTasks();
    }
  }

  public void stopListeningTo(KelpRegion region) {
    this.listenedRegions.remove(region.getRegionId());

  }

  public boolean isListeningTo(KelpRegion region) {
    return this.listenedRegions.containsKey(region.getRegionId());
  }

  public void startListenerTasks() {
    RepeatingScheduler.create()
      .async()
      .every(100)
      .milliseconds()
      .waitForTaskCompletion(true)
      .run(taskId -> {

        // if there are no regions to listen to anymore, cancel the scheduler
        if (listenedRegions.isEmpty()) {
          listenerRunning = false;
          schedulerRepository.interruptScheduler(taskId);
        }

        listenerRunning = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
          listenedRegions.forEach((regionId, region) -> {
            if (!region.getWorldName().equalsIgnoreCase(player.getWorld().getName())) {
              return;
            }


          });
        }
    });
  }


}
