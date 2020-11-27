package de.pxav.kelp.implementation1_12.command;

import de.pxav.kelp.core.command.KelpConsoleSender;
import de.pxav.kelp.core.command.version.KelpConsoleSenderVersionTemplate;
import de.pxav.kelp.core.version.Versioned;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedConsoleSender extends KelpConsoleSenderVersionTemplate {

  @Override
  public void sendMessage(KelpConsoleSender consoleSender, String message) {
    consoleSender.getBukkitSender().sendMessage(message);
  }

  @Override
  public String getName(KelpConsoleSender consoleSender) {
    return consoleSender.getBukkitSender().getName();
  }

}
