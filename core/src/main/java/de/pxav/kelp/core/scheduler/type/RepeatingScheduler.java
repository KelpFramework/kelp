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
 * This class represents schedulers, which execute their code repeatedly.
 * That means you give them an initial delay to wait in the beginning,
 * and an interval in which the code should be repeated.
 *
 * You can choose whether the scheduler should either run asynchronous
 * using a {@link ScheduledExecutorService} or use a default bukkit
 * scheduler instead.
 *
 * All schedulers (async and sync) are automatically cancelled on every
 * server shutdown.
 *
 * If you need to execute some tasks on the main thread, you might want
 * to use {@link de.pxav.kelp.core.scheduler.synchronize.ServerMainThread}.
 *
 *
 * @see ScheduledExecutorService
 * @see KelpSchedulerRepository
 * @author pxav
 */
public class RepeatingScheduler {

  // delay between each execution
  private int interval;

  // delay before the first iteration starts
  private int initialDelay;

  // time unit for interval and initialDelay
  private TimeUnit timeUnit;

  // true if the scheduler should use async (ScheduledExecutorService)
  // or sync (bukkit scheduler) timing methods.
  private boolean isAsync;

  // If your task is async and this value is set to true,
  // the interval delay will be counted down when the given
  // task is finished. If false, the interval will be counted
  // down when the execution has just started.
  private boolean waitForTaskCompletion;

  // the code to run in each iteration.
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

  public static RepeatingScheduler create() {
    return new RepeatingScheduler(
      KelpPlugin.getInjector().getInstance(KelpSchedulerRepository.class)
    );
  }

  /**
   * Tells the scheduler to execute the given code on a separate thread.
   * Async schedulers use {@link ScheduledExecutorService} instead of the
   * normal bukkit scheduler.
   *
   * Note: Within your scheduler, you can jump to the main thread for a single
   * calculation at any time using {@link de.pxav.kelp.core.scheduler.synchronize.ServerMainThread}.
   *
   * @return
   */
  public RepeatingScheduler async() {
    this.isAsync = true;
    return this;
  }

  /**
   * Tells the scheduler to execute the given code on the main thread of
   * the server. This is the default setting for schedulers.
   *
   * Note: If you only have a single task to be executed from the main thread
   * and the rest can be done async, you can make your scheduler async
   * and create a reference to the main thread using {@link de.pxav.kelp.core.scheduler.synchronize.ServerMainThread}.
   *
   * @return
   */
  public RepeatingScheduler sync() {
    this.isAsync = false;
    return this;
  }

  /**
   * Sets the interval of your scheduler. The interval is the time
   * between each iteration of the code. The unit of the interval
   * is defined by {@link #timeUnit} or any time-unit method
   * such as {@link #seconds()}.
   *
   * @param interval The interval between each iteration.
   * @return
   */
  public RepeatingScheduler every(int interval) {
    this.interval = interval;
    return this;
  }

  /**
   * Sets the time unit of the given interval as well as of the
   * initial delay. If you want to use the fluent builder design,
   * you can also rely on one of the time unit methods such as
   * {@link #seconds()}.
   *
   * @param timeUnit The time unit you want to set.
   * @return
   */
  public RepeatingScheduler timeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
    return this;
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code SECONDS}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return
   */
  public RepeatingScheduler seconds() {
    return timeUnit(TimeUnit.SECONDS);
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

  /**
   * Sets the initial delay of the scheduler. The initial delay is the time
   * the server will wait until the scheduler should be started.
   *
   * The time unit of this value is the same as for the {@code interval}.
   *
   * @param initialDelay The delay you want to set.
   * @return
   */
  public RepeatingScheduler withInitialDelay(int initialDelay) {
    this.initialDelay = initialDelay;
    return this;
  }

  /**
   * If your scheduler is async and you want to let your task finish until
   * the countdown for the next iteration starts, set this to {@code true}.
   * This can be useful to avoid a "task overflow" for processes where it is
   * not sure when they will be finished.
   *
   * The default value for this property is {@code false}.
   *
   * @param wait {@code true} whether the scheduler should wait with
   *                         counting down until the task is completed.
   * @return
   */
  public RepeatingScheduler waitForTaskCompletion(boolean wait) {
    this.waitForTaskCompletion = wait;
    return this;
  }

  /**
   * Finally starts the scheduler and registers it in the {@link KelpSchedulerRepository}.
   * The repository assigns an id to each scheduler, which enables you to cancel it later
   * using {@link KelpSchedulerRepository#interruptScheduler(UUID)}.
   *
   * @param runnable The code to execute
   * @return The id of the scheduler in the repository. This can be used to stop it later.
   * @see KelpSchedulerRunnable
   */
  public UUID run(KelpSchedulerRunnable runnable) {
    this.runnable = runnable;
    UUID taskId = UUID.randomUUID();

    // if the scheduler is async use a ScheduledFuture wrapped in a ScheduledExecutorService.
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
      // if the scheduler is sync use the bukkit scheduler instead.
      int bukkitId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
        KelpPlugin.getPlugin(KelpPlugin.class),
        () -> runnable.run(taskId),
        initialDelay,
        TimeConverter.getTicks(interval, timeUnit));
      return this.kelpSchedulerRepository.registerScheduler(taskId, bukkitId);
    }

  }

}
