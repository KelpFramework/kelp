package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedDamageable;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class VersionedLivingEntity<T extends LivingKelpEntity<T>>
  extends VersionedDamageable<T>
  implements LivingKelpEntity<T> {

  private EntityLiving entityHandle;
  private CraftLivingEntity craftLivingEntity;

  public VersionedLivingEntity(Entity entityHandle,
                               KelpEntityType entityType,
                               Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.entityHandle = (EntityLiving) entityHandle;
    this.craftLivingEntity = (CraftLivingEntity) entityHandle.getBukkitEntity();
  }

  @Override
  public KelpLocation getEyeLocation() {
    return KelpLocation.from(craftLivingEntity.getEyeLocation());
  }

  @Override
  public SimpleEntityEquipment getEquipment() {
    return new VersionedSimpleEntityEquipment(getBukkitEntity());
  }

  @Override
  public T damage(double damage) {
    craftLivingEntity.damage(damage);
    return (T) this;
  }

  @Override
  public T damage(double damage, KelpEntity<?> source) {
    craftLivingEntity.damage(damage, source.getBukkitEntity());
    return (T) this;
  }

  @Override
  public double getAbsorptionAmount() {
    return entityHandle.getAbsorptionHearts();
  }

  @Override
  public T setAbsorptionAmount(double absorptionAmount) {
    entityHandle.setAbsorptionHearts((float) absorptionAmount);
    return (T) this;
  }

  @Override
  public double getHealth() {
    return entityHandle.getHealth();
  }

  @Override
  public double getMaxHealth() {
    return entityHandle.getMaxHealth();
  }

  @Override
  public T setHealth(double health) {
    entityHandle.setHealth((float) health);
    return (T) this;
  }

  @Override
  public org.bukkit.entity.LivingEntity getBukkitEntity() {
    return (LivingEntity) super.getBukkitEntity();
  }

}
