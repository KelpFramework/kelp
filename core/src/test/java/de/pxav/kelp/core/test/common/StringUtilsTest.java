package de.pxav.kelp.core.test.common;

import de.pxav.kelp.core.common.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * This class contains a bunch of unit tests testing the
 * functionality of {@link StringUtils}.
 *
 * @author pxav
 */
public class StringUtilsTest {

  private StringUtils stringUtils;

  /**
   * Before each test, a fresh instance of {@link StringUtils}
   * is created for neutral, unaffected test results.
   */
  @Before
  public void newInstance() {
    this.stringUtils = new StringUtils();
  }

  @Test
  public void testColorCodeEndings() {
    char colorCode = stringUtils.randomColorCode();
    String text1 = UUID.randomUUID() + "§" + colorCode;

    Assert.assertEquals("§" + colorCode, stringUtils.endsWithColorCode(text1));
  }

}
