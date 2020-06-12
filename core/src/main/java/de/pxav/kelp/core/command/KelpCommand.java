package de.pxav.kelp.core.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * This is the base class of any command - no matter if sub- or
 * main command. Every command class has to inherit from this class
 * to be able to use essential methods like {@code #onCommand}.
 *
 * Note that your command class will also have to be annotated with
 * one of the command annotations.
 *
 * @see CreateCommand
 * @see CreateSubCommand
 * @author pxav
 */
public class KelpCommand {

  // saves the sub commands of this command, where the key is the command class
  // of the sub command and the value is its annotation.
  private Map<KelpCommand, CreateSubCommand> subCommands = Maps.newHashMap();

  // saves the aliases for the command.
  private Collection<String> aliases = Lists.newArrayList();

  // description for the command (can be displayed later in help commands for example)
  private String description;

  // the message which is sent, when a player has no permission.
  private String noPermissionMessage;

  // the message which is sent when a a console executes a command
  // which is only meant for players.
  private String noPlayerMessage;

  // the message which is sent when a player executes a command
  // which is only meant for the console.
  private String noConsoleMessage;

  // the permission needed by players to execute this command.
  private String permission;

  // if 'true', this will allow to enter custom parameters after a command
  // which are not defined as a sub command.
  // Example: /xp 10 where 10 would be a custom parameter.
  private boolean allowCustomParameters;

  // resets the argument count for sub commands to 0 again.
  private boolean argumentsStartFromZero;

  /**
   * This method is executed by the command registry, when the command
   * is executed by a player and {@code PLAYER_ONLY} is set as executor type.
   *
   * @param player  The player who has executed the command.
   * @param args    The arguments of the command. They act the same as the
   *                normal bukkit arguments if you have not reset them manually.
   * @see ExecutorType
   * @see KelpPlayer
   */
  public void onCommand(KelpPlayer player, String[] args) {}

  /**
   * This method is executed by the command registry, when the command
   * is executed by the console and {@code CONSOLE_ONLY} is set as executor type.
   *
   * It is also called when {@code PLAYER_AND_CONSOLE} is enabled and either a player
   * or a console executes the command.
   *
   * @param consoleSender The {@code KelpConsoleSender} object of the command executor.
   * @param args          The arguments of the command. They act the same as the
   *                      normal bukkit arguments if you have not reset them manually.
   * @see ExecutorType
   * @see KelpConsoleSender
   */
  public void onCommand(KelpConsoleSender consoleSender, String[] args) {}

  /**
   * This method is executed when the command is registered.
   * Mostly, this happens once on every server startup and only once
   * during the application runtime.
   *
   * So it is recommended to put the command properties like permission,
   * alias, etc. in here
   */
  public void onCommandRegister() {}

  /**
   * @return  A map of all sub commands, where the key is the command class
   *          of the command and the value is the sub command annotation.
   */
  public Map<KelpCommand, CreateSubCommand> getSubCommands() {
    return subCommands;
  }

  /**
   * Sets the description of the command, which can later be used
   * to display in help commands, etc.
   *
   * @param description The description of your command. It should be brief
   *                    but accurate.
   */
  public void description(String description) {
    this.description = description;
  }

  /**
   * Sets the message, which is sent to a player when they do not
   * have enough permissions to execute the command. If this message
   * is not set, kelp will use the default one.
   *
   * @param noPermissionMessage The message which should be sent (may
   *                            include color codes with '§')
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand noPermissionMessage(String noPermissionMessage) {
    this.noPermissionMessage = noPermissionMessage;
    return this;
  }

  /**
   * Sets the message, which is sent to the console when it
   * executes a command, which is only meant for players.
   *
   * @param noPlayerMessage The message which should be sent (may
   *                        include color codes with '§')
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand noPlayerMessage(String noPlayerMessage) {
    this.noPlayerMessage = noPlayerMessage;
    return this;
  }

  /**
   * Sets the message, which is sent to a player when they try
   * to execute a command, which is meant for the console only.
   * If this message is not set, kelp will use a default one.
   *
   * @param noConsoleMessage The message which should be sent (may
   *                         include color codes with '§')
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand noConsoleMessage(String noConsoleMessage) {
    this.noConsoleMessage = noConsoleMessage;
    return this;
  }

  /**
   * Sets the permission of the command. This permission has to be assigned
   * to a player by a separate permission system or similar. Consoles won't
   * be checked for permissions as they already have all permissions by default.
   *
   * @param permission The permission, which is needed by the player.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand permission(String permission) {
    this.permission = permission;
    return this;
  }

  /**
   * Sets a collection of aliases for the command. All
   * other aliases, which have existed before are overwritten with
   * this method.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param aliases The collection of aliases you want to set.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand setAliases(Collection<String> aliases) {
    this.aliases = aliases;
    return this;
  }

  /**
   * Sets an array of aliases for the command. All
   * other aliases, which have existed before are overwritten with
   * this method.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param aliases The array of aliases you want to set.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand setAliases(String... aliases) {
    this.aliases = Arrays.asList(aliases);
    return this;
  }

  /**
   * Adds an alias for a command. This method simply adds a single
   * alias, other aliases are not overwritten by that.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param alias The array of aliases you want to set.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand addAlias(String alias) {
    this.aliases.add(alias);
    return this;
  }

  /**
   * Adds an array of aliases for the command. Other aliases are
   * not overwritten by this method.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param aliases The array of aliases you want to set.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand addAliases(String... aliases) {
    this.aliases.addAll(Arrays.asList(aliases));
    return this;
  }

  /**
   * Adds a collection of aliases for the command. Other aliases are
   * not overwritten by this method.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param aliases The collection of aliases you want to set.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand addAliases(Collection<String> aliases) {
    this.aliases.addAll(aliases);
    return this;
  }

  /**
   * Removes a single alias from your command.
   *
   * Note that removing aliases is not possible during the application
   * runtime. The remove methods only exist for processing reasons.
   * If you are reading aliases from a config file for example, you
   * can remove certain aliases again if needed.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param alias The alias you want to remove.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand removeAlias(String alias) {
    this.aliases.remove(alias);
    return this;
  }

  /**
   * Removes an array of aliases from your command.
   *
   * Note that removing aliases is not possible during the application
   * runtime. The remove methods only exist for processing reasons.
   * If you are reading aliases from a config file for example, you
   * can remove certain aliases again if needed.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param aliases The array of aliases you want to remove.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand removeAliases(String... aliases) {
    this.aliases.removeAll(Arrays.asList(aliases));
    return this;
  }

  /**
   * Removes a collection of aliases from your command.
   *
   * Note that removing aliases is not possible during the application
   * runtime. The remove methods only exist for processing reasons.
   * If you are reading aliases from a config file for example, you
   * can remove certain aliases again if needed.
   *
   * Aliases are alternative names for a command, which can
   * be typed by the executor to execute the same command.
   *
   * @param aliases The collection of aliases you want to remove.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand removeAliases(Collection<String> aliases) {
    this.aliases.removeAll(aliases);
    return this;
  }

  /**
   * Sets the sub command map of this command. It is recommended not to
   * touch this method, as sub commands are administrated by the kelp-internal
   * command registry, but you can manipulate it if you want of course.
   *
   * @param subCommands The map of sub commands.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand subCommands(Map<KelpCommand, CreateSubCommand> subCommands) {
    this.subCommands = subCommands;
    return this;
  }

  /**
   * Allow/Disallow custom parameters in your command. Custom parameters
   * are arguments, which are no sub commands defined by any plugin.
   *
   * Examples of custom parameters would be:
   * /giveXP 20 <- the amount of XP points.
   * /setSpawn 0 120 0 <- the axes of the location
   *
   * @param allow {@code true} if you want to accept custom parameters,
   *              {@code false} if not.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand allowCustomParameters(boolean allow) {
    this.allowCustomParameters = allow;
    return this;
  }

  /**
   * You can enable this property, if you want to reset the argument
   * count within a sub command.
   *
   * If you have nested commands with a complicated structure, you probably
   * lose the overview of your commands and do not know at which argument
   * you have to start counting in your sub command. Or if you want to add
   * a new sub command in between two commands, you would have to change
   * all argument ids in the existing commands. To avoid that, you can
   * reset the argument count to 0 every time. Then you have constant
   * argument ids and it becomes more readable for other developers.
   *
   * @param argumentsStartFromZero {@code true} if you want to reset the argument count,
   *                               if not, choose {@code false}.
   * @return The current command object, which can be used to append further properties.
   */
  public KelpCommand argumentsStartFromZero(boolean argumentsStartFromZero) {
    this.argumentsStartFromZero = argumentsStartFromZero;
    return this;
  }

  /**
   * @return The description of the command.
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return  The message which is sent to players, if they do not have enough
   *          permissions.
   */
  public String getNoPermissionMessage() {
    return noPermissionMessage;
  }

  /**
   * @return The message which is sent to the console, when it
   *         executes a command, which is meant for players only.
   */
  public String getNoPlayerMessage() {
    return noPlayerMessage;
  }

  /**
   * @return The message which is sent to players when they execute a console command.
   */
  public String getNoConsoleMessage() {
    return noConsoleMessage;
  }

  /**
   * @return Returns the permission string, which is needed for this command.
   */
  public String getPermission() {
    return permission;
  }

  /**
   * @return {@code true} if custom parameters are allowed in this command.
   */
  public boolean customParametersAllowed() {
    return this.allowCustomParameters;
  }

  /**
   * @return {@code true} if the argument count should be reset to 0.
   */
  public boolean shouldArgumentsStartFromZero() {
    return this.argumentsStartFromZero;
  }

  /**
   * @return A collection of all aliases the command has.
   */
  public Collection<String> getAliases() {
    return aliases;
  }
}
