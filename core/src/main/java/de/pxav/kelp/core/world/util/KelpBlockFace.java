package de.pxav.kelp.core.world.util;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public enum KelpBlockFace {

  NORTH(0, 0, -1),
  EAST(1, 0, 0),
  SOUTH(0, 0, 1),
  WEST(-1, 0, 0),
  UP(0, 1, 0),
  DOWN(0, -1, 0),
  NORTH_EAST(NORTH, EAST),
  NORTH_WEST(NORTH, WEST),
  SOUTH_EAST(SOUTH, EAST),
  SOUTH_WEST(SOUTH, WEST),
  WEST_NORTH_WEST(WEST, NORTH_WEST),
  NORTH_NORTH_WEST(NORTH, NORTH_WEST),
  NORTH_NORTH_EAST(NORTH, NORTH_EAST),
  EAST_NORTH_EAST(EAST, NORTH_EAST),
  EAST_SOUTH_EAST(EAST, SOUTH_EAST),
  SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),
  SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),
  WEST_SOUTH_WEST(WEST, SOUTH_WEST),
  SELF(0, 0, 0);

  private final int deltaX;
  private final int deltaY;
  private final int deltaZ;

  public static KelpBlockFace from(BlockFace blockFace) {
    return KelpBlockFace.valueOf(blockFace.name());
  }

  KelpBlockFace(int deltaX, int deltaY, int deltaZ) {
    this.deltaX = deltaX;
    this.deltaY = deltaY;
    this.deltaZ = deltaZ;
  }

  KelpBlockFace(KelpBlockFace face1, KelpBlockFace face2) {
    this.deltaX = face1.getDeltaX() + face2.getDeltaX();
    this.deltaY = face1.getDeltaY() + face2.getDeltaY();
    this.deltaZ = face1.getDeltaZ() + face2.getDeltaZ();
  }

  public int getDeltaX() {
    return this.deltaX;
  }

  public int getDeltaY() {
    return this.deltaY;
  }

  public int getDeltaZ() {
    return this.deltaZ;
  }

  public Vector getDirection() {
    Vector direction = new Vector(this.deltaX, this.deltaY, this.deltaZ);
    if (this.deltaX != 0 || this.deltaY != 0 || this.deltaZ != 0) {
      direction.normalize();
    }

    return direction;
  }

  public KelpBlockFace getOppositeFace() {
    switch(this) {
      case NORTH:
        return SOUTH;
      case EAST:
        return WEST;
      case SOUTH:
        return NORTH;
      case WEST:
        return EAST;
      case UP:
        return DOWN;
      case DOWN:
        return UP;
      case NORTH_EAST:
        return SOUTH_WEST;
      case NORTH_WEST:
        return SOUTH_EAST;
      case SOUTH_EAST:
        return NORTH_WEST;
      case SOUTH_WEST:
        return NORTH_EAST;
      case WEST_NORTH_WEST:
        return EAST_SOUTH_EAST;
      case NORTH_NORTH_WEST:
        return SOUTH_SOUTH_EAST;
      case NORTH_NORTH_EAST:
        return SOUTH_SOUTH_WEST;
      case EAST_NORTH_EAST:
        return WEST_SOUTH_WEST;
      case EAST_SOUTH_EAST:
        return WEST_NORTH_WEST;
      case SOUTH_SOUTH_EAST:
        return NORTH_NORTH_WEST;
      case SOUTH_SOUTH_WEST:
        return NORTH_NORTH_EAST;
      case WEST_SOUTH_WEST:
        return EAST_NORTH_EAST;
      default:
        return SELF;
    }
  }

  public BlockFace getBukkitFace() {
    return BlockFace.valueOf(this.toString());
  }

}
