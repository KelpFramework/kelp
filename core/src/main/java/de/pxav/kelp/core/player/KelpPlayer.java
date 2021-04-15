package de.pxav.kelp.core.player;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.type.general.HumanEntity;
import de.pxav.kelp.core.entity.type.general.ProjectileLauncher;
import de.pxav.kelp.core.event.kelpevent.sidebar.KelpSidebarRemoveEvent;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.inventory.type.PlayerInventory;
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
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import de.pxav.kelp.core.sound.KelpSound;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

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
public interface KelpPlayer extends HumanEntity<KelpPlayer>, ProjectileLauncher<KelpPlayer> {

  static KelpPlayer from(UUID player) {
    KelpPlayerRepository repository = KelpPlugin.getInjector().getInstance(KelpPlayerRepository.class);
    return repository.getKelpPlayer(player);
  }

  static KelpPlayer from(String name) {
    Player player = Bukkit.getPlayer(name);
    if (player == null) {
      return null;
    }
    return KelpPlayer.from(player);
  }

  static KelpPlayer from(Player player) {
    return KelpPlayer.from(player.getUniqueId());
  }

  default PlayerInventory getInventory() {
    return PlayerInventory.of(this);
  }

  default SignPrompt openSignPrompt() {
    return new SignPrompt(this.getBukkitPlayer(), Dependencies.getSignPromptVersionTemplate());
  }

  default AnvilPrompt openAnvilPrompt() {
    return new AnvilPrompt(this.getBukkitPlayer(), Dependencies.getAnvilPromptVersionTemplate());
  }

  default SimpleChatPrompt openSimpleChatPrompt() {
    return new SimpleChatPrompt(this.getBukkitPlayer(), Dependencies.getChatPromptVersionTemplate());
  }

  /**
   * Checks if the player has any scoreboard with content stored in any
   * objective type ({@code SIDEBAR, PLAYER_LIST}, etc.)
   *
   * @return {@code true} if the player has a scoreboard with an objective.
   */
  default boolean hasScoreboard() {
    Scoreboard scoreboard = getBukkitPlayer().getScoreboard();
    return scoreboard.getObjective(DisplaySlot.SIDEBAR) != null
      || scoreboard.getObjective(DisplaySlot.BELOW_NAME) != null
      || scoreboard.getObjective(DisplaySlot.PLAYER_LIST) != null;
  }

  /**
   * Removes the {@link de.pxav.kelp.core.sidebar.type.KelpSidebar} from the player.
   * This hides the sidebar from the screen, but also stops all schedulers connected
   * to it (such as title animation). This method does not effect other parts
   * of the scoreboard such as the tab list.
   */
  default KelpPlayer removeSidebar() {
    setSidebarInternally(null);
    Bukkit.getPluginManager().callEvent(new KelpSidebarRemoveEvent(this));
    removeScoreboard();
  }

  /**
   * Removes the player's bukkit scoreboard no matter if this is a {@link KelpSidebar}
   * or a simple {@link org.bukkit.scoreboard.Scoreboard}. This can be used universally.
   *
   * But if the sidebar is a kelp sidebar, this method won't call the corresponding
   * {@link KelpSidebarRemoveEvent} unlike the {@link #removeSidebar()} method.
   *
   * @return
   */
  KelpPlayer removeScoreboard();

  /**
   * Sets the internal sidebar instance for the current player.
   * This won't actually update the sidebar content itself nor call
   * the {@link de.pxav.kelp.core.event.kelpevent.sidebar.KelpSidebarRenderEvent}
   * or {@link de.pxav.kelp.core.event.kelpevent.sidebar.KelpSidebarUpdateEvent}.
   *
   * @param sidebar The new sidebar instance to set internally.
   */
  void setSidebarInternally(KelpSidebar<?> sidebar);

  /**
   * Gets the sidebar that is currently rendered to the player.
   *
   * @return  The sidebar that is currently rendered to the player.
   *          {@code null} if the player has no sidebar.
   */
  KelpSidebar<?> getCurrentSidebar();

