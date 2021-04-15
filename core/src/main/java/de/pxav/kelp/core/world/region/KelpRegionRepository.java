package de.pxav.kelp.core.world.region;

import de.pxav.kelp.core.common.ConcurrentMultimap;
import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.event.kelpevent.region.PlayerEnterRegionEvent;
import de.pxav.kelp.core.event.kelpevent.region.PlayerLeaveRegionEvent;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.type.RepeatingScheduler;
import de.pxav.kelp.core.world.util.ApproximateLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

/**
 * This repository class is used to manage {@link KelpRegion}s and their listeners.
 *
 * The listener system is optimized for maximum performance: Only the regions you need
 * the listeners for are really listened by calling {@link KelpRegion#enableListeners()}.
 * Furthermore Kelp does not execute the {@code contains} check for every player and every
 * region, but only for the regions which are in the range of the player. This massively
 * reduces the required computing power for each listener iteration. Look at
 * {@link ApproximateLocation} for more detail.
 *
 * @author pxav
 */
@Singleton
public class KelpRegionRepository {

  // approximate location -> all regions within the area of the approx location
  private ConcurrentSetMultimap<ApproximateLocation, KelpRegion> nearRegions;

  // player -> region id
  private ConcurrentMultimap<UUID, UUID> containedBy;

  // the task id of the listener scheduler
  private UUID task;

  // scheduler repository for interrupting the task
  private KelpSchedulerRepository schedulerRepository;

  @Inject
  public KelpRegionRepository(KelpSchedulerRepository schedulerRepository) {
    this.containedBy = ConcurrentSetMultimap.create();
    this.nearRegions = ConcurrentSetMultimap.create();

    this.schedulerRepository = schedulerRepository;
  }

  /**
   * Enables listeners for the given region. This method caches the approximate
   * location of the given region, so it should be re-registered every time
   * those approximate locations change (on move/expand).
   *
   * This method automatically activates the listener scheduler if there
   * is currently no running.
   *
   * @param region The region to enable the listeners for.
   */
  void listenTo(KelpRegion region) {
    // go through all coordinates on the x/z plane and add their approximate
    // location to the map.
    for (int x = region.getMinPos().getBlockX(); x <= region.getMaxPos().getBlockX(); x++) {
      for (int z = region.getMinPos().getBlockZ(); z <= region.getMaxPos().getBlockZ(); z++) {
        ApproximateLocation current = ApproximateLocation.fromExact(region.getWorldName(), x, z);
        nearRegions.put(current, region);
      }
    }

    // enable scheduler if there is no one running
    if (!isListenerRunning()) {
      startListenerTasks();
    }
  }

  /**
   * Disables listeners for the given region. This will remove
   * all entries in the cache for this region.
   *
   * @param region The region to remove the listener of.
   */
  void stopListeningTo(KelpRegion region) {
    for (int x = region.getMinPos().getBlockX(); x <= region.getMaxPos().getBlockX(); x++) {
      for (int z = region.getMinPos().getBlockZ(); z <= region.getMaxPos().getBlockZ(); z++) {
        ApproximateLocation current = ApproximateLocation.fromExact(region.getWorldName(), x, z);
        nearRegions.remove(current, region);
      }
    }
  }

  /**
   * Checks whether the given region has listeners enabled.
   *
   * @param region The region you want to check for.
   * @return {@code true} the region to check for.
   */
  boolean isListeningTo(KelpRegion region) {
    return nearRegions.containsValue(region);
  }

  /**
   * This listener checks if there is any region having listeners enabled
   * and starts the listener scheduler if needed.
   *
   * This is needed because the scheduler is shut down automatically if no
   * players are online to save performance. Hence, it has to be started when a player
   * joins.
   *
   * @param event The event to listen for.
   */
  @EventHandler
  public void handlePlayerJoin(PlayerJoinEvent event) {
    // check if there is any region to listen for and if the listeners are really shut down.
    if (nearRegions.isEmpty() || this.isListenerRunning()) {
      return;
    }

    this.startListenerTasks();

  }

  /**
   * Starts the region listener task. This task automatically
   * checks when to shut down the listeners.
   */
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
          // iterate each region in range of the player
          nearRegions.getOrEmpty(ApproximateLocation.from(player.getLocation())).forEach(region -> {

            // if the region world differs from the player world, the player cannot
            // be contained by the region.
            if (!region.getWorldName().equalsIgnoreCase(player.getWorld().getName())) {
              return;
            }

            boolean contains = region.contains(
              player.getLocation().getX(),
              player.getLocation().getY(),
              player.getLocation().getZ()
            );

            // if player is in region but was not in the region before -> Enter
            if (contains && !containedBy.containsEntry(player.getUniqueId(), region.getRegionId())) {
              this.containedBy.put(player.getUniqueId(), region.getRegionId());
              Bukkit.getPluginManager().callEvent(new PlayerEnterRegionEvent(region, KelpPlayer.from(player)));

            // if the player is not in region but was in there before -> Exit
            } else if (!contains && containedBy.containsEntry(player.getUniqueId(), region.getRegionId())) {
              this.containedBy.remove(player.getUniqueId(), region.getRegionId());
              Bukkit.getPluginManager().callEvent(new PlayerLeaveRegionEvent(region, KelpPlayer.from(player)));
            }
          });
        }
    });
  }


  /**
   * Stops the listener task and sets the listener id back to {@code null}.
   * This can be used if there is no region to listen for anymore
   * for example and therefore to save performance.
   */
  private void stopListenerTasks() {
    if (task != null) {
      schedulerRepository.interruptScheduler(task);
      task = null;
    }
  }

  /**
   * Checks whether the listener scheduler is currently running.
   *
   * @return {@code true} if the listener task is currently running.
   */
  private boolean isListenerRunning() {
    return task != null;
  }

}
