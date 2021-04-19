package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractArrowEntity;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.inventory.metadata.Color;

import java.util.List;

public interface ArrowEntity extends AbstractArrowEntity<ArrowEntity> {

  ArrowEntity addCustomEffect(Class<? extends KelpPotionEffect> effect, int tier);

  boolean hasCustomEffect(Class<? extends KelpPotionEffect> effect);

  ArrowEntity removeCustomEffect(Class<? extends KelpPotionEffect> effect);

  ArrowEntity setColor(Color color);

  Color getColor();

  List<Class<? extends KelpPotionEffect>> getCustomEffects();

  boolean hasCustomEffects();

  int getCustomEffectTier(Class<? extends KelpPotionEffect> effect);

}