  /**
   * Opens a {@link KelpInventory} to the player. This
   * is equivalent to calling the {@link KelpInventoryRepository#openInventory(KelpInventory, KelpPlayer)}
   * method.
   *
   * @param inventory The inventory you want to show to the player
   * @return the current instance of the player
   */
  default KelpPlayer openInventory(KelpInventory<?> inventory) {
    Dependencies.getInventoryRepository().openInventory(inventory, this);
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
  default KelpPlayer updateKelpInventory() {
    Dependencies.getInventoryRepository().updateInventory(this);
    return this;
  }

  /**
   * Updates the title of the player's {@link de.pxav.kelp.core.inventory.type.SimpleInventory}.
   * This inventory takes its title as {@link com.google.common.base.Supplier<String>} and the
   * content is therefore updatable. If the player has an animated inventory, the updating process
   * is handled by the {@link de.pxav.kelp.core.inventory.type.AnimatedInventory} class and
   * executing this method will have no effect.
   *
   * @return An instance of the the current player.
   */
  default KelpPlayer updateKelpInventoryTitle() {
    Dependencies.getInventoryRepository().updateInventoryTitle(this);
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
  default KelpPlayer closeInventory() {
    Dependencies.getInventoryRepository().closeInventory(this);
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
  KelpPlayer forceInventoryClose();

  default boolean hasKelpInventory() {
    return Dependencies.getInventoryRepository().hasInventory(this);
  }

  default KelpPlayer playSound(KelpSound sound) {
    playSound(sound, getLocation(), 2, 0);
    return this;
  }

  default KelpPlayer playSound(KelpSound sound, KelpLocation location) {
    playSound(sound, location, 2, 0);
    return this;
  }

  default KelpPlayer playSound(KelpSound sound, float volume) {
    playSound(sound, getLocation(), volume, 0);
    return this;
  }

  default KelpPlayer playSound(KelpSound sound, float volume, float pitch) {
    playSound(sound, volume, pitch);
    return this;
  }

  KelpPlayer playSound(KelpSound sound, KelpLocation from, float volume, float pitch);

  KelpPlayer sendActionbar(String message);

  KelpPlayer sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut);

  default KelpPlayer sendTitle(String title, String subTitle) {
    sendTitle(title, subTitle, 20, 60, 20);
    return this;
  }

  default KelpPlayer sendSuddenTitle(String title, String subTitle) {
    sendTitle(title, subTitle, 0, 60, 0);
    return this;
  }

  KelpPlayer spawnParticle(ParticleType particleType,
                           double x,
                           double y,
                           double z,
                           float offsetX,
                           float offsetY,
                           float offsetZ,
                           int count,
                           float particleData,
                           Object generalData);

  default KelpPlayer spawnParticle(ParticleType particleType, KelpLocation location) {
    spawnParticle(
      particleType,
      location.getX(),
      location.getY(),
      location.getZ(),
      0,
      0,
      0,
      1,
      0,
      null);
    return this;
  }

  default KelpPlayer spawnParticle(ParticleType particleType, KelpLocation location, int count, float offset) {
    spawnParticle(
      particleType,
      location.getX(),
      location.getY(),
      location.getZ(),
      offset,
      offset,
      offset,
      count,
      0,
      null);
    return this;
  }

  default KelpPlayer spawnParticle(ParticleType particleType,
                                   KelpLocation location,
                                   int count,
                                   float offsetX,
                                   float offsetY,
                                   float offsetZ) {
    spawnParticle(
      particleType,
      location.getX(),
      location.getY(),
      location.getZ(),
      offsetX,
      offsetY,
      offsetZ,
      count,
      0,
      null);
    return this;
  }

  UUID getUUID();

  String getName();

  KelpPlayer setHealth(int health);

  boolean isInCobweb();

  boolean isInWater();

  KelpPlayer chat(String message);

  default KelpPlayer clearChat() {
    for (int i = 0; i < 103; i++) {
      sendMessage(" ");
    }
    return this;
  }

  boolean mayFly();

  String getDisplayName();

  KelpPlayer setDisplayName();

  String getTabListName();

  KelpPlayer setTabListName();

  KelpPlayer setCompassTarget(KelpLocation target);

  KelpPlayer kickPlayer(String kickMessage);

  boolean isSneaking();

  KelpPlayer setSneaking(boolean sneaking);

  default KelpPlayer toggleSneak() {
    setSneaking(!isSneaking());
    return this;
  }

  default KelpPlayer sneak() {
    setSneaking(true);
    return this;
  }

  default KelpPlayer unSneak() {
    setSneaking(false);
    return this;
  }

  boolean isSprinting();

  KelpPlayer setSprinting(boolean sneaking);

  default KelpPlayer toggleSprinting() {
    setSneaking(!isSneaking());
    return this;
  }

  default KelpPlayer sprint() {
    setSprinting(true);
    return this;
  }

  default KelpPlayer unSprint() {
    setSprinting(false);
    return this;
  }

  boolean isSleepingIgnored();

  default KelpPlayer ignoreSleeping() {
    setSleepingIgnored(true);
    return this;
  }

  default KelpPlayer unIgnoreSleeping() {
    setSleepingIgnored(false);
    return this;
  }

  default KelpPlayer toggleIgnoreSleeping() {
    setSleepingIgnored(!isSleepingIgnored());
    return this;
  }

  KelpPlayer setSleepingIgnored(boolean sleepingIgnored);

  KelpPlayer setRelativePlayerTime(long time);

  KelpPlayer setPlayerTime(long time);

  long getPlayerTime();

  long getPlayerTimeOffset();

  boolean isPlayerTimeRelative();

  KelpPlayer resetPlayerTime();

  default KelpPlayer giveExperience(int amount) {
    setExperience(getExperience() + amount);
    return this;
  }

  default KelpPlayer giveExperienceLevels(int amount) {
    setLevel(getLevel() + amount);
    return this;
  }

  float getExperience();

  KelpPlayer setExperience(float experience);

  KelpPlayer setLevel(int level);

  int getLevel();

  int getTotalExperience();

  KelpPlayer setTotalExperience(int experience);

  float getExhaustionLevel();

  KelpPlayer setExhaustionLevel(float exhaustionLevel);

  KelpPlayer setSaturationLevel(float saturationLevel);

  float getSaturationLevel();

  int getFoodLevel();

  KelpPlayer setFoodLevel(int foodLevel);

  KelpPlayer setAllowFlight(boolean allowed);

  KelpPlayer allowFlying();

  KelpPlayer disallowFlying();

  KelpPlayer hidePlayer(KelpPlayer toHide);

  KelpPlayer showPlayer(KelpPlayer toShow);

  boolean canSee(KelpPlayer toCheck);

  boolean isFlying();

  default KelpPlayer makeFlying() {
    setFlying(true);
    return this;
  }

  default KelpPlayer stopFlying() {
    setFlying(false);
    return this;
  }

  default KelpPlayer toggleFlying() {
    setFlying(!isFlying());
    return this;
  }

  KelpPlayer setFlying(boolean flying);

  KelpPlayer setFlySpeed(float flySpeed);

  float getFlySpeed();

  float getWalkSpeed();

  KelpPlayer setResourcePack(String url);

  KelpPlayer setResourcePack(String url, byte[] hash);

  boolean isHealthScaled();

  KelpPlayer setHealthScale(double healthScale) ;

  double getHealthScale();

  KelpPlayer resetTitle();

  KelpPlayer setClientViewDistanceInternally(int clientViewDistance);

  int getClientViewDistance();

  KelpPlayer setClientLanguageInternally(String clientLanguage);

  String getClientLanguage();

  KelpPlayer setPlayerChatVisibilityInternally(PlayerChatVisibility playerChatVisibility);

  PlayerChatVisibility getPlayerChatVisibility();

  KelpPlayer setPlayerChatColorEnabledInternally(boolean playerChatColorEnabled);

  boolean isPlayerChatColorEnabled();

  KelpPlayer setTabListHeader(String header);

  KelpPlayer setTabListFooter(String footer);

  default KelpPlayer setTabListHeaderAndFooter(String header, String footer) {
    setTabListHeader(header).setTabListFooter(footer);
    return this;
  }

  String getTabListFooter();

  String getTabListHeader();

  boolean isOperator();

  default KelpPlayer makeOperator() {
    setOperator(true);
    return this;
  }

  default KelpPlayer removeOperator() {
    setOperator(false);
    return this;
  }

  default KelpPlayer toggleOperator() {
    setOperator(!isOperator());
    return this;
  }

  KelpPlayer setOperator(boolean operator);

  KelpPlayer grantPermission(String permissionName);

  KelpPlayer removePermission(String permissionName);

  boolean hasPermission(String permissionName);

  boolean isBannedByBukkit();

  boolean isWhitelisted();

  default KelpPlayer whitelist() {
    setWhitelisted(true);
    return this;
  }

  default KelpPlayer removeFromWhitelist() {
    setWhitelisted(false);
    return this;
  }

  default KelpPlayer toggleWhitelist() {
    setWhitelisted(!isWhitelisted());
    return this;
  }

  KelpPlayer setWhitelisted(boolean whitelisted);

  KelpPlayer sendMessage(String message);

  default KelpPlayer sendMessages(String... messages) {
    for (String message : messages) {
      sendMessage(message);
    }
    return this;
  }

  default KelpPlayer sendMessages(Collection<String> messages) {
    for (String message : messages) {
      sendMessage(message);
    }
    return this;
  }

  default KelpPlayer sendPrefixedMessages(String prefix, String... messages) {
    for (String message : messages) {
      sendMessage(prefix + message);
    }
    return this;
  }

  default KelpPlayer sendPrefixedMessages(String prefix, Collection<String> messages) {
    for (String message : messages) {
      sendMessage(prefix + message);
    }
    return this;
  }

  default KelpPlayer sendCenteredMessage(String message) {
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
    return this;
  }

  default KelpPlayer sendCenteredMessages(String... messages) {
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
    return this;
  }

  default KelpPlayer sendCenteredMessages(Collection<String> messages) {
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
    return this;
  }

  default KelpPlayer sendCenteredMessages(String header, String footer, String... messages) {
    if (header != null) {
      this.sendMessage(header);
    }
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
    if (footer != null) {
      this.sendMessage(footer);
    }
    return this;
  }

  default KelpPlayer sendCenteredMessages(String header, Collection<String> messages, String footer) {
    if (header != null) {
      this.sendMessage(header);
    }
    for (String s : messages) {
      this.sendCenteredMessage(s);
    }
    if (footer != null) {
      this.sendMessage(footer);
    }
    return this;
  }

  /**
   * Sends a boss bar to the player by spawning a boss entity near it. If you use this
   * method in 1.8, please keep in mind that bar colors other than {@code PURPLE} and bar styles
   * other than {@code SOLID} are not supported.
   *
   * @param message   The message you want to be displayed above the boss bar.
   * @param health    How much the boss bar should be loaded (equivalent to how much
   *                  health the boss entity has. 300f is a full boss bar and 0f an empty one).
   * @param barColor  The color of the boss bar. Please note that in 1.8 only
   *                  {@code PURPLE} is allowed. If you use any color, no exception
   *                  is thrown but purple will be chosen automatically.
   * @param barStyle  The style of the boss bar (how many segments?, ...). Note that
   *                  in 1.8 only {@code SOLID} is supported. If you use any different
   *                  style, no exception will be thrown, but {@code SOLID} is chosen
   *                  automatically.
   */
  KelpPlayer sendBossBar(String message, float health, BossBarColor barColor, BossBarStyle barStyle);

  /**
   * Sends a boss bar to the player by spawning a boss entity near it.
   * This method uses {@link BossBarColor#PURPLE} and {@link BossBarStyle#SOLID}
   * in order to be compatible with all versions. If you want to use another style or color
   * you can use {@link #sendBossBar(String, float, BossBarColor, BossBarStyle)} instead.
   *
   * @param message The message you want to be displayed above the boss bar.
   */
  default KelpPlayer sendBossBar(String message) {
    return sendBossBar(message, 300f, BossBarColor.PURPLE, BossBarStyle.SOLID);
  }

  /**
   * Sets the progress of the player's boss bar by modifying the
   * health of the boss bar entity. As withers are used for that
   * purpose, the maximum value {@code 300f} represents full boss
   * bar and {@code 0f} would be an empty boss bar (equivalent to
   * the wither dieing.)
   *
   * @param health The health of the boss bar entity.
   */
  KelpPlayer setBossBarProgressHealth(float health);

  /**
   * Sets the progress of the player's boss bar, where 1 means
   * that the bar is fully loaded and 0 means the bar is completely
   * unloaded (equivalent to the boss entity being killed).
   *
   * @param percentage The percentage value of the progress between 0 and 1.
   */
  default KelpPlayer setBossBarProgress(double percentage) {
    float health = 300f * (float) percentage;
    setBossBarProgressHealth(health);
    return this;
  }

  /**
   * Sets the progress of the player's boss bar. This method
   * automatically calculates the percentage value based on the
   * value representing your maximum and the current state you want
   * to display. Example: If you have a 60 seconds timer and there
   * are still 20 seconds to wait, you pass 60 seconds as your maximum
   * and 40 seconds as your current value.
   *
   * @param current The current state of reaching the maximum in absolute numbers.
   * @param max     The maximum value that is reachable for parameter {@code current}.
   */
  default KelpPlayer setBossBarProgress(int current, int max) {
    double percentage = (double) current / (double) max;
    setBossBarProgress(percentage);
    return this;
  }

  /**
   * Sets the progress of the player's boss bar. This method
   * automatically calculates the percentage value based on the
   * value representing your maximum and the current state you want
   * to display. Example: If you have a 60 seconds timer and there
   * are still 20 seconds to wait, you pass 60 seconds as your maximum
   * and 40 seconds as your current value.
   *
   * @param current The current state of reaching the maximum in absolute numbers.
   * @param max     The maximum value that is reachable for parameter {@code current}.
   */
  default KelpPlayer setBossBarProgress(double current, double max) {
    double percentage = current / max;
    setBossBarProgress(percentage);
    return this;
  }

  /**
   * Makes the boss bar disappear for the player.
   */
  KelpPlayer removeBossBar();

  /**
   * Sends an interactive message to the player. An interactive message is a message
   * the player can click on and events (execute a command, open a url, ...) are triggered.
   * You can also add hover events to it. You can add as many components as you want.
   * More detailed information about how to build an interactive message can be found out
   * in {@link InteractiveMessage}.
   *
   * @param interactiveMessage The interactive message you want to send to the player.
   */
  KelpPlayer sendInteractiveMessage(InteractiveMessage interactiveMessage);

  /**
   * Gets the bukkit instance of this player.
   * This can be used to access bukkit methods if you cannot
   * find the appropriate kelp equivalent in this class.
   *
   * @return The instance of the bukkit player equivalent to this kelp player.
   */
  Player getBukkitPlayer();

  /**
   * This class allows for hidden access for dependencies used in the
   * {@link KelpPlayer} class. Instead of creating interface methods
   * that should not be publicly available, they are wrapped into this
   * class as private interface methods are only compatible with Java
   * 1.9 upwards.
   *
   * This should give the incentive to developers to avoid using
   * one of those methods as version templates are not safe and might change
   * over time.
   *
   * @author pxav
   */
  final class Dependencies {
    private static SignPromptVersionTemplate getSignPromptVersionTemplate() {
      return KelpPlugin.getInjector().getInstance(SignPromptVersionTemplate.class);
    }
    private static KelpInventoryRepository getInventoryRepository() {
      return KelpPlugin.getInjector().getInstance(KelpInventoryRepository.class);
    }
    private static ParticleVersionTemplate getParticleVersionTemplate() {
      return KelpPlugin.getInjector().getInstance(ParticleVersionTemplate.class);
    }
    private static AnvilPromptVersionTemplate getAnvilPromptVersionTemplate() {
      return KelpPlugin.getInjector().getInstance(AnvilPromptVersionTemplate.class);
    }
    private static ChatPromptVersionTemplate getChatPromptVersionTemplate() {
      return KelpPlugin.getInjector().getInstance(ChatPromptVersionTemplate.class);
    }
  }

}
