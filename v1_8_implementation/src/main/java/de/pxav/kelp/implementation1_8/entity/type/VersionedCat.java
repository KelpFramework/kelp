package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.CatEntity;
import de.pxav.kelp.core.entity.util.CatType;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAnimalEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftOcelot;
import org.bukkit.entity.Ocelot;

public class VersionedCat extends VersionedAnimalEntity<CatEntity> implements CatEntity {

  private CraftOcelot craftOcelot;
  private EntityConstantsVersionTemplate entityConstantsVersionTemplate;

  public VersionedCat(Entity entityHandle,
                      KelpEntityType entityType,
                      Location initialLocation,
                      EntityTypeVersionTemplate entityTypeVersionTemplate,
                      EntityConstantsVersionTemplate entityConstantsVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftOcelot = (CraftOcelot) entityHandle.getBukkitEntity();
    craftOcelot.setCatType(Ocelot.Type.valueOf(entityConstantsVersionTemplate.getCatType(CatType.randomCatType())));
    this.entityConstantsVersionTemplate = entityConstantsVersionTemplate;
  }

  @Override
  public Color getCollarDyeColor() {
    return Color.DYE_RED;
  }

  @Override
  public CatEntity setCollarDyeColor(Color color) {
    return this;
  }

  @Override
  public CatType getCatType() {
    return entityConstantsVersionTemplate.getCatType(craftOcelot.getCatType().toString());
  }

  @Override
  public CatEntity setCatType(CatType catType) {
    String bukkitCatType = entityConstantsVersionTemplate.getCatType(catType);
    craftOcelot.setCatType(Ocelot.Type.valueOf(bukkitCatType));
    return this;
  }

}
