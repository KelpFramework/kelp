package string;

import de.pxav.kelp.common.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public final class StringUtilsTest {

  // the string utils to check inside this unit test
  private StringUtils stringUtils = new StringUtils();

  /**
   * Tests if the string utils remove the correct chars
   * from a given text.
   */
  @Test
  public void testCharDeletion() {
    String text = "Hello.";

    Assert.assertEquals("Hello", stringUtils.removeLastChar(text));
  }

}
