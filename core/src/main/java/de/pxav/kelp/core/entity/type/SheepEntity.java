package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

public interface SheepEntity extends AnimalEntity<SheepEntity>, BreedableAnimalEntity<SheepEntity> {

  static SheepEntity create(KelpLocation location) {
    return (SheepEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .newKelpEntity(KelpEntityType.SHEEP, location.getBukkitLocation());
  }

  static SheepEntity from(Entity sheep) {
    return (SheepEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .getKelpEntity(sheep);
  }

  boolean isSheared();

  SheepEntity setSheared(boolean sheared);

}
