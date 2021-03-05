package de.pxav.kelp.core.world;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.world.util.CardinalDirection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.io.Serializable;

/**
 * A {@code KelpLocation} is a point in a 3D-world. It basically is a replacement
 * for the default {@link Location} of bukkit with some additional useful methods.
 *
 * You can easily convert those location types:
 *   - From a bukkit location to a KelpLocation: {@link #from(Location)}
 *   - From a KelpLocation to a bukkit location: {@link #getBukkitLocation()}
 *
 * @author pxav
 */
public class KelpLocation implements Serializable, Cloneable {

  // name of the location's world
  //
  // There is no need to store the entire KelpWorld object,
  // which is why it's not saved for performance reasons.
  private String worldName = "world";

  // axis of the location
  private double x = 0.0D;
  private double y = 0.0D;
  private double z = 0.0D;
  private float yaw = 0.0F;
  private float pitch = 0.0F;

  public static KelpLocation from(Location location) {
    KelpLocation kelpLocation = new KelpLocation();

    kelpLocation.setX(location.getX());
    kelpLocation.setY(location.getY());
    kelpLocation.setZ(location.getZ());
    kelpLocation.setYaw(location.getYaw());
    kelpLocation.setPitch(location.getPitch());

    kelpLocation.setWorldName(location.getWorld().getName());

    return kelpLocation;
  }

  public static KelpLocation from(String worldName, double x, double y, double z) {
    Preconditions.checkNotNull(worldName);
    KelpLocation location = new KelpLocation();

    location.setX(x);
    location.setY(y);
    location.setZ(z);
    location.setWorldName(worldName);

    return location;
  }

  public static KelpLocation from(String worldName, double x, double y, double z, float yaw, float pitch) {
    Preconditions.checkNotNull(worldName);
    KelpLocation location = new KelpLocation();

    location.setX(x);
    location.setY(y);
    location.setZ(z);
    location.setYaw(yaw);
    location.setPitch(pitch);
    location.setWorldName(worldName);

    return location;
  }

  public static KelpLocation from(KelpWorld world, double x, double y, double z, float yaw, float pitch) {
    return from(world.getName(), x, y, z, yaw, pitch);
  }

  public static KelpLocation from(KelpWorld world, double x, double y, double z) {
    return from(world.getName(), x, y, z);
  }

  public static KelpLocation from(World world, double x, double y, double z, float yaw, float pitch) {
    return from(world.getName(), x, y, z, yaw, pitch);
  }

  public static KelpLocation from(World world, double x, double y, double z) {
    return from(world.getName(), x, y, z);
  }

  public static KelpLocation create() {
    return new KelpLocation();
  }

  /**
   * Gets the name of the world this location is valid for.
   *
   * @return The name of the world of this location.
   */
  public String getWorldName() {
    return worldName;
  }

  /**
   * Sets the name of the world of this location.
   * Note that changing a location's world while keeping
   * the same coordinates, might lead to unintended world generation
   * when working with the location. This might cause lag to the server.
   *
   * @param worldName The name of the new world for this location.
   * @return Instance of the current location
   */
  public KelpLocation setWorldName(String worldName) {
    this.worldName = worldName;
    return this;
  }

  /**
   * Gets the locations exact value on the world's X-axis.
   *
   * @return The locations value on the X-axis.
   */
  public double getX() {
    return x;
  }

  /**
   * Sets the location's exact value on the world's X-axis.
   *
   * @param x The new value on the world's X-axis for the location.
   * @return Instance of the current location
   */
  public KelpLocation setX(double x) {
    this.x = x;
    return this;
  }

  /**
   * Gets the locations exact value on the world's Y-axis.
   *
   * @return The locations value on the Y-axis.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets the location's exact value on the world's Y-axis (height).
   *
   * @param y The new value on the world's Y-axis for the location.
   * @return Instance of the current location
   */
  public KelpLocation setY(double y) {
    this.y = y;
    return this;
  }

  /**
   * Gets the locations exact value on the world's Z-axis.
   *
   * @return The locations value on the Z-axis.
   */
  public double getZ() {
    return z;
  }

