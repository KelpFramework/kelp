package de.pxav.kelp.implementation1_8.player;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.inject.Inject;
import de.pxav.kelp.core.common.StringUtils;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.player.PlayerVersionTemplate;
import de.pxav.kelp.core.player.bossbar.BossBarColor;
import de.pxav.kelp.core.player.bossbar.BossBarStyle;
import de.pxav.kelp.core.player.message.InteractiveMessage;
import de.pxav.kelp.core.player.message.MessageClickAction;
import de.pxav.kelp.core.player.message.MessageComponent;
import de.pxav.kelp.core.player.message.MessageHoverAction;
import de.pxav.kelp.core.scheduler.synchronize.ServerMainThread;
import de.pxav.kelp.core.sound.KelpSound;
import de.pxav.kelp.core.sound.SoundRepository;
import de.pxav.kelp.core.version.Versioned;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 * @author Etrayed
 */
@Versioned
public class VersionedPlayer extends PlayerVersionTemplate {

  private SoundRepository soundRepository;
  private BossBarLocationUpdater bossBarLocationUpdater;
  private JavaPlugin plugin;
  private StringUtils stringUtils;
  private KelpLogger logger;

  @Inject
  public VersionedPlayer(SoundRepository soundRepository,
                         JavaPlugin plugin,
                         BossBarLocationUpdater bossBarLocationUpdater,
                         StringUtils stringUtils,
                         KelpLogger logger) {
    this.soundRepository = soundRepository;
    this.plugin = plugin;
    this.bossBarLocationUpdater = bossBarLocationUpdater;
    this.stringUtils = stringUtils;
    this.logger = logger;
  }

