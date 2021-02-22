package de.pxav.kelp.core.world;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.world.version.BlockVersionTemplate;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

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

  public KelpBlock getBlockBelow() {
    return getLocation().subtractY(1).getBlock();
  }

  public KelpBlock getBlockAbove() {
    return getLocation().addY(1).getBlock();
  }

  public KelpBlock getSouthernBlock() {
    return getLocation().addZ(1).getBlock();
  }

  public KelpBlock getNorthernBlock() {
    return getLocation().subtractZ(1).getBlock();
  }

  public KelpBlock getWesternBlock() {
    return getLocation().subtractX(1).getBlock();
  }

  public KelpBlock getEasternBlock() {
    return getLocation().addX(1).getBlock();
  }

  public KelpBlock getFrontBlock(Vector direction) {
    return getLocation().add(direction).getBlock();
  }

  public KelpBlock getBackBlock(Vector direction) {
    return getLocation().subtract(direction).getBlock();
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
