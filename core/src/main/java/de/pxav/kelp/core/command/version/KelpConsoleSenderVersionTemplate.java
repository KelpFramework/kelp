package de.pxav.kelp.core.command.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.command.KelpConsoleSender;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class KelpConsoleSenderVersionTemplate {

  public abstract void sendMessage(KelpConsoleSender consoleSender, String message);

  /**
   * @param consoleSender The console sender you want to get the name of.
   * @return
   */
  public abstract String getName(KelpConsoleSender consoleSender);

}
