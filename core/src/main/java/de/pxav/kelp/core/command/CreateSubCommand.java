package de.pxav.kelp.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateSubCommand {

  String name();

  ExecutorType executorType() default ExecutorType.PLAYER_AND_CONSOLE;

  Class<? extends KelpCommand> parentCommand();

}
