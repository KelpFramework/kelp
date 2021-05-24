package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.EndermanEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedMonster;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderman;
import org.bukkit.material.MaterialData;

public class VersionedEnderMan extends VersionedMonster<EndermanEntity> implements EndermanEntity {

  CraftEnderman craftEnderman;

  public VersionedEnderMan(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  @Override
  public KelpMaterial getCarriedBlock() {
    return KelpMaterial.from(craftEnderman.getCarriedMaterial().getItemType());
  }

  @Override
  public EndermanEntity setCarriedBlock(KelpMaterial carriedBlock) {
    MaterialData materialData = new MaterialData(KelpMaterial.convertUnsafe(carriedBlock));
    craftEnderman.setCarriedMaterial(materialData);
    return this;
  }
}
