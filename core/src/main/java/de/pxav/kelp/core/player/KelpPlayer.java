package de.pxav.kelp.core.player;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.particle.version.ParticleVersionTemplate;
import de.pxav.kelp.core.player.bossbar.BossBarColor;
import de.pxav.kelp.core.player.bossbar.BossBarStyle;
import de.pxav.kelp.core.player.message.InteractiveMessage;
import de.pxav.kelp.core.player.prompt.anvil.AnvilPrompt;
import de.pxav.kelp.core.player.prompt.anvil.AnvilPromptVersionTemplate;
import de.pxav.kelp.core.player.prompt.chat.ChatPromptVersionTemplate;
import de.pxav.kelp.core.player.prompt.chat.DefaultFontSize;
import de.pxav.kelp.core.player.prompt.chat.SimpleChatPrompt;
import de.pxav.kelp.core.player.prompt.sign.SignPrompt;
import de.pxav.kelp.core.player.prompt.sign.SignPromptVersionTemplate;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import de.pxav.kelp.core.sound.KelpSound;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * {@code KelpPlayer} is an alternative to the normal bukkit {@link Player}.
 * Although the normal bukkit player is an interface and aims to be version in-
 * dependent, there are some features which have changed over the versions or
 * are unavailable in some versions.
 *
 * In addition the {@code KelpPlayer} provides new methods to easily access
 * kelp-specific features such as opening a {@code KelpSidebar} or a {@code KelpInventory}.
 *
 * The KelpPlayer is a subclass of a {@link LivingKelpEntity} and therefore of
 * {@link de.pxav.kelp.core.entity.KelpEntity} as well. This allows you to cast entities
 * to players and access further methods from the individual entity classes.
 *
 * It is not recommended to create new player instances as a normal application developer
 * if you do not know what you're doing as the constructor subjects to change and all
 * KelpPlayers are cached. This is done in order to keep custom information
 * about the player such as their view distance or client language, which cannot be retrieved
 * easily in all versions. So instead of creating new instances of KelpPlayers,
 * use {@link KelpPlayerRepository}, where you have methods such as {@link KelpPlayerRepository#getKelpPlayer(Player)}.
 *
 * @see de.pxav.kelp.core.entity.KelpEntity
 * @see LivingKelpEntity
 * @see KelpPlayerRepository
 * @author pxav
 */
public class KelpPlayer extends LivingKelpEntity {

  private PlayerVersionTemplate playerVersionTemplate;
  private SidebarRepository sidebarRepository;
  private KelpInventoryRepository inventoryRepository;
  private ParticleVersionTemplate particleVersionTemplate;
  private SignPromptVersionTemplate signPromptVersionTemplate;
  private AnvilPromptVersionTemplate anvilPromptVersionTemplate;
  private ChatPromptVersionTemplate chatPromptVersionTemplate;

  private Player bukkitPlayer;

  // caches the view distance configured in the player's video settings
  private int clientViewDistance;

  // caches the language of the player's client
  private String clientLanguage;

  // caches the chat visibility configured by the player in the chat settings tab
  private PlayerChatVisibility playerChatVisibility;

  private boolean playerChatColorEnabled;

  private String tabListHeader;
  private String tabListFooter;

  public KelpPlayer(Player bukkitPlayer,
                    PlayerVersionTemplate playerVersionTemplate,
                    SidebarRepository sidebarRepository,
                    KelpInventoryRepository inventoryRepository,
                    KelpPlayerRepository kelpPlayerRepository,
                    ParticleVersionTemplate particleVersionTemplate,
                    SignPromptVersionTemplate signPromptVersionTemplate,
                    AnvilPromptVersionTemplate anvilPromptVersionTemplate,
                    ChatPromptVersionTemplate chatPromptVersionTemplate,
                    EntityVersionTemplate entityVersionTemplate,
                    LivingEntityVersionTemplate livingEntityVersionTemplate,
                    UUID uuid,
                    Location location,
                    int entityId) {
    super(kelpPlayerRepository.getMinecraftEntity(uuid),
      KelpEntityType.PLAYER,
      location,
      entityId,
      entityVersionTemplate,
      livingEntityVersionTemplate,
      bukkitPlayer);
    this.bukkitPlayer = bukkitPlayer;
    this.playerVersionTemplate = playerVersionTemplate;
    this.sidebarRepository = sidebarRepository;
    this.inventoryRepository = inventoryRepository;
    this.particleVersionTemplate = particleVersionTemplate;
    this.signPromptVersionTemplate = signPromptVersionTemplate;
    this.chatPromptVersionTemplate = chatPromptVersionTemplate;
    this.anvilPromptVersionTemplate = anvilPromptVersionTemplate;
  }

  public SignPrompt openSignPrompt() {
    return new SignPrompt(this.getBukkitPlayer(), this.signPromptVersionTemplate);
  }

  public AnvilPrompt openAnvilPrompt() {
    return new AnvilPrompt(this.getBukkitPlayer(), this.anvilPromptVersionTemplate);
  }

  public SimpleChatPrompt openSimpleChatPrompt() {
    return new SimpleChatPrompt(this.getBukkitPlayer(), this.chatPromptVersionTemplate);
  }

  /**
   * Opens a kelp sidebar with the given identifier. This method
   * only applies to sidebars created using an annotation.
   *
   * @param identifier The identifier of the sidebar you want to show
   *                   to the player
   * @return the current instance of the player.
   */
  public KelpPlayer openKelpSidebar(String identifier) {
    this.sidebarRepository.openSidebar(identifier, bukkitPlayer);
    return this;
  }

  /**
   * Makes the current sidebar of the player disappear.
   *
   * @return the current instance of the player.
   */
  public KelpPlayer removeKelpSidebar() {
    this.sidebarRepository.removeSidebar(bukkitPlayer);
    return this;
  }

  /**
   * Opens a {@link KelpInventory} to the player. This
   * is equivalent to calling the {@link KelpInventoryRepository#openInventory(KelpInventory, KelpPlayer)}
   * method.
   *
   * @param inventory The inventory you want to show to the player
   * @return the current instance of the player
   */
  public KelpPlayer openInventory(KelpInventory inventory) {
    this.inventoryRepository.openInventory(inventory, this);
    return this;
  }

  /**
   * Updates the {@link KelpInventory} of the player, which includes
   * all widgets contained in it.
   *
   * This is equivalent to calling the {@link KelpInventoryRepository#updateInventory(KelpPlayer)}
   * method.
   *
   * @return the current instance of the player.
   */
  public KelpPlayer updateKelpInventory() {
    this.inventoryRepository.updateInventory(this);
    return this;
  }

  /**
   * Closes the current kelp inventory of the player.
   *
   * This is equivalent to calling the {@link KelpInventoryRepository#closeInventory(KelpPlayer)}
   * method.
   *
   * @return the current instance of the player.
   */
  public KelpPlayer closeInventory() {
    this.inventoryRepository.closeInventory(this);
    return this;
  }

  /**
   * Closes the inventory using the bukkit method. This is
   * not recommended for closing kelp inventories as listeners
   * and schedulers are not canceled/unregistered, which might
   * flood the CPU and memory of your server.
   *
   * In those cases use {@link #closeInventory()} instead.
   *
   * @return the current instance of the player
   */
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

  public KelpPlayer spawnParticle(ParticleType particleType, boolean longDistance, float offsetX, float offsetY, float offsetZ, int count) {
    particleVersionTemplate.spawnParticle(this,
      particleType,
      longDistance,
      getLocation().getX(),
      getLocation().getY(),
      getLocation().getZ(),
      offsetX, offsetY,
      offsetZ,
      0,
      count,
      null);
    return this;
  }

  public KelpPlayer spawnParticle(ParticleType particleType, float offsetX, float offsetY, float offsetZ) {
    particleVersionTemplate.spawnParticle(this,
      particleType,
      false,
      getLocation().getX(),
      getLocation().getY(),
      getLocation().getZ(),
      offsetX, offsetY,
      offsetZ,
      0,
      1,
      null);
    return this;
  }

  public KelpPlayer spawnParticle(ParticleType particleType, boolean longDistance, float offsetX, float offsetY, float offsetZ, int count, float particleData, Object generalData) {
    particleVersionTemplate.spawnParticle(this,
      particleType,
      longDistance,
      getLocation().getX(),
      getLocation().getY(),
      getLocation().getZ(),
      offsetX, offsetY,
      offsetZ,
      particleData,
      count,
      generalData);
    return this;
  }

  public KelpPlayer spawnParticle(ParticleType particleType, boolean longDistance, Location location, float offsetX, float offsetY, float offsetZ, int count, float particleData, Object generalData) {
    particleVersionTemplate.spawnParticle(this,
      particleType,
      longDistance,
      location.getX(),
      location.getY(),
      location.getZ(),
      offsetX, offsetY,
      offsetZ,
      particleData,
      count,
      generalData);
    return this;
  }

  public KelpPlayer spawnParticle(ParticleType particleType, boolean longDistance, double x, double y, double z, float offsetX, float offsetY, float offsetZ, int count, float particleData, Object generalData) {
    particleVersionTemplate.spawnParticle(this,
      particleType,
      longDistance,
      x,
      y,
      z,
      offsetX, offsetY,
      offsetZ,
      particleData,
      count,
      generalData);
    return this;
  }

  public UUID getUUID() {
    return playerVersionTemplate.getUniqueId(bukkitPlayer);
  }

//  @Override
//  public KelpPlayer teleport(Location location) {
//    playerVersionTemplate.teleport(bukkitPlayer, location);
//    return this;
//  }

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

  // TODO make tablist name hideable

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

  public boolean isBannedByBukkit() {
    return playerVersionTemplate.isBannedByBukkit(bukkitPlayer);
  }

  public boolean isWhitelisted() {
    return playerVersionTemplate.isWhitelisted(bukkitPlayer);
  }

  public KelpPlayer whitelist() {
    playerVersionTemplate.setOperator(bukkitPlayer, true);
    return this;
  }

  public KelpPlayer removeFromWhitelist() {
    playerVersionTemplate.setOperator(bukkitPlayer, false);
    return this;
  }

  public KelpPlayer toggleWhitelist() {
    if (this.isOperator()) {
      this.removeOperator();
    } else {
      this.makeOperator();
    }
    return this;
  }

  public KelpPlayer setWhitelisted(boolean whitelisted) {
    playerVersionTemplate.setWhitelisted(bukkitPlayer, whitelisted);
    return this;
  }

  public KelpPlayer sendMessage(String message) {
    playerVersionTemplate.sendMessage(bukkitPlayer, message);
    return this;
  }

  public KelpPlayer sendMessages(String... messages) {
    for (String message : messages) {
      playerVersionTemplate.sendMessage(bukkitPlayer, message);
    }
    return this;
  }

  public KelpPlayer sendMessages(Collection<String> messages) {
    for (String message : messages) {
      playerVersionTemplate.sendMessage(bukkitPlayer, message);
    }
    return this;
  }

  public void sendCenteredMessage(String message) {
    Preconditions.checkNotNull(message);
    if(message.equals("")) {
      this.sendMessage("");
    }

    final int CENTER_PX = 154;
    int messagePxSize = 0;
    boolean previousCode = false;
    boolean isBold = false;

    for(char c : message.toCharArray()){
      if(c == '§') {
        previousCode = true;
        continue;
      }

      if(previousCode) {
        previousCode = false;
        isBold = c == 'l' || c == 'L';
      } else {
        DefaultFontSize dFI = DefaultFontSize.getDefaultFontInfo(c);
        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
        messagePxSize++;
      }
    }

    int halvedMessageSize = messagePxSize / 2;
    int toCompensate = CENTER_PX - halvedMessageSize;
    int spaceLength = DefaultFontSize.SPACE.getLength() + 1;
    int compensated = 0;
    StringBuilder sb = new StringBuilder();
    while(compensated < toCompensate){
      sb.append(" ");
      compensated += spaceLength;
    }
    this.sendMessage(sb.toString() + message);
  }

  public void sendCenteredMessages(String... messages) {
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
  }

  public void sendCenteredMessages(Collection<String> messages) {
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
  }

  public void sendCenteredMessages(String header, String footer, String... messages) {
    if (header != null) {
      this.sendMessage(header);
    }
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
    if (footer != null) {
      this.sendMessage(footer);
    }
  }

  public void sendCenteredMessages(String header, Collection<String> messages, String footer) {
    if (header != null) {
      this.sendMessage(header);
    }
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
    if (footer != null) {
      this.sendMessage(footer);
    }
  }

  /**
   * Sends a boss bar to the player by spawning a boss entity near it. If you use this
   * method in 1.8, please keep in mind that bar colors other than {@code PURPLE} and bar styles
   * other than {@code SOLID} are not supported.
   *
   * @param message   The message you want to be displayed above the boss bar.
   * @param barColor  The color of the boss bar. Please note that in 1.8 only
   *                  {@link BossBarColor#PURPLE} is allowed. If you use any color, no exception
   *                  is thrown but purple will be chosen automatically.
   * @param barStyle  The style of the boss bar (how many segments?, ...). Note that
   *                  in 1.8 only {@link BossBarStyle#SOLID} is supported. If you use any different
   *                  style, no exception will be thrown, but {@link BossBarStyle#SOLID} is chosen
   *                  automatically.
   */
  public void sendBossBar(String message, BossBarColor barColor, BossBarStyle barStyle) {
    playerVersionTemplate.sendBossBar(bukkitPlayer, message, barColor, barStyle);
  }

  /**
   * Sends a boss bar to the player by spawning a boss entity near it.
   * This method uses {@link BossBarColor#PURPLE} and {@link BossBarStyle#SOLID}
   * in order to be compatible with all versions. If you want to use another style or color
   * you can use {@link #sendBossBar(String, BossBarColor, BossBarStyle)} instead.
   *
   * @param message The message you want to be displayed above the boss bar.
   */
  public void sendBossBar(String message) {
    playerVersionTemplate.sendBossBar(bukkitPlayer, message, BossBarColor.PURPLE, BossBarStyle.SOLID);
  }

  /**
   * Makes the boss bar disappear for the player.
   */
  public void removeBossBar() {
    playerVersionTemplate.removeBossBar(bukkitPlayer);
  }

  /**
   * Sends an interactive message to the player. An interactive message is a message
   * the player can click on and events (execute a command, open a url, ...) are triggered.
   * You can also add hover events to it. You can add as many components as you want.
   * More detailed information about how to build an interactive message can be found out
   * in {@link InteractiveMessage}.
   *
   * @param interactiveMessage The interactive message you want to send to the player.
   */
  public void sendInteractiveMessage(InteractiveMessage interactiveMessage) {
    playerVersionTemplate.sendInteractiveMessage(bukkitPlayer, interactiveMessage);
  }

  /**
   * Gets the bukkit instance of the current {@link Player}. Be aware that
   * by using this, you might lose version independence.
   *
   * @return The current bukkit player instance.
   */
  public Player getBukkitPlayer() {
    return bukkitPlayer;
  }

}
