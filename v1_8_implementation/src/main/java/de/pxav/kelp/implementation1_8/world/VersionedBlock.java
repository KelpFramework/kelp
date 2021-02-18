package de.pxav.kelp.implementation1_8.world;

import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialContainer;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.version.BlockVersionTemplate;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;

@Versioned
public class VersionedBlock extends BlockVersionTemplate {

  public void f() {

  }

  @Override
  public KelpChunk getChunk(KelpBlock block) {
    return KelpChunk.from(block.getBukkitBlock().getChunk());
  }

  @Override
  public KelpMaterial getMaterial(KelpBlock block) {
    // if block has a sub id
    if (block.getBukkitBlock().getData() != 0) {
      return KelpMaterial.from(block.getBukkitBlock().getType(), block.getBukkitBlock().getData());
    }

    // if the block has no special data to obey
    return KelpMaterial.from(block.getBukkitBlock().getType());
  }

  @Override
  public void setMaterial(KelpBlock block, KelpMaterial material) {
    MaterialContainer newMaterial = KelpMaterial.convert(material);
    block.getBukkitBlock().setType(newMaterial.getBukkitMaterial());
    if (newMaterial.getSubId() != 0) {
      block.getBukkitBlock().setData((byte) newMaterial.getSubId());
    }
  }

  private CraftBlock craftBlock(KelpBlock block) {
    return (CraftBlock) block.getBukkitBlock();
  }

}
