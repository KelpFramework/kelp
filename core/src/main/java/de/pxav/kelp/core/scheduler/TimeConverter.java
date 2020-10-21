package de.pxav.kelp.core.scheduler;

import java.util.concurrent.TimeUnit;

/**
 * This util class is used to convert time units and
 * the central minecraft time unit {@code ticks}.
 *
 * A {@code tick} is one heartbeat of the minecraft server
 * and client and the smallest unit which can be taken to
 * schedule events. One tick is equal to 50ms, so 20 ticks
 * are usually one second. But due to server lags, the server
 * might run at lower tick rates such as 19 or 18 TPS (ticks
 * per second), which is why there is no exact calculation
 * between ticks and seconds.
 *
 * Using scheduler delays or intervals of less than 50ms does
 * not make sense in most cases, because the minecraft server
 * cannot handle this scheduler speed.
 *
 * @author pxav
 */
public class TimeConverter {

  /**
   * Converts the given amount of seconds to the equivalent amount of ticks.
   * In particular the operation is {@code seconds * 20} as one second
   * is equivalent to 20 ticks.
   *
   * This method is equal to {@link #getTicks(int, TimeUnit)} where TimeUnit is
   * {@code SECONDS}.
   *
   * @param seconds The amount of seconds
   * @return The amount of ticks which pass in the time of the given seconds.
   */
  public static int secondsToTicks(int seconds) {
    return seconds * 20;
  }

  /**
   * Takes any "real" time unit provided by {@link TimeUnit} and
   * converts it to the equivalent amount of ticks, which pass
   * in about the same time.
   *
   * @param value     The amount of seconds/minutes/... passed
   * @param timeUnit  The time unit of the given value.
   * @return the amount of ticks which would pass
   */
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
