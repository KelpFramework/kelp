package animation;

import de.pxav.kelp.core.animation.BuildingTextAnimation;
import de.pxav.kelp.core.common.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class should test if the building
 * text animations are formed correctly.
 *
 * @author pxav
 */
public class BuildingTextAnimationTest {

  private StringUtils stringUtils = new StringUtils();
  private BuildingTextAnimation animation = new BuildingTextAnimation(stringUtils);

  @Test
  public void testBuildingAnimation() {
    animation.text("Hello World");
    Assert.assertArrayEquals(new Object[]{
            "H",
            "He",
            "Hel",
            "Hell",
            "Hello",
            "Hello ",
            "Hello W",
            "Hello Wo",
            "Hello Wor",
            "Hello Worl",
            "Hello World",
    }, animation.states().toArray());

    animation.text("Hello World");
    animation.ignoreSpaces();
    Assert.assertArrayEquals(new Object[]{
            "H",
            "He",
            "Hel",
            "Hell",
            "Hello",
            "Hello W",
            "Hello Wo",
            "Hello Wor",
            "Hello Worl",
            "Hello World",
    }, animation.states().toArray());

    animation.text("§aHello §dWorld");
    animation.useSpaces();
    Assert.assertArrayEquals(new Object[]{
            "§aH",
            "§aHe",
            "§aHel",
            "§aHell",
            "§aHello",
            "§aHello ",
            "§aHello §dW",
            "§aHello §dWo",
            "§aHello §dWor",
            "§aHello §dWorl",
            "§aHello §dWorld",
    }, animation.states().toArray());
  }

}
