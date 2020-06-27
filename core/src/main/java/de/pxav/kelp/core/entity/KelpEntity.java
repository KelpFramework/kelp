package de.pxav.kelp.core.entity;

import org.bukkit.Location;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpEntity {

  private Object bukkitEntity;
  private KelpEntityType entityType;
  private Location currentLocation;
  private int entityId;

  public KelpEntity(Object bukkitEntity, KelpEntityType entityType, Location currentLocation, int entityId) {
    this.bukkitEntity = bukkitEntity;
    this.entityType = entityType;
    this.currentLocation = currentLocation;
    this.entityId = entityId;
  }

  public KelpEntity() {}

  public Object getBukkitEntity() {
    return bukkitEntity;
  }

  public KelpEntity bukkitEntity(Object bukkitEntity) {
    this.bukkitEntity = bukkitEntity;
    return this;
  }

  public KelpEntityType getEntityType() {
    return entityType;
  }

  public KelpEntity entityType(KelpEntityType entityType) {
    this.entityType = entityType;
    return this;
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public KelpEntity currentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
    return this;
  }

  public int getEntityId() {
    return entityId;
  }

  public KelpEntity entityId(int entityId) {
    this.entityId = entityId;
    return this;
  }

}