  /**
   * Sets the location's exact value on the world's Y-axis.
   *
   * @param z The new value on the world's Y-axis for the location.
   * @return Instance of the current location
   */
  public KelpLocation setZ(double z) {
    this.z = z;
    return this;
  }

  /**
   * Gets the locations yaw. The yaw is part of the facing.
   * If you are using this location as a block location, this value
   * is likely {@code 0}, but if you are using it with a {@link de.pxav.kelp.core.player.KelpPlayer}
   * or any other entity, this indicates the angle the player is looking at left and right
   * (rotation around the y-axis).
   *
   * If the player is looking straight west, it is {@code 0F} and towards west it rises up to
   * {@code 180F} in the north direction and up to {@code -180F} if you rotate in the other
   * direction.
   *
   * You can calculate the yaw from a {@link Vector} if you need to using {@link #setDirection(Vector)},
   * which will also set the {@code pitch}.
   *
   * @return The locations yaw (rotation around the y-axis).
   */
  public float getYaw() {
    return yaw;
  }

  /**
   * Sets the location's yaw (rotation around the y-Axis).
   *
   * @param yaw The new yaw to set for this location.
   * @return Instance of the current location
   */
  public KelpLocation setYaw(float yaw) {
    this.yaw = yaw;
    return this;
  }

  /**
   * Gets the pitch for this location. The pitch is the angle in which a player is looking
   * up or down. If a player is looking right into the sky, his pitch is {@code -90F}, if
   * they are looking at the ground their pitch is {@code 90F} and looking straight on stands
   * for {@code 0F}. Block locations usually don't have a pitch (= 0F).
   *
   * You can calculate the pitch from a {@link Vector} if you need to using {@link #setDirection(Vector)},
   * which will also set the {@code yaw}.
   *
   * @return The current location's pitch.
   */
  public float getPitch() {
    return pitch;
  }

  /**
   * Sets the locations pitch. This is the angle in which the entity is facing up or down.
   *
   * @param pitch The new pitch for the location.
   * @return Instance of the current location
   */
  public KelpLocation setPitch(float pitch) {
    this.pitch = pitch;
    return this;
  }

  /**
   * Gets the block X-location, which is a rounded value of the exact x-axis.
   * Instead of {@code -456.223183497}, this will simply return {@code -456}
   * as any more detail would be unnecessary when working with simple blocks.
   *
   * @return The block x-axis value of this location.
   */
  public int getBlockX() {
    return Location.locToBlock(this.x);
  }

  /**
   * Gets the block Y-location, which is a rounded value of the exact y-axis.
   * Instead of {@code -87.223183497}, this will simply return {@code 87}
   * as any more detail would be unnecessary when working with simple blocks.
   *
   * @return The block y-axis value of this location.
   */
  public int getBlockY() {
    return Location.locToBlock(this.y);
  }

  /**
   * Gets the block Z-location, which is a rounded value of the exact Z-axis.
   * Instead of {@code -456.223183497}, this will simply return {@code -456}
   * as any more detail would be unnecessary when working with simple blocks.
   *
   * @return The block Z-axis value of this location.
   */
  public int getBlockZ() {
    return Location.locToBlock(this.z);
  }

  /**
   * Sets the block X-value of this location.
   *
   * This will overwrite the exact x-value as well,
   * so be careful if you still need the exact value.
   *
   * @param x The new block x-value of this location.
   * @return Instance of the current location
   */
  public KelpLocation setBlockX(double x) {
    this.x = Location.locToBlock(this.x);
    return this;
  }

  /**
   * Sets the block Y-value of this location.
   *
   * This will overwrite the exact x-value as well,
   * so be careful if you still need the exact value.
   *
   * @param y The new block y-value of this location.
   * @return Instance of the current location
   */
  public KelpLocation setBlockY(double y) {
    this.y = Location.locToBlock(this.y);
    return this;
  }

  /**
   * Sets the block Z-value of this location.
   *
   * This will overwrite the exact x-value as well,
   * so be careful if you still need the exact value.
   *
   * @param z The new block z-value of this location.
   * @return Instance of the current location
   */
  public KelpLocation setBlockZ(double z) {
    this.z = Location.locToBlock(this.z);
    return this;
  }

