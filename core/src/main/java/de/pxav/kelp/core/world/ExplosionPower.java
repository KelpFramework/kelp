package de.pxav.kelp.core.world;

public class ExplosionPower {

  public static final ExplosionPower CREEPER = new ExplosionPower(3F);
  public static final ExplosionPower CHARGED_CREEPER = new ExplosionPower(6F);
  public static final ExplosionPower TNT = new ExplosionPower(4F);
  public static final ExplosionPower WITHER_HALF_HEALTH = new ExplosionPower(8F);
  public static final ExplosionPower WITHER_SPAWN = new ExplosionPower(7F);
  public static final ExplosionPower WITHER_KILL = new ExplosionPower(7F);
  public static final ExplosionPower END_CRYSTAL = new ExplosionPower(6F);
  public static final ExplosionPower BED_NETHER = new ExplosionPower(5F);
  public static final ExplosionPower BED_END = new ExplosionPower(5F);
  public static final ExplosionPower RESPAWN_ANCHOR = new ExplosionPower(5F);
  public static final ExplosionPower UNDERWATER_TNT = new ExplosionPower(4F);
  public static final ExplosionPower GHAST_FIREBALL = new ExplosionPower(1F);
  public static final ExplosionPower BLACK_WITHER_SKULL = new ExplosionPower(1F);
  public static final ExplosionPower DANGEROUS_WITHER_SKULL = new ExplosionPower(1F);

  public static ExplosionPower custom(float power) {
    return new ExplosionPower(power);
  }

  private final float power;

  ExplosionPower(float power) {
    this.power = power;
  }

  public float getPower() {
    return power;
  }

}
