package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.LivingKelpEntity;

import java.util.UUID;

public interface TameableAnimal<T extends TameableAnimal<?>> extends AnimalEntity<T> {

  T setTamed(boolean tamed);

  boolean isTamed();

  KelpEntity<?> getOwner();

  UUID getOwnerUUID();

  T setOwner(KelpEntity<?> owner);

}
