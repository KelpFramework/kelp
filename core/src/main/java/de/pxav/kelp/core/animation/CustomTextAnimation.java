package de.pxav.kelp.core.animation;

import java.util.Arrays;
import java.util.List;

/**
 * This animation allows you to create custom text animations
 * which are independent from the existing algorithms.
 *
 * If you don't like the animation templates you can use this
 * to create your own.
 *
 * You can also create own animation algorithms if you want
 * by simply creating a new class which implements
 * {@code TextAnimation} and takes a text input to process.
 *
 * @author pxav
 * @see TextAnimation
 */
public class CustomTextAnimation implements TextAnimation {

  private List<String> states;

  public CustomTextAnimation() {}

  public static CustomTextAnimation create() {
    return new CustomTextAnimation();
  }

  public CustomTextAnimation addStates(String... states) {
    this.states.addAll(Arrays.asList(states));
    return this;
  }

  public CustomTextAnimation setStates(String... states) {
    this.states.clear();
    this.states.addAll(Arrays.asList(states));
    return this;
  }

  @Override
  public List<String> states() {
    return this.states;
  }
}
