package de.pxav.kelp.core.test.constant;

import de.pxav.kelp.core.version.KelpVersion;
import org.junit.Assert;
import org.junit.Test;

public class KelpVersionTest {

  @Test
  public void testHigher() {
    Assert.assertEquals(KelpVersion.higherVersion(KelpVersion.MC_1_8_0, KelpVersion.MC_1_10_2), KelpVersion.MC_1_10_2);
  }

  @Test
  public void testLower() {
    Assert.assertEquals(KelpVersion.lowerVersion(KelpVersion.MC_1_8_0, KelpVersion.MC_1_10_2), KelpVersion.MC_1_8_0);
  }

  @Test
  public void testHighestVersionOf() {
    Assert.assertEquals(KelpVersion.highestVersionOf(KelpVersion.MC_1_8_0, KelpVersion.MC_1_10_2, KelpVersion.MC_1_15_1, KelpVersion.MC_1_11_1), KelpVersion.MC_1_15_1);
  }

  @Test
  public void testLowestVersionOf() {
    Assert.assertEquals(KelpVersion.lowestVersionOf(KelpVersion.MC_1_8_0, KelpVersion.MC_1_10_2, KelpVersion.MC_1_15_1, KelpVersion.MC_1_11_1), KelpVersion.MC_1_8_0);
  }

  @Test
  public void testNextFullVersion() {
    Assert.assertEquals(KelpVersion.nextFullVersion(KelpVersion.MC_1_12_2), KelpVersion.MC_1_13_0);
  }

}
