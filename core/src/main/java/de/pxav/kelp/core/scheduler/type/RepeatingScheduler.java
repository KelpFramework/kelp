package de.pxav.kelp.core.scheduler.type;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.KelpSchedulerRunnable;
import de.pxav.kelp.core.scheduler.TimeConverter;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class RepeatingScheduler {

  private int interval;
  private int initialDelay;
  private TimeUnit timeUnit;
  private boolean isAsync;
  private boolean waitForTaskCompletion;

  private KelpSchedulerRunnable runnable;

  private KelpSchedulerRepository kelpSchedulerRepository;
  private ScheduledExecutorService scheduledExecutorService;

  RepeatingScheduler(KelpSchedulerRepository kelpSchedulerRepository) {
    this.kelpSchedulerRepository = kelpSchedulerRepository;
    this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
      .setDaemon(true)
      .build());

    this.interval = 1;
    this.initialDelay = 0;
    this.timeUnit = TimeUnit.SECONDS;
    this.isAsync = false;
    this.waitForTaskCompletion = false;
  }

  public RepeatingScheduler async() {
    this.isAsync = true;
    return this;
  }

  public RepeatingScheduler sync() {
    this.isAsync = false;
    return this;
  }

  public RepeatingScheduler every(int interval) {
    this.interval = interval;
    return this;
  }

  public RepeatingScheduler timeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
    return this;
  }

  public RepeatingScheduler seconds() {
    this.timeUnit = TimeUnit.SECONDS;
    return this;
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code MINUTES}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return
   */
  public RepeatingScheduler minutes() {
    return timeUnit(TimeUnit.MINUTES);
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code MILLISECONDS}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return
   */
  public RepeatingScheduler milliseconds() {
    return timeUnit(TimeUnit.MILLISECONDS);
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code HOURS}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return
   */
  public RepeatingScheduler hours() {
    return timeUnit(TimeUnit.HOURS);
  }

  public RepeatingScheduler withInitialDelay(int initialDelay) {
    this.initialDelay = initialDelay;
    return this;
  }

  public RepeatingScheduler waitForTaskCompletion(boolean wait) {
    this.waitForTaskCompletion = wait;
    return this;
  }

  public UUID run(KelpSchedulerRunnable runnable) {
    this.runnable = runnable;
    UUID taskId = UUID.randomUUID();

    if (this.isAsync) {
      ScheduledFuture<?> scheduledFuture;

      if (this.waitForTaskCompletion) {
        scheduledFuture = this.scheduledExecutorService.scheduleWithFixedDelay(
          () -> runnable.run(taskId), this.initialDelay, this.interval, this.timeUnit
        );
      } else {
        scheduledFuture = this.scheduledExecutorService.scheduleAtFixedRate(
          () -> runnable.run(taskId), this.initialDelay, this.interval, this.timeUnit
        );
      }

      return kelpSchedulerRepository.registerScheduler(taskId, scheduledFuture);
    } else {
      int bukkitId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
        KelpPlugin.getPlugin(KelpPlugin.class),
        () -> runnable.run(taskId),
        initialDelay,
        TimeConverter.getTicks(interval, timeUnit));
      return this.kelpSchedulerRepository.registerScheduler(taskId, bukkitId);
    }

  }

}
