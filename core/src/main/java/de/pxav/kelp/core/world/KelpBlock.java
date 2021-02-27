package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.world.util.CardinalDirection;
import de.pxav.kelp.core.world.version.BlockVersionTemplate;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * This class represents a block of a world. A block is the smallest unit
 * a world consists of. Blocks are grouped together in 16x16 areas of the
 * world (-> {@link KelpChunk}), which can be loaded and unloaded for
 * optimal performance usage. A block can have a {@link KelpMaterial}
 * which defines its hardness, collision and other properties.
 *
 * @author pxav
 */
public class KelpBlock {

  private Block bukkitBlock;
  private BlockVersionTemplate versionTemplate;

  KelpBlock(Block bukkitBlock, BlockVersionTemplate versionTemplate) {
    this.bukkitBlock = bukkitBlock;
    this.versionTemplate = versionTemplate;
  }

  public static KelpBlock from(Block bukkitBlock) {
    return new KelpBlock(
      bukkitBlock,
      KelpPlugin.getInjector().getInstance(BlockVersionTemplate.class)
    );
  }

  /**
   * Gets the {@link KelpWorld} this block is located in.
   *
   * @return The world this block is located in.
   */
  public KelpWorld getWorld() {
    return KelpWorld.from(getBukkitBlock().getWorld());
  }

  /**
   * Gets the name of the world this block is located in.
   *
   * @return The name of the world this block is located in.
   */
  public String getWorldName() {
    return getBukkitBlock().getWorld().getName();
  }

  /**
   * Gets the {@link KelpChunk} the given block is located in.
   *
   * @return The {@link KelpChunk} the given block is located in.
   */
  public KelpChunk getChunk() {
    return versionTemplate.getChunk(this);
  }

  /**
   * Gets the {@link KelpLocation} of the given block in its {@link KelpWorld}.
   *
   * @return The {@link KelpLocation} of this world.
   */
  public KelpLocation getLocation() {
    return versionTemplate.getLocation(this);
  }

  /**
   * Gets the block which is blow this block. This means it
   * gets the block which has the same x and z coordinates, but a
   * lower y coordinate.
   *
   * @return The block below this block.
   */
  public KelpBlock getBlockBelow() {
    return getLocation().subtractY(1).getBlock();
  }

  /**
   * Gets the block which is above this block. This means it
   * gets the block which has the same x and z coordinates, but a
   * higher y coordinate.
   *
   * @return The block above this block.
   */
  public KelpBlock getBlockAbove() {
    return getLocation().addY(1).getBlock();
  }

  /**
   * Gets the block which is south of this block in the same y-axis.
   *
   * @return The southern block of this block.
   */
  public KelpBlock getSouthernBlock() {
    return getLocation().addZ(1).getBlock();
  }

  /**
   * Gets the block which is north of this block in the same y-axis.
   *
   * @return The northern block of this block.
   */
  public KelpBlock getNorthernBlock() {
    return getLocation().subtractZ(1).getBlock();
  }

  /**
   * Gets the block which is west of this block in the same y-axis.
   *
   * @return The western block of this block.
   */
  public KelpBlock getWesternBlock() {
    return getLocation().subtractX(1).getBlock();
  }

  /**
   * Gets the block which is east of this block in the same y-axis.
   *
   * @return The eastern block of this block.
   */
  public KelpBlock getEasternBlock() {
    return getLocation().addX(1).getBlock();
  }

  /**
   * Gets the block which is north east of this block in the same y-axis.
   *
   * @return The north eastern block of this block.
   */
  public KelpBlock getNorthEasternBlock() {
    return getLocation().add(1, 0, -1).getBlock();
  }

  /**
   * Gets the block which is north west of this block in the same y-axis.
   *
   * @return The north western block of this block.
   */
  public KelpBlock getNorthWesternBlock() {
    return getLocation().add(-1, 0, -1).getBlock();
  }

  /**
   * Gets the block which is south west of this block in the same y-axis.
   *
   * @return The south western block of this block.
   */
  public KelpBlock getSouthWesternBlock() {
    return getLocation().add(-1, 0, 1).getBlock();
  }

  /**
   * Gets the block which is south east of this block in the same y-axis.
   *
   * @return The south eastern block of this block.
   */
  public KelpBlock getSouthEasternBlock() {
    return getLocation().add(1, 0, 1).getBlock();
  }

  /**
   * Gets the block relative to this block in a specific
   * {@link CardinalDirection}.
   *
   * @param direction The direction of the block you want to get.
   * @return The block relative to this block in the given direction.
   */
  public KelpBlock getRelative(CardinalDirection direction) {
    switch (direction) {
      case NORTH:
        return getNorthernBlock();
      case NORTH_EAST:
        return getNorthEasternBlock();
      case EAST:
        return getEasternBlock();
      case SOUTH_EAST:
        return getSouthEasternBlock();
      case SOUTH:
        return getSouthernBlock();
      case SOUTH_WEST:
        return getSouthWesternBlock();
      case WEST:
        return getWesternBlock();
      case NORTH_WEST:
        return getNorthWesternBlock();
    }
    return null;
  }

  /**
   * Gets the block in front of this block relative to the given direction.
   *
   * @param direction A {@link Vector} indicating the direction
   *                 to determine which block is actually in front.
   * @return The block in front of this block.
   */
  public KelpBlock getFrontBlock(Vector direction) {
    KelpLocation location = getLocation();
    location.setDirection(direction);

    return getRelative(location.getCardinalDirection());
  }

  /**
   * Gets the block behind this block relative to the given direction.
   *
   * @param direction A {@link Vector} indicating the direction
   *                 to determine which block is actually behind.
   * @return The block behind this block.
   */
  public KelpBlock getBackBlock(Vector direction) {
    KelpLocation location = getLocation();
    location.setDirection(direction.multiply(-1));

    return getRelative(location.getCardinalDirection());
  }

  /**
   * Gets the x-coordinate of this block in the world grid.
   *
   * @return The block's x-coordinate.
   */
  public int getX() {
    return bukkitBlock.getX();
  }

  /**
   * Gets the y-coordinate of this block in the world grid.
   *
   * @return The block's y-coordinate.
   */
  public int getY() {
    return bukkitBlock.getY();
  }

  /**
   * Gets the z-coordinate of this block in the world grid.
   *
   * @return The block's z-coordinate.
   */
  public int getZ() {
    return bukkitBlock.getZ();
  }

  /**
   * Gets the material of the current block. The material defines
   * the block's general behavior including its hardness or
   * collision.
   *
   * @return The {@link KelpMaterial} the given block is made of.
   */
  public KelpMaterial getMaterial() {
    return versionTemplate.getMaterial(this);
  }

  /**
   * Sets the material of the given block to the given {@link KelpMaterial}.
   * So if you had an {@link KelpMaterial#AIR} block for example,
   *
   * @param material  The new material you want the block to have.
   */
  public void setMaterial(KelpMaterial material) {
    versionTemplate.setMaterial(this, material);
  }

  /**
   * Converts this block to a bukkit {@link Block}.
   *
   * @return The bukkit block instance of this block.
   */
  public Block getBukkitBlock() {
    return bukkitBlock;
  }

}
