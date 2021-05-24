package de.pxav.kelp.implementation1_8.entity.potion;

import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.entity.util.potion.minecraft.LevitationEffect;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.Versioned;

@Versioned
public class VersionedLevitation extends LevitationEffect {

  @Override
  public void onConsume(LivingKelpEntity<?> entity, KelpPotionEffect effectInfo) {
    System.out.println("Levitation effect is not implemented yet.");
  }

  @Override
  public void onRemove(LivingKelpEntity<?> entity) {
    System.out.println("Removed levitation effect.");
  }

}
