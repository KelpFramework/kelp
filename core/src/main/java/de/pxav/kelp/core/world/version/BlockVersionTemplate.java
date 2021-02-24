package de.pxav.kelp.core.world.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;

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

}
