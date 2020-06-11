package de.pxav.kelp.core.command.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.KelpCommand;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class CommandRegistryVersionTemplate {

  public abstract void registerCommand(KelpCommand command, CreateCommand commandAnnotation);

}
