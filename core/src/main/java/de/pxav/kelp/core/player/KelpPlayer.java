package de.pxav.kelp.core.player;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.type.general.HumanEntity;
import de.pxav.kelp.core.entity.type.general.ProjectileLauncher;
import de.pxav.kelp.core.event.kelpevent.sidebar.KelpSidebarRemoveEvent;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.type.InventoryOwner;
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

import java.net.InetSocketAddress;
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
public interface KelpPlayer extends HumanEntity<KelpPlayer>, ProjectileLauncher<KelpPlayer>, InventoryOwner {

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
    return this;
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

  /**
   * Sends an action bar message to the player.
   * The action bar is a line of text, which is displayed
   * above the player's hotbar.
   *
   * @param message The message you want to send.
   */
  KelpPlayer sendActionbar(String message);

  /**
   * Sends a title to a player. A title is a big text displayed
   * right in the middle of the player's screen.
   *
   * @param title The upper title text (will be displayed slightly bigger than the sub title).
   * @param subTitle The lower title text (will be displayed slightly smaller than the main title).
   * @param fadeIn How long should it take to fade the title in? (in ticks)
   * @param stay How long should the title stay in 100% opacity? (in ticks)
   * @param fadeOut How long should it take to fade the title out? (in ticks)
   */
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

  /**
   * Sets the player's health.
   *
   * @param health How many health points the player should have.
   *               2 health points equal 1 heart.
   *               So 20 health points equal the full 10 hearts.
   */
  KelpPlayer setHealth(int health);

  /**
   * Checks if the player is currently stuck in a cobweb.
   *
   * @return {@code true} if the player is currently stuck in a cobweb.
   */
  boolean isInCobweb();

  /**
   * Checks if the player is currently located in water.
   *
   * @return {@code true} if the player is currently in water.
   */
  boolean isInWater();

  /**
   * Sends a chat message from the given player.
   * This means you can send a message as if the player itself
   * typed in this message. This also works with commands, if
   * you add a slash in front of the message.
   *
   * @param message The message you want to send.
   */
  KelpPlayer chat(String message);

  default KelpPlayer clearChat() {
    for (int i = 0; i < 103; i++) {
      sendMessage(" ");
    }
    return this;
  }

  /**
   * Gets the socket address of a specific player.
   *
   * @return The {@code InetSocketAddress} object of the player's address.
   */
  InetSocketAddress getSocketAddress();

  /**
   * Determines if the Player is allowed to fly via
   * jump key double-tap like in creative mode.
   *
   * If a player flies without permission, they will get kicked
   * by the server automatically.
   *
   * @return {@code true} if the player is allowed to fly.
   */
  boolean mayFly();

  /**
   * Gets the display name of a player.
   * The display name is a name which - unlike the normal name -
   * can be modified during the server runtime. You can use this
   * to include custom prefixes, ...
   *
   * @return The display name.
   */
  String getDisplayName();

  /**
   * Sets the display name of a player
   * The display name is a name which - unlike the normal name -
   * can be modified during the server runtime. You can use this
   * to include custom prefixes, ...
   *
   * @param displayName The display name you want to set.
   */
  KelpPlayer setDisplayName(String displayName);

  /**
   * Gets the tab-list name of the player.
   * The tab-list name is the name which is used to represent
   * the player in the tab-list of online players.
   *
   * @return The tab-list name.
   */
  String getTabListName();

  /**
   * Sets the tab-list name of the player.
   * The tab-list name is the name which is used to represent
   * the player in the tab-list of online players.
   *
   * @param tabListName The tab-list name you want to set.
   */
  KelpPlayer setTabListName(String tabListName);

  /**
   * Sets the compass target of the player.
   * The compass target is the location, where the compass needle
   * points to. By default this is the spawn location of the world,
   * but you could set this to specific player locations as well.
   *
   * @param target The location, where the compass should point to.
   */
  KelpPlayer setCompassTarget(KelpLocation target);