  /**
   * Gets the {@link KelpChunk} at the current Location.
   *
   * @return The chunk at the current location.
   */
  public KelpChunk getChunk() {
    return KelpChunk.from(getBukkitLocation().getChunk());
  }

  /**
   * Gets the {@link KelpBlock} at the current Location.
   *
   * @return The block at the current location.
   */
  public KelpBlock getBlock() {
    return KelpBlock.from(getBukkitLocation().getBlock());
  }

  /**
   * Adds the given values to the current axis values.
   *
   * Note that this method will overwrite the old values.
   * If you still need them, {@link #clone()} the instance before
   * and then add the values.
   *
   * @param x The x-coordinate to add.
   * @param y The x-coordinate to add.
   * @param z The x-coordinate to add.
   * @return The current location object with the added values.
   */
  public KelpLocation add(double x, double y, double z) {
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
  }

  /**
   * Adds the given value to all coordinate values of this location.
   * This means that all axis {@code x, y and z} will grow
   * by {@code value}.
   *
   * @param value The value to add to all location coordinates.
   * @return The current location object with the added values.
   */
  public KelpLocation add(double value) {
    this.x += value;
    this.y += value;
    this.z += value;
    return this;
  }

  /**
   * Only adds the x-coordinate of this location.
   * Other coordinates are not affected by this method.
   *
   * Note that this method will overwrite the old the value.
   * If you still need them, {@link #clone()} the instance before
   * and then add the values.
   *
   * @param x The x-coordinate to add.
   * @return The current location object with the added value.
   */
  public KelpLocation addX(double x) {
    this.x += x;
    return this;
  }

  /**
   * Only adds the y-coordinate of this location.
   * Other coordinates are not affected by this method.
   *
   * Note that this method will overwrite the old the value.
   * If you still need them, {@link #clone()} the instance before
   * and then add the values.
   *
   * @param y The y-coordinate to add.
   * @return The current location object with the added value.
   */
  public KelpLocation addY(double y) {
    this.y += y;
    return this;
  }

  /**
   * Only adds the z-coordinate of this location.
   * Other coordinates are not affected by this method.
   *
   * Note that this method will overwrite the old the value.
   * If you still need them, {@link #clone()} the instance before
   * and then add the values.
   *
   * @param z The z-coordinate to add.
   * @return The current location object with the added value.
   */
  public KelpLocation addZ(double z) {
    this.z += z;
    return this;
  }

  /**
   * Subtracts the given values from the current axis values.
   *
   * Note that this method will overwrite the old values.
   * If you still need them, {@link #clone()} the instance before
   * and then subtract the values.
   *
   * @param x The x-coordinate to subtract.
   * @param y The x-coordinate to subtract.
   * @param z The x-coordinate to subtract.
   * @return The current location object with the subtracted values.
   */
  public KelpLocation subtract(double x, double y, double z) {
    this.x -= x;
    this.y -= y;
    this.z -= z;
    return this;
  }

  /**
   * Subtracts the given value from all the locations coordinates.
   * So all axis {@code x, y and z} will be smaller by {@code value}.
   *
   * @param value The value to subtract from all coordinate values.
   * @return The current location object with the subtracted values.
   */
  public KelpLocation subtract(double value) {
    this.x -= value;
    this.y -= value;
    this.z -= value;
    return this;
  }

  /**
   * Only subtracts the x-coordinate of this location.
   * Other values are not affected by this method.
   *
   * Note that this method will overwrite the old value.
   * If you still need it, {@link #clone()} the instance before
   * and then subtract the value.
   *
   * @param x The x-coordinate to subtract.
   * @return The current location object with the subtracted value.
   */
  public KelpLocation subtractX(double x) {
    this.x -= x;
    return this;
  }

  /**
   * Only subtracts the y-coordinate of this location.
   * Other values are not affected by this method.
   *
   * Note that this method will overwrite the old value.
   * If you still need it, {@link #clone()} the instance before
   * and then subtract the value.
   *
   * @param y The y-coordinate to subtract.
   * @return The current location object with the subtracted value.
   */
  public KelpLocation subtractY(double y) {
    this.y -= y;
    return this;
  }

