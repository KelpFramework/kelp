package de.pxav.kelp.core.test.common;

import de.pxav.kelp.core.common.MathUtils;
import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

  @Test
  public void testRomanNumbers() {
    Assert.assertEquals(MathUtils.getRomanNumber(1), "I");
    Assert.assertEquals(MathUtils.getRomanNumber(2), "II");
    Assert.assertEquals(MathUtils.getRomanNumber(3), "III");
    Assert.assertEquals(MathUtils.getRomanNumber(4), "IV");
    Assert.assertEquals(MathUtils.getRomanNumber(5), "V");
    Assert.assertEquals(MathUtils.getRomanNumber(6), "VI");
    Assert.assertEquals(MathUtils.getRomanNumber(14), "XIV");
  }

}
