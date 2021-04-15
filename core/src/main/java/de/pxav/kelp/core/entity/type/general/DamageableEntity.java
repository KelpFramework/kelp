package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;

public interface DamageableEntity<T extends DamageableEntity<?>> extends KelpEntity<T> {

  T damage(double damage);

  T damage(double damage, KelpEntity<?> source);

  double getAbsorptionAmount();

  T setAbsorptionAmount(int absorptionAmount);

  int getHealth();

  int getMaxHealth();

  T setHealth(int health);

}
