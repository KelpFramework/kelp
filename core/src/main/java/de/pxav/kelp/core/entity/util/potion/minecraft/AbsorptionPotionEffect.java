package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.inventory.metadata.Color;

public class AbsorptionPotionEffect extends KelpPotionEffect {

  @Override
  public String getName() {
    return null;
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return null;
  }

  @Override
  public int getDurationForLevel(int level) {
    return 0;
  }

}
