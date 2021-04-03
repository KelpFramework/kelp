package de.pxav.kelp.core.test.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.inventory.util.SlotArea;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class SlotAreaTest {

  @Test
  public void testSlotToCoordinate() {
    int slot = 10;
    Assert.assertEquals(SlotArea.getRow(slot), 1);
    Assert.assertEquals(SlotArea.getColumn(slot), 1);

    slot = 25;
    Assert.assertEquals(SlotArea.getRow(slot), 2);
    Assert.assertEquals(SlotArea.getColumn(slot), 7);
  }

  @Test
  public void testCoordinateToSlot() {
    int row = 3;
    int column = 4;
    Assert.assertEquals(SlotArea.getSlot(row, column), 31);
  }

  @Test
  public void testRectangle() {
    int pos1 = 10, pos2 = 32;
    Collection<Integer> expected = Sets.newHashSet(10, 11, 12, 13, 14, 19, 20, 21, 22, 23, 28, 29, 30, 31, 32);
    Assert.assertEquals(SlotArea.rectangle(pos1, pos2), expected);
  }

  @Test
  public void testLeftLine() {
    Collection<Integer> expected = Sets.newHashSet(0, 9, 18);
    Assert.assertEquals(SlotArea.leftLine(27), expected);
  }

  @Test
  public void testRightLine() {
    Collection<Integer> expected = Sets.newHashSet(8, 17, 26);
    Assert.assertEquals(SlotArea.rightLine(27), expected);
  }

  @Test
  public void testLowerLine() {
    Collection<Integer> expected = Sets.newHashSet(18, 19, 20, 21, 22, 23, 24, 25, 26);
    Assert.assertEquals(SlotArea.lowerLine(27), expected);
  }

}