  /**
   * Sends a title to a player. A title is a big text displayed
   * right in the middle of the player's screen.
   *
   * @param player The player who should see the title.
   * @param title The upper title text (will be displayed slightly bigger than the sub title).
   * @param subTitle The lower title text (will be displayed slightly smaller than the main title).
   * @param fadeIn How long should it take to fade the title in? (in ticks)
   * @param stay How long should the title stay in 100% opacity? (in ticks)
   * @param fadeOut How long should it take to fade the title out? (in ticks)
   */
  @Override
  public void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
    final IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
    final IChatBaseComponent subComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
    final PacketPlayOutTitle titleOut = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, fadeIn, stay, fadeOut);
    final PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, subComponent);
    final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
    final PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subComponent);

    this.sendPacket(titleOut, player);
    this.sendPacket(subtitle, player);
    this.sendPacket(titlePacket, player);
    this.sendPacket(subtitlePacket, player);
  }

  /**
   * Sends an action bar message to the player.
   * The action bar is a line of text, which is displayed
   * above the player's hotbar.
   *
   * @param player  The player who should receive the message.
   * @param message The message you want to send.
   */
  @Override
  public void sendActionBar(Player player, String message) {
    PacketPlayOutChat packet =
            new PacketPlayOutChat(
                    IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte) 2);
    sendPacket(packet, player);
  }

  /**
   * Sets the tab header and footer of player. The tab header is
   * a text displayed above the player list in the tab, while
   * the tab footer is a message displayed below the player list.
   *
   * The messages may contain '\n' to create new lines inside the
   * message.
   *
   * @param player The player who should see the messages.
   * @param header The header message you want to send.
   * @param footer The footer message you want to send.
   */
  @Override
  public void sendTabHeaderAndFooter(Player player, String header, String footer) {
    if (header == null) header = "";
    if (footer == null) footer = "";

    IChatBaseComponent tabHeader =
            IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
    IChatBaseComponent tabFooter =
            IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabHeader);

    try {
      Field f = packet.getClass().getDeclaredField("b");
      f.setAccessible(true);
      f.set(packet, tabFooter);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }

    sendPacket(packet, player);
  }

  /**
   * Plays a sound to the player.
   *
   * @param player    The player who should hear the sound.
   * @param sound     The sound you want to play.
   * @param location  The location, where the sound should come from.
   * @param volume    How loud the sound should be.
   * @param pitch     How strong the sound should be pitched.
   */
  @Override
  public void playSound(Player player, KelpSound sound, Location location, float volume, float pitch) {
    Sound bukkitSound = Sound.valueOf(soundRepository.getSound(sound));
    player.playSound(player.getLocation(), bukkitSound, volume, pitch);
  }

  /**
   * Sets the player's health.
   *
   * @param player The player whose health you want to change.
   * @param health How many health points the player should have.
   *               2 health points equal 1 heart.
   *               So 20 health points equal the full 10 hearts.
   */
  @Override
  public void setHealth(Player player, int health) {
    player.setHealth(health);
  }

  /**
   * Teleports the player to the given location.
   *
   * @param player    The player you want to teleport.
   * @param location  The location you want to teleport the player to.
   */
  @Override
  public void teleport(Player player, Location location) {
    player.teleport(location);
  }

  /**
   * @param player The player whose UUID you want to get.
   * @return The player's uuid.
   */
  public UUID getUniqueId(Player player) {
    return player.getUniqueId();
  }

  /**
   * @param player The player whose location you want to get
   * @return The location the player is currently at.
   */
  @Override
  public Location getLocation(Player player) {
    return null;
  }

  /**
   * Checks if the player is currently located in water.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently in water.
   */
  @Override
  public boolean isInWater(Player player) {
    try {
      Field inWaterField = Entity.class.getDeclaredField("inWater");

      inWaterField.setAccessible(true);

      return inWaterField.getBoolean(((CraftEntity) player).getHandle());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * Checks if the player is currently stuck in a cobweb.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently stuck in a cobweb.
   */
  @Override
  public boolean isInCobweb(Player player) {
    try {
      Field hField = Entity.class.getDeclaredField("H");

      hField.setAccessible(true);

      return hField.getBoolean(((CraftEntity) player).getHandle());
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * Sends a chat message from the given player.
   * This means you can send a message as if the player itself
   * typed in this message. This also works with commands, if
   * you add a slash in front of the message.
   *
   * @param player  The player from whom the message should be sent.
   * @param message The message you want to send.
   */
  @Override
  public void chat(Player player, String message) {
    player.chat(message);
  }

  /**
   * Gets the socket address of a specific player.
   *
   * @param player The player whose socket address you want to get.
   * @return The {@code InetSocketAddress} object of the player's address.
   */
  @Override
  public InetSocketAddress getSocketAddress(Player player) {
    return player.getAddress();
  }

  /**
   * Determines if the Player is allowed to fly via
   * jump key double-tap like in creative mode.
   *
   * If a player flies without permission, they will get kicked
   * by the server automatically.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is allowed to fly.
   */
  @Override
  public boolean getAllowFlight(Player player) {
    return player.getAllowFlight();
  }

  /**
   * Gets the display name of a player.
   * The display name is a name which - unlike the normal name -
   * can be modified during the server runtime. You can use this
   * to include custom prefixes, ...
   *
   * @param player The player whose display name you want to get.
   * @return The display name.
   */
  @Override
  public String getDisplayName(Player player) {
    return player.getDisplayName();
  }

  /**
   * Sets the display name of a player
   * The display name is a name which - unlike the normal name -
   * can be modified during the server runtime. You can use this
   * to include custom prefixes, ...
   *
   * @param player      The player whose display name you want to set.
   * @param displayName The display name you want to set.
   */
  @Override
  public void setDisplayName(Player player, String displayName) {
    player.setDisplayName(displayName);
  }

  /**
   * Gets the tab-list name of the player.
   * The tab-list name is the name which is used to represent
   * the player in the tab-list of online players.
   *
   * @param player The player whose tab-list name you want to get.
   * @return The tab-list name.
   */
  @Override
  public String getPlayerTabListName(Player player) {
    return player.getPlayerListName();
  }

  /**
   * Sets the tab-list name of the player.
   * The tab-list name is the name which is used to represent
   * the player in the tab-list of online players.
   *
   * @param player The player whose tab-list name you want to set.
   * @param tabListName The tab-list name you want to set.
   */
  @Override
  public void setPlayerTabListName(Player player, String tabListName) {
    player.setPlayerListName(tabListName);
  }

  /**
   * Gets the tab-list header of the player.
   * The tab-list header is a text displayed above the
   * list of online players, which is shown when you press tab.
   *
   * @param player The player whose header you want to get.
   * @return The tab-list header string.
   */
  @Override
  public String getPlayerListHeader(Player player) {
    return null;
  }

  /**
   * Gets the tab-list footer of the player.
   * The tab-list footer is a text displayed below the
   * list of online players, which is shown when you press tab.
   *
   * @param player The player whose footer you want to get.
   * @return The tab-list footer string.
   */
  @Override
  public String getPlayerListFooter(Player player) {
    return null;
  }

  @Override
  public void setPlayerListHeader(Player player, String header) {

  }

  @Override
  public void setPlayerListFooter(Player player, String footer) {

  }

  /**
   * Sets the compass target of the player.
   * The compass target is the location, where the compass needle
   * points to. By default this is the spawn location of the world,
   * but you could set this to specific player locations as well.
   *
   * @param player The player whose target location you want to update.
   * @param target The location, where the compass should point to.
   */
  @Override
  public void setCompassTarget(Player player, Location target) {
    player.setCompassTarget(target);
  }

  /**
   * Gets the compass target of the player.
   * The compass target is the location, where the compass needle
   * points to. By default this is the spawn location of the world.
   *
   * @param player The player whose compass target you want to set.
   * @return The target location of the player's compass.
   */
  @Override
  public Location getCompassTarget(Player player) {
    return player.getCompassTarget();
  }

  /**
   * Kicks the given player from the server.
   *
   * @param player      The player who should be kicked.
   * @param kickMessage The message, which should be received by the player.
   *                    Could also be named kick reason.
   */
  @Override
  public void kickPlayer(Player player, String kickMessage) {
    player.kickPlayer(kickMessage);
  }

  /**
   * Checks if the player is currently sneaking/crouching.
   * @return {@code true} if the player is sneaking.
   */
  @Override
  public boolean isSneaking(Player player) {
    return player.isSneaking();
  }

  /**
   * Modifies the sneak state of the player.
   *
   * @param player    The player whose sneak state you want to change.
   * @param sneaking  {@code true} if you want to make the player sneak.
   *                  {@code false}, if not.
   */
  @Override
  public void setSneaking(Player player, boolean sneaking) {
    player.setSneaking(sneaking);
  }

  /**
   * Checks if the player is currently sprinting.
   * Sprinting is active when the player has double-pressed the
   * walking key or pressed the walking and sprinting key at
   * once.
   *
   * @return {@code true} if the player is currently sprinting.
   */
  @Override
  public boolean isSprinting(Player player) {
    return player.isSprinting();
  }

  /**
   * Changes the sprinting state of a player.
   * Sprinting is active when the player has double-pressed the
   * walking key or pressed the walking and sprinting key at
   * once.
   *
   * @param player    The player you want to change the sprinting state of.
   * @param sprinting {@code true} if the player should be sprinting.
   */
  @Override
  public void setSprinting(Player player, boolean sprinting) {
    player.setSprinting(sprinting);
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
   * @param player  The player you want to change the ignoring state of.
   * @param ignored {@code true} if the player should be ignored when sleeping.
   */
  @Override
  public void setSleepingIgnored(Player player, boolean ignored) {
    player.setSleepingIgnored(ignored);
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
   * @param player  The player you want to change the ignoring state of.
   * @return {@code true} if the player has the ignore flag.
   */
  @Override
  public boolean isSleepingIgnored(Player player) {
    return player.isSleepingIgnored();
  }

  /**
   * Sets the player time. Each player can have an individual time
   * - even if they are in the same world.
   *
   * @param player    The player whose time you want to set.
   * @param time      If {@code relative} is set to {@code true}, this is the
   *                  time offset to the server time in ticks. If {@code relative}
   *                  is set to {@code false} it is the absolute day time ticks.
   * @param relative  {@code true} if the {@code time} should stay relative
   *                  to the server time.
   */
  @Override
  public void setPlayerTime(Player player, long time, boolean relative) {
    player.setPlayerTime(time, relative);
  }

  /**
   * Gets the current player time. Each player can have an
   * individual time - even if they are in the same world.
   *
   * @param player The player whose time you want to get.
   * @return The current time of the player in ticks.
   */
  @Override
  public long getPlayerTime(Player player) {
    return player.getPlayerTime();
  }

  /**
   * If the player time was set with {@code relative} to {@code true},
   * then it will get the offset of the player time to the current server
   * time. If {@code relative} was set to {@code false}, it will simply
   * return the current player time.
   *
   * @param player The player whose time (offset) you want to check.
   * @return The player's time (offset).
   */
  @Override
  public long getPlayerTimeOffset(Player player) {
    return player.getPlayerTimeOffset();
  }

  /**
   * Checks if the player time set for the player is relative
   * to the server time. More information can be found
   * at {@code #setPlayerTime(player, time, relative)}
   *
   * @param player The player you want to check.
   * @return {@code true} if player time is relative to server time.
   */
  @Override
  public boolean isPlayerTimeRelative(Player player) {
    return player.isPlayerTimeRelative();
  }

  /**
   * Resets the player time back to the current server
   * time. So the times of both are synchronized again.
   *
   * @param player The player whose time you want to reset.
   */
  @Override
  public void resetPlayerTime(Player player) {
    player.resetPlayerTime();
  }

  /**
   * Gives the player the given amount of experience.
   * This method simply adds the given amount to the player's
   * current exp count and does not overwrite anything.
   *
   * @param player The player you want to give the experience to.
   * @param amount The amount of experience to give.
   */
  @Override
  public void giveExperience(Player player, int amount) {
    player.giveExp(amount);
  }

  /**
   * Gives or takes the player the given amount of experience.
   * Negative amounts express that levels should be taken
   * away from the player, positive amounts will be added.
   *
   * @param player The player you want to give the levels to.
   * @param amount The amount of levels to give or take.
   */
  @Override
  public void giveExperienceLevels(Player player, int amount) {
    player.giveExpLevels(amount);
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
   * @param player The player whose experience you want to get.
   * @return The current experience progress (from 0 to 1).
   */
  @Override
  public float getExperience(Player player) {
    return player.getExp();
  }

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
   * @param player      The player whose experience you want to set.
   * @param experience  The experience value you want to set.
   *                    May range from 0 to 1.
   */
  @Override
  public void setExperience(Player player, float experience) {
    player.setExp(experience);
  }

  /**
   * Gets the current amount of the player's levels.
   *
   * Levels are reached when the experience count reaches 1.
   * The amount of levels is represented by the small green
   * number above the level bar.
   *
   * @param player The player whose levels you want to get.
   * @return The current amount of levels.
   */
  @Override
  public int getLevel(Player player) {
    return player.getLevel();
  }

  /**
   * Sets the current amount of the player's levels.
   *
   * Levels are reached when the experience count reaches 1.
   * The amount of levels is represented by the small green
   * number above the level bar.
   *
   * @param player  The player whose levels you want to set.
   * @param level   The new level count to set.
   */
  @Override
  public void setLevel(Player player, int level) {
    player.setLevel(level);
  }

  /**
   * Gets the players total experience points.
   *
   * This refers to the total amount of experience
   * the player has collected over time and is not currently
   * displayed to the client.
   *
   * @param player The player whose total experience you want to get.
   * @return The total experience points amount.
   */
  @Override
  public int getTotalExperience(Player player) {
    return player.getTotalExperience();
  }

  /**
   * Sets the players total experience points.
   *
   * This refers to the total amount of experience
   * the player has collected over time and is not currently
   * displayed to the client.
   *
   * @param player      The player whose total experience you want to get.
   * @param experience  The new amount of total experience.
   */
  @Override
  public void setTotalExperience(Player player, int experience) {
    player.setTotalExperience(experience);
  }

  /**
   * Gets the player's current exhaustion level.
   * Exhaustion controls how fast the food level drops.
   * While you have a certain amount of exhaustion,
   * your saturation will drop to zero, and then your
   * food will drop to zero.
   *
   * @param player The player you want to get the exhaustion level of.
   * @return The exhaustion level of the current player.
   */
  @Override
  public float getExhaustion(Player player) {
    return player.getExhaustion();
  }

  /**
   * Sets the player's current exhaustion level.
   * Exhaustion controls how fast the food level drops.
   * While you have a certain amount of exhaustion,
   * your saturation will drop to zero, and then your
   * food will drop to zero.
   *
   * @param exhaustionLevel The exhaustion level you want to set.
   */
  @Override
  public void setExhaustion(Player player, float exhaustionLevel) {
    player.setExhaustion(exhaustionLevel);
  }

  /**
   * Gets the saturation level of a player.
   * Saturation is a buffer for food level.
   * Your food level will not drop if you are saturated > 0.
   *
   * @param player The player whose saturation level you want to get.
   * @return The saturation of the player.
   */
  @Override
  public float getSaturation(Player player) {
    return player.getSaturation();
  }

  /**
   * Sets the saturation level of a player.
   * Saturation is a buffer for food level.
   * Your food level will not drop if you are saturated > 0.
   *
   * @param player      The player whose saturation level you want
   *                    to set.
   * @param saturation  The saturation level you want to set.
   */
  @Override
  public void setSaturation(Player player, float saturation) {
    player.setSaturation(saturation);
  }

  /**
   * Gets the current food level of the player. The
   * food level indicates how full the food bar is.
   * 20 means a full food bar, 0 an empty food bar.
   *
   * @param player The player whose food bar you want to change.
   * @return The food level of the player.
   */
  @Override
  public int getFoodLevel(Player player) {
    return player.getFoodLevel();
  }

  /**
   * Sets the food level of a player.
   * The food level indicates how full the food bar is.
   * 20 means a full food bar, 0 an empty food bar.
   *
   * @param player    The player whose food level you want to change.
   * @param foodLevel The absolute food level you want to set.
   */
  @Override
  public void setFoodLevel(Player player, int foodLevel) {
    player.setFoodLevel(foodLevel);
  }

  /**
   * Sets if the player is allowed to fly as if he was in creative
   * mode
   *
   * @param player      The player you want to allow/disallow flying
   *                    for.
   * @param allowFlight {@code true} if you want to allow, {@code false}
   *                    if not.
   */
  @Override
  public void setAllowFlight(Player player, boolean allowFlight) {
    player.setAllowFlight(allowFlight);
  }

  /**
   * Hides a player from another player, so they become invisible
   * for the other player.
   *
   * @param player The player who should not see {@code toHide}
   *               anymore.
   * @param toHide The player you want to hide.
   */
  @Override
  public void hidePlayer(Player player, Player toHide) {
    player.hidePlayer(toHide);
  }

  /**
   * Shows a player to another player again, so they become visible
   * for the other player.
   *
   * @param player The player who should see {@code toShow}
   *               again.
   * @param toShow The player you want to show.
   */
  @Override
  public void showPlayer(Player player, Player toShow) {
    player.showPlayer(toShow);
  }

  /**
   * Checks if the given player can see the targeted player.
   * This does not check if the player currently really sees
   * the given target player, but if the player is not hidden.
   *
   * @param player   The player who should see the target player.
   * @param toCheck  The player to check if he is visible for the
   *                 other player.
   * @return {@code true} if {@code player} can see {@code toCheck}.
   */
  @Override
  public boolean canSee(Player player, Player toCheck) {
    return player.canSee(toCheck);
  }

  /**
   * Checks if the player is currently flying.
   *
   * @return {@code true} if the player is flying.
   */
  @Override
  public boolean isFlying(Player player) {
    return player.isFlying();
  }

  /**
   * Changes the flying state of the player.
   *
   * @param player The player you want to change the flying state of.
   * @param flying {@code true} if you want to make the player fly.
   *               {@code false} if you don't want to make the player fly.
   */
  @Override
  public void setFlying(Player player, boolean flying) {
    player.setFlying(flying);
  }

  /**
   * Sets the current fly speed of a player. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @param player    The player whose speed you want to set.
   * @param flySpeed  The desired fly speed. The value may
   *                  range from -1 to 1.
   */
  @Override
  public void setFlySpeed(Player player, float flySpeed) {
    player.setFlySpeed(flySpeed);
  }

  /**
   * Sets the current walk speed of a player. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @param player    The player whose speed you want to set.
   * @param walkSpeed The desired walk speed. The value may
   *                  range from -1 to 1.
   */
  @Override
  public void setWalkSpeed(Player player, float walkSpeed) {
    player.setWalkSpeed(walkSpeed);
  }

  /**
   * Gets the current speed a player can fly. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @return  The current fly speed of a player.
   *          Value can range from -1 to 1
   */
  @Override
  public float getFlySpeed(Player player) {
    return player.getFlySpeed();
  }

  /**
   * Gets the current speed a player can walk. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @return  The current walk speed of a player.
   *          Value can range from -1 to 1
   */
  @Override
  public float getWalkSpeed(Player player) {
    return player.getWalkSpeed();
  }

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
   * @param player The player who should download the Resource pack.
   * @param url  The URL from which the client will download the resource pack.
   *             The string must contain only US-ASCII characters
   *             and should be encoded as per RFC 1738.
   */
  @Override
  public void setResourcePack(Player player, String url) {
    player.setResourcePack(url);
  }

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
   * @param player The player who should download the Resource pack.
   * @param url  The URL from which the client will download the resource pack.
   *             The string must contain only US-ASCII characters
   *             and should be encoded as per RFC 1738.
   * @param hash The sha1 hash sum of the resource pack file
   *             which is used to apply a cached version of the pack
   *             directly without downloading if it is available.
   *             Has to be 20 bytes long!
   */
  @Override
  public void setResourcePack(Player player, String url, byte[] hash) {
    Preconditions.checkNotNull(url, "Resource pack URL cannot be null");
    Preconditions.checkNotNull(hash, "Resource pack hash cannot be null");
    Validate.isTrue(hash.length == 20, "Resource pack hash should be 20 bytes long but was " + hash.length);
    ((CraftPlayer)player).getHandle().setResourcePack(url, BaseEncoding.base16().lowerCase().encode(hash));
  }

  /**
   * Checks if the player has received the scaled health bar
   * from the server.
   *
   * @return {@code true} if the player's health bar scale is up to date.
   */
  @Override
  public boolean isHealthScaled(Player player) {
    return player.isHealthScaled();
  }

  /**
   * Sets if the player is shown the scaled health bar.
   * If you modify your health scale with {@code setHealthScale},
   * you have to say the server to send this new scaled health
   * to the player.
   *
   * @param player The player you want to scale the health of.
   * @param scaled {@code true} whether the health should be scaled.
   */
  @Override
  public void setHealthScaled(Player player, boolean scaled) {
    player.setHealthScaled(scaled);
  }

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
   * @param player      The player whose amount of scaled health you want
   *                    to set.
   * @param healthScale The amount of scaled health you want to set.
   */
  @Override
  public void setHealthScale(Player player, double healthScale) {
    player.setHealthScale(healthScale);
  }

  /**
   * Gets the number of scaled health points,
   * which are currently displayed to the client.
   *
   * @param player The player whose health scale you want to get.
   * @return  The number of scaled health if health
   *          scaling was set to {@code true} earlier.
   */
  @Override
  public double getHealthScale(Player player) {
    return player.getHealthScale();
  }

  /**
   * Cancels the title animation for a player. The current
   * title is removed immediately.
   *
   * @param player The player whose title should be reset.
   */
  @Override
  public void resetTitle(Player player) {
    player.resetTitle();
  }

  @Override
  public void openBook(Player player, ItemStack book) {

  }

  @Override
  public int getProtocolVersion(Player player) {
    return 0;
  }

  /**
   * Sets the player to a server operator. A server operator
   * is a player, who has all permissions and can execute every
   * command.
   *
   * @param player  The player you want to change the operator state of.
   * @param value   {@code true} If you want to make the player an operator.
   */
  @Override
  public void setOperator(Player player, boolean value) {
    player.setOp(value);
  }

  /**
   * Checks if the given player is a server operator. A server operator
   * is a player, who has all permissions and can execute every
   * command.
   *
   * @param player The player you want to check the operator state of.
   * @return {@code true} if the player is a server operator.
   */
  @Override
  public boolean isOperator(Player player) {
    return player.isOp();
  }

  /**
   * Gives the player the desired permission.
   *
   * @param player            The player who should get the permission.
   * @param permission        The name of the permission you want to give the player.
   */
  @Override
  public void givePermission(Player player, String permission) {
    PermissionAttachment attachment = player.addAttachment(this.plugin);
    attachment.setPermission(permission, true);
  }

  /**
   * Removes the specified permission from the given player.
   *
   * @param player      The player you want to remove the permission from.
   * @param permission  The name of the permission you want to remove.
   */
  @Override
  public void removePermission(Player player, String permission) {
    PermissionAttachment attachment = player.addAttachment(this.plugin);
    attachment.setPermission(permission, false);
  }

  /**
   * Checks if the given player has the desired permission.
   *
   * @param player      The player you want to check.
   * @param permission  The permission you want to check for.
   * @return {@code true} if the player has the permission.
   */
  @Override
  public boolean hasPermission(Player player, String permission) {
    return player.hasPermission(permission);
  }

  /**
   * Checks if the given player is currently banned from the server.
   * This does only check if the player was banned by the
   * bukkit server using the normal {@code /ban} command. If another
   * plugin has banned the player, this is ignored.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player has been banned by the bukkit server.
   */
  @Override
  public boolean isBannedByBukkit(Player player) {
    return player.isBanned();
  }

  /**
   * Checks if the player is on the bukkit whitelist.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is whitelisted.
   */
  @Override
  public boolean isWhitelisted(Player player) {
    return player.isWhitelisted();
  }

  /**
   * Sets the player whitelisted or not whitelisted.
   *
   * @param player        The player you want to whitelist/unwhitelist.
   * @param whitelisted   {@code true} if the player should be whitelisted.
   */
  @Override
  public void setWhitelisted(Player player, boolean whitelisted) {
    player.setWhitelisted(whitelisted);
  }

  /**
   * Gets the bed spawn location of the player.
   * This is the location, where the player has slept for the
   * last time. A location is only returned if the player has
   * already slept at least once and the location is valid.
   *
   * @param player The player you want to get the bed spawn location of.
   * @return The spawn location, {@code null} if the player has not slept or location is invalid.
   */
  @Override
  public Location getBedSpawnLocation(Player player) {
    return player.getBedSpawnLocation();
  }

  @Override
  public void sendMessage(Player player, String message) {
    player.sendMessage(message);
  }

  /**
   * Sends a boss bar to the player by spawning a boss entity near it. If you use this
   * method in 1.8, please keep in mind that bar colors other than {@code PURPLE} and bar styles
   * other than {@code SOLID} are not supported.
   *
   * @param player    The player you want to send the message to.
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
  @Override
  public void sendBossBar(Player player, String message, float health, BossBarColor barColor, BossBarStyle barStyle) {
    CraftPlayer craftPlayer = (CraftPlayer) player;
    Vector direction = craftPlayer.getLocation().getDirection();
    Location location = craftPlayer.getLocation().add(direction.multiply(40));

    if (location.getY() < 1) {
      location.setY(1);
    }

    EntityWither entityWither = new EntityWither(craftPlayer.getHandle().getWorld());
    entityWither.setInvisible(true);
    entityWither.setCustomName((message == null ? "Custom Boss Bar Message." : message));
    entityWither.setCustomNameVisible(false);
    entityWither.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
    entityWither.setHealth(health);

    PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(entityWither);
    craftPlayer.getHandle().playerConnection.sendPacket(spawnPacket);

    bossBarLocationUpdater.remove(player.getUniqueId());
    bossBarLocationUpdater.add(player.getUniqueId(), entityWither.getId(), health, message);

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
  @Override
  public void setBossBarProgress(Player player, float health) {
    bossBarLocationUpdater.setHealth(player.getUniqueId(), health);
  }

  /**
   * Makes the boss bar disappear for the given player.
   *
   * @param player The player whose boss bar you want to remove.
   */
  @Override
  public void removeBossBar(Player player) {
    ServerMainThread.RunParallel.run(()
      -> bossBarLocationUpdater.remove(player.getUniqueId()));
  }

  /**
   * Sends an interactive message to the player. An interactive message is a message
   * the player can click on and events (execute a command, open a url, ...) are triggered.
   * You can also add hover events to it. You can add as many components as you want.
   * More detailed information about how to build an interactive message can be found out
   * in {@link InteractiveMessage}.
   *
   * @param player              The player who should receive this message and be able
   *                            to interact with it.
   * @param interactiveMessage  The actual message you want to send to the player.
   */
  @Override
  public void sendInteractiveMessage(Player player, InteractiveMessage interactiveMessage) {
    // create the component builder retaining no formatting code information so that
    // the messages stay independent from each other.
    ComponentBuilder componentBuilder = new ComponentBuilder("")
      .retain(ComponentBuilder.FormatRetention.NONE);
    String retainedColorCode = null;

    // iterate through all components of the message to add the individual events and
    // handle the color codes correctly.
    for (MessageComponent component : interactiveMessage.getComponents()) {

      String message = component.getText();
      StringBuilder tempMessage = new StringBuilder();
      ChatColor color = ChatColor.WHITE;
      boolean colorCode = false, formattingCode = false;
      boolean bold = false, italic = false, underlined = false, strikethrough = false, obfuscated = false;

      // check whether a color code from the last component was retained
      // and eventually apply it.
      if (retainedColorCode != null) {
        appendComponentBuilder(componentBuilder,
          component,
          new StringBuilder(),
          stringUtils.getChatColor(retainedColorCode),
          false,
          false,
          false,
          false,
          false);
        retainedColorCode = null;
      }

      // if the message of the current component ends with any formatting code,
      // it is saved and applied to the next component but ignored in the current iteration.
      retainedColorCode = stringUtils.endsWithFormattingCode(message);
      if (retainedColorCode != null) {
        if (message.length() <= 2) {
          continue;
        }
        message = message.substring(0, message.length() - 2);
      }

      // iterate through each character of the current message and search for color and style codes.
      // a message is basically divided every time a new color code occurs. This has to be done as the
      // spigot component builder can only handle one color at once. Example (| is a point where the message is divided.)
      // §8[|§aYourPlugin|§8] |§7Click ... [next component]
      for (int i = 0; i < message.length() - 1; i++) {

        // if a § with a valid color code id was found, the message up to this point is
        // appended to the component builder.
        if (message.charAt(i) == '§' && stringUtils.isColorCode(message.charAt(i + 1))) {
          if (tempMessage.length() > 0) {
            appendComponentBuilder(componentBuilder, component, tempMessage, color, bold, italic, underlined, strikethrough, obfuscated);
          }
          colorCode = true;
          color = stringUtils.getChatColor(message.charAt(i + 1));
          continue;
        }

        // if a color code was detected in the last iteration, this is the
        // id of the color code (such as 1, 2, 3, ..., a, b, ...) so we can skip this
        // char as it was already handled.
        if (colorCode) {
          colorCode = false;
          continue;
        }

        // if a formatting code is detected, it is cached and applied to the message.
        if (message.charAt(i) == '§' && stringUtils.isFormattingCode(message.charAt(i + 1))) {
          formattingCode = true;
          ChatColor chatColor = stringUtils.getChatColor(message.charAt(i + 1));
          switch (chatColor) {
            case STRIKETHROUGH:
              strikethrough = true;
              break;
            case MAGIC:
              obfuscated = true;
              break;
            case UNDERLINE:
              underlined = true;
              break;
            case ITALIC:
              italic = true;
              break;
            case BOLD:
              bold = true;
              break;
          }
          continue;
        }

        // if a formatting code has been detected in the last iteration, we can skip this char.
        if (formattingCode) {
          formattingCode = false;
          continue;
        }

        // append the current char to the string builder for the temporary message
        // if this was a normal char.
        tempMessage.append(message.charAt(i));

        // if we are at the last char of the message, we can append the text to the component builder
        // and jump to the next MessageComponent if there is one.
        if (i + 1 == message.length() - 1) {
          tempMessage.append(message.charAt(i + 1));
          appendComponentBuilder(componentBuilder, component, tempMessage, color, bold, italic, underlined, strikethrough, obfuscated);
        }
      }
    }

    // finally send the message to the player via spigot api call.
    player.spigot().sendMessage(componentBuilder.create());
  }

  /**
   * If the player currently sees a sidebar, it will be hidden for the given
   * player. This mostly happens by replacing it with a new, empty scoreboard.
   *
   * @param player The player whose sidebar you want to hide/remove.
   */
  @Override
  public void removeSidebar(Player player) {
    Scoreboard scoreboard = player.getScoreboard();
    if (scoreboard.getObjective(DisplaySlot.SIDEBAR) == null) {
      return;
    }

    scoreboard.getObjective(DisplaySlot.SIDEBAR).unregister();
    player.setScoreboard(scoreboard);

  }

  /**
   * Appends a message to the given {@link ComponentBuilder}. Please note that every message you append
   * using this method may only have one chat color and the formatting code applies for the entire message.
   *
   * @param componentBuilder    The {@link ComponentBuilder} the message should be appended to.
   * @param component           The {@link MessageComponent} that produced this message.
   * @param tempMessage         The actual message to send.
   * @param color               The color of the message.
   * @param bold                Whether the message should be printed bold.
   * @param italic              Whether the message should be printed italic.
   * @param underlined          Whether the message should be underlined.
   * @param strikethrough       Whether the message should have a strikethrough.
   * @param obfuscated          Whether the message should be obfuscated/magic.
   */
  private void appendComponentBuilder(ComponentBuilder componentBuilder,
                                      MessageComponent component,
                                      StringBuilder tempMessage,
                                      ChatColor color,
                                      boolean bold,
                                      boolean italic,
                                      boolean underlined,
                                      boolean strikethrough,
                                      boolean obfuscated) {
    componentBuilder
      .append(tempMessage.toString())
      .reset()
      .color(color);
    if (bold) {
      componentBuilder.bold(true);
    } else if (italic) {
      componentBuilder.italic(true);
    } else if (underlined) {
      componentBuilder.underlined(true);
    } else if (strikethrough) {
      componentBuilder.strikethrough(true);
    } else if (obfuscated) {
      componentBuilder.obfuscated(true);
    }
    if (tempMessage.length() > 0) {
      applyEvents(component, componentBuilder);
    }
    tempMessage.setLength(0);
  }

  /**
   * Applies the hover and click events contained in the {@link MessageComponent} to the {@link ComponentBuilder}.
   *
   * @param component   The {@link MessageComponent} containing the events you want to apply.
   * @param builder     The {@link ComponentBuilder} the events should be applied to.
   * @return The newly built {@link ComponentBuilder} containing the event data.
   */
  private ComponentBuilder applyEvents(MessageComponent component, ComponentBuilder builder) {
    if (component.getClickAction() == MessageClickAction.EXECUTE_COMMAND) {
      builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.SEND_CHAT_MESSAGE) {
      builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.CHANGE_PAGE) {
      try {
        // The click value is converted to an integer first in order to check if the given
        // value really is an integer and then converted back into a string to be passed
        // as a parameter.
        builder.event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, String.valueOf(Integer.parseInt(component.getClickValue().toString()))));
      } catch (NumberFormatException e) {
        logger.log(LogLevel.ERROR, "Error converting click value to type INTEGER. If you selected click action CHANGE_PAGE, the click value has to be an integer.");
      }
    }

    if (component.getClickAction() == MessageClickAction.COPY_TO_CLIPBOARD) {
      // In Spigot 1.8, this feature is not available.
    }

    if (component.getClickAction() == MessageClickAction.OPEN_FILE) {
      builder.event(new ClickEvent(ClickEvent.Action.OPEN_FILE, component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.OPEN_URL) {
      builder.event(new ClickEvent(ClickEvent.Action.OPEN_URL, component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.SUGGEST_COMMAND) {
      builder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, component.getClickValue().toString()));
    }

    if (component.getHoverAction() == MessageHoverAction.SHOW_MESSAGE) {
      builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
        TextComponent.fromLegacyText(component.getHoverValue().toString())));
    }

    return builder;
  }

  /**
   * Sends the given packet to the given player.
   *
   * @param packet The packet you want to send.
   * @param player The player who should receive the packet.
   */
  private void sendPacket(Packet packet, Player player) {
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
  }

}
