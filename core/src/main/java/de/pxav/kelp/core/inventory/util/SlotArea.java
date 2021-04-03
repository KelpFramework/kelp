package de.pxav.kelp.core.inventory.util;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to easily represent a collection of slots
 * inside a {@link de.pxav.kelp.core.inventory.type.KelpInventory}.
 *
 * If you want to create a {@link de.pxav.kelp.core.inventory.widget.Pagination pagination}
 * for example, where you want to use the entire inventory space as pagination area, it would be a pain
 * to pass all slots individually. It would be more convenient to just calculate
 * a rectangular area and pass all slots of this area in the
 * {@link de.pxav.kelp.core.inventory.widget.Pagination#contentSlots(int...)} method,
 * which can be achieved using {@link #rectangle(int, int)}.
 *
 * Other use cases would be if you want to create a line separator inside a GUI.
 * Instead of calculating the slots for this line by yourself, use one of the line
 * methods provided by this class such as {@link #lineAtRow(int)} or {@link #outerBorder(int)}.
 *
 * @author pxav
 */
public class SlotArea {

  /**
   * Gets all slots at the uppermost line of an inventory,
   * which is equivalent to the first nine slots (0-8).
   * This method is independent from the inventory size as the
   * top line always exists independent from the row amount.
   *
   * @return A set of all slots in the uppermost row of the inventory.
   */
  public static Set<Integer> upperLine() {
    return custom(0, 1, 2, 3, 4, 5, 6, 7, 8);
  }

  /**
   * Gets all slots at the lowermost line of an inventory
   * (The last row).
   *
   * @param inventorySize The size of the inventory you want to get the
   *                      line of in slots. This is needed to know
   *                      which of the rows is really the last one of the
   *                      inventory.
   * @return A set of all slots at the lowermost row of the given inventory.
   */
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

  /**
   * Gets all all slots of the first ("leftest") column in
   * an inventory.
   *
   * @param inventorySize The size of the inventory whose left line
   *                      you want to get.
   * @return A set of all slots at the first column of the inventory.
   */
  public static Set<Integer> leftLine(int inventorySize) {
    return lineAtColumn(0, inventorySize);
  }

  public static Set<Integer> rightLine(int inventorySize) {
    return lineAtColumn(8, inventorySize);
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

  /**
   * Gets all slots on the outermost slots of the inventory. This
   * includes all slots returned by:
   * - {@link #upperLine()}
   * - {@link #lowerLine(int)}
   * - {@link #leftLine(int)}
   * - {@link #rightLine(int)}
   *
   * @param inventorySize The size of the inventory whose outermost
   *                      slots you want to get.
   * @return A set of all slots at the outer margin of the inventory.
   */
  public static Set<Integer> outerBorder(int inventorySize) {
    Set<Integer> output = Sets.newHashSet();
    output.addAll(upperLine());
    output.addAll(lowerLine(inventorySize));
    output.addAll(rightLine(inventorySize));
    output.addAll(leftLine(inventorySize));
    return output;
  }

  /**
   * Generates a rectangular area of slots. This rectangle is defined by
   * two corners passed as {@code firstSlot} and {@code secondSlot}. It does
   * not matter which of those slots is the highest/lowest as this is calculated by
   * the method automatically. The only important thing is that both of the
   * slots are inside the inventory you want to render the rectangle two and that
   * the given slots are opposite of each other.
   *
   * @param firstSlot   One corner of the rectangle (has to be opposite of the other slot)
   * @param secondSlot  Another corner of the rectangle (has to be opposite of the other slot)
   * @return A set of slots representing the rectangular area between the two given slots.
   */
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

  /**
   * Creates a new set of slots based on the given slot array.
   * This method automatically removes all duplicates from the
   * array.
   *
   * @param slots The slot array to create the hash set of.
   * @return A set of all slots passed in the slot array (no duplicates)
   */
  public static Set<Integer> custom(Integer... slots) {
    return Sets.newHashSet(slots);
  }

  /**
   * Gets the exact slot id of a slot located at the given
   * row and column.
   *
   * @param row     The row of the slot you want to get.
   * @param column  The column of the slot you want to get.
   * @return The exact slot id of the slot located at the given row and column.
   */
  public static int getSlot(int row, int column) {
    // get all slots located at the given row and column
    // of the desired slots. Those arrays should have exactly
    // one common element, which is the desired slot id.
    int[] slotsInRow = slotsInRow(row);
    int[] slotsInColumn = slotsInColumn(column);

    // search for the common element in the given arrays
    // representing the searched slot id.
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

  /**
   * Gets the row of a specific slot.
   *
   * @param slot The slot you want to get the row of.
   * @return The row in which the given slot is located in.
   */
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

  /**
   * Gets all slots located in the given row.
   * If the row is invalid (too big or negative), an empty
   * array will be returned.
   *
   * @param row The row you want to get all the slots of.
   * @return An array containing all slots of the given row.
   */
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

  /**
   * Gets the column in which the given slot is located in.
   *
   * @param slot The slot you want to get the column of.
   * @return The column of the provided slot.
   */
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

  /**
   * Gets all slots located in the given column.
   * If the column is invalid (too big or a negative value),
   * an empty array is returned.
   *
   * @param column The column to get the slots of.
   * @return An array containing all slots located in the given column.
   */
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
