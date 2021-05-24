package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.inventory.material.KelpMaterial;
import org.bukkit.util.Vector;

public interface MinecartEntity<T extends MinecartEntity<?>> extends VehicleEntity<T> {

  T setDamage(double damage);

  double getDamage();

  double getMaxSpeed();

  T setMaxSpeed(double maxSpeed);

  boolean isSlowWhenEmpty();

  T setSlowWhenEmpty(boolean slowWhenEmpty);

  Vector getFlyingVelocityModifier();

  T setFlyingVelocityModifier(Vector flyingVelocityModifier);

  Vector getDerailedVelocityModifier();

  T setDerailedVelocityModifier(Vector derailedVelocityModifier);

  T setDisplayBlock(KelpMaterial material);

  KelpMaterial getDisplayBlock();

  T setDisplayBlockOffset(int displayBlockOffset);

  int getDisplayBlockOffset();

}
