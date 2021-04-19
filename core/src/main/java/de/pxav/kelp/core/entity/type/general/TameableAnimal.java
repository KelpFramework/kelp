package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.LivingKelpEntity;

public interface TameableAnimal<T extends TameableAnimal<?>> extends AnimalEntity<T> {

  T setTamed(boolean tamed);

  boolean isTamed();

  KelpEntity<?> getOwner();

  T setOwner(KelpEntity<?> owner);

}
