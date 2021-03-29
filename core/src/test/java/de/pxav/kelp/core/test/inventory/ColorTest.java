package de.pxav.kelp.core.test.inventory;

import de.pxav.kelp.core.inventory.metadata.Color;
import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

  @Test
  public void testHEXtoRGB() {
    String hex = "1D870F";

    Color color = Color.fromHEX(hex);
    Color expected = Color.fromRGB(29, 135, 15);
    Assert.assertEquals(color, expected);
  }

  @Test
  public void testRGBtoHEX() {
    int red = 29;
    int green = 135;
    int blue = 15;

    String expected = "1D870F";
    String hex = Color.fromRGB(red, green, blue).asHex();

    Assert.assertEquals(expected, hex);
  }

}
