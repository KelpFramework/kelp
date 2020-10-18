package de.pxav.kelp.core.scheduler.type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SchedulerFactory {

  private KelpSchedulerRepository kelpSchedulerRepository;

  @Inject
  public SchedulerFactory(KelpSchedulerRepository kelpSchedulerRepository) {
    this.kelpSchedulerRepository = kelpSchedulerRepository;
  }

  public RepeatingScheduler newRepeatingScheduler() {
    return new RepeatingScheduler(this.kelpSchedulerRepository);
  }

  public DelayedScheduler newDelayedScheduler() {
    return new DelayedScheduler(this.kelpSchedulerRepository);
  }

}
