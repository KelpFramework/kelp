package de.pxav.kelp.core.common;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A small collection of some mathematical utils for Kelp.
 * This class is functional, so all methods can be accessed statically.
 *
 * @author pxav
 */
public class MathUtils {

  /**
   * Checks if the given number x is even, which is
   * equal to {@code x % 2 == 0}
   *
   * @param number The number to check.
   * @return {@code true} if the number is even.
   */
  public static boolean isEven(int number) {
    return number % 2 == 0;
  }

  /**
   * Checks if the given number x is odd, which is
   * equal to {@code x % 2 == 1}
   *
   * @param number The number to check.
   * @return {@code true} if the number is odd.
   */
  public static boolean isOdd(int number) {
    return number % 2 != 0;
  }

  /**
   * Randomly calculates whether a certain chance has been
   * fulfilled. If your {@code chance} is set to {@code 50},
   * there will be a 50% chance that this method will return
   * {@code true}. If it is {@code 5}, then there will be a
   * {@code 5} per cent chance that this method will return
   * {@code true}.
   *
   * @param chance The chance to use for random calculation.
   * @return Either true or false depending on your luck and the provided chance.
   */
  public static boolean perCentChance(int chance) {
    if (chance == 100) {
      return true;
    }
    if (chance == 0) {
      return false;
    }
    return ThreadLocalRandom.current().nextInt(0, 101) < chance;
  }

}
