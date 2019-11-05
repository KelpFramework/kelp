package animation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import kelp.animation.FloatingTextAnimation;
import kelp.animation.SlideDirection;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class FloatingTextAnimationTest {

  @Test
  public void testFloatingTextAnimation() {
//    FloatingTextAnimation animation = new FloatingTextAnimation();
//    animation.text("Hello you");
//    animation.slideIn(true);
//    animation.slideDirection(SlideDirection.FROM_LEFT);
//    animation.charWidth(5);

    List<String> test = Lists.newArrayList();
    test.add("Test1");
    test.add("Test2");
    test.add("Test3");
    test.add("Test4");
    test.add("Test5");

    Map<String, Integer> lines = Maps.newHashMap();

    for (int i = test.size() - 1; i >= 0; i--) {
      lines.put(test.get(i), i);
    }

    for (Map.Entry<String, Integer> stringIntegerEntry : lines.entrySet()) {
      System.out.println(stringIntegerEntry.getKey() +"@" +stringIntegerEntry.getValue());
    }

  }

}
