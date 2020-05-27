package string;

import de.pxav.kelp.core.common.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class tests how the plugin can process color
 * codes using the {@code StringUtils}.
 *
 * @author pxav
 */
public final class FormattingCodeProcessing {

  // instance of the util class to test
  private StringUtils stringUtils = new StringUtils();

  /**
   * This method tests how the string utils can extract
   * the color codes of a given text.
   */
  @Test
  public void testColorCodeExtraction() {
    String text = "§aHello §b§oWhat's up?";

    String colorResult = stringUtils.extractColorCodes(text).toString();
    String styleResult = stringUtils.extractStyleCodes(text).toString();

    Assert.assertEquals("[§a, §b]", colorResult);
    Assert.assertEquals("[§o]", styleResult);
  }

  /**
   * This method tests how the plugin detects the latest color codes
   * that affect the end of the string.
   */
  @Test
  public void testLatestCodeExtraction() {
    String normalText = "§8> §b§o@YourTwitter";
    String cutText = "§8> §b§o@YourTwitterAccount";
    Assert.assertEquals("§b§o", stringUtils.lastFormattingCodesOf(normalText));
    Assert.assertEquals("§b§o", stringUtils.lastFullFormattingCodesOf(cutText));
  }

  /**
   * This test tests the functionality of extracting
   * color codes from a string and remove them so you
   * only have the raw text left.
   */
  @Test
  public void testColorCodeReplacement() {
    String text = "§aWelcome §eto §7our §6Server§9!";
    Assert.assertEquals("Welcome to our Server!", stringUtils.removeFormattingCodes(text));
  }

}