  /**
   * Gets the compass target of the player.
   * The compass target is the location, where the compass needle
   * points to. By default this is the spawn location of the world.
   *
   * @return The target location of the player's compass.
   */
  KelpPlayer getCompassTarget();

  /**
   * Kicks the given player from the server.
   *
   * @param kickMessage The message, which should be received by the player.
   *                    Could also be named kick reason.
   */
  KelpPlayer kickPlayer(String kickMessage);

  /**
   * Checks if the player is currently sneaking/crouching.
   * @return {@code true} if the player is sneaking.
   */
  boolean isSneaking();

  /**
   * Modifies the sneak state of the player.
   *
   * @param sneaking  {@code true} if you want to make the player sneak.
   *                  {@code false}, if not.
   */
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

  /**
   * Checks if the player is currently sprinting.
   * Sprinting is active when the player has double-pressed the
   * walking key or pressed the walking and sprinting key at
   * once.
   *
   * @return {@code true} if the player is currently sprinting.
   */
  boolean isSprinting();

  /**
   * Changes the sprinting state of a player.
   * Sprinting is active when the player has double-pressed the
   * walking key or pressed the walking and sprinting key at
   * once.
   *
   * @param sprinting {@code true} if the player should be sprinting.
   */
  KelpPlayer setSprinting(boolean sprinting);

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

  /**
   * Checks if the player is ignored when the server checks who is sleeping.
   *
   * When it is night, normally all players have to sleep so that the
   * time can jump to morning. If you don't want all players to sleep,
   * you can give specific players this flag and they don't have to sleep
   * anymore.
   *
   * If you give this flag to all players, nothing will happen at night.
   * So there has to be at least one player sleeping at night.
   *
   * @return {@code true} if the player has the ignore flag.
   */
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

  /**
   * Sets the player ignored when the server checks who is sleeping.
   *
   * When it is night, normally all players have to sleep so that the
   * time can jump to morning. If you don't want all players to sleep,
   * you can give specific players this flag and they don't have to sleep
   * anymore.
   *
   * If you give this flag to all players, nothing will happen at night.
   * So there has to be at least one player sleeping at night.
   *
   * @param sleepingIgnored {@code true} if the player should be ignored when sleeping.
   */
  KelpPlayer setSleepingIgnored(boolean sleepingIgnored);

  KelpPlayer setRelativePlayerTime(long time);

  /**
   * Sets the player time. Each player can have an individual time
   * - even if they are in the same world.
   *
   * @param time      If {@code relative} is set to {@code true}, this is the
   *                  time offset to the server time in ticks. If {@code relative}
   *                  is set to {@code false} it is the absolute day time ticks.
   */
  KelpPlayer setPlayerTime(long time);

  /**
   * Gets the current player time. Each player can have an
   * individual time - even if they are in the same world.
   *
   * @return The current time of the player in ticks.
   */
  long getPlayerTime();

  /**
   * If the player time was set with {@code relative} to {@code true},
   * then it will get the offset of the player time to the current server
   * time. If {@code relative} was set to {@code false}, it will simply
   * return the current player time.
   *
   * @return The player's time (offset).
   */
  long getPlayerTimeOffset();

  /**
   * Checks if the player time set for the player is relative
   * to the server time. More information can be found
   * at {@code #setPlayerTime(player, time, relative)}
   *
   * @return {@code true} if player time is relative to server time.
   */
  boolean isPlayerTimeRelative();

  /**
   * Resets the player time back to the current server
   * time. So the times of both are synchronized again.
   *
   */
  KelpPlayer resetPlayerTime();

  /**
   * Gives the player the given amount of experience.
   * This method simply adds the given amount to the player's
   * current exp count and does not overwrite anything.
   *
   * @param amount The amount of experience to give.
   */
  default KelpPlayer giveExperience(int amount) {
    setExperience(getExperience() + amount);
    return this;
  }

  /**
   * Gives or takes the player the given amount of experience.
   * Negative amounts express that levels should be taken
   * away from the player, positive amounts will be added.
   *
   * @param amount The amount of levels to give or take.
   */
  default KelpPlayer giveExperienceLevels(int amount) {
    setLevel(getLevel() + amount);
    return this;
  }

