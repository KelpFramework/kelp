package de.pxav.kelp.core.entity;

import de.pxav.kelp.core.entity.type.general.DamageableEntity;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import de.pxav.kelp.core.world.KelpLocation;

public interface LivingKelpEntity<T extends LivingKelpEntity<?>> extends KelpEntity<T>, DamageableEntity<T> {

  /**
   * Gets the location of the entity's eyes. When you get
   * the normal location of a player for example, the feet location
   * will be returned. This method gets the eye location depending
   * on the current entity type.
   *
   * @return The location of the entity's eyes.
   */
  KelpLocation getEyeLocation();

  SimpleEntityEquipment getEquipment();

}
