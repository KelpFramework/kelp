package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractArrowEntity;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

import java.util.List;

public interface ArrowEntity extends AbstractArrowEntity<ArrowEntity> {

  ArrowEntity addCustomEffect(KelpPotionEffectType effect, int tier);

  boolean hasCustomEffect(KelpPotionEffectType effect);

  ArrowEntity removeCustomEffect(KelpPotionEffectType effect);

  ArrowEntity setColor(Color color);

  Color getColor();

  List<KelpPotionEffectType> getCustomEffects();

  boolean hasCustomEffects();

  int getCustomEffectTier(KelpPotionEffectType effect);

}
