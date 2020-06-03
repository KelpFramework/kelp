package de.pxav.kelp.core.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.version.KelpConsoleSenderVersionTemplate;
import org.bukkit.command.CommandSender;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpConsoleSenderFactory {

  private KelpConsoleSenderVersionTemplate versionTemplate;

  @Inject
  public KelpConsoleSenderFactory(KelpConsoleSenderVersionTemplate versionTemplate) {
    this.versionTemplate = versionTemplate;
  }

  public KelpConsoleSender newKelpConsoleSender(CommandSender bukkitSender) {
    return new KelpConsoleSender(bukkitSender, this.versionTemplate);
  }

}
