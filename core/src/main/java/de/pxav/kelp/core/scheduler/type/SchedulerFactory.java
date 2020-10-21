package de.pxav.kelp.core.scheduler.type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;

/**
 * This factory class is used to create instances of different
 * scheduler types. If you want to create a new scheduler inside
 * your class, you can inject this factory via Guice and call one
 * of the factory methods such as {@link #newRepeatingScheduler()}.
 *
 * A factory is necessary to inject the dependencies of the individual
 * scheduler classes.
 *
 * @author pxav
 */
@Singleton
public class SchedulerFactory {

  // dependencies of the scheduler classes
  private KelpSchedulerRepository kelpSchedulerRepository;

  @Inject
  public SchedulerFactory(KelpSchedulerRepository kelpSchedulerRepository) {
    this.kelpSchedulerRepository = kelpSchedulerRepository;
  }

  /**
   * Creates a new instance of {@link RepeatingScheduler}.
   *
   * @return a new {@link RepeatingScheduler} instance.
   */
  public RepeatingScheduler newRepeatingScheduler() {
    return new RepeatingScheduler(this.kelpSchedulerRepository);
  }

  /**
   * Creates a new instance of {@link DelayedScheduler}.
   *
   * @return a new {@link DelayedScheduler} instance.
   */
  public DelayedScheduler newDelayedScheduler() {
    return new DelayedScheduler(this.kelpSchedulerRepository);
  }

}
