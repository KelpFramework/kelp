package de.pxav.kelp.core.player;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.bossbar.BossBarColor;
import de.pxav.kelp.core.player.bossbar.BossBarStyle;
import de.pxav.kelp.core.player.message.InteractiveMessage;
import de.pxav.kelp.core.sound.KelpSound;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * This is a version template for the {@code KelpPlayer}.
 * It contains template methods for all version specific
 * stuff a player can do.
 *
 * @see KelpPlayer
 * @author pxav
 * @author Etrayed
 */
@KelpVersionTemplate
public abstract class PlayerVersionTemplate {

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
  public abstract void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut);

  /**
   * Sends an action bar message to the player.
   * The action bar is a line of text, which is displayed
   * above the player's hotbar.
   *
   * @param player  The player who should receive the message.
   * @param message The message you want to send.
   */
  public abstract void sendActionBar(Player player, String message);

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
  public abstract void sendTabHeaderAndFooter(Player player, String header, String footer);

  /**
   * Plays a sound to the player.
   *
   * @param player    The player who should hear the sound.
   * @param sound     The sound you want to play.
   * @param location  The location, where the sound should come from.
   * @param volume    How loud the sound should be.
   * @param pitch     How strong the sound should be pitched.
   */
  public abstract void playSound(Player player, KelpSound sound, Location location, float volume, float pitch);

  /**
   * Sets the player's health.
   *
   * @param player The player whose health you want to change.
   * @param health How many health points the player should have.
   *               2 health points equal 1 heart.
   *               So 20 health points equal the full 10 hearts.
   */
  public abstract void setHealth(Player player, int health);

  /**
   * @param player The player whose UUID you want to get.
   * @return The player's uuid.
   */
  public abstract UUID getUniqueId(Player player);

  /**
   * @param player The player whose location you want to get
   * @return The location the player is currently at.
   */
  public abstract Location getLocation(Player player);

  /**
   * Teleports the player to the given location.
   *
   * @param player    The player you want to teleport.
   * @param location  The location you want to teleport the player to.
   */
  public abstract void teleport(Player player, Location location);

  /**
   * Checks if the player is currently stuck in a cobweb.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently stuck in a cobweb.
   */
  public abstract boolean isInCobweb(Player player);

  /**
   * Checks if the player is currently located in water.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is currently in water.
   */
  public abstract boolean isInWater(Player player);

  // TODO can see -> handle via kelpPlayer class

  /**
   * Sends a chat message from the given player.
   * This means you can send a message as if the player itself
   * typed in this message. This also works with commands, if
   * you add a slash in front of the message.
   *
   * @param player  The player from whom the message should be sent.
   * @param message The message you want to send.
   */
  public abstract void chat(Player player, String message);

  /**
   * Gets the socket address of a specific player.
   *
   * @param player The player whose socket address you want to get.
   * @return The {@code InetSocketAddress} object of the player's address.
   */
  public abstract InetSocketAddress getSocketAddress(Player player);

  // TODO advancement/achievement

  // TODO statistics and achievements

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
  public abstract boolean getAllowFlight(Player player);

  // TODO client view distance

  /**
   * Gets the display name of a player.
   * The display name is a name which - unlike the normal name -
   * can be modified during the server runtime. You can use this
   * to include custom prefixes, ...
   *
   * @param player The player whose display name you want to get.
   * @return The display name.
   */
  public abstract String getDisplayName(Player player);

  /**
   * Sets the display name of a player
   * The display name is a name which - unlike the normal name -
   * can be modified during the server runtime. You can use this
   * to include custom prefixes, ...
   *
   * @param player      The player whose display name you want to set.
   * @param displayName The display name you want to set.
   */
  public abstract void setDisplayName(Player player, String displayName);

  /**
   * Gets the tab-list name of the player.
   * The tab-list name is the name which is used to represent
   * the player in the tab-list of online players.
   *
   * @param player The player whose tab-list name you want to get.
   * @return The tab-list name.
   */
  public abstract String getPlayerTabListName(Player player);

  /**
   * Sets the tab-list name of the player.
   * The tab-list name is the name which is used to represent
   * the player in the tab-list of online players.
   *
   * @param player The player whose tab-list name you want to set.
   * @param tabListName The tab-list name you want to set.
   */
  public abstract void setPlayerTabListName(Player player, String tabListName);

  /**
   * Gets the tab-list header of the player.
   * The tab-list header is a text displayed above the
   * list of online players, which is shown when you press tab.
   *
   * @param player The player whose header you want to get.
   * @return The tab-list header string.
   */
  public abstract String getPlayerListHeader(Player player);

  /**
   * Gets the tab-list footer of the player.
   * The tab-list footer is a text displayed below the
   * list of online players, which is shown when you press tab.
   *
   * @param player The player whose footer you want to get.
   * @return The tab-list footer string.
   */
  public abstract String getPlayerListFooter(Player player);

  /**
   * Sets the tab-list header of the player.
   * The tab-list header is a text displayed above the
   * list of online players, which is shown when you press tab.
   *
   * @param player The player whose header you want to set.
   * @param header The header you want to set. You may use \n
   *               to create new lines within this header.
   */
  public abstract void setPlayerListHeader(Player player, String header);

  /**
   * Sets the tab-list footer of the player.
   * The tab-list footer is a text displayed below the
   * list of online players, which is shown when you press tab.
   *
   * @param player The player whose footer you want to set.
   * @param footer The footer you want to set. You may use \n
   *               to create new lines within this footer.
   */
  public abstract void setPlayerListFooter(Player player, String footer);

  /**
   * Sets the compass target of the player.
   * The compass target is the location, where the compass needle
   * points to. By default this is the spawn location of the world,
   * but you could set this to specific player locations as well.
   *
   * @param player The player whose target location you want to update.
   * @param target The location, where the compass should point to.
   */
  public abstract void setCompassTarget(Player player, Location target);

  /**
   * Gets the compass target of the player.
   * The compass target is the location, where the compass needle
   * points to. By default this is the spawn location of the world.
   *
   * @param player The player whose compass target you want to set.
   * @return The target location of the player's compass.
   */
  public abstract Location getCompassTarget(Player player);

  // TODO send raw message, handle conversations (Bukkit conversation API)

  /**
   * Kicks the given player from the server.
   *
   * @param player      The player who should be kicked.
   * @param kickMessage The message, which should be received by the player.
   *                    Could also be named kick reason.
   */
  public abstract void kickPlayer(Player player, String kickMessage);

  /**
   * Checks if the player is currently sneaking/crouching.
   * @return {@code true} if the player is sneaking.
   */
  public abstract boolean isSneaking(Player player);

  /**
   * Modifies the sneak state of the player.
   *
   * @param player    The player whose sneak state you want to change.
   * @param sneaking  {@code true} if you want to make the player sneak.
   *                  {@code false}, if not.
   */
  public abstract void setSneaking(Player player, boolean sneaking);

  /**
   * Checks if the player is currently sprinting.
   * Sprinting is active when the player has double-pressed the
   * walking key or pressed the walking and sprinting key at
   * once.
   *
   * @return {@code true} if the player is currently sprinting.
   */
  public abstract boolean isSprinting(Player player);

  /**
   * Changes the sprinting state of a player.
   * Sprinting is active when the player has double-pressed the
   * walking key or pressed the walking and sprinting key at
   * once.
   *
   * @param player    The player you want to change the sprinting state of.
   * @param sprinting {@code true} if the player should be sprinting.
   */
  public abstract void setSprinting(Player player, boolean sprinting);

  // void saveData();

  // void loadData();

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
  public abstract void setSleepingIgnored(Player player, boolean ignored);

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
  public abstract boolean isSleepingIgnored(Player player);

