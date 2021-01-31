package de.pxav.kelp.core.animation;

import java.util.Collections;
import java.util.List;

/**
 * This text animation is used to display static texts where a
 * {@link TextAnimation} object is required by design. If you have
 * an animated sidebar for example and you want to display a static
 * text for example for debugging purposes, you can use this animation
 * type.
 *
 * @author pxav
 */
public final class StaticTextAnimation implements TextAnimation {

  private String text;

  StaticTextAnimation(String text) {
    this.text = text;
  }

  StaticTextAnimation() {}

  public static StaticTextAnimation create() {
    return new StaticTextAnimation();
  }

  /**
   * Sets the text to be displayed in the static animation.
   *
   * @param text The text to be displayed.
   * @return Instance of the current animation object.
   */
  public StaticTextAnimation text(String text) {
    this.text = text;
    return this;
  }

  @Override
  public List<String> states() {
    return Collections.singletonList(this.text);
  }
}
