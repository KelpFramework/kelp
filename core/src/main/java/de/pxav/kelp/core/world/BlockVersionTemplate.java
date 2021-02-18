package de.pxav.kelp.core.world;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import org.bukkit.block.BlockFace;

@KelpVersionTemplate
public abstract class BlockVersionTemplate {

  public abstract KelpChunk getChunk(KelpBlock block);

  public abstract KelpMaterial getMaterial(KelpBlock block);

  public abstract void setMaterial(KelpBlock block, KelpMaterial material);

}
