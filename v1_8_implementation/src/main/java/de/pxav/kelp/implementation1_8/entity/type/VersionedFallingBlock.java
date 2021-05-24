package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.FallingBlockEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFallingSand;

public class VersionedFallingBlock extends VersionedEntity<FallingBlockEntity> implements FallingBlockEntity {

  private EntityFallingBlock fallingHandle;
  private CraftFallingSand craftFallingSand;

  public VersionedFallingBlock(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.fallingHandle = (EntityFallingBlock) entityHandle;
    this.craftFallingSand = (CraftFallingSand) entityHandle.getBukkitEntity();
  }

  @Override
  public boolean canHurtEntities() {
    return fallingHandle.hurtEntities;
  }

  @Override
  public boolean willDropItem() {
    return fallingHandle.dropItem;
  }

  @Override
  public FallingBlockEntity dropsItem(boolean dropsItem) {
    fallingHandle.dropItem = dropsItem;
    return this;
  }

  @Override
  public FallingBlockEntity hurtEntities(boolean hurtEntities) {
    fallingHandle.hurtEntities = hurtEntities;
    return this;
  }

  @Override
  public KelpMaterial getMaterial() {
    return KelpMaterial.from(craftFallingSand.getMaterial());
  }

}
