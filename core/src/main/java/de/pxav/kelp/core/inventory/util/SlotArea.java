package de.pxav.kelp.core.inventory.util;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class SlotArea {

  public static Set<Integer> upperLine() {
    return custom(0, 1, 2, 3, 4, 5, 6, 7, 8);
  }

  public static Set<Integer> lowerLine(int inventorySize) {
    return custom(
      inventorySize - 1,
      inventorySize - 2,
      inventorySize - 3,
      inventorySize - 4,
      inventorySize - 5,
      inventorySize - 6,
      inventorySize - 7,
      inventorySize - 8,
      inventorySize - 9
    );
  }

  public static Set<Integer> leftLine(int inventorySize) {
    Set<Integer> output = Sets.newHashSet();
    for (int row = 0; row < inventorySize / 9; row++) {
       output.add(getSlot(row, 0));
    }
    return output;
  }

  public static Set<Integer> rightLine(int inventorySize) {
    Set<Integer> output = Sets.newHashSet();
    for (int row = 0; row < inventorySize / 9; row++) {
      output.add(getSlot(row, 8));
    }
    return output;
  }

  public static Set<Integer> lineAtRow(int row) {
    Set<Integer> output = Sets.newHashSet();
    for (int column = 0; column < 8; column++) {
      output.add(getSlot(row, column));
    }
    return output;
  }

  public static Set<Integer> lineAtColumn(int column, int inventorySize) {
    Set<Integer> output = Sets.newHashSet();
    for (int row = 0; row < inventorySize / 9; row++) {
      output.add(getSlot(row, column));
    }
    return output;
  }

  public static Set<Integer> outerBorder(int inventorySize) {
    Set<Integer> output = Sets.newHashSet();
    output.addAll(upperLine());
    output.addAll(lowerLine(inventorySize));
    output.addAll(rightLine(inventorySize));
    output.addAll(leftLine(inventorySize));
    return output;
  }

  public static Set<Integer> rectangle(int firstSlot, int secondSlot) {
    Set<Integer> output = Sets.newHashSet();
    int maxPos = Math.max(firstSlot, secondSlot);
    int minPos = Math.min(firstSlot, secondSlot);

    for (int row = getRow(minPos); row <= getRow(maxPos); row++) {
      for (int column = getColumn(minPos); column <= getColumn(maxPos); column++) {
        if (row <= getRow(maxPos)
          && row >= getRow(minPos)
          && column >= getColumn(minPos)
          && column <= getColumn(maxPos)) {
            output.add(getSlot(row, column));
        }
      }
    }

    return output;
  }

  public static Set<Integer> custom(Integer... slots) {
    return Sets.newHashSet(slots);
  }

  public static int getSlot(int row, int column) {
    int[] slotsInRow = slotsInRow(row);
    int[] slotsInColumn = slotsInColumn(column);

    if (slotsInRow.length > 0 && slotsInColumn.length > 0) {
      Set<Integer> rowSlots = new HashSet<>();
      for (int j : slotsInRow) {
        rowSlots.add(j);
      }

      for (int i : slotsInColumn) {
        if (rowSlots.contains(i)) {
          return i;
        }
      }
    }

    return 0;
  }

  public static int getRow(int slot) {
    if (slot < 9) {
      return 0;
    }
    if (slot < 18) {
      return 1;
    }
    if (slot < 27) {
      return 2;
    }
    if (slot < 36) {
      return 3;
    }
    if (slot < 45) {
      return 4;
    }
    if (slot < 54) {
      return 5;
    }
    return 0;
  }


  public static int[] slotsInRow(int row) {
    switch (row) {
      case 0:
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
      case 1:
        return new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17};
      case 2:
        return new int[] {18, 19, 20, 21, 22, 23, 24, 25, 26};
      case 3:
        return new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35};
      case 4:
        return new int[] {36, 37, 38, 39, 40, 41, 42, 43, 44};
      case 5:
        return new int[] {45, 46, 47, 48, 49, 50, 51, 52, 53};
    }
    return new int[] {};
  }

  public static int getColumn(int slot) {
    switch (slot) {
      case 0: case 9: case 18: case 27: case 36: case 45:
        return 0;
      case 1: case 10: case 19: case 28: case 37: case 56:
        return 1;
      case 2: case 11: case 20: case 29: case 38: case 47:
        return 2;
      case 3: case 12: case 21: case 30: case 39: case 48:
        return 3;
      case 4: case 13: case 22: case 31: case 40: case 49:
        return 4;
      case 5: case 14: case 23: case 32: case 41: case 50:
        return 5;
      case 6: case 15: case 24: case 33: case 42: case 51:
        return 6;
      case 7: case 16: case 25: case 34: case 43: case 52:
        return 7;
      case 8: case 17: case 26: case 35: case 44: case 53:
        return 8;
    }
    return 0;
  }

  public static int[] slotsInColumn(int column) {
    switch (column) {
      case 0:
        return new int[] {0, 9, 18, 27, 36, 45};
      case 1:
        return new int[] {1, 10, 19, 28, 37, 56};
      case 2:
        return new int[] {2, 11, 20, 29, 38, 47};
      case 3:
        return new int[] {3, 12, 21, 30, 39, 48};
      case 4:
        return new int[] {4, 13, 22, 31, 40, 49};
      case 5:
        return new int[] {5, 14, 23, 32, 41, 50};
      case 6:
        return new int[] {6, 15, 24, 33, 42, 51};
      case 7:
        return new int[] {7, 16, 25, 34, 43, 52};
      case 8:
        return new int[] {8, 17, 26, 35, 44, 53};
    }
    return new int[] {};
  }

}
