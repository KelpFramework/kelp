package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.SinceKelpVersion;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class EntityVersionTemplate {

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
  public abstract void spawnEntity(KelpEntity entity);

  public abstract Entity toBukkitEntity(Object minecraftEntity);

  /**
   * Gets the current entity's location in the world.
   *
   * @param entity The entity whose location you want to get.
   * @return The location of the given entity.
   */
  public abstract KelpLocation getLocation(Entity entity);

  /**
   * Sets the entity's velocity to the given vector.
   *
   * @param entity The entity whose velocity you want to manipulate.
   * @param vector The vector of the velocity you want to set.
   */
  public abstract void setVelocity(Entity entity, Vector vector);

  /**
   * Gets the velocity of the desired entity.
   *
   * @param entity The entity whose velocity you want to get.
   * @return The velocity of the given entity.
   */
  public abstract Vector getVelocity(Entity entity);

  /**
   * Gets the height of an entity. In older versions
   * this property was called 'length' of an entity.
   *
   * @param entity The entity whose height you want to get.
   * @return The height of the given entity.
   */
  public abstract double getHeight(Entity entity);

  /**
   * Gets the width of the given entity.
   *
   * @param entity The entity whose width you want to get.
   * @return The entity's width.
   */
  public abstract double getWidth(Entity entity);

//  /**
//   * Get the current bounding box of the given entity.
//   * A bounding box is a way to mark 3D regions in the
//   * minecraft world and surrounds the entity. More
//   * information can be found in the documentation of
//   * {@code BoundingBox}
//   *
//   * @param entity The entity whose bounding box you want to get.
//   * @return The entity's bounding box.
//   * @see BoundingBox
//   */
//  public abstract BoundingBox getBoundingBox(Entity entity);

  /**
   * Checks if the entity is currently on the ground.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is currently on ground.
   */
  public abstract boolean isOnGround(Entity entity);

  /**
   * Sets the rotation of the given entity. This does not
   * affect the location x, y and z axes.
   *
   * @param entity  The entity whose rotation you want to modify.
   * @param yaw     The yaw value of the desired rotation.
   * @param pitch   The pitch value of the desired rotation.
   */
  public abstract void setRotation(Entity entity, float yaw, float pitch);

  /**
   * Teleports the entity to the given location.
   *
   * @param entity        The entity you want to teleport.
   * @param location      The location you want the entity to teleport to.
   * @param teleportCause The cause for the teleportation.
   * @return {@code true} if the teleport action was successful.
   */
  public abstract boolean teleport(Entity entity, KelpLocation location, PlayerTeleportEvent.TeleportCause teleportCause);

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
  public abstract List<Entity> getNearbyEntities(Entity entity, double x, double y, double z);

  /**
   * Gets the id of the given entity.
   *
   * @param entity The entity whose id you want to get.
   * @return the entity id
   */
  public abstract int getEntityId(Entity entity);

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
  public abstract int getFireTicks(Entity entity);

  /**
   * Gets the amount of the maximum fire ticks of the given entity.
   *
   * @param entity The entity whose maximum fire ticks you want to get.
   * @return The amount of maximum fire ticks of the given entity.
   */
  public abstract int getMaxFireTicks(Entity entity);

  /**
   * Sets the amount of the maximum fire ticks of the given entity.
   *
   * @param entity        The entity whose maximum fire ticks you want to modify.
   * @param maxFireTicks  The amount of maximum fire ticks to set.
   */
  public abstract void setMaxFireTicks(Entity entity, int maxFireTicks);

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
  public abstract void setFireTicks(Entity entity, int fireTicks);

  /**
   * Removes the entity from the world.
   *
   * @param entity The entity you want to remove.
   */
  public abstract void remove(Entity entity);

  /**
   * Checks if the given entity is dead.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is dead.
   */
  public abstract boolean isDead(Entity entity);

  /**
   * Checks if the entity is valid.
   *
   * Valid means the entity has to be alive and currently
   * spawned in a world.
   *
   * @param entity The entity whose validity you want to check.
   * @return {@code true} if the entity is 'valid'.
   */
  public abstract boolean isValid(Entity entity);

  /**
   * Gets the current server containing the entity.
   *
   * @param entity The entity whose server you want to get.
   * @return The server instance.
   */
  public abstract Server getServer(Entity entity);

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
  public abstract List<Entity> getPassengers(Entity entity);

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
  public abstract boolean addPassenger(Entity entity, Entity passenger);

  /**
   * Removes a passenger from the given entity.
   *
   * @param entity      The entity you want to remove the passenger from.
   * @param passenger   The passenger you want to remove.
   * @return {@code true} if the action succeeded.
   */
  public abstract boolean removePassenger(Entity entity, Entity passenger);

  /**
   * Checks if the given entity has no passengers.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity has no passengers at all.
   */
  public abstract boolean isEmpty(Entity entity);

  /**
   * Ejects any passenger currently riding on the
   * given entity.
   *
   * @param entity The entity you want to eject all
   *               passengers of.
   * @return {@code true} if there were any passengers to eject.
   */
  public abstract boolean eject(Entity entity);

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
  public abstract float getFallDistance(Entity entity);

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
  public abstract void setFallDistance(Entity entity, float fallDistance);

  /**
   * Sets the last damage cause the entity has suffered.
   *
   * @param entity  The entity whose damage cause you want to set.
   * @param event   The instance of the last entity damage event containing
   *                the last damage cause.
   */
  public abstract void setLastDamageCause(Entity entity, EntityDamageEvent event);

  /**
   * Gets the last damage cause. If the entity has not been damaged
   * so far, it will return null.
   *
   * @param entity The entity whose last damage cause you want to get.
   * @return  The last damage event instance containing the last
   *          damage cause.
   */
  public abstract EntityDamageEvent getLastDamageCause(Entity entity);

  /**
   * Returns a unique as well as persistent id for
   * the given entity. For players, the player UUID will
   * be returned.
   *
   * @param entity The entity whose unique id you want to get.
   * @return The UUID of the given entity.
   */
  public abstract UUID getUniqueId(Entity entity);

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @param entity The entity whose lifetime you want to get.
   * @return The age of the entity.
   */
  public abstract int getTicksLived(Entity entity);

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @param entity The entity whose lifetime you want to get.
   * @param ticksLived The amount of ticks you want to set.
   *                   May not be less than one tick.
   */
  public abstract void setTicksLived(Entity entity, int ticksLived);

  // TODO: play effect public abstract void playEffect(@NotNull EntityEffect var1);

  // public abstract EntityType getType();

  /**
   * Returns true if the entity is currently inside a vehicle.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is inside a vehicle.
   */
  public abstract boolean isInsideVehicle(Entity entity);

  /**
   * Makes the entity leave its current vehicle.
   *
   * @param entity The entity you want to kick from its
   *               vehicle.
   * @return {@code true} if the entity was actually removed
   *          and has been sitting in a vehicle before.
   */
  public abstract boolean leaveVehicle(Entity entity);

  /**
   * Gets the current vehicle of the given entity.
   *
   * @param entity The entity whose vehicle you want to check.
   * @return  The vehicle of the entity. If the entity
   *          has no vehicle, null will be returned.
   */
  public abstract Entity getVehicle(Entity entity);

  /**
   * Sets the custom name of an entity visible or invisible
   * for the clients.
   *
   * @param entity   The entity whose nametag you want to hide/show.
   * @param visible  {@code true} if it should be visible
   *                 {@code false} if it should not be visible.
   */
  public abstract void setCustomNameVisible(Entity entity, boolean visible);

  /**
   * Checks if the custom name of the given entity is
   * currently visible.
   *
   * @param entity The entity whose nametag visibility you want to check.
   * @return {@code true} if the custom name is visible.
   */
  public abstract boolean isCustomNameVisible(Entity entity);

  @SinceKelpVersion(KelpVersion.MC_1_9_0)
  public abstract void setGlowing(Entity entity, boolean glowing);

  @SinceKelpVersion(KelpVersion.MC_1_9_0)
  public abstract boolean isGlowing(Entity entity);

  // public abstract void setInvulnerable(boolean var1);

  // public abstract boolean isInvulnerable();

  /**
   * Checks whether the entity is currently silent.
   *
   * If the silent flag is set, the entity will not
   * produce any sounds.
   *
   * @param entity The entity you want to check.
   * @return {@code true} if the entity is silent
   */
  public abstract boolean isSilent(Entity entity);

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
  public abstract void setSilent(Entity entity, boolean silent);

  // public abstract boolean hasGravity(Entity entity);

  // public abstract void setGravity(Entity entity, boolean gravity);

  // public abstract int getPortalCooldown();

  // public abstract void setPortalCooldown(int var1);

  // public abstract PistonMoveReaction getPistonMoveReaction();

  // public abstract BlockFace getFacing();

  // public abstract Pose getPose();

}
