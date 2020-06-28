package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import org.bukkit.Location;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class GuardianEntity extends KelpEntity {

  public GuardianEntity() {}

  public GuardianEntity(EntityVersionTemplate entityVersionTemplate, Object entity, int entityId, Location location) {
    super(entity, KelpEntityType.GUARDIAN, location, entityId, entityVersionTemplate);
  }

}