//  public abstract void playNote(Player player, Location from, Instrument instrument, Note note);
//
//  public abstract void sendBlockChange(Player player, Location blockLocation, @NotNull BlockData var2);
//
//  public abstract void sendSignChange(@NotNull Location var1, @Nullable String[] var2) throws IllegalArgumentException;
//
//  public abstract void sendSignChange(@NotNull Location var1, @Nullable String[] var2, @NotNull DyeColor var3) throws IllegalArgumentException;
//
//  public abstract void sendMap(@NotNull MapView var1);

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
  public abstract void setPlayerTime(Player player, long time, boolean relative);

  /**
   * Gets the current player time. Each player can have an
   * individual time - even if they are in the same world.
   *
   * @param player The player whose time you want to get.
   * @return The current time of the player in ticks.
   */
  public abstract long getPlayerTime(Player player);

  /**
   * If the player time was set with {@code relative} to {@code true},
   * then it will get the offset of the player time to the current server
   * time. If {@code relative} was set to {@code false}, it will simply
   * return the current player time.
   *
   * @param player The player whose time (offset) you want to check.
   * @return The player's time (offset).
   */
  public abstract long getPlayerTimeOffset(Player player);

  /**
   * Checks if the player time set for the player is relative
   * to the server time. More information can be found
   * at {@code #setPlayerTime(player, time, relative)}
   *
   * @param player The player you want to check.
   * @return {@code true} if player time is relative to server time.
   */
  public abstract boolean isPlayerTimeRelative(Player player);

  /**
   * Resets the player time back to the current server
   * time. So the times of both are synchronized again.
   *
   * @param player The player whose time you want to reset.
   */
  public abstract void resetPlayerTime(Player player);

  // public abstract void setPlayerWeather(@NotNull WeatherType var1);

  // public abstract WeatherType getPlayerWeather();

  // public abstract void resetPlayerWeather();

  /**
   * Gives the player the given amount of experience.
   * This method simply adds the given amount to the player's
   * current exp count and does not overwrite anything.
   *
   * @param player The player you want to give the experience to.
   * @param amount The amount of experience to give.
   */
  public abstract void giveExperience(Player player, int amount);

  /**
   * Gives or takes the player the given amount of experience.
   * Negative amounts express that levels should be taken
   * away from the player, positive amounts will be added.
   *
   * @param player The player you want to give the levels to.
   * @param amount The amount of levels to give or take.
   */
  public abstract void giveExperienceLevels(Player player, int amount);

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
  public abstract float getExperience(Player player);

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
  public abstract void setExperience(Player player, float experience);

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
  public abstract int getLevel(Player player);

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
  public abstract void setLevel(Player player, int level);

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
  public abstract int getTotalExperience(Player player);

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
  public abstract void setTotalExperience(Player player, int experience);

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
  public abstract float getExhaustion(Player player);

  /**
   * Sets the player's current exhaustion level.
   * Exhaustion controls how fast the food level drops.
   * While you have a certain amount of exhaustion,
   * your saturation will drop to zero, and then your
   * food will drop to zero.
   *
   * @param exhaustionLevel The exhaustion level you want to set.
   */
  public abstract void setExhaustion(Player player, float exhaustionLevel);

  /**
   * Gets the saturation level of a player.
   * Saturation is a buffer for food level.
   * Your food level will not drop if you are saturated > 0.
   *
   * @param player The player whose saturation level you want to get.
   * @return The saturation of the player.
   */
  public abstract float getSaturation(Player player);

  /**
   * Sets the saturation level of a player.
   * Saturation is a buffer for food level.
   * Your food level will not drop if you are saturated > 0.
   *
   * @param player      The player whose saturation level you want
   *                    to set.
   * @param saturation  The saturation level you want to set.
   */
  public abstract void setSaturation(Player player, float saturation);

  /**
   * Gets the current food level of the player. The
   * food level indicates how full the food bar is.
   * 20 means a full food bar, 0 an empty food bar.
   *
   * @param player The player whose food bar you want to change.
   * @return The food level of the player.
   */
  public abstract int getFoodLevel(Player player);

  /**
   * Sets the food level of a player.
   * The food level indicates how full the food bar is.
   * 20 means a full food bar, 0 an empty food bar.
   *
   * @param player    The player whose food level you want to change.
   * @param foodLevel The absolute food level you want to set.
   */
  public abstract void setFoodLevel(Player player, int foodLevel);

  /**
   * Sets if the player is allowed to fly as if he was in creative
   * mode
   *
   * @param player      The player you want to allow/disallow flying
   *                    for.
   * @param allowFlight {@code true} if you want to allow, {@code false}
   *                    if not.
   */
  public abstract void setAllowFlight(Player player, boolean allowFlight);

  /**
   * Hides a player from another player, so they become invisible
   * for the other player.
   *
   * @param player The player who should not see {@code toHide}
   *               anymore.
   * @param toHide The player you want to hide.
   */
  public abstract void hidePlayer(Player player, Player toHide);

  /**
   * Shows a player to another player again, so they become visible
   * for the other player.
   *
   * @param player The player who should see {@code toShow}
   *               again.
   * @param toShow The player you want to show.
   */
  public abstract void showPlayer(Player player, Player toShow);

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
  public abstract boolean canSee(Player player, Player toCheck);

  /**
   * Checks if the player is currently flying.
   *
   * @return {@code true} if the player is flying.
   */
  public abstract boolean isFlying(Player player);

  /**
   * Changes the flying state of the player.
   *
   * @param player The player you want to change the flying state of.
   * @param flying {@code true} if you want to make the player fly.
   *               {@code false} if you don't want to make the player fly.
   */
  public abstract void setFlying(Player player, boolean flying);

  /**
   * Sets the current fly speed of a player. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @param player    The player whose speed you want to set.
   * @param flySpeed  The desired fly speed. The value may
   *                  range from -1 to 1.
   */
  public abstract void setFlySpeed(Player player, float flySpeed);

  /**
   * Sets the current walk speed of a player. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @param player    The player whose speed you want to set.
   * @param walkSpeed The desired walk speed. The value may
   *                  range from -1 to 1.
   */
  public abstract void setWalkSpeed(Player player, float walkSpeed);

  /**
   * Gets the current speed a player can fly. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @return  The current fly speed of a player.
   *          Value can range from -1 to 1
   */
  public abstract float getFlySpeed(Player player);

  /**
   * Gets the current speed a player can walk. The higher
   * the value the higher the speed. Negative values
   * indicate reverse directions.
   *
   * @return  The current walk speed of a player.
   *          Value can range from -1 to 1
   */
  public abstract float getWalkSpeed(Player player);

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
  public abstract void setResourcePack(Player player, String url);

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
  public abstract void setResourcePack(Player player, String url, byte[] hash);

  /**
   * Checks if the player has received the scaled health bar
   * from the server.
   *
   * @return {@code true} if the player's health bar scale is up to date.
   */
  public abstract boolean isHealthScaled(Player player);

  /**
   * Sets if the player is shown the scaled health bar.
   * If you modify your health scale with {@code setHealthScale},
   * you have to say the server to send this new scaled health
   * to the player.
   *
   * @param player The player you want to scale the health of.
   * @param scaled {@code true} whether the health should be scaled.
   */
  public abstract void setHealthScaled(Player player, boolean scaled);

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
  public abstract void setHealthScale(Player player, double healthScale);

  /**
   * Gets the number of scaled health points,
   * which are currently displayed to the client.
   *
   * @param player The player whose health scale you want to get.
   * @return  The number of scaled health if health
   *          scaling was set to {@code true} earlier.
   */
  public abstract double getHealthScale(Player player);

