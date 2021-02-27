package de.pxav.kelp.core.common;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {

  public static boolean isEven(int number) {
    return number % 2 == 0;
  }

  public static boolean isOdd(int number) {
    return number % 2 != 0;
  }

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
