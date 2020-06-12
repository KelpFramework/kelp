package de.pxav.kelp.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes which should represent a new command
 * have to be annotated with this annotation. It tells
 * the Kelp system that this is a valid command class
 * and you can provide essential information about
 * your command.
 *
 * This annotation is only used for main commands, so
 * commands that directly some after the '/'. If you want to
 * add sub commands, take a look at {@code CreateSubCommand}.
 *
 * Furthermore your class has to inherit from the
 * {@code KelpCommand} class and override at least
 * one of the {@code #onCommand} methods.
 *
 * @see KelpCommand
 * @see CreateSubCommand
 * @author pxav
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateCommand {

  /**
   * Defines the name of the command, so the
   * string the player has to enter in the chat
   * to execute it. The '/' does not have to be
   * included in this name.
   *
   * The name should be unique as only one command
   * can be executed at a time by bukkit.
   *
   * This method is only meant for defining the
   * main command name. If you want to add aliases,
   * you can do that by using {@code #addAlias} in the
   * {@code #onCommandRegister} method in your command class.
   *
   * @return The command name.
   */
  String name();

  /**
   * Sets the executor type of the command, so it
   * defines who is allowed to execute the command.
   * Further information about that can be found in
   * the {@code ExecutorType} class.
   *
   * Note that the executor type is only set for the
   * very main command and not for the child/sub commands.
   * You can adjust their executor types manually.
   *
   * @see ExecutorType
   * @return The executor type for this specific command.
   */
  ExecutorType executorType() default ExecutorType.PLAYER_AND_CONSOLE;

}
