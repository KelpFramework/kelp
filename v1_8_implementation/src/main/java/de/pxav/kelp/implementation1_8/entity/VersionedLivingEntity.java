package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedLivingEntity extends LivingEntityVersionTemplate {

  @Override
  public KelpLocation getEyeLocation(LivingEntity livingEntity) {
    return KelpLocation.from(livingEntity.getEyeLocation());
  }

}