  /**
   * Gets the current experience of the given player.
   *
   * Experience is a percentage value (ranging from 0 to 1)
   * indicating the progress to the next full level.
   * 0 means he has just reached a new level and has made no
   * progress since then, wile 1 says the player is just about
   * to reach a new level.
   *
   * Inside Minecraft, the experience is represented by the
   * green bar above the hotbar.
   *
   * @return The current experience progress (from 0 to 1).
   */
  float getExperience();

  /**
   * Sets the current experience of the given player.
   *
   * Experience is a percentage value (ranging from 0 to 1)
   * indicating the progress to the next full level.
   * 0 means he has just reached a new level and has made no
   * progress since then, wile 1 says the player is just about
   * to reach a new level.
   *
   * Inside Minecraft, the experience is represented by the
   * green bar above the hotbar.
   *
   * @param experience  The experience value you want to set.
   *                    May range from 0 to 1.
   */
  KelpPlayer setExperience(float experience);

  /**
   * Sets the current amount of the player's levels.
   *
   * Levels are reached when the experience count reaches 1.
   * The amount of levels is represented by the small green
   * number above the level bar.
   *
   * @param level   The new level count to set.
   */
  KelpPlayer setLevel(int level);

  /**
   * Gets the current amount of the player's levels.
   *
   * Levels are reached when the experience count reaches 1.
   * The amount of levels is represented by the small green
   * number above the level bar.
   *
   * @return The current amount of levels.
   */
  int getLevel();

  /**
   * Gets the players total experience points.
   *
   * This refers to the total amount of experience
   * the player has collected over time and is not currently
   * displayed to the client.
   *
   * @return The total experience points amount.
   */
  int getTotalExperience();

  /**
   * Sets the players total experience points.
   *
   * This refers to the total amount of experience
   * the player has collected over time and is not currently
   * displayed to the client.
   *
   * @param experience  The new amount of total experience.
   */
  KelpPlayer setTotalExperience(int experience);

  /**
   * Gets the player's current exhaustion level.
   * Exhaustion controls how fast the food level drops.
   * While you have a certain amount of exhaustion,
   * your saturation will drop to zero, and then your
   * food will drop to zero.
   *
   * @return The exhaustion level of the current player.
   */
  float getExhaustionLevel();

  /**
   * Sets the player's current exhaustion level.
   * Exhaustion controls how fast the food level drops.
   * While you have a certain amount of exhaustion,
   * your saturation will drop to zero, and then your
   * food will drop to zero.
   *
   * @param exhaustionLevel The exhaustion level you want to set.
   */
  KelpPlayer setExhaustionLevel(float exhaustionLevel);

  /**
   * Sets the saturation level of a player.
   * Saturation is a buffer for food level.
   * Your food level will not drop if you are saturated > 0.
   *
   * @param saturationLevel  The saturation level you want to set.
   */
  KelpPlayer setSaturationLevel(float saturationLevel);

  /**
   * Gets the saturation level of a player.
   * Saturation is a buffer for food level.
   * Your food level will not drop if you are saturated > 0.
   *
   * @return The saturation of the player.
   */
  float getSaturationLevel();

  /**
   * Gets the current food level of the player. The
   * food level indicates how full the food bar is.
   * 20 means a full food bar, 0 an empty food bar.
   *
   * @return The food level of the player.
   */
  int getFoodLevel();

  /**
   * Sets the food level of a player.
   * The food level indicates how full the food bar is.
   * 20 means a full food bar, 0 an empty food bar.
   *
   * @param foodLevel The absolute food level you want to set.
   */
  KelpPlayer setFoodLevel(int foodLevel);

  /**
   * Sets if the player is allowed to fly as if he was in creative
   * mode
   *
   * @param allowed {@code true} if you want to allow, {@code false}
   *                    if not.
   */
  KelpPlayer setAllowFlight(boolean allowed);

  KelpPlayer allowFlying();

  KelpPlayer disallowFlying();

