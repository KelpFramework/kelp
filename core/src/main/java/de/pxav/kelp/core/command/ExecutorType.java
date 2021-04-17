package de.pxav.kelp.core.command;

import de.pxav.kelp.core.player.KelpPlayer;

/**
 * An executor type represents a group of potential command
 * executors, which can execute a specific command.
 *
 * This enum is passed in every command annotation to define
 * who will be allowed to execute the command (player, console,
 * or even both?). This does not include permission management.
 * Permissions have to be passed manually in th {@code #onCommandRegister}
 * method.
 *
 * @see CreateCommand
 * @see CreateSubCommand
 * @author pxav
 */
public enum ExecutorType {

  /**
   * The given command can only be executed by players.
   * If a console tries to execute it, there will be an
   * error message.
   *
   * If you set this executor type, you have to chose the
   * {@code #onCommand(kelpPlayer, args)} method.
   */
  PLAYER_ONLY(KelpPlayer.class),

  /**
   * The given command can only be executed by the console.
   * If a player tries to execute it, there will be an
   * error message.
   *
   * Note that there will be no permission check, as the
   * console has all permissions.
   *
   * If you set this executor type, you have to chose the
   * {@code #onCommand(kelpConsoleSender, args)} method.
   */
  CONSOLE_ONLY(KelpConsoleSender.class),

  /**
   * The given command can only be executed by
   * both the player and console.
   *
   * Note that there will be a permission check for players only.
   *
   * If you set this executor type, you have to chose the
   * {@code #onCommand(kelpConsoleSender, args)} method.
   */
  PLAYER_AND_CONSOLE(KelpConsoleSender.class, KelpPlayer.class);


  private final Class<? extends KelpCommandSender<?>>[] acceptedCommandSenders;

  @SafeVarargs
  ExecutorType(Class<? extends KelpCommandSender<?>>... acceptedCommandSenders) {
    this.acceptedCommandSenders = acceptedCommandSenders;
  }

  public Class<? extends KelpCommandSender<?>>[] getAcceptedCommandSenders() {
    return acceptedCommandSenders;
  }
}
