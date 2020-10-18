package de.pxav.kelp.core.scheduler;

import java.util.concurrent.TimeUnit;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class TimeConverter {

  public static int secondsToTicks(int seconds) {
    return seconds * 20;
  }

  public static int getTicks(int value, TimeUnit timeUnit) {
    switch (timeUnit) {
      case MILLISECONDS:
        return secondsToTicks(value * 1000);
      case SECONDS:
        return secondsToTicks(value);
      case MINUTES:
        return secondsToTicks(value * 60);
      case HOURS:
        return secondsToTicks(value * 60 * 60);
      case DAYS:
        return secondsToTicks(value * 60 * 60 * 24);
    }
    return 0;
  }

}
