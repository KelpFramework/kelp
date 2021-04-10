package de.pxav.kelp.core.entity;

import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpEntity {

  private Object minecraftEntity;
  private KelpEntityType entityType;
  private Location initialLocation;
  private int entityId;
  private EntityVersionTemplate entityVersionTemplate;

  public KelpEntity(Object minecraftEntity,
                    KelpEntityType entityType,
                    Location initialLocation,
                    int entityId,
                    EntityVersionTemplate entityVersionTemplate) {
    this.minecraftEntity = minecraftEntity;
    this.entityType = entityType;
    this.initialLocation = initialLocation;
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

  public Location getInitialBukkitLocation() {
    return initialLocation;
  }

  public KelpLocation getInitialLocation() {
    return KelpLocation.from(initialLocation);
  }

  public KelpEntity initialLocation(KelpLocation currentLocation) {
    this.initialLocation = currentLocation.getBukkitLocation();
    return this;
  }

  public KelpEntity initialLocation(Location location) {
    this.initialLocation = location;
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

  public KelpEntity spawn() {
    entityVersionTemplate.spawnEntity(this);
    return this;
  }

  /**
   * Converts the current {@code KelpEntity} to a normal
   * bukkit entity.
   *
   * @return The bukkit entity of the current KelpEntity.
   */
  public Entity toBukkitEntity() {
    return entityVersionTemplate.toBukkitEntity(this.minecraftEntity);
  }

  /**
   * Gets the current entity's location in the world.
   *
   * @return The current location of the current entity.
   */
  public KelpLocation getLocation() {
    return entityVersionTemplate.getLocation(toBukkitEntity());
  }

  /**
   * Sets the entity's velocity to the given vector.
   *
   * @param vector The vector of the velocity you want to set.
   * @see Vector
   */
  public KelpEntity setVelocity(Vector vector) {
    entityVersionTemplate.setVelocity(toBukkitEntity(), vector);
    return this;
  }

  /**
   * Gets the velocity of the desired entity.
   *
   * @return The velocity of the given entity.
   * @see Vector
   */
  public Vector getVelocity() {
    return entityVersionTemplate.getVelocity(toBukkitEntity());
  }

  /**
   * Gets the height of an entity. In older versions
   * this property was called 'length' of an entity.
   *
   * @return The height of the given entity.
   */
  public double getEntityHeight() {
    return entityVersionTemplate.getHeight(toBukkitEntity());
  }

  /**
   * Gets the width of the given entity.
   *
   * @return The entity's width.
   */
  public double getEntityWidth() {
    return entityVersionTemplate.getWidth(toBukkitEntity());
  }

  /**
   * Checks if the entity is currently on the ground.
   *
   * @return {@code true} if the entity is currently on ground.
   */
  public boolean isOnGround() {
    return entityVersionTemplate.isOnGround(toBukkitEntity());
  }

  /**
   * Gets the current world of the entity.
   *
   * @return The world where the entity is currently located.
   */
  public KelpWorld getWorld() {
    return KelpWorld.from(getLocation().getWorldName());
  }

  /**
   * Sets the rotation of the given entity. This does not
   * affect the location x, y and z axes.
   *
   * @param yaw     The yaw value of the desired rotation.
   * @param pitch   The pitch value of the desired rotation.
   */
  public KelpEntity setRotation(float yaw, float pitch) {
    entityVersionTemplate.setRotation(toBukkitEntity(), yaw, pitch);
    return this;
  }

  /**
   * Teleports the entity to the given location.
   *
   * @param to The location you want the entity to be teleported to.
   */
  public KelpEntity teleport(KelpLocation to) {
    entityVersionTemplate.teleport(toBukkitEntity(), to, PlayerTeleportEvent.TeleportCause.PLUGIN);
    return this;
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
  public KelpEntity teleport(double x, double y, double z) {
    KelpLocation to = KelpLocation.from(getWorld(), x, y, z);
    teleport(to);
    return this;
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
  public KelpEntity teleport(double x, double y, double z, float yaw, float pitch) {
    KelpLocation to = KelpLocation.from(getWorld(), x, y, z, yaw, pitch);
    teleport(to);
    return this;
  }

  /**
   * Teleports the entity to the location at the given
   * coordinates.
   *
   * @param world The world where the entity should be teleported to.
   * @param x     The exact value of the location's x axis.
   * @param y     The exact value of the location's y axis.
   * @param z     The exact value of the location's z axis.
   * @param yaw   The yaw rotation of the entity.
   * @param pitch The location's pitch
   */
  public KelpEntity teleport(KelpWorld world, double x, double y, double z, float yaw, float pitch) {
    KelpLocation to = KelpLocation.from(world, x, y, z, yaw, pitch);
    teleport(to);
    return this;
  }

  /**
   * Teleports the entity to the location at the given
   * coordinates.
   *
   * @param world The world where the entity should be teleported to.
   * @param x     The exact value of the location's x axis.
   * @param y     The exact value of the location's y axis.
   * @param z     The exact value of the location's z axis.
   */
  public KelpEntity teleport(KelpWorld world, double x, double y, double z) {
    KelpLocation to = KelpLocation.from(world, x, y, z);
    teleport(to);
    return this;
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
  public KelpEntity teleport(String worldName, double x, double y, double z, float yaw, float pitch) {
    KelpLocation to = KelpLocation.from(worldName, x, y, z, yaw, pitch);
    teleport(to);
    return this;
  }

  /**
   * Teleports the entity to the location at the given
   * coordinates.
   *
   * @param worldName The name of the world where the entity should be teleported to.
   * @param x         The exact value of the location's x axis.
   * @param y         The exact value of the location's y axis.
   * @param z         The exact value of the location's z axis.
   */
  public KelpEntity teleport(String worldName, double x, double y, double z) {
    KelpLocation to = KelpLocation.from(worldName, x, y, z);
    teleport(to);
    return this;
  }

  /**
   * Gets the id of the given entity.
   *
   * The entity id is a unique integer id, which is given
   * to each entity when it is created by the server. It is
   * used to identify entities during the server runtime.
   *
   * In contrast to the other {@code #getEntityId} method,
   * this method uses the method provided by bukkit and not
   * the Kelp-internal id. However, in most server versions these
   * ids should be the same as an entity id is immutable
   * for the same entity.
   *
   * @return the entity's id provided by bukkit.
   */
  public int getEntityIdBukkit() {
    return entityVersionTemplate.getEntityId(toBukkitEntity());
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
  public int getFireTicks() {
    return entityVersionTemplate.getFireTicks(toBukkitEntity());
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
   * @param fireTicks The amount of fire ticks you want to set.
   */
  public KelpEntity setFireTicks(int fireTicks) {
    entityVersionTemplate.setFireTicks(toBukkitEntity(), fireTicks);
    return this;
  }

  /**
   * Gets the amount of the maximum fire ticks of the given entity.
   *
   * @return The amount of maximum fire ticks of the given entity.
   */
  public int getMaxFireTicks() {
    return entityVersionTemplate.getMaxFireTicks(toBukkitEntity());
  }

  /**
   * Sets the amount of the maximum fire ticks of the given entity.
   *
   * @param maxFireTicks  The amount of maximum fire ticks to set.
   */
  public KelpEntity setMaxFireTicks(int maxFireTicks) {
    entityVersionTemplate.setMaxFireTicks(toBukkitEntity(), maxFireTicks);
    return this;
  }

  /**
   * Removes the entity from the world.
   */
  public KelpEntity remove() {
    entityVersionTemplate.remove(toBukkitEntity());
    return this;
  }

  /**
   * Checks if the given entity is dead.
   *
   * @return {@code true} if the entity is dead.
   */
  public boolean isDead() {
    return entityVersionTemplate.isDead(toBukkitEntity());
  }

  /**
   * Checks if the entity is valid.
   *
   * Valid means the entity has to be alive and currently
   * spawned in a world.
   *
   * @return {@code true} if the entity is 'valid'.
   */
  public boolean isValid() {
    return entityVersionTemplate.isValid(toBukkitEntity());
  }

  /**
   * Gets the current server containing the entity.
   *
   * @return The server instance.
   */
  public Server getServer() {
    return entityVersionTemplate.getServer(toBukkitEntity());
  }

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
  public Entity getPassenger() {
    List<Entity> passengers = entityVersionTemplate.getPassengers(toBukkitEntity());
    if (passengers != null && !passengers.isEmpty()) {
      return passengers.get(0);
    }
    return null;
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
   * @return  A list of all passengers of the given entity.
   *          {@code null} if there are no passengers.
   */
  public List<Entity> getPassengers() {
    return entityVersionTemplate.getPassengers(toBukkitEntity());
  }

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
  public KelpEntity addPassenger(Entity passenger) {
    entityVersionTemplate.addPassenger(toBukkitEntity(), passenger);
    return this;
  }

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
  public KelpEntity addPassenger(List<Entity> passengers) {
    passengers.forEach(this::addPassenger);
    return this;
  }

  /**
   * Removes a passenger from the given entity.
   *
   * @param toRemove The passenger you want to remove.
   */
  public KelpEntity removePassenger(Entity toRemove) {
    entityVersionTemplate.removePassenger(toBukkitEntity(), toRemove);
    return this;
  }

  /**
   * Checks if the current entity is empty. An empty
   * entity is an entity which currently has no passengers.
   *
   * @return {@code true} if the entity is empty.
   */
  public boolean isEmpty() {
    return entityVersionTemplate.isEmpty(toBukkitEntity());
  }

  /**
   * Checks if the current entity has any passengers.
   *
   * @return {@code true} if there are any passengers.
   */
  public boolean hasAnyPassengers() {
    return !entityVersionTemplate.isEmpty(toBukkitEntity());
  }

  /**
   * Ejects any passenger currently riding on the
   * given entity.
   */
  public KelpEntity ejectPassengers() {
    entityVersionTemplate.eject(toBukkitEntity());
    return this;
  }

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
  public KelpEntity setFallDistance(float fallDistance) {
    entityVersionTemplate.setFallDistance(toBukkitEntity(), fallDistance);
    return this;
  }

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
  public float getFallDistance() {
    return entityVersionTemplate.getFallDistance(toBukkitEntity());
  }

  /**
   * Returns a unique as well as persistent id for
   * the given entity. For players, the player UUID will
   * be returned.
   *
   * @return The UUID of the given entity.
   */
  public UUID getUUID() {
    return entityVersionTemplate.getUniqueId(toBukkitEntity());
  }

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @return The age of the entity.
   */
  public int getTicksLived() {
    return entityVersionTemplate.getTicksLived(toBukkitEntity());
  }

  /**
   * Gets the amount of ticks the entity has been alive.
   * This number is equivalent to the 'age' property in entities.
   *
   * @param ticksLived The amount of ticks you want to set.
   *                   May not be less than one tick.
   */
  public KelpEntity setTicksLived(int ticksLived) {
    entityVersionTemplate.setTicksLived(toBukkitEntity(), ticksLived);
    return this;
  }

  /**
   * Returns true if the entity is currently inside a vehicle.
   *
   * @return {@code true} if the entity is inside a vehicle.
   */
  public boolean isInsideVehicle() {
    return entityVersionTemplate.isInsideVehicle(toBukkitEntity());
  }

  /**
   * Makes the entity leave its current vehicle.
   */
  public KelpEntity leaveVehicle() {
    entityVersionTemplate.leaveVehicle(toBukkitEntity());
    return this;
  }

  /**
   * Gets the current vehicle of the given entity.
   *
   * @return  The vehicle of the entity. If the entity
   *          has no vehicle, null will be returned.
   */
  public Entity getVehicle() {
    return entityVersionTemplate.getVehicle(toBukkitEntity());
  }

}
