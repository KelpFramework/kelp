package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.type.ItemDropType;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.SinceKelpVersion;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedEntity extends EntityVersionTemplate {

  /**
   * Spawns the entity at the given location. As
   * creating the entity only registers it and returns
   * the entity instance, this method is necessary to
   * finally add your entity to the world.
   *
   * It is recommended to execute this method with a
   * small delay if you use it on a player join as
   * some versions will not spawn it correctly if you
   * do not do so.
   *
   * @param entity The entity you want to spawn.
   */
  @Override
  public void spawnEntity(KelpEntity entity) {
    CraftWorld craftWorld = (CraftWorld) entity.getInitialBukkitLocation().getWorld();

    if (entity.getEntityType() == KelpEntityType.DROPPED_ITEM) {
      DroppedItemEntity kelpEntity = (DroppedItemEntity) entity;
      if (kelpEntity.getItemDropType() == ItemDropType.NATURAL) {
        craftWorld.dropItemNaturally(entity.getInitialBukkitLocation(), kelpEntity.getItem().getItemStack());
      } else {
        craftWorld.dropItem(entity.getInitialBukkitLocation(), kelpEntity.getItem().getItemStack());
      }
      return;
    }

    Entity minecraftEntity = (Entity) entity.getMinecraftEntity();
    ((Entity) entity.getMinecraftEntity()).setPositionRotation(entity.getInitialBukkitLocation().getX(), entity.getInitialBukkitLocation().getY(), entity.getInitialBukkitLocation().getZ(), entity.getInitialBukkitLocation().getYaw(), entity.getInitialBukkitLocation().getPitch());

    craftWorld.addEntity(minecraftEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
  }

  @Override
  public org.bukkit.entity.Entity toBukkitEntity(Object minecraftEntity) {
    return ((Entity) minecraftEntity).getBukkitEntity();
  }

  /**
   * Gets the current entity's location in the world.
   *
   * @param entity The entity whose location you want to get.
   * @return The location of the given entity.
   */
  @Override
  public KelpLocation getLocation(org.bukkit.entity.Entity entity) {
    return KelpLocation.from(entity.getLocation());
  }

  /**
   * Sets the entity's velocity to the given vector.
   *
   * @param entity The entity whose velocity you want to manipulate.
   * @param vector The vector of the velocity you want to set.
   */
  @Override
  public void setVelocity(org.bukkit.entity.Entity entity, Vector vector) {
    entity.setVelocity(vector);
  }

  /**
   * Gets the velocity of the desired entity.
   *
   * @param entity The entity whose velocity you want to get.
   * @return The velocity of the given entity.
   */
  @Override
  public Vector getVelocity(org.bukkit.entity.Entity entity) {
    return entity.getVelocity();
  }

  /**
   * Gets the height of an entity. In older versions
   * this property was called 'length' of an entity.
   *
   * @param entity The entity whose height you want to get.
   * @return The height of the given entity.
   */
  @Override
  public double getHeight(org.bukkit.entity.Entity entity) {
    return ((CraftEntity)entity).getHandle().length;
  }

  /**
   * Gets the width of the given entity.
   *
   * @param entity The entity whose width you want to get.
   * @return The entity's width.
   */
  @Override
  public double getWidth(org.bukkit.entity.Entity entity) {
    return ((CraftEntity)entity).getHandle().width;
  }

  /**
   * Checks if the entity is currently on the ground.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is currently on ground.
   */
  @Override
  public boolean isOnGround(org.bukkit.entity.Entity entity) {
    return entity.isOnGround();
  }

  /**
   * Sets the rotation of the given entity. This does not
   * affect the location x, y and z axes.
   *
   * @param entity  The entity whose rotation you want to modify.
   * @param yaw     The yaw value of the desired rotation.
   * @param pitch   The pitch value of the desired rotation.
   */
  @Override
  public void setRotation(org.bukkit.entity.Entity entity, float yaw, float pitch) {
    ((CraftEntity)entity).getHandle().setPositionRotation(
      entity.getLocation().getX(),
      entity.getLocation().getY(),
      entity.getLocation().getZ(),
      yaw,
      pitch
    );
  }

  /**
   * Teleports the entity to the given location.
   *
   * @param entity        The entity you want to teleport.
   * @param location      The location you want the entity to teleport to.
   * @param teleportCause The cause for the teleportation.
   * @return {@code true} if the teleport action was successful.
   */
  @Override
  public boolean teleport(org.bukkit.entity.Entity entity, KelpLocation location, PlayerTeleportEvent.TeleportCause teleportCause) {
    return entity.teleport(location.getBukkitLocation(), teleportCause);
  }

  /**
   * Gets all entities within the given radius centered around
   * the given entity.
   *
   * @param entity  The entity where the center should be set.
   * @param x       the radius for the x axis
   *                (1/2 the size of the box along x axis)
   * @param y       the radius for the y axis
   *                (1/2 the size of the box along y axis)
   * @param z       the radius for the z axis
   *                (1/2 the size of the box along z axis)
   * @return A list of all nearby entities within the given radius.
   */
  @Override
  public List<org.bukkit.entity.Entity> getNearbyEntities(org.bukkit.entity.Entity entity, double x, double y, double z) {
    return entity.getNearbyEntities(x, y, z);
  }

  /**
   * Gets the id of the given entity.
   *
   * @param entity The entity whose id you want to get.
   * @return the entity id
   */
  @Override
  public int getEntityId(org.bukkit.entity.Entity entity) {
    return entity.getEntityId();
  }

  /**
   * Gets the current amount of fire ticks for the given
   * entity.
   *
   * Fire ticks are the duration before the entity stops
   * being on fire in ticks. So 60 for example would mean
   * the entity will stop burning after 3 seconds once
   * it ran into fire.
   *
   * @param entity The entity whose fire ticks you want to get.
   * @return The amount of fire ticks.
   */
  @Override
  public int getFireTicks(org.bukkit.entity.Entity entity) {
    return entity.getFireTicks();
  }

  /**
   * Gets the amount of the maximum fire ticks of the given entity.
   *
   * @param entity The entity whose maximum fire ticks you want to get.
   * @return The amount of maximum fire ticks of the given entity.
   */
  @Override
  public int getMaxFireTicks(org.bukkit.entity.Entity entity) {
    return entity.getMaxFireTicks();
  }

  /**
   * Sets the amount of the maximum fire ticks of the given entity.
   *
   * @param entity        The entity whose maximum fire ticks you want to modify.
   * @param maxFireTicks  The amount of maximum fire ticks to set.
   */
  @Override
  public void setMaxFireTicks(org.bukkit.entity.Entity entity, int maxFireTicks) {
    ((CraftEntity)entity).getHandle().maxFireTicks = maxFireTicks;
  }

  /**
   * Sets the current amount of fire ticks for the given
   * entity.
   *
   * Fire ticks are the duration before the entity stops
   * being on fire in ticks. So 60 for example would mean
   * the entity will stop burning after 3 seconds once
   * it ran into fire.
   *
   * @param entity    The entity whose fire ticks you want to set.
   * @param fireTicks The amount of fire ticks you want to set.
   */
  @Override
  public void setFireTicks(org.bukkit.entity.Entity entity, int fireTicks) {
    entity.setFireTicks(fireTicks);
  }

  /**
   * Removes the entity from the world.
   *
   * @param entity The entity you want to remove.
   */
  @Override
  public void remove(org.bukkit.entity.Entity entity) {
    entity.remove();
  }

  /**
   * Checks if the given entity is dead.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is dead.
   */
  @Override
  public boolean isDead(org.bukkit.entity.Entity entity) {
    return entity.isDead();
  }

  /**
   * Checks if the entity is valid.
   *
   * Valid means the entity has to be alive and currently
   * spawned in a world.
   *
   * @param entity The entity whose validity you want to check.
   * @return {@code true} if the entity is 'valid'.
   */
  @Override
  public boolean isValid(org.bukkit.entity.Entity entity) {
    return entity.isValid();
  }

  /**
   * Gets the current server containing the entity.
   *
   * @param entity The entity whose server you want to get.
   * @return The server instance.
   */
  @Override
  public Server getServer(org.bukkit.entity.Entity entity) {
    return entity.getServer();
  }

  /**
   * Returns a list of all passengers currently riding
   * on the entity.
   *
   * Note that most entities are only able to hold only
   * one passenger and the list will likely contain only
   * one element. Boats from newer versions can contain
   * up to two passengers for example.
   *
   * @param entity The entity whose passengers you want to get.
   * @return A list of all passengers of the given entity.
   */
  @Override
  public List<org.bukkit.entity.Entity> getPassengers(org.bukkit.entity.Entity entity) {
    return Collections.singletonList(entity.getPassenger());
  }

  /**
   * Adds a new passenger to the given entity.
   *
   * Note that most entities can only hold one passenger.
   * In this case the old entity will be removed in order
   * to hold the new passenger provided in this method.
   *
   * @param entity    The entity you want to add the passenger to.
   * @param passenger The passenger you want to add.
   * @return {@code true} if the action was successful.
   */
  @Override
  public boolean addPassenger(org.bukkit.entity.Entity entity, org.bukkit.entity.Entity passenger) {
    return entity.setPassenger(passenger);
  }

  /**
   * Removes a passenger from the given entity.
   *
   * @param entity      The entity you want to remove the passenger from.
   * @param passenger   The passenger you want to remove.
   * @return {@code true} if the action succeeded.
   */
  @Override
  public boolean removePassenger(org.bukkit.entity.Entity entity, org.bukkit.entity.Entity passenger) {
    return entity.eject();
  }

  /**
   * Checks if the given entity has no passengers.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity has no passengers at all.
   */
  @Override
  public boolean isEmpty(org.bukkit.entity.Entity entity) {
    return entity.isEmpty();
  }

  /**
   * Ejects any passenger currently riding on the
   * given entity.
   *
   * @param entity The entity you want to eject all
   *               passengers of.
   * @return {@code true} if there were any passengers to eject.
   */
  @Override
  public boolean eject(org.bukkit.entity.Entity entity) {
    return entity.eject();
  }

  /**
   * Gets the current fall distance of the current entity.
   *
   * The fall distance is the distance of blocks in the y-axis
   * the player is falling down. The higher the fall distance
   * the higher the fall damage. If the player is currently on
   * ground, the fall distance will be 0.
   *
   * @param entity The entity whose fall distance you want to get.
   * @return The current fall distance of the entity.
   */
  @Override
  public float getFallDistance(org.bukkit.entity.Entity entity) {
    return entity.getFallDistance();
  }

  /**
   * Sets the current fall distance of the current entity.
   *
   * The fall distance is the distance of blocks in the y-axis
   * the player is falling down. The higher the fall distance
   * the higher the fall damage. If the player is currently on
   * ground, the fall distance will be 0.
   *
   * @param entity        The entity whose fall distance you want to set.
   * @param fallDistance  The new fall distance you want to set.
   */
  @Override
  public void setFallDistance(org.bukkit.entity.Entity entity, float fallDistance) {
    entity.setFallDistance(fallDistance);
  }

  /**
   * Sets the last damage cause the entity has suffered.
   *
   * @param entity  The entity whose damage cause you want to set.
   * @param event   The instance of the last entity damage event containing
   *                the last damage cause.
   */
  @Override
  public void setLastDamageCause(org.bukkit.entity.Entity entity, EntityDamageEvent event) {
    entity.setLastDamageCause(event);
  }

  /**
   * Gets the last damage cause. If the entity has not been damaged
   * so far, it will return null.
   *
   * @param entity The entity whose last damage cause you want to get.
   * @return  The last damage event instance containing the last
   *          damage cause.
   */
  @Override
  public EntityDamageEvent getLastDamageCause(org.bukkit.entity.Entity entity) {
    return entity.getLastDamageCause();
  }

  /**
   * Returns a unique as well as persistent id for
   * the given entity. For players, the player UUID will
   * be returned.
   *
   * @param entity The entity whose unique id you want to get.
   * @return The UUID of the given entity.
   */
  @Override
  public UUID getUniqueId(org.bukkit.entity.Entity entity) {
    return entity.getUniqueId();
  }

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @param entity The entity whose lifetime you want to get.
   * @return The age of the entity.
   */
  @Override
  public int getTicksLived(org.bukkit.entity.Entity entity) {
    return entity.getTicksLived();
  }

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @param entity The entity whose lifetime you want to get.
   * @param ticksLived The amount of ticks you want to set.
   *                   May not be less than one tick.
   */
  @Override
  public void setTicksLived(org.bukkit.entity.Entity entity, int ticksLived) {
    entity.setTicksLived(ticksLived);
  }

  /**
   * Returns true if the entity is currently inside a vehicle.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is inside a vehicle.
   */
  @Override
  public boolean isInsideVehicle(org.bukkit.entity.Entity entity) {
    return entity.isInsideVehicle();
  }

  /**
   * Makes the entity leave its current vehicle.
   *
   * @param entity The entity you want to kick from its
   *               vehicle.
   * @return {@code true} if the entity was actually removed
   *          and has been sitting in a vehicle before.
   */
  @Override
  public boolean leaveVehicle(org.bukkit.entity.Entity entity) {
    return entity.leaveVehicle();
  }

  /**
   * Gets the current vehicle of the given entity.
   *
   * @param entity The entity whose vehicle you want to check.
   * @return  The vehicle of the entity. If the entity
   *          has no vehicle, null will be returned.
   */
  @Override
  public org.bukkit.entity.Entity getVehicle(org.bukkit.entity.Entity entity) {
    return entity.getVehicle();
  }

  /**
   * Sets the custom name of an entity visible or invisible
   * for the clients.
   *
   * @param entity   The entity whose nametag you want to hide/show.
   * @param visible  {@code true} if it should be visible
   *                 {@code false} if it should not be visible.
   */
  @Override
  public void setCustomNameVisible(org.bukkit.entity.Entity entity, boolean visible) {
    entity.setCustomNameVisible(visible);
  }

  /**
   * Checks if the custom name of the given entity is
   * currently visible.
   *
   * @param entity The entity whose nametag visibility you want to check.
   * @return {@code true} if the custom name is visible.
   */
  @Override
  public boolean isCustomNameVisible(org.bukkit.entity.Entity entity) {
    return false;
  }

  @SinceKelpVersion(KelpVersion.MC_1_9_0)
  @Override
  public void setGlowing(org.bukkit.entity.Entity entity, boolean glowing) {
    throw new UnsupportedOperationException("Glowing entities are not supported in MC1.8");
  }

  @SinceKelpVersion(KelpVersion.MC_1_9_0)
  @Override
  public boolean isGlowing(org.bukkit.entity.Entity entity) {
    throw new UnsupportedOperationException("Glowing entities are not supported in MC1.8");
  }

  /**
   * Checks whether the entity is currently silent.
   *
   * If the silent flag is set, the entity will not
   * produce any sounds.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is silent
   */
  @Override
  public boolean isSilent(org.bukkit.entity.Entity entity) {
    return false;
  }

  /**
   * Makes the entity silent or unsilent.
   *
   * If the silent flag is set, the entity will not
   * produce any sounds.
   *
   * @param entity The entity you want to make silent/unsilent.
   * @param silent {@code true} if you want to make the
   *                           entity silent,
   *               {@code false} if not.
   */
  @Override
  public void setSilent(org.bukkit.entity.Entity entity, boolean silent) {

  }

}
