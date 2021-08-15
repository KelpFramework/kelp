package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.type.general.MobileEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.VersionedLivingEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 * There is currently no implementation of a {@code Mob} in craftbukkit, so
 * this mob implementation uses the {@code Creature} implementation provided
 * by bukkit and NMS.
 *
 * @param <T>
 */
public class VersionedMobileEntity<T extends MobileEntity<T>>
  extends VersionedLivingEntity<T>
  implements MobileEntity<T> {

  private EntityInsentient entityInsentient;

  public VersionedMobileEntity(Entity entityHandle,
                               KelpEntityType entityType,
                               Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.entityInsentient = (EntityInsentient) entityHandle;
  }

  @Override
  public T setTarget(LivingKelpEntity<?> target) {
    EntityLiving entityLiving = ((CraftLivingEntity)target.getBukkitEntity()).getHandle();
    entityInsentient.setGoalTarget(entityLiving, EntityTargetEvent.TargetReason.UNKNOWN, true);
    return (T) this;
  }

  @Override
  public LivingKelpEntity<?> getTarget() {
    return (LivingKelpEntity<?>) entityTypeVersionTemplate.getKelpEntity(entityInsentient.getGoalTarget().getBukkitEntity());
  }

  @Override
  public boolean isAware() {
    return entityInsentient != null;
  }

  @Override
  public T setAware(boolean aware) {
    return (T) this;
  }

}
