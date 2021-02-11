package de.pxav.kelp.core.npc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.npc.activity.NpcActivity;
import de.pxav.kelp.core.npc.version.NpcVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.Location;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * This is the most important class for developers
 * when it comes to NPC creation.
 *
 * It allows you to give properties to your NPC
 * and finally spawn it.
 *
 * @author pxav
 */
public class KelpNpc {

  private KelpPlayer player;

  private Location spawnLocation;
  private Location currentLocation;
  private KelpItem itemInHand;

  private int entityId;
  private UUID uuid;
  private String customName;
  private GameProfile gameProfile;

  private Supplier<List<String>> titles;
  private String skinSignature;
  private String skinTexture;

  // behaviour
  private boolean isSpawned = false;
  private double removeDistance;
  private boolean isBurning;
  private boolean isInvisible;

  // movement
  private boolean isSneaking;
  private boolean isSprinting;
  private boolean noClip;
  private boolean falling;
  private boolean flying;

  private boolean customNameShown = false;
  private boolean showInTab = false;
  private String tabListName;
  // armor, ...

  private Collection<NpcActivity<?>> activities;

  private KelpNpcMeta npcMeta;
  private KelpLogger logger;

  private NpcVersionTemplate npcVersionTemplate;
  private KelpNpcRepository kelpNpcRepository;

  KelpNpc(NpcVersionTemplate npcVersionTemplate,
                 KelpNpcRepository kelpNpcRepository,
                 KelpLogger logger) {
    this.npcVersionTemplate = npcVersionTemplate;
    this.kelpNpcRepository = kelpNpcRepository;
    this.logger = logger;

    this.titles = Lists::newArrayList;
    this.activities = Lists.newArrayList();
    this.removeDistance = 40;
    this.isBurning = false;
  }

  /**
   * Sets the location, where the NPC will spawn later.
   *
   * @param location The desired location
   * @return An instance of the current NPC object.
   */
  public KelpNpc spawnLocation(Location location) {
    this.spawnLocation = location;
    return this;
  }

  public KelpNpc currentLocation(Location location) {
    this.currentLocation = location;
    return this;
  }

  /**
   * Sets the item the NPC is holding in its hand.
   *
   * @param itemInHand The desired item.
   * @return An instance of the current NPC object.
   */
  public KelpNpc itemInHand(KelpItem itemInHand) {
    this.itemInHand = itemInHand;
    return this;
  }

  /**
   * Sets the custom name of the NPC. This is the
   * name which is displayed above its head.
   *
   * @param customName The custom name you want to set.
   * @return An instance of the current NPC object.
   */
  public KelpNpc customName(String customName) {
    this.customName = customName;
    return this;
  }

  /**
   * Sets the UUID of the NPC's game profile. If not set,
   * Kelp will use a random one.
   *
   * @param uuid The desired {@code UUID}.
   * @return An instance of the current NPC object.
   */
  public KelpNpc uuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  /**
   * Sets the UUID of the NPC's game profile. If not set,
   * Kelp will use a random one.
   *
   * @param uuid The desired {@code UUID} as a string.
   * @return An instance of the current NPC object.
   */
  public KelpNpc uuid(String uuid) {
    this.uuid = UUID.fromString(uuid);
    return this;
  }

  /**
   * Sets the name the NPC will have when it is shown in
   * the tab list.
   *
   * @param tabListName The desired tab list name.
   * @return An instance of the current NPC object.
   */
  public KelpNpc tabListName(String tabListName) {
    this.tabListName = tabListName;
    if (isSpawned) {
      npcVersionTemplate.updateTab(this, null);
    }
    return this;
  }

  public KelpNpc showInTab(boolean show) {
    this.showInTab = show;
    if (isSpawned) {
      npcVersionTemplate.updateTab(this, null);
    }
    return this;
  }

