package de.pxav.kelp.core.entity;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

public interface KelpEntity<T extends KelpEntity<?>> {

  static KelpEntity<?> create(KelpEntityType entityType, KelpLocation location) {
    return KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class).newKelpEntity(entityType, location.getBukkitLocation());
  }

  static KelpEntity<?> from(Entity bukkitEntity) {
    return KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class).getKelpEntity(bukkitEntity);
  }

  /**
   * Gets the unique id of this entity in its world.
   *
   * Every entity is assigned an id when spawned to a world,
   * which can be used to identify this entity when sending packets
   * for example.
   *
   * Entity ids are incremental in a range
   * from 0 to 2000, which usually is the maximum id used
   * by bukkit.
   *
   * @return The id of this entity.
   */
  int getEntityId();

  /**
   * Spawns the entity to the given initial location and makes it visible
   * for all players on the world.
   *
   * @return An instance of the entity for fluent builder design.
   */
  T spawn();

  /**
   * Sets the entity's velocity to the given vector.
   *
   * @param velocity The vector of the velocity you want to set.
   * @see Vector
   */
  T setVelocity(Vector velocity);

  /**
   * Gets the velocity of the desired entity.
   *
   * @return The velocity of the given entity.
   * @see Vector
   */
  Vector getVelocity();

  /**
   * Gets the height of an entity's model. In older versions
   * this property was called 'length' of an entity.
   *
   * @return The height of the given entity.
   */
  double getEntityHeight();

  /**
   * Gets the width of the given entity's model.
   *
   * @return The entity's width.
   */
  double getEntityWidth();

  /**
   * Gets the exact {@link KelpEntityType type} of this entity such as ZOMBIE, SHEEP, CREEPER, etc.
   *
   * @return The type of this entity.
   */
  KelpEntityType getType();

  /**
   * Gets the current entity's location in the world.
   *
   * @return The current location of the current entity.
   */
  KelpLocation getLocation();

  boolean isInWater();

  boolean isInCobweb();

  /**
   * Converts the current {@code KelpEntity} to a normal
   * bukkit entity.
   *
   * @return The bukkit entity of the current KelpEntity.
   */
  Entity getBukkitEntity();

  /**
   * Checks if the entity is currently on the ground.
   *
   * @return {@code true} if the entity is currently on ground.
   */
  boolean isOnGround();

  /**
   * Teleports the entity to the ground level of the
   * current coordinates. This is useful when the entity
   * is suspected to be stuck in an underground block.
   *
   * This method will then search for the highest non-passable block
   * at the entities x and z coordinates and teleport it there.
   *
   * @return An instance of the current entity for fluent builder design.
   */
  T setOnGround(boolean onGround);

  /**
   * Gets the current world of the entity.
   *
   * @return The world where the entity is currently located.
   */
  KelpWorld getWorld();

  /**
   * Sets the rotation of the given entity. This does not
   * affect the location x, y and z axes.
   *
   * @param yaw     The yaw value of the desired rotation.
   * @param pitch   The pitch value of the desired rotation.
   * @return An instance of the current entity for fluent builder design.
   */
  T setRotation(float yaw, float pitch);

  /**
   * Makes the entity look to the given location. So it
   * rotates its head to the given target location.
   *
   * @param target The location where the entity should look to.
   * @return An instance of the current entity object for fluent builder design.
   */
  default T lookTo(KelpLocation target) {
    KelpLocation location = getLocation();
    double xDiff = target.getX() - location.getX();
    double yDiff = target.getY() - location.getY();
    double zDiff = target.getZ() - location.getZ();

    double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
    double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);

    double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
    double pitch = Math.toDegrees(Math.acos(yDiff / distanceY)) - 90.0D;
    if (zDiff < 0.0D) {
      yaw += Math.abs(180.0D - yaw) * 2.0D;
    }

    setRotation((float) yaw - 90.0F, (float) pitch);
    return (T) this;
  }

  /**
   * Teleports the entity to the location at the given
   * coordinates.
   *
   * @param worldName The name of the world where the entity should be teleported to.
   * @param x         The exact value of the location's x axis.
   * @param y         The exact value of the location's y axis.
   * @param z         The exact value of the location's z axis.
   * @param yaw       The yaw rotation of the entity.
   * @param pitch     The location's pitch
   */
  T teleport(String worldName, double x, double y, double z, float yaw, float pitch);

  /**
   * Teleports the entity to the given location.
   *
   * @param to The location you want the entity to be teleported to.
   */
  default T teleport(KelpLocation to) {
    teleport(to.getWorldName(), to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());
    return (T) this;
  }

  /**
   * Teleports the entity to the location at the given
   * coordinates. As there is no world passed, this method
   * will use the current world of the entity.
   *
   * @param x The exact value of the location's x axis.
   * @param y The exact value of the location's y axis.
   * @param z The exact value of the location's z axis.
   */
  default T teleport(double x, double y, double z) {
    teleport(getWorld().getName(), x, y, z, 0, 0);
    return (T) this;
  }

  /**
   * Teleports the entity to the location at the given
   * coordinates. As there is no world passed, this method
   * will use the current world of the entity.
   *
   * @param x     The exact value of the location's x axis.
   * @param y     The exact value of the location's y axis.
   * @param z     The exact value of the location's z axis.
   * @param yaw   The yaw rotation of the entity.
   * @param pitch The location's pitch
   */
  default T teleport(double x, double y, double z, float yaw, float pitch) {
    teleport(getWorld().getName(), x, y, z, yaw, pitch);
    return (T) this;
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
   * @return The amount of fire ticks.
   */
  int getFireTicks();

  /**
   * Sets the current amount of fire ticks for the given
   * entity.
   *
   * Fire ticks are the duration before the entity stops
   * being on fire in ticks. So 60 for example would mean
   * the entity will stop burning after 3 seconds once
   * it ran into fire.
   *
   * @param fireTicks The amount of fire ticks you want to set.
   */
  T setFireTicks(int fireTicks);

  /**
   * Sets the amount of the maximum fire ticks of the given entity.
   *
   * @param maxFireTicks  The amount of maximum fire ticks to set.
   */
  T setMaxFireTicks(int maxFireTicks);

  /**
   * Gets the amount of the maximum fire ticks of the given entity.
   *
   * @return The amount of maximum fire ticks of the given entity.
   */
  int getMaxFireTicks();

  /**
   * Removes the entity from the world.
   */
  T remove();

  /**
   * Checks if the given entity is dead.
   *
   * @return {@code true} if the entity is dead.
   */
  boolean isDead();

  /**
   * Checks if the entity is valid.
   *
   * Valid means the entity has to be alive and currently
   * spawned in a world.
   *
   * @return {@code true} if the entity is 'valid'.
   */
  boolean isValid();

  /**
   * Gets the current server containing the entity.
   *
   * @return The server instance.
   */
  Server getServer();

  /**
   * Gets the passenger of the current entity.
   *
   * In newer versions some entities can have multiple passengers,
   * so in general it is recommended to use the {@code #getPassengers}
   * method. If you are sure your entity can only hold one passenger
   * or you are developing for older versions, you can simply use
   * this method.
   *
   * It either returns the first element of the passenger list
   * or null if there are no passengers.
   *
   * @return The first or only passenger of the entity. {@code null} if there is no passenger.
   */
  KelpEntity<?> getPassenger();

  /**
   * Returns a list of all passengers currently riding
   * on the entity.
   *
   * Note that most entities are only able to hold only
   * one passenger and the list will likely contain only
   * one element. Boats from newer versions can contain
   * up to two passengers for example.
   *
   * @return  A list of all passengers of the given entity.
   *          {@code null} if there are no passengers.
   */
  List<KelpEntity<?>> getPassengers();

  /**
   * Adds a new passenger to the given entity.
   *
   * Note that most entities can only hold one passenger.
   * In this case the old entity will be removed in order
   * to hold the new passenger provided in this method.
   *
   * @param passenger The passenger you want to add.
   * @return {@code true} if the action was successful.
   */
  T addPassenger(KelpEntity<?> passenger);

  /**
   * Adds multiple passengers to the given entity.
   *
   * Note that most entities can only hold one passenger.
   * In this case the old entity will be removed in order
   * to hold the new passenger provided in this method.
   *
   * @param passengers The passengers you want to add.
   * @return {@code true} if the action was successful.
   */
  T addPassenger(List<KelpEntity<?>> passengers);

  /**
   * Removes a passenger from the given entity.
   *
   * @param passenger The passenger you want to remove.
   */
  T removePassenger(KelpEntity<?> passenger);

  /**
   * Checks if the current entity is empty. An empty
   * entity is an entity which currently has no passengers.
   *
   * @return {@code true} if the entity is empty.
   */
  boolean isEmpty();

  /**
   * Checks if the current entity has any passengers.
   *
   * @return {@code true} if there are any passengers.
   */
  default boolean hasAnyPassengers() {
    return !isEmpty();
  }

  /**
   * Ejects any passenger currently riding on the
   * given entity.
   */
  T ejectPassengers();

  /**
   * Sets the current fall distance of the current entity.
   *
   * The fall distance is the distance of blocks in the y-axis
   * the player is falling down. The higher the fall distance
   * the higher the fall damage. If the player is currently on
   * ground, the fall distance will be 0.
   *
   * @param fallDistance  The new fall distance you want to set.
   */
  T setFallDistance(float fallDistance);

  /**
   * Gets the current fall distance of the current entity.
   *
   * The fall distance is the distance of blocks in the y-axis
   * the player is falling down. The higher the fall distance
   * the higher the fall damage. If the player is currently on
   * ground, the fall distance will be 0.
   *
   * @return The current fall distance of the entity.
   */
  float getFallDistance();

  /**
   * Returns a unique as well as persistent id for
   * the given entity. For players, the player UUID will
   * be returned.
   *
   * @return The UUID of the given entity.
   */
  UUID getUUID();

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @return The age of the entity.
   */
  int getTicksLived();

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @param ticksLived The amount of ticks you want to set.
   *                   May not be less than one tick.
   */
  T setTicksLived(int ticksLived);

  /**
   * Returns true if the entity is currently inside a vehicle.
   *
   * @return {@code true} if the entity is inside a vehicle.
   */
  boolean isInsideVehicle();

  /**
   * Makes the entity leave its current vehicle.
   */
  T leaveVehicle();

  /**
   * Gets the current vehicle of the given entity.
   *
   * @return  The vehicle of the entity. If the entity
   *          has no vehicle, null will be returned.
   */
  KelpEntity<?> getVehicle();

  boolean isGlowing();

  T setGlowing(boolean glowing);

  /**
   * Sets the custom name of an entity visible or invisible
   * for the clients.
   *
   * @param visible  {@code true} if it should be visible
   *                 {@code false} if it should not be visible.
   */
  T customNameVisible(boolean visible);

  T customName(String customName);

  /**
   * Checks if the custom name of the given entity is
   * currently visible.
   *
   * @return {@code true} if the custom name is visible.
   */
  boolean isCustomNameVisible();

  /**
   * Gets the last damage cause. If the entity has not been damaged
   * so far, it will return null.
   *
   * @return  The last damage event instance containing the last
   *          damage cause.
   */
  EntityDamageEvent getLastDamageCause();

  /**
   * Sets the last damage cause the entity has suffered.
   *
   * @param damageCause   The instance of the last entity damage event containing
   *                      the last damage cause.
   */
  void setLastDamageCause(EntityDamageEvent damageCause);

  /**
   * Gets all entities within the given radius centered around
   * the given entity.
   *
   * @param radiusX the radius for the x axis
   *                (1/2 the size of the box along x axis)
   * @param radiusY the radius for the y axis
   *                (1/2 the size of the box along y axis)
   * @param radiusZ the radius for the z axis
   *                (1/2 the size of the box along z axis)
   * @return A list of all nearby entities within the given radius.
   */
  List<KelpEntity<?>> getNearbyEntities(double radiusX, double radiusY, double radiusZ);

  default List<KelpEntity<?>> getNearbyEntities(double radius) {
    return getNearbyEntities(radius, radius, radius);
  }

  /**
   * Checks whether the entity obeys the laws of gravity.
   * If this is {@code true}, an entity can fall down when
   * it is in the air, if {@code false} it can not.
   *
   * This can be used if you want to spawn an entity that
   * sticks to its position no matter if spawned in the sky
   * or on the ground.
   *
   * @return {@code true} if the entity has gravity.
   */
  boolean hasGravity();

  /**
   * Enables or disables gravity based on the given parameter.
   * If gravity is disabled, an entity won't fall down if it is
   * spawned in the sky. If you enable it, the entity will behave
   * normally, which is the default setting.
   *
   * @param gravity {@code true} if you want to enable gravity,
   *                {@code false} to disable.
   * @return An instance of the current entity for fluent builder design.
   */
  T setGravity(boolean gravity);

}
