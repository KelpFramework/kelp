package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.inventory.item.KelpItem;

import java.util.Collection;

public interface ThrownPotionEntity extends KelpProjectile<ThrownPotionEntity> {

  Collection<Class<? extends KelpPotionEffect>> getPotionEffects();

  KelpItem getItem();

  ThrownPotionEntity setItem(KelpItem item);

}