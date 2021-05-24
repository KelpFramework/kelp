package de.pxav.kelp.core.common;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A small collection of some mathematical utils for Kelp.
 * This class is functional, so all methods can be accessed statically.
 *
 * @author pxav
 */
public class MathUtils {

  private static final int[] values =           { 1000, 900,  500, 400,  100, 90,   50,  40,   10,  9,    5,   4,     1 };
  private static final String[] romanLiterals = { "M",  "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

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
   * fulfilled. If your {@code chance} is set to {@code 0.5},
   * there will be a 50% chance that this method will return
   * {@code true}. If it is {@code 0.05}, then there will be a
   * {@code 5} per cent chance that this method will return
   * {@code true}.
   *
   * @param chance The chance to use for random calculation.
   *               This number may range from 0.0 (always false, 0% change)
   *               and 1.0 (always true, 100% chance), where 0.01 is equal to
   *               1%.
   * @return Either true or false depending on your luck and the provided chance.
   */
  public static boolean perCentChance(double chance) {
    if (chance >= 1.0d) {
      return true;
    }
    if (chance <= 0.0d) {
      return false;
    }

    return Math.random() < chance;
  }

  public static String getRomanNumber(int number) {
    StringBuilder s = new StringBuilder();

    for (int i = 0; i < values.length; i++) {
      while (number >= values[i]) {
        number -= values[i];
        s.append(romanLiterals[i]);
      }
    }
    return s.toString();
  }

}
