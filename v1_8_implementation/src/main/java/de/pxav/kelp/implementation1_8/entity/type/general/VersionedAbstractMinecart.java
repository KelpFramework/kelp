package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.MinecartEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMinecart;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class VersionedAbstractMinecart<T extends MinecartEntity<T>> extends VersionedVehicle<T> implements MinecartEntity<T> {

  private CraftMinecart craftMinecart;

  public VersionedAbstractMinecart(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftMinecart = (CraftMinecart) entityHandle.getBukkitEntity();
  }

  @Override
  public T setDamage(double damage) {
    craftMinecart.setDamage(damage);
    return (T) this;
  }

  @Override
  public double getDamage() {
    return craftMinecart.getDamage();
  }

  @Override
  public double getMaxSpeed() {
    return craftMinecart.getMaxSpeed();
  }

  @Override
  public T setMaxSpeed(double maxSpeed) {
    craftMinecart.setMaxSpeed(maxSpeed);
    return (T) this;
  }

  @Override
  public boolean isSlowWhenEmpty() {
    return craftMinecart.isSlowWhenEmpty();
  }

  @Override
  public T setSlowWhenEmpty(boolean slowWhenEmpty) {
    craftMinecart.setSlowWhenEmpty(slowWhenEmpty);
    return (T) this;
  }

  @Override
  public Vector getFlyingVelocityModifier() {
    return craftMinecart.getFlyingVelocityMod();
  }

  @Override
  public T setFlyingVelocityModifier(Vector flyingVelocityModifier) {
    craftMinecart.setFlyingVelocityMod(flyingVelocityModifier);
    return (T) this;
  }

  @Override
  public Vector getDerailedVelocityModifier() {
    return craftMinecart.getDerailedVelocityMod();
  }

  @Override
  public T setDerailedVelocityModifier(Vector derailedVelocityModifier) {
    craftMinecart.setDerailedVelocityMod(derailedVelocityModifier);
    return (T) this;
  }

  @Override
  public T setDisplayBlock(KelpMaterial material) {
    Material bukkitMaterial = KelpMaterial.convertUnsafe(material);
    craftMinecart.setDisplayBlock(new MaterialData(bukkitMaterial));
    return (T) this;
  }

  @Override
  public KelpMaterial getDisplayBlock() {
    Material bukkitMaterial = craftMinecart.getDisplayBlock().getItemType();
    return KelpMaterial.from(bukkitMaterial);
  }

  @Override
  public T setDisplayBlockOffset(int displayBlockOffset) {
    craftMinecart.setDisplayBlockOffset(displayBlockOffset);
    return (T) this;
  }

  @Override
  public int getDisplayBlockOffset() {
    return craftMinecart.getDisplayBlockOffset();
  }
}
