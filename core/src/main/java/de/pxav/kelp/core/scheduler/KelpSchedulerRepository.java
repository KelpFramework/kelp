package de.pxav.kelp.core.scheduler;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpSchedulerRepository {

  private ConcurrentMap<UUID, ScheduledFuture<?>> scheduledFutures;
  private ConcurrentMap<UUID, Integer> bukkitTasks;

  public KelpSchedulerRepository() {
    this.scheduledFutures = new ConcurrentHashMap<>();
    this.bukkitTasks = new ConcurrentHashMap<>();
  }

  public UUID registerScheduler(UUID id, ScheduledFuture<?> scheduledFuture) {
    this.scheduledFutures.put(id, scheduledFuture);
    return id;
  }

  public UUID registerScheduler(UUID id, int taskId) {
    this.bukkitTasks.put(id, taskId);
    return id;
  }

  public void interruptScheduler(UUID uuid) {
    if (this.scheduledFutures.containsKey(uuid)) {
      this.scheduledFutures.get(uuid).cancel(true);
      this.scheduledFutures.remove(uuid);
      return;
    }
    if (this.bukkitTasks.containsKey(uuid)) {
      Bukkit.getScheduler().cancelTask(this.bukkitTasks.get(uuid));
      this.bukkitTasks.remove(uuid);
    }
  }

  public void interruptAll() {
    Maps.newHashMap(scheduledFutures).forEach((id, task) -> {
      task.cancel(true);
      scheduledFutures.remove(id);
    });
    Maps.newHashMap(bukkitTasks).forEach((id, task) -> {
      Bukkit.getScheduler().cancelTask(this.bukkitTasks.get(id));
      this.bukkitTasks.remove(id);
    });
  }

}
