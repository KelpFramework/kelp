package de.pxav.kelp.core.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.version.KelpConsoleSenderVersionTemplate;
import org.bukkit.command.CommandSender;

/**
 * This is a factory class for the {@code KelpConsoleSender}.
 * New objects of this class cannot be created from outside
 * the Kelp command package, so if you want to create a new
 * console sender, you have to use this class.
 *
 * @see KelpConsoleSender
 * @author pxav
 */
@Singleton
public class KelpConsoleSenderFactory {

  private KelpConsoleSenderVersionTemplate versionTemplate;

  @Inject
  public KelpConsoleSenderFactory(KelpConsoleSenderVersionTemplate versionTemplate) {
    this.versionTemplate = versionTemplate;
  }

  /**
   * Creates a new instance of {@code KelpConsoleSender} and injects
   * all necessary dependencies.
   *
   * @param bukkitSender The {@code CommandSender} object from bukkit of the
   *                     console sender you want to convert to a KelpConsoleSender.
   * @return The new instance.
   */
  public KelpConsoleSender newKelpConsoleSender(CommandSender bukkitSender) {
    return new KelpConsoleSender(bukkitSender, this.versionTemplate);
  }

}