  /**
   * Hides a player from another player, so they become invisible
   * for the other player.
   *
   * @param toHide The player you want to hide.
   */
  KelpPlayer hidePlayer(KelpPlayer toHide);

  /**
   * Shows a player to another player again, so they become visible
   * for the other player.
   *
   * @param toShow The player you want to show.
   */
  KelpPlayer showPlayer(KelpPlayer toShow);

  /**
   * Checks if the given player can see the targeted player.
   * This does not check if the player currently really sees
   * the given target player, but if the player is not hidden.
   *
   * @param toCheck  The player to check if he is visible for the
   *                 other player.
   * @return {@code true} if {@code player} can see {@code toCheck}.
   */
  boolean canSee(KelpPlayer toCheck);

  /**
   * Checks if the player is currently flying.
   *
   * @return {@code true} if the player is flying.
   */
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

  /**
   * Changes the flying state of the player.
   *
   * @param flying {@code true} if you want to make the player fly.
   *               {@code false} if you don't want to make the player fly.
   */
  KelpPlayer setFlying(boolean flying);

  /**
   * Sets the current fly speed of a player. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @param flySpeed  The desired fly speed. The value may
   *                  range from -1 to 1.
   */
  KelpPlayer setFlySpeed(float flySpeed);

  /**
   * Gets the current speed a player can fly. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @return  The current fly speed of a player.
   *          Value can range from -1 to 1
   */
  float getFlySpeed();

  /**
   * Gets the current speed a player can walk. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @return  The current walk speed of a player.
   *          Value can range from -1 to 1
   */
  float getWalkSpeed();

  /**
   * Sets the current walk speed of a player. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @param walkSpeed The desired walk speed. The value may
   *                  range from -1 to 1.
   */
  KelpPlayer setWalkSpeed(float walkSpeed);

  KelpPlayer resetWalkSpeed();

  /**
   * Request that the player's client download and switch resource packs.
   *
   * The player's client will download the new resource pack asynchronously
   * in the background, if the request was accepted, and will automatically switch to it once the download
   * is complete. If the client has downloaded and cached a resource pack
   * with the same hash in the past it will not download but directly apply
   * the cached pack. When this request is sent for the very first time
   * from a given server, the client will first display a confirmation GUI
   * to the player before proceeding with the download.
   *
   * @param url  The URL from which the client will download the resource pack.
   *             The string must contain only US-ASCII characters
   *             and should be encoded as per RFC 1738.
   */
  KelpPlayer setResourcePack(String url);

  /**
   * Request that the player's client download and switch resource packs.
   *
   * The player's client will download the new resource pack asynchronously
   * in the background, if the request was accepted, and will automatically switch to it once the download
   * is complete. If the client has downloaded and cached a resource pack
   * with the same hash in the past it will not download but directly apply
   * the cached pack. When this request is sent for the very first time
   * from a given server, the client will first display a confirmation GUI
   * to the player before proceeding with the download.
   *
   * @param url  The URL from which the client will download the resource pack.
   *             The string must contain only US-ASCII characters
   *             and should be encoded as per RFC 1738.
   * @param hash The sha1 hash sum of the resource pack file
   *             which is used to apply a cached version of the pack
   *             directly without downloading if it is available.
   *             Has to be 20 bytes long!
   */
  KelpPlayer setResourcePack(String url, byte[] hash);

  /**
   * Sets if the player is shown the scaled health bar.
   * If you modify your health scale with {@code setHealthScale},
   * you have to say the server to send this new scaled health
   * to the player.
   *
   * @param scaled {@code true} whether the health should be scaled.
   */
  KelpPlayer setHealthScaled(boolean scaled);

  /**
   * Checks if the player has received the scaled health bar
   * from the server.
   *
   * @return {@code true} if the player's health bar scale is up to date.
   */
  boolean isHealthScaled();

