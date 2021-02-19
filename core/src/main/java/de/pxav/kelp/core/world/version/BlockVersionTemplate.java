package de.pxav.kelp.core.world.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import de.pxav.kelp.core.world.KelpLocation;

@KelpVersionTemplate
public abstract class BlockVersionTemplate {

  public abstract KelpChunk getChunk(KelpBlock block);

  public abstract KelpLocation getLocation(KelpBlock block);

  public abstract KelpMaterial getMaterial(KelpBlock block);

  public abstract void setMaterial(KelpBlock block, KelpMaterial material);

}
