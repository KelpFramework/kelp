package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntityFactory;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import de.pxav.kelp.core.world.KelpLocation;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface ElderGuardianEntity extends MonsterEntity<ElderGuardianEntity> {

  static ElderGuardianEntity create(KelpLocation location) {
    return (ElderGuardianEntity) KelpPlugin.getInjector().getInstance(KelpEntityFactory.class)
      .newKelpEntity(KelpEntityType.ELDER_GUARDIAN, location.getBukkitLocation());
  }

}