  /**
   * Only subtracts the z-coordinate of this location.
   * Other values are not affected by this method.
   *
   * Note that this method will overwrite the old value.
   * If you still need it, {@link #clone()} the instance before
   * and then subtract the value.
   *
   * @param z The z-coordinate to subtract.
   * @return The current location object with the subtracted value.
   */
  public KelpLocation subtractZ(double z) {
    this.z -= z;
    return this;
  }

  /**
   * Converts the locations {@code yaw} and {@code pitch} to a {@link Vector}.
   * This vector will point into the facing of the current location and will
   * have the default length of {@code 1}.
   *
   * @return A vector pointing in this location's facing.
   */
  public Vector getDirection() {
    Vector vector = new Vector();

    double rotY = this.getPitch();
    vector.setY(-Math.sin(Math.toRadians(rotY)));

    double xz = Math.cos(Math.toRadians(rotY));
    double rotX = this.getYaw();
    vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
    vector.setZ(xz * Math.cos(Math.toRadians(rotX)));

    return vector;
  }

  /**
   * Gets the location in front of this location. The front is defined
   * by the location's facing (yaw & pitch), so doing that with a block location
   * does not make sense. Use {@link KelpBlock#getFrontBlock(Vector)} for this instead.
   *
   * As this method uses the pitch as well the front location returned by this method
   * might be below this location if the facing is looking down or above this location
   * if the facing up. If you want to get the front location on the same y-level,
   * use one of the 'keep-height' methods such as {@link #getFrontLocationKeepHeight()}.
   *
   * @return The location in front of this location.
   */
  public KelpLocation getFrontLookLocation() {
    return this.clone().add(getDirection());
  }

  /**
   * Gets the location behind this location. The back is defined
   * by the location's facing (yaw & pitch), so doing that with a block location
   * does not make sense. Use {@link KelpBlock#getBackBlock(Vector)} for this instead.
   *
   * As this method uses the pitch as well the back location returned by this method
   * might be below this location if the facing is looking up or above this location
   * if the facing down. If you want to get the back location on the same y-level,
   * use one of the 'keep-height' methods such as {@link #getBackLocationKeepHeight()}.
   *
   * @return The location in behind this location.
   */
  public KelpLocation getBackLookLocation() {
    return this.clone().subtract(getDirection());
  }

  /**
   * Gets the location behind this location. In this case, only the location's
   * yaw is used for calculation, which means it does not matter if the player
   * is currently looking up or down. The location will keep on the same y-level.
   *
   * If you want the pitch of the player to be respected as well in the calculation,
   * then use {@link #getBackLookLocation()}.
   *
   * @return The location in behind this location on the same y-level.
   */
  public KelpLocation getBackLocationKeepHeight() {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.add(Math.sin(yawRadians), 0, -Math.cos(yawRadians));
  }

  /**
   * Gets the location in front of this location. In this case, only the location's
   * yaw is used for calculation, which means it does not matter if the player
   * is currently looking up or down. The location will keep on the same y-level.
   *
   * If you want the pitch of the player to be respected as well in the calculation,
   * then use {@link #getFrontLookLocation()}.
   *
   * @return The location in in front of this location on the same y-level.
   */
  public KelpLocation getFrontLocationKeepHeight() {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.subtract(Math.sin(yawRadians), 0, -Math.cos(yawRadians));
  }

  /**
   * Gets the location behind this location with the given distance. In this case, only the location's
   * yaw is used for calculation, which means it does not matter if the player
   * is currently looking up or down. The location will keep on the same y-level.
   *
   * While {@link #getBackLocationKeepHeight()} will simply take the location one block behind
   * this location, you can pass the distance from the original location here as well.
   * {@code 3} for example will get the location three blocks behind this location.
   *
   * If you want the pitch of the player to be respected as well in the calculation,
   * then use {@link #getBackLookLocation()}.
   *
   * @param distance How many blocks behind this location should the new location be?
   * @return The location in behind this location on the same y-level.
   */
  public KelpLocation getBackLocationKeepHeight(double distance) {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.add(distance * Math.sin(yawRadians), 0, -distance * Math.cos(yawRadians));
  }

