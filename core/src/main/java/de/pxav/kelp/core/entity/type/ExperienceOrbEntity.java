package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;

public interface ExperienceOrbEntity extends KelpEntity<ExperienceOrbEntity> {

  static ExperienceOrbEntity from(ExperienceOrb experienceOrb) {
    return (ExperienceOrbEntity) KelpPlugin.getInjector()
      .getInstance(EntityTypeVersionTemplate.class).getKelpEntity(experienceOrb);
  }

  int getExperience();

  ExperienceOrbEntity setExperience(int experience);

}