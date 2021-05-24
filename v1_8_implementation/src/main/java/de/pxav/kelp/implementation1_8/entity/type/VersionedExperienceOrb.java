package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ExperienceOrbEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import org.bukkit.Location;

public class VersionedExperienceOrb extends VersionedEntity<ExperienceOrbEntity> implements ExperienceOrbEntity {

  private EntityExperienceOrb orbHandle;

  public VersionedExperienceOrb(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.orbHandle = (EntityExperienceOrb) entityHandle;
  }

  @Override
  public int getExperience() {
    return orbHandle.value;
  }

  @Override
  public ExperienceOrbEntity setExperience(int experience) {
    this.orbHandle.value = experience;
    return this;
  }

}
