package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.LivingKelpEntity;

public interface MobileEntity<T extends MobileEntity<?>> extends LivingKelpEntity<T> {

  T setTarget(LivingKelpEntity<?> target);

  LivingKelpEntity<?> getTarget();

  boolean isAware();

  T setAware(boolean aware);

}
