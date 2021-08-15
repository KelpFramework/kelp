package de.pxav.kelp.core.world.util;

import de.pxav.kelp.core.world.KelpBlock;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

/**
 * Represents the face of a given block. This face can point
 * in a cardinal direction such as {@code NORTH} or {@code SOUTH}
 * or around the y-axis with {@link KelpBlockFace#UP} and {@link KelpBlockFace#DOWN}.
 *
 * This can be used to get neighbouring blocks of another block for example
 * with {@link KelpBlock#getBlockBelow()}.
 *
 * @author pxav
 */
public enum KelpBlockFace {

  NORTH(0, 0, -1) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return SOUTH;
    }
  },

  EAST(1, 0, 0) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return WEST;
    }
  },

  SOUTH(0, 0, 1) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return NORTH;
    }
  },

  WEST(-1, 0, 0) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return EAST;
    }
  },

  UP(0, 1, 0) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return DOWN;
    }
  },

  DOWN(0, -1, 0) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return UP;
    }
  },

  NORTH_EAST(NORTH, EAST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return NORTH_WEST;
    }
  },

  NORTH_WEST(NORTH, WEST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return SOUTH_EAST;
    }
  },

  SOUTH_EAST(SOUTH, EAST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return NORTH_WEST;
    }
  },

  SOUTH_WEST(SOUTH, WEST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return NORTH_EAST;
    }
  },

  WEST_NORTH_WEST(WEST, NORTH_WEST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return EAST_SOUTH_EAST;
    }
  },

  NORTH_NORTH_WEST(NORTH, NORTH_WEST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return SOUTH_SOUTH_EAST;
    }
  },

  NORTH_NORTH_EAST(NORTH, NORTH_EAST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return SOUTH_SOUTH_WEST;
    }
  },

  EAST_NORTH_EAST(EAST, NORTH_EAST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return WEST_SOUTH_WEST;
    }
  },

  EAST_SOUTH_EAST(EAST, SOUTH_EAST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return WEST_NORTH_WEST;
    }
  },

  SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return NORTH_NORTH_WEST;
    }
  },

  SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST) {
    @Override
    public KelpBlockFace getOppositeFace() {
      return NORTH_NORTH_EAST;
    }
  },

  WEST_SOUTH_WEST(WEST, SOUTH_WEST) {
    public KelpBlockFace getOppositeFace() {
      return EAST_NORTH_EAST;
    }
  },

  /**
   * Represents the current block and no specific face of it.
   */
  SELF(0, 0, 0) {
    public KelpBlockFace getOppositeFace() {
      return SELF;
    }
  };

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

  /**
   * Gets the difference from the original block to the
   * current block face in the x direction.
   *
   * @return The distance from the original block to the current face on the x-axis.
   */
  public int getDeltaX() {
    return this.deltaX;
  }

  /**
   * Gets the difference from the original block to the
   * current block face in the y direction.
   *
   * @return The distance from the original block to the current face on the y-axis.
   */
  public int getDeltaY() {
    return this.deltaY;
  }

  /**
   * Gets the difference from the original block to the
   * current block face in the z direction.
   *
   * @return The distance from the original block to the current face on the z-axis.
   */
  public int getDeltaZ() {
    return this.deltaZ;
  }

  /**
   * Converts the current block face into a {@link Vector}.
   * This vector will point in the cardinal direction of the face.
   *
   * @return A vector pointing in the direction of the current block face.
   */
  public Vector3 getDirection() {
    Vector3 direction = Vector3.create(this.deltaX, this.deltaY, this.deltaZ);
    if (this.deltaX != 0 || this.deltaY != 0 || this.deltaZ != 0) {
      direction.normalize();
    }

    return direction;
  }

  /**
   * Gets the block face that is opposite of the current block face.
   * If the current block face is {@code NORTH} for example, this will return
   * {@code SOUTH}. If it is {@code NORTH_EAST}, it will return {@code SOUTH_WEST} and so on.
   *
   * @return The block face opposite to the current block face.
   */
  public KelpBlockFace getOppositeFace() {
    throw new AbstractMethodError("Cannot get opposite face of block face with type 'null'");
  }

  /**
   * Converts the current block face into a block face of
   * the bukkit library.
   *
   * @return The bukkit block face equivalent to the current block face.
   */
  public BlockFace getBukkitFace() {
    return BlockFace.valueOf(this.toString());
  }

}
