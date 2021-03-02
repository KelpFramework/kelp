package de.pxav.kelp.core.world.util;

import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.util.Vector;

/**
 * This util class is used to express cardinal directions
 * of any {@link de.pxav.kelp.core.world.KelpLocation}.
 *
 * Cardinal directions depend on the rotation on the y-axis
 * and are therefore calculated using a location's {@code yaw}.
 * If you use the {@link KelpLocation#getCardinalDirection()} method,
 * the given location has to have a {@code yaw} to be able to convert
 * it to a {@code CardinalDirection}.
 *
 * You can also use a {@link org.bukkit.util.Vector} to determine
 * the yaw, which can be done with {@link KelpLocation#setDirection(Vector)}.
 *
 * Cardinal directions can be used to express the facing of a player or
 * a relative block for example. Given a block, you could use the direction
 * with {@link de.pxav.kelp.core.world.KelpBlock#getRelative(CardinalDirection)}
 * to get the neighbouring block in a specific direction.
 *
 * @author pxav
 */
public enum CardinalDirection {

  NORTH,
  NORTH_EAST,
  EAST,
  SOUTH_EAST,
  SOUTH,
  SOUTH_WEST,
  WEST,
  NORTH_WEST;

}
