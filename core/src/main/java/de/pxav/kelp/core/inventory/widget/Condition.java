package de.pxav.kelp.core.inventory.widget;

/**
 * This interface represents a dynamic condition as it is used
 * in the {@code ToggleableWidget} for example.
 *
 * This is useful if you want to check a condition multiple times
 * during the application runtime. If you would only pass a boolean
 * once to represent a condition, this value is constant and
 * checking it again won't check the current situation.
 *
 * Therefore it is useful to write a condition check into an
 * interface method as it can always be executed again, which
 * will return the latest result.
 *
 * @author pxav
 * @see ToggleableWidget
 */
public interface Condition {

  /**
   * Contains the condition you want to check.
   *
   * @return The result of your condition check.
   */
  boolean getCondition();

}
