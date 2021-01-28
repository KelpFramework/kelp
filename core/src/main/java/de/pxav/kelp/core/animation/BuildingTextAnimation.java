package de.pxav.kelp.core.animation;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.common.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a text animation,
 * which builds up the desired text by always
 * appending a new character. This ignores color codes
 * so that the animation does not pause the animation flow.
 *
 * @author pxav
 */
public final class BuildingTextAnimation implements TextAnimation {

  // the actual text you want to animate
  private String text;

  // should spaces be skipped when building the animation states?
  private boolean ignoreSpaces = false;

  private StringUtils stringUtils;

  public BuildingTextAnimation(StringUtils stringUtils, String text) {
    this.stringUtils = stringUtils;
    this.text = text;
  }

  public BuildingTextAnimation(StringUtils stringUtils) {
    this.stringUtils = stringUtils;
  }

  public static BuildingTextAnimation create() {
    return new BuildingTextAnimation(
      KelpPlugin.getInjector().getInstance(StringUtils.class)
    );
  }

  public BuildingTextAnimation text(String text) {
    this.text = text;
    return this;
  }

  public BuildingTextAnimation ignoreSpaces() {
    this.ignoreSpaces = true;
    return this;
  }

  public BuildingTextAnimation useSpaces() {
    this.ignoreSpaces = false;
    return this;
  }

  @Override
  public List<String> states() {

    List<String> states = new ArrayList<>();
    StringBuilder currentState = new StringBuilder();

    int colorCodeState = -1;
    int stateCursor = -1;

    for (char c : text.toCharArray()) {

      if (c == ' ' && this.ignoreSpaces) {
        currentState.append(c);
        continue;
      }

      if (c == '§') {
        currentState.append(c);
        colorCodeState = 0;
        continue;
      }

      if (colorCodeState == 0) {
        if (stringUtils.isFormattingCode(c)) {
          currentState.append(c);
          colorCodeState = 1;
        } else {
          colorCodeState = -1;
        }

        continue;
      }

      if (colorCodeState == 1) {
        colorCodeState = -1;
      }

      currentState.append(c);
      String previousState = stateCursor == -1 ? "" : states.get(stateCursor);
      states.add(previousState + currentState.toString());
      currentState.delete(0, currentState.length());
      stateCursor++;

    }

    return states;
  }

}
