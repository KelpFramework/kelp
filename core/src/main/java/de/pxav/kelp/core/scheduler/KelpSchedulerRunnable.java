package de.pxav.kelp.core.scheduler;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface KelpSchedulerRunnable {

  void run(UUID taskId);

}
