package de.pxav.kelp.implementation1_12.entity;

import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
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
  public Location getEyeLocation(LivingEntity livingEntity) {
    return livingEntity.getEyeLocation();
  }
}
