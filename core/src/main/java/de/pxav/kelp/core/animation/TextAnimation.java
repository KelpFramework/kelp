package de.pxav.kelp.core.animation;

import java.util.List;

/**
 * This interface represents the basis of every text animation
 * used within the Kelp framework. A text animation basically takes
 * a string and optionally some additional data and converts it to
 * an animation. The animation consists of a collection of multiple
 * strings. If you play those strings with a regular interval
 * it will look like an animation.
 *
 * @author pxav
 */
public interface TextAnimation {

  /**
   * This method calculates the given data from the animation class
   * to a final animation. The list contains all animation states that - if played
   * one by one - make up the final animation.
   *
   * @return The final collection of all states of the animation in a chronological order.
   */
  List<String> states();

}