  /**
   * Sets the health scale of the given player. The health
   * scale is the maximum amount of hearts displayed to the
   * client.
   * 2 means one heart is displayed. If you choose values
   * above 20, the player will get additional hearts.
   * Consider changing the max health as well.
   *
   * This method will automatically set {@code setHealthScaled} to
   * {@code true}.
   *
   * @param healthScale The amount of scaled health you want to set.
   */
  KelpPlayer setHealthScale(double healthScale) ;

  /**
   * Gets the number of scaled health points,
   * which are currently displayed to the client.
   *
   * @return  The number of scaled health if health
   *          scaling was set to {@code true} earlier.
   */
  double getHealthScale();

  /**
   * Cancels the title animation for a player. The current
   * title is removed immediately.
   */
  KelpPlayer resetTitle();

  KelpPlayer setClientViewDistanceInternally(int clientViewDistance);

  int getClientViewDistance();

  KelpPlayer setClientLanguageInternally(String clientLanguage);

  String getClientLanguage();

  KelpPlayer setPlayerChatVisibilityInternally(PlayerChatVisibility playerChatVisibility);

  PlayerChatVisibility getPlayerChatVisibility();

  KelpPlayer setPlayerChatColorEnabledInternally(boolean playerChatColorEnabled);

  boolean isPlayerChatColorEnabled();

  default KelpPlayer setTabListHeader(String header) {
    setTabListHeaderAndFooter(header, getTabListFooter());
    return this;
  }

  default KelpPlayer setTabListFooter(String footer) {
    setTabListHeaderAndFooter(getTabListHeader(), footer);
    return this;
  }

  /**
   * Sets the tab header and footer of player. The tab header is
   * a text displayed above the player list in the tab, while
   * the tab footer is a message displayed below the player list.
   *
   * The messages may contain '\n' to create new lines inside the
   * message.
   *
   * @param header The header message you want to send.
   * @param footer The footer message you want to send.
   */
  KelpPlayer setTabListHeaderAndFooter(String header, String footer);

  String getTabListFooter();

  String getTabListHeader();

  /**
   * Gets the version number of the client's protocol. You
   * can convert this information to the release name with the
   * help of this list: https://wiki.vg/Protocol_version_numbers
   * Please note that you should use the versions after the Netty
   * rewrite.
   *
   * @return The protocol version number.
   */
  int getProtocolVersion();

  /**
   * Checks if the given player is a server operator. A server operator
   * is a player, who has all permissions and can execute every
   * command.
   *
   * @return {@code true} if the player is a server operator.
   */
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

  /**
   * Sets the player to a server operator. A server operator
   * is a player, who has all permissions and can execute every
   * command.
   *
   * @param operator   {@code true} If you want to make the player an operator.
   */
  KelpPlayer setOperator(boolean operator);

  /**
   * Gives the player the desired permission.
   *
   * @param permission        The name of the permission you want to give the player.
   */
  KelpPlayer grantPermission(String permission);

  /**
   * Removes the specified permission from the given player.
   *
   * @param permission  The name of the permission you want to remove.
   */
  KelpPlayer removePermission(String permission);

  /**
   * Checks if the given player has the desired permission.
   *
   * @param permission  The permission you want to check for.
   * @return {@code true} if the player has the permission.
   */
  boolean hasPermission(String permission);

  /**
   * Checks if the given player is currently banned from the server.
   * This does only check if the player was banned by the
   * bukkit server using the normal {@code /ban} command. If another
   * plugin has banned the player, this is ignored.
   *
   * @return {@code true} if the player has been banned by the bukkit server.
   */
  boolean isBannedByBukkit();

  /**
   * Checks if the player is on the bukkit whitelist.
   *
   * @return {@code true} if the player is whitelisted.
   */
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

  /**
   * Sets the player whitelisted or not whitelisted to the server.
   *
   * The whitelist determines which players are allowed to join the server
   * and which are not. It has to be enabled manually by the server owner.
   *
   * @param whitelisted   {@code true} if the player should be whitelisted.
   */
  KelpPlayer setWhitelisted(boolean whitelisted);

  /**
   * Sends the given player a message into their chat.
   *
   * @param message   The message itself. May contain color codes.
   */
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
