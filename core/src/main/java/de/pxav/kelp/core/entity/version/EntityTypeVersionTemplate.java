package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class EntityTypeVersionTemplate {

  public abstract KelpEntity<?> newKelpEntity(KelpEntityType entityType, Location location);

  public abstract KelpEntity<?> getKelpEntity(Entity bukkitEntity);

}
