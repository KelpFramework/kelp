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
 * This class represents a scheduler type, which executes a given task once
 * with a given delay.
 *
 * You can choose whether the task should be executed async (using {@link ScheduledExecutorService}
 * or sync (using the default bukkit scheduler).
 *
 * All schedulers (async and sync) are automatically cancelled when they
 * have finished their task or the server is shut down.
 *
 * If you need to execute some tasks on the main thread, you might want
 * to use {@link de.pxav.kelp.core.scheduler.synchronize.ServerMainThread}.
 *
 * @see ScheduledExecutorService
 * @see KelpSchedulerRepository
 * @see RepeatingScheduler
 * @author pxav
 */
public class DelayedScheduler {

  // true if the task should be executed on another thread
  private boolean isAsync;

  // the delay to wait until the task is executed
  private int delay;

  // the  time unit for delay
  private TimeUnit timeUnit;

  // the code to run when the delay has expired
  private KelpSchedulerRunnable runnable;

  private ScheduledExecutorService executorService;
  private KelpSchedulerRepository kelpSchedulerRepository;

  DelayedScheduler(KelpSchedulerRepository kelpSchedulerRepository) {
    this.kelpSchedulerRepository = kelpSchedulerRepository;
    this.executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
      .setDaemon(true)
      .build());

    this.isAsync = false;
    this.delay = 0;
    this.timeUnit = TimeUnit.SECONDS;
  }

  public static DelayedScheduler create() {
    return new DelayedScheduler(
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
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler async() {
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
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler sync() {
    this.isAsync = false;
    return this;
  }

  /**
   * Sets the time unit of the given delay If you want to use the fluent builder design,
   * you can also rely on one of the time unit methods such as
   * {@link #seconds()}.
   *
   * @param timeUnit The time unit you want to set.
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler timeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
    return this;
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code SECONDS}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler seconds() {
    return timeUnit(TimeUnit.SECONDS);
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code MINUTES}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler minutes() {
    return timeUnit(TimeUnit.MINUTES);
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code MILLISECONDS}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler milliseconds() {
    return timeUnit(TimeUnit.MILLISECONDS);
  }

  /**
   * Sets the time unit for the {@code interval} as well as {@code initialDelay}
   * to {@code HOURS}. This method can be used if you want to
   * have a fluent builder design and uses {@link #timeUnit(TimeUnit)} in the background.
   *
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler hours() {
    return timeUnit(TimeUnit.HOURS);
  }

  /**
   * Sets the delay of the scheduler. The (initial) delay is the time
   * the server will wait until the given runnable should be executed.
   *
   * @param delay The delay you want to set.
   * @return current instance of the scheduler. Used for fluent builder design.
   */
  public DelayedScheduler withDelayOf(int delay) {
    this.delay = delay;
    return this;
  }

  /**
   * Finally starts the scheduler and registers it in the {@link KelpSchedulerRepository} if
   * necessary (the {@code delay} is > 0). The repository assigns an id to it, so that the
   * task can be cancelled if needed.
   *
   * @param runnable The task to run with the given delay.
   * @return the task id given by the repository class.
   */
  public UUID run(KelpSchedulerRunnable runnable) {
    this.runnable = runnable;
    UUID taskId = UUID.randomUUID();

    if (this.isAsync) {
      // start the scheduler and automatically interrupt it once it finished.
      ScheduledFuture<?> scheduledFuture = this.executorService.schedule(() -> {
        this.runnable.run(taskId);
        this.kelpSchedulerRepository.interruptScheduler(taskId);
      }, this.delay, this.timeUnit);

      // if the delay was 0, the task was executed immediately and
      // might already be finished. A registration is not necessary in
      // these cases
      if (!scheduledFuture.isDone()) {
        kelpSchedulerRepository.registerScheduler(taskId, scheduledFuture);
      }
    } else {
      // if the scheduler is sync use the bukkit scheduler instead.
      int bukkitId = Bukkit.getScheduler().scheduleSyncDelayedTask(
        KelpPlugin.getPlugin(KelpPlugin.class),
        () -> {
          runnable.run(taskId);
          this.kelpSchedulerRepository.interruptScheduler(taskId);
        },
        TimeConverter.getTicks(delay, timeUnit));
      if (this.delay > 0) {
        this.kelpSchedulerRepository.registerScheduler(taskId, bukkitId);
      }
      return taskId;
    }

    return taskId;
  }

}
