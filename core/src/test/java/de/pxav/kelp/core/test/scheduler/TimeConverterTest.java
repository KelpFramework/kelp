package de.pxav.kelp.core.test.scheduler;

import de.pxav.kelp.core.scheduler.TimeConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * This class tests if the {@link TimeConverter} converts
 * ticks and real time correctly.
 *
 * @author pxav
 */
public class TimeConverterTest {

  @Test
  public void testSecondsToTicks() {
    Assert.assertEquals(TimeConverter.secondsToTicks(1), 20);
    Assert.assertEquals(TimeConverter.secondsToTicks(3), 60);
    Assert.assertEquals(TimeConverter.secondsToTicks(10), 200);
  }

  @Test
  public void testRealTimeToTicks() {
    Assert.assertEquals(TimeConverter.getTicks(20, TimeUnit.MINUTES), 24_000);
    Assert.assertEquals(TimeConverter.getTicks(1, TimeUnit.HOURS), 72_000);
  }

}
