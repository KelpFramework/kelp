package de.pxav.kelp.core.test.common;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.common.ConcurrentListMultimap;
import de.pxav.kelp.core.common.ConcurrentMultimap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * This class is a basic test for the features of a list-based
 * concurrent multimap provided by Kelp, {@link ConcurrentListMultimap} to be
 * specific.
 *
 * @author pxav
 */
public class ConcurrentListMultimapTest {

  private ConcurrentMultimap<String, Integer> map;

  /**
   * Refreshes the instance of the map before each test
   * so we get neutral and unaffected results in each test.
   */
  @Before
  public void initMap() {
    this.map = ConcurrentListMultimap.create();
  }

  /**
   * Tests basic insert operations of the map including:
   * - {@link ConcurrentListMultimap#put(Object, Object)}
   * - {@link ConcurrentListMultimap#putAll(Map)}
   */
  @Test
  public void testInsert() {
    Map<String, Integer> killStreak = Maps.newHashMap();
    killStreak.put("player1", 3);
    killStreak.put("player2", 0);
    killStreak.put("player3", 5);

    this.map.putAll(killStreak);
    this.map.put("player2", 0);
    Assert.assertEquals(this.map.size(), 4);

    this.map.put("player4", 6);
    Assert.assertEquals(this.map.size(), 5);
  }

  /**
   * Tests all {@code contains()} methods of the multimap.
   * This includes checking for keys, values and whole entries.
   */
  @Test
  public void testContains() {
    this.map.put("player2", 0);
    this.map.put("player2", 1);
    this.map.put("player3", 4);

    Assert.assertFalse(this.map.containsKey("soos"));
    Assert.assertFalse(this.map.containsValue(6));
    Assert.assertFalse(this.map.containsEntry("player3", 1));

    Assert.assertTrue(this.map.containsValue(0));
    Assert.assertTrue(this.map.containsKey("player2"));
    Assert.assertTrue(this.map.containsEntry("player2", 0));
    Assert.assertTrue(this.map.containsEntry("player2", 1));
  }

  /**
   * Tests the thread-safety of the multimap. In a normal
   * map, a {@link java.util.ConcurrentModificationException} would
   * occur if you iterate through a map and modify it at the same time,
   * which does not happen if you use a concurrent multimap.
   */
  @Test
  public void testConcurrentModificationException() {
    this.map.put("1", 2);
    this.map.put("1", 3);
    this.map.put("2", 24);

    // test whether concurrent modification exception can occur
    this.map.forEach((player, streak) -> this.map.remove(player, streak));

    // test whether all items have been removed
    Assert.assertEquals(this.map.size(), 0);
  }

}