  /**
   * Gets the location in front of this location with the given distance. In this case, only the location's
   * yaw is used for calculation, which means it does not matter if the player
   * is currently looking up or down. The location will keep on the same y-level.
   *
   * While {@link #getFrontLocationKeepHeight()} will simply take the location one block in front of
   * this location, you can pass the distance from the original location here as well.
   * {@code 3} for example will get the location three blocks in front of this location.
   *
   * If you want the pitch of the player to be respected as well in the calculation,
   * then use {@link #getFrontLookLocation()} ()}.
   *
   * @param distance How many blocks in front of this location should the new location be?
   * @return The location in in front of this location on the same y-level.
   */
  public KelpLocation getFrontLocationKeepHeight(double distance) {
    double yawRadians = Math.PI * getYaw() / 180;
    return this.subtract(distance * Math.sin(yawRadians), 0, -distance * Math.cos(yawRadians));
  }

  /**
   * Gets the {@link CardinalDirection} of this location's facing. For this, only the location's
   * yaw will be used. For more information about carinal direction, check out the documentation
   * of {@link CardinalDirection}
   *
   * @return The cardinal direction based on this location's facing.
   */
  public CardinalDirection getCardinalDirection() {
    double rotation = (getYaw() - 180) % 360;

    if (rotation < 0) {
      rotation += 360.0;
    }

    if (0 <= rotation && rotation < 22.5) {
      return CardinalDirection.NORTH;
    } else if (22.5 <= rotation && rotation < 67.5) {
      return CardinalDirection.NORTH_EAST;
    } else if (67.5 <= rotation && rotation < 112.5) {
      return CardinalDirection.EAST;
    } else if (112.5 <= rotation && rotation < 157.5) {
      return CardinalDirection.SOUTH_EAST;
    } else if (157.5 <= rotation && rotation < 202.5) {
      return CardinalDirection.SOUTH;
    } else if (202.5 <= rotation && rotation < 247.5) {
      return CardinalDirection.SOUTH_WEST;
    } else if (247.5 <= rotation && rotation < 292.5) {
      return CardinalDirection.WEST;
    } else if (292.5 <= rotation && rotation < 337.5) {
      return CardinalDirection.NORTH_WEST;
    } else if (337.5 <= rotation && rotation < 360.0) {
      return CardinalDirection.NORTH;
    } else {
      return null;
    }
  }

  /**
   * Sets the yaw and pitch value of this location based on any
   * vector. The length of the vector is ignored for this operation.
   *
   * @param vector The vector to calculate the yaw and pitch value of.
   * @return The current location object with the new yaw and pitch.
   */
  public KelpLocation setDirection(Vector vector) {
    double _2PI = 6.283185307179586D;
    double x = vector.getX();
    double z = vector.getZ();
    if (x == 0.0D && z == 0.0D) {
      this.pitch = (float)(vector.getY() > 0.0D ? -90 : 90);
    } else {
      double theta = Math.atan2(-x, z);
      this.yaw = (float)Math.toDegrees((theta + _2PI) % _2PI);
      double x2 = NumberConversions.square(x);
      double z2 = NumberConversions.square(z);
      double xz = Math.sqrt(x2 + z2);
      this.pitch = (float)Math.toDegrees(Math.atan(-vector.getY() / xz));
    }
    return this;
  }

  /**
   * Creates a new {@link Vector} representing this location.
   *
   * @return A new vectors containing the coordinates of this location.
   */
  public Vector toVector() {
    return new Vector(this.x, this.y, this.z);
  }

  /**
   * Adds another location to this location. This will add all axis
   * of the locations but not the facing.
   *
   * @param location The location to add to this location.
   * @throws IllegalArgumentException if the worlds of the both locations are not the same.
   * @return The new location with the added values.
   */
  public KelpLocation add(KelpLocation location) {
    Preconditions.checkNotNull(location, "Cannot add 'null' to a KelpLocation");
    if (!location.getWorldName().equalsIgnoreCase(getWorldName())) {
      throw new IllegalArgumentException("Cannot add two locations of differing worlds!");
    }

    this.x += location.getX();
    this.y += location.getY();
    this.z += location.getZ();
    return this;
  }

