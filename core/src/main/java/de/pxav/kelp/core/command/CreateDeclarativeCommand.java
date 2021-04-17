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
 * Furthermore your class has to inherit from the
 * {@code DeclarativeKelpCommand} class.
 *
 * @see KelpCommand
 * @author DSeeLP
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateDeclarativeCommand {

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
