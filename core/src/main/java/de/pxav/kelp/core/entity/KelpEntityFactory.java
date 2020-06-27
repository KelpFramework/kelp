package de.pxav.kelp.core.entity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import org.bukkit.Location;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpEntityFactory {

  private EntityTypeVersionTemplate typeVersionTemplate;

  @Inject
  public KelpEntityFactory(EntityTypeVersionTemplate typeVersionTemplate) {
    this.typeVersionTemplate = typeVersionTemplate;
  }

  public KelpEntity newKelpEntity(KelpEntityType entityType, Location location) {
    return typeVersionTemplate.newKelpEntity(entityType, location);
  }

}
