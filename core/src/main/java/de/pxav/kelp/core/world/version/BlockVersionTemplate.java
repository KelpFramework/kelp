package de.pxav.kelp.core.world.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.block.BlockFace;

/**
 * This version template is used for version-dependent operations
 * related to bukkit's default {@link org.bukkit.block.Block}.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class BlockVersionTemplate {

  /**
   * Gets the {@link KelpChunk} the given block is located in.
   *
   * @param block The block you want to get the chunk of.
   * @return The {@link KelpChunk} the given block is located in.
   */
  public abstract KelpChunk getChunk(KelpBlock block);

  /**
   * Gets the {@link KelpLocation} of the given block. This also
   * contains information like the current world.
   *
   * @param block The block you want to get the location of.
   * @return The {@link KelpLocation} of this world.
   */
  public abstract KelpLocation getLocation(KelpBlock block);

  /**
   * Gets the material of the current block.
   *
   * @param block The block you want to get the material of.
   * @return The {@link KelpMaterial} the given block is made of.
   */
  public abstract KelpMaterial getMaterial(KelpBlock block);

  /**
   * Sets the material of the given block to the given {@link KelpMaterial}.
   * So if you had an {@link KelpMaterial#AIR} block for example,
   *
   * @param block     The block you want to change the material of.
   * @param material  The new material you want the block to have.
   */
  public abstract void setMaterial(KelpBlock block, KelpMaterial material);

  /**
   * Checks whether bone meal will have an effect on this block.
   * This means it will check whether the block is a sapling
   * or a plant that will grow if you right click it with bone
   * meal. More information about that can be found
   * <a href="https://minecraft.gamepedia.com/Bone_Meal">in this wiki
   * article</a>
   *
   * @param block The block you want to check the application state of.
   * @return {@code true} whether bone meal is applicable.
   */
  public abstract boolean canApplyBoneMeal(KelpBlock block);

  /**
   * Simulates the application of bone meal on a block.
   * This means if the block is a sapling for example, it
   * might grow to a tree or grass might spawn random flowers
   * and so on.
   *
   * @param block The block you want to simulate the application of.
   */
  public abstract void applyBoneMeal(KelpBlock block, BlockFace blockFace);

}
