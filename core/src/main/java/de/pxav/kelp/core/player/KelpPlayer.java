package de.pxav.kelp.core.player;

import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import de.pxav.kelp.core.sound.KelpSound;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpPlayer {

  private PlayerVersionTemplate playerVersionTemplate;
  private SidebarRepository sidebarRepository;
  private KelpInventoryRepository inventoryRepository;

  private Player bukkitPlayer;

  private int clientViewDistance;
  private String clientLanguage;
  private PlayerChatVisibility playerChatVisibility;
  private boolean playerChatColorEnabled;
  private String tabListHeader;
  private String tabListFooter;

  public KelpPlayer(Player bukkitPlayer,
                    PlayerVersionTemplate playerVersionTemplate,
                    SidebarRepository sidebarRepository,
                    KelpInventoryRepository inventoryRepository) {
    this.bukkitPlayer = bukkitPlayer;
    this.playerVersionTemplate = playerVersionTemplate;
    this.sidebarRepository = sidebarRepository;
    this.inventoryRepository = inventoryRepository;
  }

  public KelpPlayer openKelpSidebar(String identifier) {
    this.sidebarRepository.openSidebar(identifier, bukkitPlayer);
    return this;
  }

  public KelpPlayer removeKelpSidebar() {
    this.sidebarRepository.removeSidebar(bukkitPlayer);
    return this;
  }

  public KelpPlayer openInventory(KelpInventory inventory) {
    this.inventoryRepository.openInventory(inventory, this);
    return this;
  }

  public KelpPlayer closeInventory() {
    this.inventoryRepository.closeInventory(this);
    return this;
  }

  public KelpPlayer forceInventoryClose() {
    bukkitPlayer.closeInventory();
    return this;
  }

  public KelpPlayer playSound(KelpSound sound) {
    playerVersionTemplate.playSound(bukkitPlayer, sound, playerVersionTemplate.getLocation(bukkitPlayer), 3, 0);
    return this;
  }

  public KelpPlayer playSound(KelpSound sound, Location location) {
    playerVersionTemplate.playSound(bukkitPlayer, sound, location, 3, 0);
    return this;
  }

  public KelpPlayer playSound(KelpSound sound, float volume) {
    playerVersionTemplate.playSound(bukkitPlayer, sound, playerVersionTemplate.getLocation(bukkitPlayer), volume, 0);
    return this;
  }

  public KelpPlayer playSound(KelpSound sound, float volume, float pitch) {
    playerVersionTemplate.playSound(bukkitPlayer, sound, playerVersionTemplate.getLocation(bukkitPlayer), volume, pitch);
    return this;
  }

  public KelpPlayer playSound(KelpSound sound, Location location, float volume, float pitch) {
    playerVersionTemplate.playSound(bukkitPlayer, sound, location, volume, pitch);
    return this;
  }

  public KelpPlayer sendTitle(String title, String subTitle) {
    playerVersionTemplate.sendTitle(bukkitPlayer, title, subTitle, 20, 60, 20);
    return this;
  }

  public KelpPlayer sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
    playerVersionTemplate.sendTitle(bukkitPlayer, title, subTitle, fadeIn, stay, fadeOut);
    return this;
  }

  public KelpPlayer sendActionbar(String message) {
    playerVersionTemplate.sendActionBar(bukkitPlayer, message);
    return this;
  }

  public UUID getUUID() {
    return playerVersionTemplate.getUniqueId(bukkitPlayer);
  }

  public KelpPlayer teleport(Location location) {
    playerVersionTemplate.teleport(bukkitPlayer, location);
    return this;
  }

  public KelpPlayer setHealth(int health) {
    playerVersionTemplate.setHealth(bukkitPlayer, health);
    return this;
  }

  public boolean isInWater() {
    return playerVersionTemplate.isInWater(bukkitPlayer);
  }

  public boolean isInCobweb() {
    return playerVersionTemplate.isInCobweb(bukkitPlayer);
  }

  public KelpPlayer chat(String message) {
    playerVersionTemplate.chat(bukkitPlayer, message);
    return this;
  }

  public boolean mayFly() {
    return playerVersionTemplate.getAllowFlight(bukkitPlayer);
  }

  public String getDisplayName() {
    return playerVersionTemplate.getDisplayName(bukkitPlayer);
  }

  public KelpPlayer setDisplayName(String displayName) {
    playerVersionTemplate.setDisplayName(bukkitPlayer, displayName);
    return this;
  }

  public String getTabListName() {
    return playerVersionTemplate.getPlayerTabListName(bukkitPlayer);
  }

  public KelpPlayer setTabListName(String tabListName) {
    playerVersionTemplate.setPlayerTabListName(bukkitPlayer, tabListName);
    return this;
  }

  // TODO TAB LIST HEADER AND FOOTER

  public KelpPlayer setCompassTarget(Location target) {
    playerVersionTemplate.setCompassTarget(bukkitPlayer, target);
    return this;
  }

  public KelpPlayer kickPlayer(String kickMessage) {
    playerVersionTemplate.kickPlayer(bukkitPlayer, kickMessage);
    return this;
  }

  public boolean isSneaking() {
    return playerVersionTemplate.isSneaking(bukkitPlayer);
  }

  public KelpPlayer sneak() {
    playerVersionTemplate.setSneaking(bukkitPlayer, true);
    return this;
  }

  public KelpPlayer unSneak() {
    playerVersionTemplate.setSneaking(bukkitPlayer, false);
    return this;
  }

  public KelpPlayer toggleSneak() {
    if (this.isSneaking()) {
      this.unSneak();
    } else {
      this.sneak();
    }
    return this;
  }

  public KelpPlayer setSneaking(boolean sneaking) {
    playerVersionTemplate.setSneaking(bukkitPlayer, sneaking);
    return this;
  }

  public boolean isSprinting() {
    return playerVersionTemplate.isSprinting(bukkitPlayer);
  }

  public KelpPlayer sprint() {
    playerVersionTemplate.setSprinting(bukkitPlayer, true);
    return this;
  }

  public KelpPlayer stopSprinting() {
    playerVersionTemplate.setSprinting(bukkitPlayer, false);
    return this;
  }

  public KelpPlayer toggleSprint() {
    if (this.isSprinting()) {
      this.stopSprinting();
    } else {
      this.sprint();
    }
    return this;
  }

  public KelpPlayer setSprinting(boolean sprinting) {
    playerVersionTemplate.setSprinting(bukkitPlayer, sprinting);
    return this;
  }

  public boolean isSleepingIgnored() {
    return playerVersionTemplate.isSprinting(bukkitPlayer);
  }

  public KelpPlayer ignoreSleeping() {
    playerVersionTemplate.setSleepingIgnored(bukkitPlayer, true);
    return this;
  }

  public KelpPlayer unignoreSleeping() {
    playerVersionTemplate.setSleepingIgnored(bukkitPlayer, false);
    return this;
  }

  public KelpPlayer toggleIgnoreSleeping() {
    if (this.isSleepingIgnored()) {
      this.unignoreSleeping();
    } else {
      this.ignoreSleeping();
    }
    return this;
  }

  public KelpPlayer setSleepingIgnored(boolean sleepingIgnored) {
    playerVersionTemplate.setSleepingIgnored(bukkitPlayer, sleepingIgnored);
    return this;
  }

  public KelpPlayer setRelativePlayerTime(long time) {
    playerVersionTemplate.setPlayerTime(bukkitPlayer, time, true);
    return this;
  }

  public KelpPlayer setPlayerTime(long time) {
    playerVersionTemplate.setPlayerTime(bukkitPlayer, time, false);
    return this;
  }

  public long getPlayerTime() {
    return playerVersionTemplate.getPlayerTime(bukkitPlayer);
  }

  public long getPlayerTimeOffset() {
    return playerVersionTemplate.getPlayerTimeOffset(bukkitPlayer);
  }

  public boolean isPlayerTimeRelative() {
    return playerVersionTemplate.isPlayerTimeRelative(bukkitPlayer);
  }

  public KelpPlayer resetPlayerTime() {
    playerVersionTemplate.resetPlayerTime(bukkitPlayer);
    return this;
  }

  public KelpPlayer giveExperience(int amount) {
    playerVersionTemplate.giveExperience(bukkitPlayer, amount);
    return this;
  }

  public KelpPlayer giveExperienceLevels(int amount) {
    playerVersionTemplate.giveExperienceLevels(bukkitPlayer, amount);
    return this;
  }

  public float getExperience() {
    return playerVersionTemplate.getExperience(bukkitPlayer);
  }

  public KelpPlayer setExperience(float experience) {
    playerVersionTemplate.setExperience(bukkitPlayer, experience);
    return this;
  }

  public KelpPlayer setLevel(int level) {
    playerVersionTemplate.setLevel(bukkitPlayer, level);
    return this;
  }

  public int getLevel() {
    return playerVersionTemplate.getLevel(bukkitPlayer);
  }

  public int getTotalExperience() {
    return playerVersionTemplate.getTotalExperience(bukkitPlayer);
  }

  public KelpPlayer setTotalExperience(int experience) {
    playerVersionTemplate.setTotalExperience(bukkitPlayer, experience);
    return this;
  }

  public float getExhaustionLevel() {
    return playerVersionTemplate.getExhaustion(bukkitPlayer);
  }

  public KelpPlayer setExhaustionLevel(float exhaustionLevel) {
    playerVersionTemplate.setExhaustion(bukkitPlayer, exhaustionLevel);
    return this;
  }

  public KelpPlayer setSaturationLevel(float saturationLevel) {
    playerVersionTemplate.setSaturation(bukkitPlayer, saturationLevel);
    return this;
  }

  public float getSaturationLevel() {
    return playerVersionTemplate.getSaturation(bukkitPlayer);
  }

  public int getFoodLevel() {
    return playerVersionTemplate.getFoodLevel(bukkitPlayer);
  }

  public KelpPlayer setFoodLevel(int foodLevel) {
    playerVersionTemplate.setFoodLevel(bukkitPlayer, foodLevel);
    return this;
  }

  public KelpPlayer setAllowFlight(boolean allowed) {
    playerVersionTemplate.setAllowFlight(bukkitPlayer, allowed);
    return this;
  }

  public KelpPlayer allowFlying() {
    playerVersionTemplate.setAllowFlight(bukkitPlayer, true);
    return this;
  }

  public KelpPlayer disallowFlying() {
    playerVersionTemplate.setAllowFlight(bukkitPlayer, false);
    return this;
  }

  public KelpPlayer hidePlayer(KelpPlayer toHide) {
    playerVersionTemplate.hidePlayer(bukkitPlayer, toHide.getBukkitPlayer());
    return this;
  }

  public KelpPlayer showPlayer(KelpPlayer toShow) {
    playerVersionTemplate.showPlayer(bukkitPlayer, toShow.getBukkitPlayer());
    return this;
  }

  public boolean canSee(KelpPlayer toCheck) {
    return playerVersionTemplate.canSee(bukkitPlayer, toCheck.getBukkitPlayer());
  }

  public boolean isFlying() {
    return playerVersionTemplate.isFlying(bukkitPlayer);
  }

  public KelpPlayer makeFlying() {
    playerVersionTemplate.setFlying(bukkitPlayer, true);
    return this;
  }

  public KelpPlayer stopFlying() {
    playerVersionTemplate.setFlying(bukkitPlayer, false);
    return this;
  }

  public KelpPlayer toggleFlying() {
    if (this.isFlying()) {
      this.stopFlying();
    } else {
      this.makeFlying();
    }
    return this;
  }

  public KelpPlayer setFlying(boolean flying) {
    playerVersionTemplate.setFlying(bukkitPlayer, flying);
    return this;
  }

  public KelpPlayer setFlySpeed(float flySpeed) {
    playerVersionTemplate.setFlySpeed(bukkitPlayer, flySpeed);
    return this;
  }

  public float getFlySpeed() {
    return playerVersionTemplate.getFlySpeed(bukkitPlayer);
  }

  public float getWalkSpeed() {
    return playerVersionTemplate.getWalkSpeed(bukkitPlayer);
  }

  public KelpPlayer setResourcePack(String url) {
    playerVersionTemplate.setResourcePack(bukkitPlayer, url);
    return this;
  }

  public KelpPlayer setResourcePack(String url, byte[] hash) {
    playerVersionTemplate.setResourcePack(bukkitPlayer, url, hash);
    return this;
  }

  public boolean isHealthScaled() {
    return playerVersionTemplate.isHealthScaled(bukkitPlayer);
  }

  public KelpPlayer setHealthScale(double healthScale) {
    playerVersionTemplate.setHealthScale(bukkitPlayer, healthScale);
    return this;
  }

  public double getHealthScale() {
    return playerVersionTemplate.getHealthScale(bukkitPlayer);
  }

  public KelpPlayer resetTitle() {
    playerVersionTemplate.resetTitle(bukkitPlayer);
    return this;
  }

  public KelpPlayer setClientViewDistanceInternally(int clientViewDistance) {
    this.clientViewDistance = clientViewDistance;
    return this;
  }

  public int getClientViewDistance() {
    return this.clientViewDistance;
  }

  public KelpPlayer setClientLanguageInternally(String clientLanguage) {
    this.clientLanguage = clientLanguage;
    return this;
  }

  public String getClientLanguage() {
    return clientLanguage;
  }

  public KelpPlayer setPlayerChatVisibilityInternally(PlayerChatVisibility playerChatVisibility) {
    this.playerChatVisibility = playerChatVisibility;
    return this;
  }

  public PlayerChatVisibility getPlayerChatVisibility() {
    return playerChatVisibility;
  }

  public KelpPlayer setPlayerChatColorEnabledInternally(boolean playerChatColorEnabled) {
    this.playerChatColorEnabled = playerChatColorEnabled;
    return this;
  }

  public boolean isPlayerChatColorEnabled() {
    return this.playerChatColorEnabled;
  }

  public KelpPlayer setTabListHeader(String header) {
    playerVersionTemplate.setPlayerListHeader(bukkitPlayer, header);
    this.tabListHeader = header;
    return this;
  }

  public KelpPlayer setTabListFooter(String footer) {
    playerVersionTemplate.setPlayerListHeader(bukkitPlayer, footer);
    this.tabListFooter = footer;
    return this;
  }

  public KelpPlayer setTabListHeaderAndFooter(String header, String footer) {
    setTabListHeader(header).setTabListFooter(footer);
    return this;
  }

  public String getTabListFooter() {
    return tabListFooter;
  }

  public String getTabListHeader() {
    return tabListHeader;
  }

  public boolean isOperator() {
    return playerVersionTemplate.isOperator(bukkitPlayer);
  }

  public KelpPlayer makeOperator() {
    playerVersionTemplate.setOperator(bukkitPlayer, true);
    return this;
  }

  public KelpPlayer removeOperator() {
    playerVersionTemplate.setOperator(bukkitPlayer, false);
    return this;
  }

  public KelpPlayer toggleOperator() {
    if (this.isOperator()) {
      this.removeOperator();
    } else {
      this.makeOperator();
    }
    return this;
  }

  public KelpPlayer setOperator(boolean operator) {
    playerVersionTemplate.setOperator(bukkitPlayer, operator);
    return this;
  }

  public KelpPlayer grantPermission(String permissionName) {
    playerVersionTemplate.givePermission(bukkitPlayer, permissionName);
    return this;
  }

  public KelpPlayer removePermission(String permissionName) {
    playerVersionTemplate.removePermission(bukkitPlayer, permissionName);
    return this;
  }

  public boolean hasPermission(String permissionName) {
    return playerVersionTemplate.hasPermission(bukkitPlayer, permissionName);
  }

  public Player getBukkitPlayer() {
    return bukkitPlayer;
  }

}
