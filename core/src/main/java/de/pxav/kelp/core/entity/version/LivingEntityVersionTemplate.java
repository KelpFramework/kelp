package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class LivingEntityVersionTemplate {

  public abstract Location getEyeLocation(LivingEntity livingEntity);

}
