package de.pxav.kelp.core.world.util;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This class is used to determine the power of an explosion.
 * Depending on which type of explosion is performed, those types
 * have different powers. {@code TNT} for example has a power of {@code 4},
 * while a creeper has just {@code 3}.
 *
 * This class basically just holds the float value for the power of the
 * explosion, while providing a bunch of default values for some default
 * Minecraft explosion types. They can be accessed with:
 * {@code ExplosionPower.EXPLOSION_NAME}.
 *
 * To create a custom explosion power, you could use the static factory
 * {@code ExplosionPower.custom(float power)}.
 *
 * The power can be passed when creating an explosion from
 * {@link de.pxav.kelp.core.world.KelpWorld} for example.
 *
 * @author pxav
 */
public class ExplosionPower {

  public static final ExplosionPower CREEPER = new ExplosionPower(3F);
  public static final ExplosionPower CHARGED_CREEPER = new ExplosionPower(6F);

  public static final ExplosionPower TNT = new ExplosionPower(4F);
  public static final ExplosionPower UNDERWATER_TNT = new ExplosionPower(4F);

  public static final ExplosionPower WITHER_HALF_HEALTH = new ExplosionPower(8F);
  public static final ExplosionPower WITHER_SPAWN = new ExplosionPower(7F);
  public static final ExplosionPower WITHER_KILL = new ExplosionPower(7F);
  public static final ExplosionPower BLACK_WITHER_SKULL = new ExplosionPower(1F);
  public static final ExplosionPower DANGEROUS_WITHER_SKULL = new ExplosionPower(1F);

  public static final ExplosionPower BED_NETHER = new ExplosionPower(5F);
  public static final ExplosionPower BED_END = new ExplosionPower(5F);

  public static final ExplosionPower END_CRYSTAL = new ExplosionPower(6F);
  public static final ExplosionPower RESPAWN_ANCHOR = new ExplosionPower(5F);
  public static final ExplosionPower GHAST_FIREBALL = new ExplosionPower(1F);

  public static final ExplosionPower NO_EXPLOSION = new ExplosionPower(0F);

  /**
   * Allows you to create an explosion with a custom power, which is not
   * provided by the default list by minecraft.
   *
   * @param power The power of your custom explosion.
   * @return The {@link ExplosionPower} object holding the given {@code power}
   */
  public static ExplosionPower custom(float power) {
    return new ExplosionPower(power);
  }

  // the actual power of the explosion
  private final float power;

  ExplosionPower(float power) {
    this.power = power;
  }

  /**
   * Gets the explosion power of this explosion type.
   * @return The explosion power value held by this object.
   */
  public float getPower() {
    return power;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append("EXPLOSIONPOWER:")
      .append(this.power)
      .append(this.power * 4 - 2).toHashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ExplosionPower)) {
      return false;
    }

    ExplosionPower compare = (ExplosionPower) object;
    return compare.power == this.power;
  }

}
