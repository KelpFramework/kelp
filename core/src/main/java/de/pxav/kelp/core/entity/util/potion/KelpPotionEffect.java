package de.pxav.kelp.core.entity.util.potion;

public class KelpPotionEffect {

  private int durationInTicks;
  private KelpPotionEffectType effectType;
  private boolean showParticles;
  private int level;

  public int getDurationInTicks() {
    return durationInTicks;
  }

  public KelpPotionEffectType getEffectType() {
    return effectType;
  }

  public int getLevel() {
    return level;
  }

  public boolean particlesShown() {
    return showParticles;
  }

}
