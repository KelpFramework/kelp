package de.pxav.kelp.core.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.version.CommandRegistryVersionTemplate;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.reflect.TypeCriterion;
import de.pxav.kelp.core.reflect.TypeFinder;

import java.util.Arrays;
import java.util.Map;

/**
 * This is a repository for all commands as it loads and
 * registers them on every server startup.
 *
 * The execution of the commands and registration itself is handled by
 * spigot/bukkit, so it is not included in this class.
 *
 * @see CommandRegistryVersionTemplate
 * @see CreateCommand
 * @see CreateSubCommand
 * @see KelpCommand
 * @author pxav
 */
@Singleton
public class KelpCommandRepository {

  private TypeFinder typeFinder;
  private CommandRegistryVersionTemplate registryVersionTemplate;
  private Injector injector;
  private KelpLogger logger;

  @Inject
  public KelpCommandRepository(TypeFinder typeFinder,
                               CommandRegistryVersionTemplate registryVersionTemplate,
                               Injector injector,
                               KelpLogger logger) {
    this.typeFinder = typeFinder;
    this.registryVersionTemplate = registryVersionTemplate;
    this.injector = injector;
    this.logger = logger;
  }

  /**
   * Searches for all command classes and automatically registers
   * them. This method also searches for sub commands and adds them to
   * their parent commands.
   *
   * @param packages The packages in which kelp should search for.
   */
  public void loadCommands(String... packages) {

    // at first, it searches for sub commands. This is important, because
    // sub commands have to be added before the parent command is registered.
    // Otherwise the sub command cannot be included in the registration process
    // and is not registered/executable.
    typeFinder.filter(packages,
      TypeCriterion.annotatedWith(CreateSubCommand.class),
      TypeCriterion.subclassOf(KelpCommand.class))
    .forEach(current -> {
      CreateSubCommand subCommandAnnotation = current.getAnnotation(CreateSubCommand.class);
      KelpCommand subCommandClass = injector.getInstance(current.asSubclass(current));
      KelpCommand parentCommandClass = injector.getInstance(subCommandAnnotation.parentCommand());

      // add sub command to main command. This is important, so that only the main
      // commands have to be registered later.
      Map<KelpCommand, CreateSubCommand> subCommands = parentCommandClass.getSubCommands();
      subCommands.put(subCommandClass, subCommandAnnotation);

      subCommandClass.onCommandRegister();
      parentCommandClass.subCommands(subCommands);
      logger.log(LogLevel.DEBUG, "[COMMAND] Registered sub command "
        + subCommandAnnotation.name()
        + " for class "
        + subCommandAnnotation.parentCommand().getName());
    });

    // when all sub commands have been loaded, register main commands.
    typeFinder.filter(packages,
      TypeCriterion.annotatedWith(CreateCommand.class),
      TypeCriterion.subclassOf(KelpCommand.class))
    .forEach(current -> {
      KelpCommand commandClass = injector.getInstance(current.asSubclass(current));
      CreateCommand commandAnnotation = current.getAnnotation(CreateCommand.class);

      commandClass.onCommandRegister();

      // inherit properties from main command to sub commands if needed.
      commandClass.getSubCommands().forEach((subCommand, subCommandAnnotation) -> {
        if (!subCommand.shouldInheritFromMainCommand()) {
          return;
        }
        if (subCommand.getNoPermissionMessage() == null) {
          subCommand.noPermissionMessage(commandClass.getNoPermissionMessage());
        }
        if (subCommand.getNoConsoleMessage() == null) {
          subCommand.noConsoleMessage(commandClass.getNoConsoleMessage());
        }
        if (subCommand.getNoPlayerMessage() == null) {
          subCommand.noPlayerMessage(commandClass.getNoPlayerMessage());
        }
        if (subCommand.getPermission() == null) {
          subCommand.permission(commandClass.getPermission());
        }
        if (subCommand.getDescription() == null) {
          subCommand.description(commandClass.getDescription());
        }
      });

      // add command to bukkit registry
      registryVersionTemplate.registerCommand(commandClass, commandAnnotation);
      logger.log(LogLevel.DEBUG, "[COMMAND] Registered main command "
        + commandAnnotation.name()
        + " in class "
        + current.getName());
    });

    logger.log("[COMMAND] Successfully loaded all commands for " + Arrays.toString(packages));
  }


}