  /**
   * Deletes all title lines, which have been active
   * before and overwrites them with the given ones.
   *
   * Title lines are lines of text above the NPC, where
   * you can for example describe the NPC. These lines
   * are rendered per player, which means that their text
   * can be individual for every player.
   *
   * @param lines A list of all lines to set.
   * @return An instance of the current NPC object.
   */
  public KelpNpc titleLines(Supplier<List<String>> lines) {
    this.titles = lines;
    return this;
  }

  /**
   * Sets the skin texture of the NPC. A skin in minecraft
   * consists of two strings: signature and texture.
   * Both have to be set to apply all properties of the skin
   * like the cape or second layers, ...
   *
   * The skin texture of a player can be queried from the Mojang
   * API with the following URL:
   * {@code https://sessionserver.mojang.com/session/minecraft/profile/<uuid>}
   * This will return the JSON object of the player's profile/session.
   * To fetch the skin texture only, get the 0th list item from "properties"
   * with the property name "value".
   *
   * @param skinTexture The desired skin texture.
   * @return An instance of the current NPC object.
   */
  public KelpNpc skinTexture(String skinTexture) {
    this.skinTexture = skinTexture;
    return this;
  }

  /**
   * Sets the skin signature of the NPC. A skin in minecraft
   * consists of two strings: signature and texture.
   * Both have to be set to apply all properties of the skin
   * like the cape or second layers, ...
   *
   * The skin signature of a player can be queried from the Mojang
   * API with the following URL:
   * {@code https://sessionserver.mojang.com/session/minecraft/profile/<uuid>?unsigned=false}
   * This will return the JSON object of the player's profile/session.
   * To fetch the skin texture only, get the 0th list item from "properties"
   * with the property name "signature".
   *
   * @param skinSignature The desired skin signature.
   * @return An instance of the current NPC object.
   */
  public KelpNpc skinSignature(String skinSignature) {
    this.skinSignature = skinSignature;
    return this;
  }

  /**
   * Makes the NPC sprint. This becomes especially important
   * if you want to play a walk animation, because the walking
   * speed wil increase to sprinting speed and sprint particles
   * will be visible.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc sprint() {
    this.isSprinting = true;
    return this;
  }

  /**
   * Makes the NPC not sprint anymore. This will remove
   * the sprint particle animation and if the npc is walking,
   * it will be slowed down to the normal walking speed instead
   * of the sprinting speed.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc stopSprinting() {
    this.isSprinting = false;
    return this;
  }

  /**
   * If you execute this method, the NPC will appear
   * to be burning. It gets a burning effect.
   *
   * Please note that handling the burning effect and
   * the sneaking state is done with the same
   * protocol id in older versions. So adding a
   * burning effect requires that you do not change
   * the sneaking state. This will cause problems
   * with the Minecraft protocol.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc addBurningEffect() {
    this.isBurning = true;
    return this;
  }

  /**
   * If you execute this method, the NPC will
   * stop having a burning effect.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc removeBurningEffect() {
    this.isBurning = false;
    return this;
  }

  /**
   * If you execute this method, the NPC will become
   * invisible for the player, but it is still
   * rendered and existent.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc setInvisible() {
    this.isInvisible = true;
    return this;
  }

  /**
   * If you execute this method, the NPC will become
   * visible again, if you executed {@code #setInvisible}
   * before.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc setVisible() {
    this.isInvisible = false;
    return this;
  }

  public KelpNpc showCustomName() {
    this.customNameShown = true;
    if (isSpawned) {
      npcVersionTemplate.updateCustomName(this);
    }
    return this;
  }

  public KelpNpc hideCustomName() {
    this.customNameShown = false;
    if (isSpawned) {
      npcVersionTemplate.updateCustomName(this);
    }
    return this;
  }

  public KelpNpc customNameShown(boolean shown) {
    this.customNameShown = shown;
    if (isSpawned) {
      npcVersionTemplate.updateCustomName(this);
    }
    return this;
  }

  public KelpNpc addActivity(NpcActivity<?> activity) {
    this.activities.add(activity);
    return this;
  }

  public KelpNpc player(KelpPlayer player) {
    this.player = player;
    return this;
  }

  public KelpNpc enableNoClip() {
    this.noClip = true;
    return this;
  }

  public KelpNpc disableNoClip() {
    this.noClip = false;
    return this;
  }

  public KelpNpc noClip(boolean noClip) {
    this.noClip = noClip;
    return this;
  }

  public KelpNpc falling(boolean falling) {
    this.falling = falling;
    return this;
  }

  public KelpNpc flying(boolean flying) {
    this.flying = flying;
    return this;
  }

  /**
   * Makes the NPC look to the given location. So it
   * rotates its head to the given target location.
   *
   * @param target The location where the NPC should look to.
   * @return An instance of the current NPC object.
   */
  public KelpNpc lookTo(Location target) {
    double xDiff = target.getX() - currentLocation.getX();
    double yDiff = target.getY() - currentLocation.getY();
    double zDiff = target.getZ() - currentLocation.getZ();

    double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
    double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);

