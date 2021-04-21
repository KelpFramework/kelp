package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import org.bukkit.entity.ShulkerBullet;

public interface ShulkerBulletEntity extends KelpProjectile<ShulkerBulletEntity> {

  KelpEntity<?> getTarget();

  void setTarget(KelpEntity<?> target);

}
