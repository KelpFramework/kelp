package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.common.ConcurrentListMultimap;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.PotionVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedDamageable;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class VersionedLivingEntity<T extends LivingKelpEntity<T>>
  extends VersionedDamageable<T>
  implements LivingKelpEntity<T> {

  // potion effects that are emulated by kelp (only those with non-bukkit effect types)
  // so if you apply the levitation effect to an entity on a 1.8 server, it is saved here,
  // while a strength effect is not.
  private static ConcurrentListMultimap<LivingKelpEntity<?>, KelpPotionEffect> potionEffects = ConcurrentListMultimap.create();

  private EntityLiving entityHandle;
  private CraftLivingEntity craftLivingEntity;

  protected PotionVersionTemplate potionVersionTemplate;

  public VersionedLivingEntity(Entity entityHandle,
                               KelpEntityType entityType,
                               Location initialLocation,
                               EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.entityHandle = (EntityLiving) entityHandle;
    this.craftLivingEntity = (CraftLivingEntity) entityHandle.getBukkitEntity();
  }

  public void setPotionVersionTemplate(PotionVersionTemplate potionVersionTemplate) {
    this.potionVersionTemplate = potionVersionTemplate;
  }

  @Override
  public T addPotionEffect(KelpPotionEffect potionEffect) {
    potionVersionTemplate.applyTo(this, potionEffect);

    // if the effect contains a custom effect
    if (!potionEffect.getEffectType().isBukkitEffect()) {
      potionEffects.put(this, potionEffect);
    }

    return (T) this;
  }

  @Override
  public Collection<KelpPotionEffect> getActivePotionEffects() {
    Collection<KelpPotionEffect> output = potionEffects.getOrEmpty(this);
    for (PotionEffect potionEffect : craftLivingEntity.getActivePotionEffects()) {
      output.add(potionVersionTemplate.fetchEffect(potionEffect));
    }
    return output;
  }

  @Override
  public T removePotionEffect(KelpPotionEffectType effectType) {
    if (effectType.isBukkitEffect()) {
      //TODO implement logic!
    }
    return null;
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
