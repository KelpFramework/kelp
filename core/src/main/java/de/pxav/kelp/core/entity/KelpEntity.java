package de.pxav.kelp.core.entity;

import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpEntity {

  private Object minecraftEntity;
  private KelpEntityType entityType;
  private Location currentLocation;
  private int entityId;
  private EntityVersionTemplate entityVersionTemplate;

  public KelpEntity(Object minecraftEntity,
                    KelpEntityType entityType,
                    Location currentLocation,
                    int entityId,
                    EntityVersionTemplate entityVersionTemplate) {
    this.minecraftEntity = minecraftEntity;
    this.entityType = entityType;
    this.currentLocation = currentLocation;
    this.entityId = entityId;
    this.entityVersionTemplate = entityVersionTemplate;
  }

  public KelpEntity(EntityVersionTemplate entityVersionTemplate) {
    this.entityVersionTemplate = entityVersionTemplate;
  }

  public KelpEntity() {}

  public Object getMinecraftEntity() {
    return minecraftEntity;
  }

  public KelpEntity minecraftEntity(Object minecraftEntity) {
    this.minecraftEntity = minecraftEntity;
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

  public KelpEntity versionTemplate(EntityVersionTemplate entityVersionTemplate) {
    this.entityVersionTemplate = entityVersionTemplate;
    return this;
  }

  public Entity toBukkitEntity() {
    System.out.println("mc entity " + this.minecraftEntity);
    System.out.println("version template " + this.entityVersionTemplate);
    return entityVersionTemplate.toBukkitEntity(this.minecraftEntity);
  }

  public void teleport(Location to) {
    entityVersionTemplate.teleport(toBukkitEntity(), to, PlayerTeleportEvent.TeleportCause.PLUGIN);
  }

}
