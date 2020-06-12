package de.pxav.kelp.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If your class represents a sub-command class, it must
 * be annotated with this annotation. It allows the kelp
 * command loader system to find your sub-command class
 * and gives you the ability to provide essential information
 * about your command.
 *
 * Note that this annotation should only be used for sub commands.
 * If you want to create a completely new command, you will have
 * to use {@code CreateCommand}.
 *
 * Your class also has to inherit from {@code KelpCommand} in
 * order to be able to use the {@code #onCommand} methods
 * just like in your main command.
 *
 * @see KelpCommand
 * @see CreateCommand
 * @author pxav
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateSubCommand {

  /**
   * Sets the name of your sub-commands. This is the string,
   * the executor has to enter, to perform this command.
   *
   * Your name must not include the '/' and parent command name.
   * Those are added automatically by the kelp system. This name
   * is just the identifier for your very sub-command and should
   * therefore be unique in its layer. So there should be no
   * sub-command with the same parent command and name.
   *
   * This method is only meant for defining the
   * simple command name. If you want to add aliases,
   * you can do that by using {@code #addAlias} in the
   * {@code #onCommandRegister} method in your command class.
   *
   * @return The name of the sub command.
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

  /**
   * Sets the parent command of your sub command. This can either
   * be directly the main command class (then the sub command would have
   * to follow directly after the main command) or any other sub command
   * class (then the sub command would simply be the sub command of the given
   * sub command, it sounds complicated, but allows you to nest unlimited commands
   * easily).
   *
   * @return The class of the desired parent command.
   */
  Class<? extends KelpCommand> parentCommand();

}
