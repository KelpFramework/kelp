package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.util.ArrowPickupStatus;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpBlock;
import org.bukkit.GameMode;

public interface AbstractArrowEntity<T extends AbstractArrowEntity<?>> extends KelpProjectile<T> {

  KelpBlock getAttachedBlock();

  double getBaseDamage();

  // equivalent to knockback enchantment level
  int getKnockbackLevel();

  ArrowPickupStatus getPickupStatus();

  default boolean canPickup(KelpEntity<?> entity) {
    if (!(entity instanceof KelpPlayer)) {
      return false;
    }

    if (getPickupStatus() == ArrowPickupStatus.ALWAYS_FORBIDDEN) {
      return false;
    }

    if (getPickupStatus() == ArrowPickupStatus.CREATIVE_ONLY) {
      KelpPlayer player = KelpPlayer.from(entity.getUUID());
      return player.getGameMode() == GameMode.CREATIVE;
    }

    return true;
  }

  int getPierceLevel();

  boolean isCritical();

  boolean isInBlock();

  boolean isShotFromCrossbow();

  default boolean isShotFromNormalBow() {
    return !isShotFromCrossbow();
  }

  T setPickupStatus(ArrowPickupStatus pickupStatus);

  T setCritical(boolean critical);

  T setDamage(double damage);

  T setKnockbackLevel(int knockbackLevel);

  T setPierceLevel(int pierceLevel);

  T setShotFromCrossbow(boolean fromCrossbow);

  default T setShowFromNormalBow(boolean fromNormalBow) {
    setShotFromCrossbow(!fromNormalBow);
    return (T) this;
  }

}