  /**
   * Subtracts another location from this location. This will subtract all axis
   * of the locations but not the facing.
   *
   * @param location The location to subtract from this location.
   * @throws IllegalArgumentException if the worlds of the both locations are not the same.
   * @return The new location with the subtracted values.
   */
  public KelpLocation subtract(KelpLocation location) {
    Preconditions.checkNotNull(location, "Cannot subtract 'null' from a KelpLocation");
    if (!location.getWorldName().equalsIgnoreCase(getWorldName())) {
      throw new IllegalArgumentException("Cannot subtract two locations of differing worlds!");
    }

    this.x -= location.getX();
    this.y -= location.getY();
    this.z -= location.getZ();
    return this;
  }

  /**
   * Adds any vector to this location.
   *
   * @param vector Vector to add the axis of.
   * @return The new location with the added values.
   */
  public KelpLocation add(Vector vector) {
    this.x += vector.getX();
    this.y += vector.getY();
    this.z += vector.getZ();
    return this;
  }

  /**
   * Subtracts any vector from this location.
   *
   * @param vector Vector to subtract the axis from.
   * @return The new location with the subtracted values.
   */
  public KelpLocation subtract(Vector vector) {
    this.x -= vector.getX();
    this.y -= vector.getY();
    this.z -= vector.getZ();
    return this;
  }

  /**
   * Returns the magnitude of this location defined as {@code squareRootOf(x^2 + y^2 + z^2)}.
   * The magnitude can be used to compare two locations with each other. A larger magnitude means
   * the location is more right/up in the grid. As this method uses performance heavy square root
   * operations, it is recommended to use {@link #magnitudeSquared()} instead of this method where possible.
   *
   * @return The location's magnitude.
   */
  public double magnitude() {
    return Math.sqrt(this.magnitudeSquared());
  }

  /**
   * Returns the squared magnitude of this location defined as {@code (x^2 + y^2 + z^2)}.
   * The magnitude can be used to compare two locations with each other. A larger magnitude means
   * the location is more right/up in the grid.
   *
   * @return The location's magnitude value squared.
   */
  public double magnitudeSquared() {
    return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
  }

  /**
   * Calculates the squared distance to another location in blocks^2.
   *
   * @param to The location to calculate the distance to.
   * @return The squared distance between the blocks.
   */
  public double distanceSquared(KelpLocation to) {
    Preconditions.checkNotNull(to, "Cannot measure distance between 'null' and a KelpLocation");
    if (!to.getWorldName().equalsIgnoreCase(worldName)) {
      throw new IllegalArgumentException("Cannot measure distance between KelpLocations from differing worlds");
    }
    return NumberConversions.square(
      this.x - to.x)
      + NumberConversions.square(this.y - to.y)
      + NumberConversions.square(this.z - to.z
    );
  }

  /**
   * Calculates the distance to another location in blocks.
   * Note that this method uses heavy square root operations to calculate
   * the distance and it is recommended to use {@link #distanceSquared(KelpLocation)}
   * where possible.
   *
   * @param to The location to measure the distance to.
   * @return The distance between the two given locations.
   */
  public double distance(KelpLocation to) {
    return Math.sqrt(this.distanceSquared(to));
  }

  /**
   * Multiplies all the location's axis with a specific
   * multiplier. This does not include yaw and pitch.
   *
   * @param multiplier The multiplier to multiply with.
   * @return The current location with the new values.
   */
  public KelpLocation multiply(double multiplier) {
    this.x *= multiplier;
    this.y *= multiplier;
    this.z *= multiplier;
    return this;
  }

  /**
   * Only multiplies the x axis with a specific multiplier.
   *
   * @param multiplier The multiplier to multiply the x-axis with.
   * @return The current location with the updated values.
   */
  public KelpLocation multiplyX(double multiplier) {
    this.x *= multiplier;
    return this;
  }

