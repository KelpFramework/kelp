package de.pxav.kelp.implementation1_8.entity;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ArmorStandEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import de.pxav.kelp.core.world.region.CuboidRegion;
import de.pxav.kelp.core.world.region.KelpRegion;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class VersionedEntity<T extends KelpEntity<T>> implements KelpEntity<T> {

  protected net.minecraft.server.v1_8_R3.Entity entityHandle;
  protected EntityTypeVersionTemplate entityTypeVersionTemplate;
  private final Location location;
  private final KelpEntityType entityType;
  private boolean gravityFlag = true;

  public VersionedEntity(net.minecraft.server.v1_8_R3.Entity entityHandle,
                         KelpEntityType entityType,
                         Location initialLocation,
                         EntityTypeVersionTemplate entityTypeVersionTemplate) {
    this.entityHandle = entityHandle;
    this.entityType = entityType;
    this.location = initialLocation;
    this.entityTypeVersionTemplate = entityTypeVersionTemplate;
  }

  @Override
  public int getEntityId() {
    return entityHandle.getId();
  }

  @Override
  public T spawn() {
    CraftWorld craftWorld = (CraftWorld) craftEntity().getWorld();

    if (getType() == KelpEntityType.DROPPED_ITEM) {
//      DroppedItemEntity kelpEntity = (DroppedItemEntity) this;
//      if (kelpEntity.getItemDropType() == ItemDropType.NATURAL) {
//        craftWorld.dropItemNaturally(location, kelpEntity.getItem().getItemStack());
//      } else {
//        craftWorld.dropItem(location, kelpEntity.getItem().getItemStack());
//      }
//      return (T) this;
    }

    entityHandle.setPositionRotation(
      entityHandle.locX,
      entityHandle.locY,
      entityHandle.locZ,
      entityHandle.yaw,
      entityHandle.pitch
    );

    craftWorld.getHandle().addEntity(entityHandle, CreatureSpawnEvent.SpawnReason.DEFAULT);
    return (T) this;
  }

  @Override
  public T setVelocity(Vector velocity) {
    craftEntity().setVelocity(velocity);
    return (T) this;
  }

  @Override
  public Vector getVelocity() {
    return craftEntity().getVelocity();
  }

  @Override
  public double getEntityHeight() {
    return entityHandle.getHeadHeight();
  }

  @Override
  public double getEntityWidth() {
    return entityHandle.width;
  }

  @Override
  public KelpEntityType getType() {
    return KelpEntityType.UNKNOWN;
  }

  @Override
  public KelpLocation getLocation() {
    return KelpLocation.from(entityHandle.world.getWorld().getName(),
      entityHandle.locX,
      entityHandle.locY,
      entityHandle.locZ,
      entityHandle.yaw,
      entityHandle.pitch);
  }

  @Override
  public boolean isInWater() {
    try {
      Field inWaterField = net.minecraft.server.v1_8_R3.Entity.class.getDeclaredField("inWater");

      inWaterField.setAccessible(true);

      return inWaterField.getBoolean(entityHandle);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public boolean isInCobweb() {
    try {
      Field hField = net.minecraft.server.v1_8_R3.Entity.class.getDeclaredField("H");

      hField.setAccessible(true);

      return hField.getBoolean(entityHandle);
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public Entity getBukkitEntity() {
    return craftEntity();
  }

  @Override
  public boolean isOnGround() {
    return entityHandle.onGround;
  }

  @Override
  public T setOnGround(boolean onGround) {
    entityHandle.onGround = onGround;
    return (T) this;
  }

  @Override
  public KelpWorld getWorld() {
    return KelpWorld.from(craftEntity().getWorld());
  }

  @Override
  public T setRotation(float yaw, float pitch) {
    entityHandle.yaw = yaw;
    entityHandle.pitch = pitch;
    return (T) this;
  }

  @Override
  public T teleport(String worldName, double x, double y, double z, float yaw, float pitch) {
    craftEntity().teleport(new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
    return (T) this;
  }

  @Override
  public int getFireTicks() {
    return entityHandle.fireTicks;
  }

  @Override
  public T setFireTicks(int fireTicks) {
    entityHandle.fireTicks = fireTicks;
    return (T) this;
  }

  @Override
  public T setMaxFireTicks(int maxFireTicks) {
    entityHandle.maxFireTicks = maxFireTicks;
    return (T) this;
  }

  @Override
  public int getMaxFireTicks() {
    return entityHandle.maxFireTicks;
  }

  @Override
  public T remove() {
    craftEntity().remove();
    return (T) this;
  }

  @Override
  public boolean isDead() {
    return !entityHandle.isAlive();
  }

  @Override
  public boolean isValid() {
    return entityHandle.valid;
  }

  @Override
  public Server getServer() {
    return craftEntity().getServer();
  }

  @Override
  public KelpEntity<?> getPassenger() {
    return entityTypeVersionTemplate.getKelpEntity(craftEntity().getPassenger());
  }

  @Override
  public List<KelpEntity<?>> getPassengers() {
    // there is only one passenger in 1.8
    KelpEntity<?> passenger = getPassenger();
    if (passenger == null) {
      return null;
    }
    return Collections.singletonList(passenger);
  }

  @Override
  public T addPassenger(KelpEntity<?> passenger) {
    craftEntity().setPassenger(passenger.getBukkitEntity());
    return (T) this;
  }

  @Override
  public T addPassenger(List<KelpEntity<?>> passengers) {
    if (passengers == null || passengers.isEmpty()) {
      return (T) this;
    }
    craftEntity().setPassenger(passengers.get(0).getBukkitEntity());
    return (T) this;
  }

  // only one passenger in 1.8
  @Override
  public T removePassenger(KelpEntity<?> passenger) {
    craftEntity().eject();
    return (T) this;
  }

  @Override
  public boolean isEmpty() {
    return craftEntity().isEmpty();
  }

  @Override
  public T ejectPassengers() {
    craftEntity().eject();
    return (T) this;
  }

  @Override
  public T setFallDistance(float fallDistance) {
    craftEntity().setFallDistance(fallDistance);
    return (T) this;
  }

  @Override
  public float getFallDistance() {
    return craftEntity().getFallDistance();
  }

  @Override
  public UUID getUUID() {
    return entityHandle.getUniqueID();
  }

  @Override
  public int getTicksLived() {
    return craftEntity().getTicksLived();
  }

  @Override
  public T setTicksLived(int ticksLived) {
    craftEntity().setTicksLived(ticksLived);
    return (T) this;
  }

  @Override
  public boolean isInsideVehicle() {
    return craftEntity().isInsideVehicle();
  }

  @Override
  public T leaveVehicle() {
    craftEntity().leaveVehicle();
    return (T) this;
  }

  @Override
  public KelpEntity<?> getVehicle() {
    return entityTypeVersionTemplate.getKelpEntity(craftEntity().getVehicle());
  }

  // not available in 1.8
  @Override
  public boolean isGlowing() {
    return false;
  }

  // not in 1.8
  @Override
  public T setGlowing(boolean glowing) {
    return (T) this;
  }

  @Override
  public T customNameVisible(boolean visible) {
    craftEntity().setCustomNameVisible(visible);
    return (T) this;
  }

  @Override
  public T customName(String customName) {
    craftEntity().setCustomName(customName);
    return (T) this;
  }

  @Override
  public boolean isCustomNameVisible() {
    return craftEntity().isCustomNameVisible();
  }

  @Override
  public EntityDamageEvent getLastDamageCause() {
    return craftEntity().getLastDamageCause();
  }

  @Override
  public void setLastDamageCause(EntityDamageEvent damageCause) {
    craftEntity().setLastDamageCause(damageCause);
  }

  @Override
  public List<KelpEntity<?>> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
    List<net.minecraft.server.v1_8_R3.Entity> notchEntityList = entityHandle.world.a(
      entityHandle,
      entityHandle.getBoundingBox().grow(radiusX, radiusY, radiusZ),
      null);

    List<KelpEntity<?>> kelpEntityList = Lists.newArrayList();
    for (net.minecraft.server.v1_8_R3.Entity notchEntity : notchEntityList) {
      KelpEntity<?> entity = entityTypeVersionTemplate.getKelpEntity(notchEntity.getBukkitEntity());
      kelpEntityList.add(entity);
    }

    return kelpEntityList;
  }

  @Override
  public boolean hasGravity() {
    return this.gravityFlag;
  }

  @Override
  public T setGravity(boolean gravity) {
    if (gravity && !this.gravityFlag) {
      this.gravityFlag = true;
      KelpLocation locationBackup = getLocation();
      KelpEntity<?> armorStand = getVehicle();
      armorStand.ejectPassengers();
      teleport(locationBackup);
      armorStand.remove();
    } else if (!gravity && this.gravityFlag) {
      this.gravityFlag = false;
      KelpLocation armorStandLocation = getLocation().subtractY(getEntityHeight());
      ArmorStandEntity.create(armorStandLocation)
        .setVisible(false)
        .setGravity(false)
        .setBasePlate(false)
        .spawn()
        .addPassenger(this);
    }
    return (T) this;
  }

  @Override
  public KelpRegion getBoundingBox() {
    net.minecraft.server.v1_8_R3.Entity nmsEntity = craftEntity().getHandle();
    AxisAlignedBB boundingBox = nmsEntity.getBoundingBox();

    return CuboidRegion.create(
      KelpLocation.from(getLocation().getWorldName(), boundingBox.a, boundingBox.b, boundingBox.c),
      KelpLocation.from(getLocation().getWorldName(), boundingBox.d, boundingBox.e, boundingBox.f)
    );
  }

  protected CraftEntity craftEntity() {
    return entityHandle.getBukkitEntity();
  }
}
