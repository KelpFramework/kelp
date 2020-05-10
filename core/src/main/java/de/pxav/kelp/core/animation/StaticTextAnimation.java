package de.pxav.kelp.core.animation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public final class StaticTextAnimation implements TextAnimation {

  private String text;

  StaticTextAnimation(String text) {
    this.text = text;
  }

  StaticTextAnimation() {}

  public StaticTextAnimation text(String text) {
    this.text = text;
    return this;
  }

  @Override
  public List<String> states() {
    return Collections.singletonList(this.text);
  }
}
