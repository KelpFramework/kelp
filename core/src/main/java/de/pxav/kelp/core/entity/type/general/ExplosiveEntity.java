package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.world.util.ExplosionPower;

public interface ExplosiveEntity<T extends ExplosiveEntity<?>> extends KelpEntity<T> {

  ExplosionPower getExplosionPower();

  T setExplosionPower(ExplosionPower power);

  boolean createsFire();

  T canCreateFire(boolean create);

  default T enableFire() {
    canCreateFire(true);
    return (T) this;
  }

  default T disableFire() {
    canCreateFire(false);
    return (T) this;
  }

  default T disableExplosion() {
    setExplosionPower(ExplosionPower.NO_EXPLOSION);
    return (T) this;
  }

}
