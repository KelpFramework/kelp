package de.pxav.kelp.core.test.common;

import de.pxav.kelp.core.common.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
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

  @Test
  public void testLastFormattingCodesOf() {
    String lastCodes1 = stringUtils.lastFormattingCodesOf("§b§lYOUR TITLE STRING HERE");
    Assert.assertEquals(lastCodes1, "§b§l");

    String lastCodes2 = stringUtils.lastFormattingCodesOf("§b§lYOUR TITLE STRING §oHERE");
    Assert.assertEquals(lastCodes2, "§b§l§o");
  }

  @Test
  public void testStyleCodeExtraction() {
    List<String> styleCodes1 = stringUtils.extractStyleCodes("§a§lWelcome §6to the §oserver");
    Assert.assertEquals(styleCodes1.size(), 2);
  }

  @Test
  public void testColorCodeExtraction() {
    List<String> styleCodes1 = stringUtils.extractColorCodes("§a§lWelcome §6to the §oserver");
    Assert.assertEquals(styleCodes1.size(), 2);
  }

  @Test
  public void testFormattingCodeExtraction() {
    List<String> styleCodes1 = stringUtils.extractFormattingCodes("§a§lWelcome §6to the §oserver");
    Assert.assertEquals(styleCodes1.size(), 4);
  }

}
