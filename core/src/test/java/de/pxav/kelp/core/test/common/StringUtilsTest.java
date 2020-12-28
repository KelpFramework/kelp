package de.pxav.kelp.core.test.common;

import de.pxav.kelp.core.common.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class StringUtilsTest {

  private StringUtils stringUtils;

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
