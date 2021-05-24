package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.scheduler.TimeConverter;
import org.bukkit.potion.PotionEffect;

import java.util.concurrent.TimeUnit;

public class KelpPotionEffect {

  private int durationInTicks = 20 * 30;
  private KelpPotionEffectType effectType = PotionEffects.ABSORPTION;
  private boolean showParticles = true;
  private int level = 1;

  public static KelpPotionEffect from(PotionEffect bukkitEffect) {
    return KelpPlugin.getInjector().getInstance(PotionVersionTemplate.class).fetchEffect(bukkitEffect);
  }

  public static KelpPotionEffect create() {
    return new KelpPotionEffect();
  }

  public KelpPotionEffect duration(int value, TimeUnit timeUnit) {
    this.durationInTicks = TimeConverter.getTicks(value, timeUnit);
    return this;
  }

  public KelpPotionEffect durationTicks(int ticks) {
    this.durationInTicks = ticks;
    return this;
  }

  public KelpPotionEffect effectType(KelpPotionEffectType effectType) {
    this.effectType = effectType;
    return this;
  }

  public KelpPotionEffect showParticles(boolean show) {
    this.showParticles = show;
    return this;
  }

  public KelpPotionEffect hideParticles() {
    this.showParticles = false;
    return this;
  }

  public KelpPotionEffect showParticles() {
    this.showParticles = true;
    return this;
  }

  public KelpPotionEffect level(int level) {
    this.level = level;
    return this;
  }

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
