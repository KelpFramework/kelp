package de.pxav.kelp.core.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.version.CommandRegistryVersionTemplate;
import de.pxav.kelp.core.reflect.TypeCriterion;
import de.pxav.kelp.core.reflect.TypeFinder;

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

  @Inject
  public KelpCommandRepository(TypeFinder typeFinder, CommandRegistryVersionTemplate registryVersionTemplate, Injector injector) {
    this.typeFinder = typeFinder;
    this.registryVersionTemplate = registryVersionTemplate;
    this.injector = injector;
  }

  public void loadCommands(String... packages) {
    typeFinder.filter(packages,
      TypeCriterion.annotatedWith(CreateCommand.class),
      TypeCriterion.subclassOf(KelpCommand.class))
    .forEach(current -> {
      KelpCommand commandClass = injector.getInstance(current.asSubclass(current));
      CreateCommand commandAnnotation = current.getAnnotation(CreateCommand.class);

      registryVersionTemplate.registerCommand(commandClass, commandAnnotation);
      System.out.println("Successfully registered command " + commandAnnotation.name());
    });
  }


}
