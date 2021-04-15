package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class VersionedEntity<T extends KelpEntity<T>> implements KelpEntity<T> {

  protected net.minecraft.server.v1_8_R3.Entity entityHandle;
  private final Location location;
  private final KelpEntityType entityType;

  public VersionedEntity(net.minecraft.server.v1_8_R3.Entity entityHandle,
                              KelpEntityType entityType,
                              Location initialLocation) {
    this.entityHandle = entityHandle;
    this.entityType = entityType;
    this.location = initialLocation;
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

    craftWorld.addEntity(entityHandle, CreatureSpawnEvent.SpawnReason.CUSTOM);
    return (T) this;
  }

  @Override
  public T setVelocity(Vector velocity) {
    return null;
  }

  @Override
  public Vector getVelocity() {
    return null;
  }

  @Override
  public double getEntityHeight() {
    return entityHandle.width;
  }

  @Override
  public double getEntityWidth() {
    return entityHandle.getHeadHeight();
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
  public Entity getBukkitEntity() {
    return null;
  }

  @Override
  public boolean isOnGround() {
    return false;
  }

  @Override
  public T setOnGround() {
    return null;
  }

  @Override
  public KelpWorld getWorld() {
    return null;
  }

  @Override
  public T setRotation(float yaw, float pitch) {
    return null;
  }

  @Override
  public T teleport(String worldName, double x, double y, double z, float yaw, float pitch) {
    return null;
  }

  @Override
  public int getFireTicks() {
    return 0;
  }

  @Override
  public T setFireTicks(int fireTicks) {
    return null;
  }

  @Override
  public T setMaxFireTicks(int maxFireTicks) {
    return null;
  }

  @Override
  public int getMaxFireTicks() {
    return 0;
  }

  @Override
  public T remove() {
    return null;
  }

  @Override
  public boolean isDead() {
    return false;
  }

  @Override
  public boolean isValid() {
    return false;
  }

  @Override
  public Server getServer() {
    return null;
  }

  @Override
  public KelpEntity<?> getPassenger() {
    return null;
  }

  @Override
  public List<KelpEntity<?>> getPassengers() {
    return null;
  }

  @Override
  public T addPassenger(KelpEntity<?> passenger) {
    return null;
  }

  @Override
  public T addPassenger(List<KelpEntity<?>> passengers) {
    return null;
  }

  @Override
  public T removePassenger(KelpEntity<?> passenger) {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public T ejectPassengers() {
    return null;
  }

  @Override
  public T setFallDistance(float fallDistance) {
    return null;
  }

  @Override
  public float getFallDistance() {
    return 0;
  }

  @Override
  public UUID getUUID() {
    return null;
  }

  @Override
  public int getTicksLived() {
    return 0;
  }

  @Override
  public T setTicksLived(int ticksLived) {
    return null;
  }

  @Override
  public boolean isInsideVehicle() {
    return false;
  }

  @Override
  public T leaveVehicle() {
    return null;
  }

  @Override
  public KelpEntity<?> getVehicle() {
    return null;
  }

  @Override
  public boolean isGlowing() {
    return false;
  }

  @Override
  public T setGlowing(boolean glowing) {
    return null;
  }

  @Override
  public T customNameVisible(boolean visible) {
    return null;
  }

  @Override
  public T customName(String customName) {
    return null;
  }

  @Override
  public boolean isCustomNameVisible() {
    return false;
  }

  @Override
  public EntityDamageEvent getLastDamageCause() {
    return null;
  }

  @Override
  public void setLastDamageCause(EntityDamageEvent damageCause) {

  }

  @Override
  public List<KelpEntity<?>> getNearbyEntities(double radiusX, double radiusY, double radiusZ) {
    return null;
  }

  protected CraftEntity craftEntity() {
    return entityHandle.getBukkitEntity();
  }
}
