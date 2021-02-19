package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.world.version.BlockVersionTemplate;
import org.bukkit.block.Block;

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

  public KelpChunk getChunk() {
    return versionTemplate.getChunk(this);
  }

  public KelpLocation getLocation() {
    return versionTemplate.getLocation(this);
  }


  public int getX() {
    return bukkitBlock.getX();
  }

  public int getY() {
    return bukkitBlock.getY();
  }

  public int getZ() {
    return bukkitBlock.getZ();
  }

  public KelpMaterial getMaterial() {
    return versionTemplate.getMaterial(this);
  }

  public void setMaterial(KelpMaterial material) {
    versionTemplate.setMaterial(this, material);
  }

  public Block getBukkitBlock() {
    return bukkitBlock;
  }

}
