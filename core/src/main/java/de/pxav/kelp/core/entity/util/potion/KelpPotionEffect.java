package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.scheduler.TimeConverter;
import org.bukkit.potion.PotionEffect;

import java.util.concurrent.TimeUnit;

/**
 * Represents a potion effect that is applied to a {@link de.pxav.kelp.core.entity.LivingKelpEntity living entity}.
 * When applying a potion effect to an entity, there is more data needed than
 * just the effect type such as the duration or level of the effect. This class
 * wraps all this information and can be seen as an equivalent of bukkit's {@link PotionEffect}.
 *
 * @author pxav
 */
public class KelpPotionEffect {

  // how many ticks the effect should last
  private int durationInTicks = 20 * 30;

  // the type of effect to apply (affects the displayed icon)
  private KelpPotionEffectType effectType = PotionEffects.ABSORPTION;

  // whether particles should be spawned around the entity while the effect is applied.
  private boolean showParticles = true;

  // which level to apply the effect with. This has no influence on some
  // effects, while others become 'stronger'
  private int level = 1;

  /**
   * Converts the given bukkit effect to its equivalent kelp potion
   * effect.
   *
   * @param bukkitEffect The effect to convert.
   * @return A kelp instance of the potion effect.
   */
  public static KelpPotionEffect from(PotionEffect bukkitEffect) {
    return KelpPlugin.getInjector().getInstance(PotionVersionTemplate.class).fetchEffect(bukkitEffect);
  }

  /**
   * Creates a new potion effect instance, which can then be
   * used for further modification, until it is finally applied
   * to an entity using {@link de.pxav.kelp.core.entity.LivingKelpEntity#addPotionEffect(KelpPotionEffect)}.
   *
   * @return A new, empty potion effect instance.
   */
  public static KelpPotionEffect create() {
    return new KelpPotionEffect();
  }

  /**
   * Sets the duration of the effect based on the given time unit.
   *
   * Durations of more than 30 minutes will be displayed as infinite
   * in the client ({@code **:**}), but as soon as the timer goes
   * below that threshold again, the time is displayed normally again.
   *
   * @param value       The time value of the given unit / The amount
   *                    of time the effect should last.
   * @param timeUnit    The unit of the {@code value} attribute.
   * @return An instance of the current effect for fluent builder design.
   */
  public KelpPotionEffect duration(int value, TimeUnit timeUnit) {
    this.durationInTicks = TimeConverter.getTicks(value, timeUnit);
    return this;
  }

  /**
   * Sets the duration of the effect in ticks, where one tick is
   * roughly equal to 50ms. If you don't have to use ticks, it is
   * recommended to prefer {@link #duration(int, TimeUnit)} for
   * increased readability.
   *
   * Durations of more than 30 minutes will be displayed as infinite
   * in the client ({@code **:**}), but as soon as the timer goes
   * below that threshold again, the time is displayed normally again.
   *
   * @param ticks The amount of ticks the effect should last.
   * @return An instance of the current effect for fluent builder design.
   */
  public KelpPotionEffect durationTicks(int ticks) {
    this.durationInTicks = ticks;
    return this;
  }

  /**
   * Sets the effect type, which determines the icon that is displayed
   * next to a player's inventory as well as what happens with the
   * entity to whom the effect is applied.
   *
   * Note that not every effect type has the same impact on all entities;
   * look at the documentations of the corresponding effects for more
   * information.
   *
   * You can use the {@link PotionEffects} class to get the instances
   * of different potion effect types, for example {@link PotionEffects#HUNGER}.
   *
   * @param effectType The type of the effect you want to apply.
   * @return An instance of the current effect for fluent builder design.
   */
  public KelpPotionEffect effectType(KelpPotionEffectType effectType) {
    this.effectType = effectType;
    return this;
  }

  /**
   * Sets whether you want to hide or show effect particles to the affected
   * entity. Default value for this is {@code true}.
   *
   * @param show {@code true} to show particles, {@code false} to hide them.
   * @return An instance of the current effect for fluent builder design.
   */
  public KelpPotionEffect showParticles(boolean show) {
    this.showParticles = show;
    return this;
  }

  /**
   * Makes the effect hide all particles around the affected
   * entity.
   *
   * @return An instance of the current effect for fluent builder design.
   */
  public KelpPotionEffect hideParticles() {
    this.showParticles = false;
    return this;
  }

  /**
   * Makes the effect show particles around the affected entity.
   * The color of those particles is determined by the selected
   * effect type.
   *
   * @return An instance of the current effect for fluent builder design.
   */
  public KelpPotionEffect showParticles() {
    this.showParticles = true;
    return this;
  }

  /**
   * Sets the level of the applied effect type.
   *
   * The level defines how 'potent' the effect is - with a
   * {@link PotionEffects#REGENERATION regeneration} effect for example,
   * a higher level would mean faster regeneration (more regenerated hearts
   * per second). Other effect types (such as {@link PotionEffects#INVISIBILITY
   * invisibility}) are the same for every level. The minimum and default
   * value for a level is {@code 1}.
   *
   * Unlike the {@code amplifier} value from bukkit effects, this gives the
   * <strong>absolute</strong> potency of the effect, while amplifiers start
   * at {@code 0} and give you the relative level increase from level 1.
   * So amplifier 0 is equal to level 1; 1 would be equal to level 2, etc.
   *
   * @param level The level the effect type should be applied with.
   *              Minimum value {@code 1}.
   * @return An instance of the current effect for fluent builder design.
   */
  public KelpPotionEffect level(int level) {
    this.level = level;
    return this;
  }

  /**
   * Gets the amount of ticks this effect should last
   * once applied to an entity.
   *
   * @return The duration of the effect in ticks.
   */
  public int getDurationInTicks() {
    return durationInTicks;
  }

  /**
   * Gets the type of this effect.
   *
   * @return The type of this effect.
   */
  public KelpPotionEffectType getEffectType() {
    return effectType;
  }

  /**
   * Gets the level of the applied effect type.
   *
   * The level defines how 'potent' the effect is - with a
   * {@link PotionEffects#REGENERATION regeneration} effect for example,
   * a higher level would mean faster regeneration (more regenerated hearts
   * per second). Other effect types (such as {@link PotionEffects#INVISIBILITY
   * invisibility}) are the same for every level. The minimum and default
   * value for a level is {@code 1}.
   *
   * Unlike the {@code amplifier} value from bukkit effects, this gives the
   * <strong>absolute</strong> potency of the effect, while amplifiers start
   * at {@code 0} and give you the relative level increase from level 1.
   * So amplifier 0 is equal to level 1; 1 would be equal to level 2, etc.
   *
   * @return The level of the applied effect type.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Checks if particles should be spawned around the entity to whom
   * the effect is applied. The particle color depends on the effect
   * type you selected.
   *
   * The default value for this is {@code true}.
   *
   * @return {@code true} if particles should be shown.
   */
  public boolean particlesShown() {
    return showParticles;
  }

}
