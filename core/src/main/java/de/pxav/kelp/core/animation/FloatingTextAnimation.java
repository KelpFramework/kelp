package de.pxav.kelp.core.animation;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class FloatingTextAnimation implements TextAnimation {

  private String text;
  private int charWidth;
  private boolean slideIn;
  private SlideDirection slideDirection;

  public FloatingTextAnimation() {

  }

  public FloatingTextAnimation text(String text) {
    this.text = text;
    return this;
  }

  public FloatingTextAnimation charWidth(int charWidth) {
    this.charWidth = charWidth;
    return this;
  }

  public FloatingTextAnimation slideIn(boolean slideIn) {
    this.slideIn = slideIn;
    return this;
  }

  public FloatingTextAnimation slideDirection(SlideDirection slideDirection) {
    this.slideDirection = slideDirection;
    return this;
  }

  private String initialState() {
    if (slideIn) {
      return spaces(charWidth);
    } else if (text.length() > charWidth) {
      return text.substring(0, charWidth);
    }
    return null;
  }

  private String spaces(int amount) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < amount; i++) {
      stringBuilder.append(" ");
    }
    return stringBuilder.toString();
  }

  private List<String> slideAnimation(SlideDirection slideDirection) {
    List<String> states = Lists.newArrayList();
    states.add(initialState());

    if (slideDirection == SlideDirection.FROM_LEFT) {
      for (int i = 0; i < text.length() && i < charWidth; i++) {
        char c = text.charAt(i);
        String s = text.substring(0, i) + c + spaces(charWidth - i);
        states.add(s);
      }
    }

    return states;
  }

  @Override
  public List<String> states() {

    return Lists.newArrayList();
  }

}