//  public abstract Entity getSpectatorTarget();
//
//  public abstract void setSpectatorTarget(Player player, Entity entity);

  /**
   * Cancels the title animation for a player. The current
   * title is removed immediately.
   *
   * @param player The player whose title should be reset.
   */
  public abstract void resetTitle(Player player);

  // TODO update commands

  public abstract void openBook(Player player, ItemStack book);

  /**
   * Gets the version number of the client's protocol. You
   * can convert this information to the release name with the
   * help of this list: https://wiki.vg/Protocol_version_numbers
   * Please note that you should use the versions after the Netty
   * rewrite.
   *
   * @param player The player whose protocol version you want to get.
   * @return The protocol version number.
   */
  public abstract int getProtocolVersion(Player player);

  /**
   * Sets the player to a server operator. A server operator
   * is a player, who has all permissions and can execute every
   * command.
   *
   * @param player  The player you want to change the operator state of.
   * @param value   {@code true} If you want to make the player an operator.
   */
  public abstract void setOperator(Player player, boolean value);

  /**
   * Checks if the given player is a server operator. A server operator
   * is a player, who has all permissions and can execute every
   * command.
   *
   * @param player The player you want to check the operator state of.
   * @return {@code true} if the player is a server operator.
   */
  public abstract boolean isOperator(Player player);

  /**
   * Gives the player the desired permission.
   *
   * @param player            The player who should get the permission.
   * @param permission        The name of the permission you want to give the player.
   */
  public abstract void givePermission(Player player, String permission);

  /**
   * Removes the specified permission from the given player.
   *
   * @param player      The player you want to remove the permission from.
   * @param permission  The name of the permission you want to remove.
   */
  public abstract void removePermission(Player player, String permission);

  /**
   * Checks if the given player has the desired permission.
   *
   * @param player      The player you want to check.
   * @param permission  The permission you want to check for.
   * @return {@code true} if the player has the permission.
   */
  public abstract boolean hasPermission(Player player, String permission);

  /**
   * Checks if the given player is currently banned from the server.
   * This does only check if the player was banned by the
   * bukkit server using the normal {@code /ban} command. If another
   * plugin has banned the player, this is ignored.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player has been banned by the bukkit server.
   */
  public abstract boolean isBannedByBukkit(Player player);

  /**
   * Checks if the player is on the bukkit whitelist.
   *
   * @param player The player you want to check.
   * @return {@code true} if the player is whitelisted.
   */
  public abstract boolean isWhitelisted(Player player);

  /**
   * Sets the player whitelisted or not whitelisted.
   *
   * @param player        The player you want to whitelist/unwhitelist.
   * @param whitelisted   {@code true} if the player should be whitelisted.
   */
  public abstract void setWhitelisted(Player player, boolean whitelisted);

  /**
   * Gets the bed spawn location of the player.
   * This is the location, where the player has slept for the
   * last time. A location is only returned if the player has
   * already slept at least once and the location is valid.
   *
   * @param player The player you want to get the bed spawn location of.
   * @return The spawn location, {@code null} if the player has not slept or location is invalid.
   */
  public abstract Location getBedSpawnLocation(Player player);

  /**
   * Sends the given player a message into their chat.
   *
   * @param player    The player who should receive the message.
   * @param message   The message itself. May contain color codes.
   */
  public abstract void sendMessage(Player player, String message);

  /**
   * Sends a boss bar to the player by spawning a boss entity near it. If you use this
   * method in 1.8, please keep in mind that bar colors other than {@code PURPLE} and bar styles
   * other than {@code SOLID} are not supported.
   *
   * @param player    The player you want to send the message to.
   * @param message   The message you want to be displayed above the boss bar.
   * @param barColor  The color of the boss bar. Please note that in 1.8 only
   *                  {@link BossBarColor#PURPLE} is allowed. If you use any color, no exception
   *                  is thrown but purple will be chosen automatically.
   * @param barStyle  The style of the boss bar (how many segments?, ...). Note that
   *                  in 1.8 only {@link BossBarStyle#SOLID} is supported. If you use any different
   *                  style, no exception will be thrown, but {@link BossBarStyle#SOLID} is chosen
   *                  automatically.
   */
  public abstract void sendBossBar(Player player, String message, BossBarColor barColor, BossBarStyle barStyle);

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
  public abstract void sendInteractiveMessage(Player player, InteractiveMessage interactiveMessage);

}