    double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
    double pitch = Math.toDegrees(Math.acos(yDiff / distanceY)) - 90.0D;
    if (zDiff < 0.0D) {
      yaw += Math.abs(180.0D - yaw) * 2.0D;
    }
    currentLocation.setYaw((float) yaw - 90.0F);
    currentLocation.setPitch((float) pitch);
    npcVersionTemplate.teleport(this, currentLocation);
    return this;
  }

  public void moveRelativeDistance(double x, double y, double z, float absoluteYaw, float absolutePitch) {
    npcVersionTemplate.moveRelativeDistance(this, player.getBukkitPlayer(), x, y, z, absoluteYaw, absolutePitch);
  }

  public void moveTo(Location target) {
    double x = target.getX() - currentLocation.getX();
    double y = target.getY() - currentLocation.getY();
    double z = target.getZ() - currentLocation.getZ();
    this.moveRelativeDistance(x, y, z, currentLocation.getYaw(), currentLocation.getPitch());
    this.currentLocation(target);
  }

  public void updateTitleLines() {
    npcVersionTemplate.updateTitleLines(this);
  }

  /**
   * Spawns the NPC for the given player and automatically
   * adds it to the NPC repository.
   *
   * @return An instance of the current NPC object. {@code null} if the npc could not be spawned.
   */
  public KelpNpc spawn() {
    if (this.uuid == null) {
      this.uuid = UUID.randomUUID();
    }

    if (this.spawnLocation == null) {
      logger.log(LogLevel.ERROR, "To spawn an NPC, you have to define a location before." +
              " But there was no location found. Please check your code again.");
      return null;
    }
    this.currentLocation = spawnLocation;

    this.npcMeta = npcVersionTemplate.spawnNpc(this, player.getBukkitPlayer());
    isSpawned = true;
    gameProfile = npcMeta.getGameProfile();
    entityId = npcMeta.getEntityId();
    customName = npcMeta.getOverHeadDisplayName();

    // execute activities that do something when the npc spawns
    this.activities.forEach(current -> current.onSpawn(this));

    this.kelpNpcRepository.addNpc(this, player);
    return this;
  }

  /**
   * De-Spawns the NPC for the given player. This completely removes the NPC
   * from the repository and the game. So the NPC won't be rendered by the
   * player anymore. The title lines will also disappear.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc deSpawn() {
    // execute activities that do something when the npc is removed
    this.activities.forEach(current -> current.onRemove(this));

    npcVersionTemplate.deSpawn(this, player.getBukkitPlayer());
    this.npcMeta = null;
    this.kelpNpcRepository.removeNpc(this, player);
    return this;
  }

  public void triggerHeartbeatTick() {
    Lists.newArrayList(this.activities).forEach(current -> {
      if (current.isFinished()) {
        this.activities.remove(current);
        return;
      }
      if (!current.hasStarted() && (isSpawned || current.alwaysActive())) {
        current.start(this);
      }
      if (isSpawned || current.alwaysActive()) {
        current.onTick(this);
      }
    });
  }

  /**
   * Refreshes all NPC properties. When you for example changed the sneak state
   * or head rotation, you have to execute this method so that the updates
   * will be sent to the player.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc refreshMetadata() {
    npcVersionTemplate.refreshMetadata(this, player.getBukkitPlayer());
    return this;
  }

  /**
   * Makes the NPC sneak/crouch.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc sneak() {
    this.isSneaking = true;
    return this;
  }

  /**
   * Removes the sneaking/crouching effect of the NPC.
   *
   * @return An instance of the current NPC object.
   */
  public KelpNpc unSneak() {
    this.isSneaking = false;
    return this;
  }

  /**
   * @return {@code true} if the NPC is currently supposed to sneak.
   */
  public boolean isSneaking() {
    return isSneaking;
  }

  /**
   * @return The {@code UUID} of the NPC's {@code GameProfile}.
   * @see GameProfile
   */
  public UUID getUuid() {
    return uuid;
  }

  /**
   * @return  The custom name of the NPC, which is displayed
   *          above the NPC's head.
   */
  public String getCustomName() {
    return customName;
  }

  /**
   * @return The location, where the NPC is spawned/will be spawned.
   */
  public Location getSpawnLocation() {
    return spawnLocation;
  }

  /**
   * @return A list of all title lines the NPC has.
   */
  public List<String> getCurrentTitles() {
    return titles.get();
  }

  /**
   * @return A list of all title lines the NPC has.
   */
  public Supplier<List<String>> getTitles() {
    return titles;
  }

  /**
   * @return The skin signature (containing data about cape, ...).
   */
  public String getSkinSignature() {
    return skinSignature;
  }

  /**
   * @return The skin texture (containing data about the skin itself)
   */
  public String getSkinTexture() {
    return skinTexture;
  }

  /**
   * @return The name under which the NPC is displayed in tab.
   */
  public String getTabListName() {
    return tabListName;
  }

  /**
   * @return The item the NPC is currently holding in its hand.
   */
  public KelpItem getItemInHand() {
    return itemInHand;
  }

  /**
   * @return {@code true} if the NPC is visible in the tab list.
   */
  public boolean shouldShowInTab() {
    return showInTab;
  }

  /**
   * @return {@code true} if the NPC is supposed to burn.
   */
  public boolean hasBurningEffect() {
    return isBurning;
  }

  /**
   * @return {@code true} if the NPC is invisible for the player.
   */
  public boolean isInvisible() {
    return isInvisible;
  }

  public boolean isSpawned() {
    return isSpawned;
  }

  /**
   * @return {@code true} if the custom name above the NPC's head should be displayed.
   */
  public boolean isCustomNameShown() {
    return this.customNameShown;
  }

  /**
   * Checks if the npc is currently in sprint mode.
   *
   * @return Whether the npc is currently sprinting.
   */
  public boolean isSprinting() {
    return isSprinting;
  }

  public boolean isFalling() {
    return falling;
  }

  public boolean hasNoClip() {
    return noClip;
  }

  public boolean isFlying() {
    return flying;
  }

  /**
   * @return The {@code GameProfile} object of the NPC.
   */
  public GameProfile getGameProfile() {
    return gameProfile;
  }

  /**
   * @return The entity id of the NPC, which is used to identify it in the version modules.
   */
  public int getEntityId() {
    return entityId;
  }

  /**
   * @return The current {@code KelpNpcMeta} object.
   * @see KelpNpcMeta
   */
  public KelpNpcMeta getNpcMeta() {
    return npcMeta;
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public KelpPlayer getPlayer() {
    return player;
  }

  public void setArmorStandEntityIds(Collection<Integer> armorStandEntityIds) {
    this.npcMeta.setArmorStandEntityIds(armorStandEntityIds);
  }

  public double getTitleHeights(int index) {
    // index 0 nearest to npc
    double add = 0;
    for (int i = 0; i < index; i++) {
      add += .25;
    }

    return add - .1;
  }
}
