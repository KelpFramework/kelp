package de.pxav.kelp.core.command.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.command.*;

/**
 * This version template is responsible for registering the commands.
 * Command registration in Kelp is done via bukkit. Kelp manipulates
 * the command registry of the bukkit server to inject its own commands.
 *
 * This is a version specific task, as reflections and CraftBukkit libraries
 * are required, so there is a version template for this process.
 *
 * Note: You only have to register main commands, as sub commands are saved
 * within the main command object.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class CommandRegistryVersionTemplate {

  /**
   * Injects the given command into the bukkit server command registry.
   *
   * @param command             The command object of the command you want to register.
   *                            Simply pass an instance of the main command class here.
   * @param commandAnnotation   An instance of the main command annotation.
   */
  public abstract void registerCommand(KelpCommand command, CreateCommand commandAnnotation);
  public abstract void registerCommand(DeclarativeKelpCommand<? extends KelpCommandSender<?>> command, CreateDeclarativeCommand commandAnnotation);

}
