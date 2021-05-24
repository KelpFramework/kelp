package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AbstractArrowEntity;
import de.pxav.kelp.core.entity.util.ArrowPickupStatus;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.KelpBlock;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;

public class VersionedAbstractArrow<T extends AbstractArrowEntity<T>> extends VersionedProjectile<T> implements AbstractArrowEntity<T> {

  private CraftArrow craftArrow;

  public VersionedAbstractArrow(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftArrow = (CraftArrow) entityHandle.getBukkitEntity();
  }

  @Override
  public KelpBlock getAttachedBlock() {
    if (!isInBlock()) {
      return null;
    }
    BlockPosition blockPosition = craftArrow.getHandle().getChunkCoordinates();
    return KelpBlock.from(craftArrow.getWorld().getBlockAt(
      blockPosition.getX(),
      blockPosition.getY(),
      blockPosition.getZ()));
  }

  @Override
  public double getBaseDamage() {
    return craftArrow.getHandle().j();
  }

  @Override
  public int getKnockbackLevel() {
    return craftArrow.getKnockbackStrength();
  }

  @Override
  public ArrowPickupStatus getPickupStatus() {
    int pickupStatusId = craftArrow.getHandle().fromPlayer;
    if (pickupStatusId == 1) {
      return ArrowPickupStatus.ALWAYS_ALLOWED;
    } else if (pickupStatusId == 2) {
      return ArrowPickupStatus.CREATIVE_ONLY;
    } else {
      return ArrowPickupStatus.ALWAYS_FORBIDDEN;
    }
  }

  @Override
  public int getPierceLevel() {
    return 1;
  }

  @Override
  public boolean isCritical() {
    return craftArrow.isCritical();
  }

  @Override
  public boolean isInBlock() {
    return craftArrow.getHandle().onGround;
  }

  @Override
  public boolean isShotFromCrossbow() {
    return false;
  }

  @Override
  public T setPickupStatus(ArrowPickupStatus pickupStatus) {
    if (pickupStatus == ArrowPickupStatus.ALWAYS_ALLOWED) {
      craftArrow.getHandle().fromPlayer = 1;
    } else if (pickupStatus == ArrowPickupStatus.CREATIVE_ONLY) {
      craftArrow.getHandle().fromPlayer = 2;
    } else {
      craftArrow.getHandle().fromPlayer = 0;
    }
    return (T) this;
  }

  @Override
  public T setCritical(boolean critical) {
    craftArrow.setCritical(critical);
    return (T) this;
  }

  @Override
  public T setDamage(double damage) {
    craftArrow.getHandle().b(damage);
    return (T) this;
  }

  @Override
  public T setKnockbackLevel(int knockbackLevel) {
    craftArrow.setKnockbackStrength(knockbackLevel);
    return (T) this;
  }

  @Override
  public T setPierceLevel(int pierceLevel) {
    return (T) this;
  }

  @Override
  public T setShotFromCrossbow(boolean fromCrossbow) {
    return (T) this;
  }

}
