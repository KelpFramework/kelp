package de.pxav.kelp.core.command.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.command.KelpConsoleSender;

/**
 * This version template should execute methods
 * of a {@code KelpConsoleSender}.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class KelpConsoleSenderVersionTemplate {

  /**
   * Sends a message to the console sender.
   *
   * @param consoleSender The console sender you want to send the message to.
   * @param message       The message you want to send. This may contain color
   *                      codes with '§'
   */
  public abstract void sendMessage(KelpConsoleSender consoleSender, String message);

  /**
   * @param consoleSender The console sender you want to get the name of.
   * @return The name of the given console sender.
   */
  public abstract String getName(KelpConsoleSender consoleSender);

}
