package de.pxav.kelp.core.world.util;

/**
 * Represents the generator type of a {@link de.pxav.kelp.core.world.KelpWorld}.
 * This can be either a normal overworld or the nether or the end.
 * This is the equivalent to the normal {@link org.bukkit.World.Environment} provided
 * by bukkit.
 *
 * @author pxav
 */
public enum WorldType {

  /**
   * The current world is an overworld.
   */
  NORMAL,

  /**
   * The current world is the end. This also changes the sky background
   * to the purple background you might know.
   */
  THE_END,

  /**
   * The current world is the nether.
   */
  THE_NETHER

}