  /**
   * Only multiplies the z axis with a specific multiplier.
   *
   * @param multiplier The multiplier to multiply the z-axis with.
   * @return The current location with the updated values.
   */
  public KelpLocation multiplyZ(double multiplier) {
    this.z *= multiplier;
    return this;
  }

  /**
   * Multiplies only the x and z axis of this location with the given multiplier.
   *
   * @param multiplier The multiplier to multiply x and z with.
   * @return The current location with the updated values.
   */
  public KelpLocation multiplyXZ(double multiplier) {
    this.x *= multiplier;
    this.z *= multiplier;
    return this;
  }

  /**
   * Multiplies this location with a {@link Vector}.
   * This means the x, y, and z axis are multiplied with
   * the vector's values.
   *
   * @param multiplier The vector to multiply with.
   * @return The current location with the updated values.
   */
  public KelpLocation multiply(Vector multiplier) {
    this.x *= multiplier.getX();
    this.y *= multiplier.getY();
    this.z *= multiplier.getZ();
    return this;
  }

  /**
   * Multiplies this location with another. This means
   * the x, y and z values are multiplied with the other
   * x, y and z values.
   *
   * @param multiplier The location to multiply with.
   * @return The current location with the updated axis.
   */
  public KelpLocation multiply(KelpLocation multiplier) {
    this.x *= multiplier.getX();
    this.y *= multiplier.getY();
    this.z *= multiplier.getZ();
    return this;
  }

  /**
   * Compares the current location with the given location
   * and returns the location that is lower in the world's grid.
   * When a location is 'lower' than another it means that its
   * coordinate values (x, y, z) are smaller. The yaw and pitch
   * value is ignored in this calculation.
   *
   * @param compareTo The location to compare the current location to.
   * @return The location that is lower in the world grid.
   */
  public KelpLocation getMinimalLocation(KelpLocation compareTo) {
    double minX = Math.min(compareTo.getX(), getX());
    double minY = Math.min(compareTo.getY(), getY());
    double minZ = Math.min(compareTo.getZ(), getZ());

    return this.clone()
      .setX(minX)
      .setY(minY)
      .setZ(minZ);
  }


  /**
   * Compares the current location with the given location
   * and returns the location that is higher in the world's grid.
   * When a location is 'higher' than another it means that its
   * coordinate values (x, y, z) are bigger. The yaw and pitch
   * value is ignored in this calculation.
   *
   * @param compareTo The location to compare the current location to.
   * @return The location that is higher in the world grid.
   */
  public KelpLocation getMaximalLocation(KelpLocation compareTo) {
    double maxX = Math.max(compareTo.getX(), getX());
    double maxY = Math.max(compareTo.getY(), getY());
    double maxZ = Math.max(compareTo.getZ(), getZ());

    return this.clone()
      .setX(maxX)
      .setY(maxY)
      .setZ(maxZ);
  }

  /**
   * Zeros all axis of the location. This sets the x, y, and z
   * coordinate to {@code 0}.
   *
   * @return The new location value with all axis set to 0.
   */
  public KelpLocation zeroAxis() {
    setX(0.0D);
    setY(0.0D);
    setZ(0.0D);
    return this;
  }

  /**
   * Zeros all values of the location except the world name.
   * This sets all coordinate values (x, y, z) as well as facing
   * values to {@code 0}.
   *
   * @return The new location with all numbers set to 0.
   */
  public KelpLocation zeroAll() {
    zeroAxis();
    return zeroLook();
  }

  /**
   * Zeros the look values of the location.
   * This means the yaw and pitch are set to {@code 0F}.
   *
   * @return The new location object with the updated yaw and pitch values.
   */
  public KelpLocation zeroLook() {
    setYaw(0.0F);
    setPitch(0.0F);
    return this;
  }

  /**
   * Creates an identical copy of this location object.
   *
   * @return The new location object with the same data as this object.
   */
  @Override
  public KelpLocation clone() {
    try {
      return (KelpLocation) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Converts this {@code KelpLocation} into a default bukkit {@link Location}.
   *
   * @return The newly created bukkit location.
   */
  public Location getBukkitLocation() {
    return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
  }

}
