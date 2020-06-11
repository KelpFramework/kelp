package de.pxav.kelp.core.command;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.version.CommandRegistryVersionTemplate;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.reflect.TypeCriterion;
import de.pxav.kelp.core.reflect.TypeFinder;

import java.util.Map;

/**
 * A class description goes here.
 *
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

  public void loadCommands(String... packages) {
    typeFinder.filter(packages,
      TypeCriterion.annotatedWith(CreateSubCommand.class),
      TypeCriterion.subclassOf(KelpCommand.class))
    .forEach(current -> {
      CreateSubCommand subCommandAnnotation = current.getAnnotation(CreateSubCommand.class);
      KelpCommand subCommandClass = injector.getInstance(current.asSubclass(current));
      KelpCommand parentCommandClass = injector.getInstance(subCommandAnnotation.parentCommand());

      Map<KelpCommand, CreateSubCommand> subCommands = parentCommandClass.getSubCommands();
      subCommands.put(subCommandClass, subCommandAnnotation);

      subCommandClass.onCommandRegister();
      parentCommandClass.subCommands(subCommands);
      logger.writeLog("[COMMAND] Successfully registered sub command "
        + subCommandAnnotation.name()
        + " for class "
        + subCommandAnnotation.parentCommand().getName());
    });

    typeFinder.filter(packages,
      TypeCriterion.annotatedWith(CreateCommand.class),
      TypeCriterion.subclassOf(KelpCommand.class))
    .forEach(current -> {
      KelpCommand commandClass = injector.getInstance(current.asSubclass(current));
      CreateCommand commandAnnotation = current.getAnnotation(CreateCommand.class);

      commandClass.onCommandRegister();
      registryVersionTemplate.registerCommand(commandClass, commandAnnotation);
      logger.writeLog("[COMMAND] Successfully registered main command "
        + commandAnnotation.name()
        + " in class "
        + current.getName());
    });
  }


}
