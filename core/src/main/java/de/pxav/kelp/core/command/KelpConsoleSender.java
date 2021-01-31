package de.pxav.kelp.core.command;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.command.version.KelpConsoleSenderVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.command.CommandSender;

/**
 * The KelpConsoleSender is an implementation of the normal
 * CommandSender from bukkit. It basically offers the same features
 * as the normal console sender.
 *
 * The advantage of using this class is that it is version
 * independent and you can simply create a new class which inherits
 * from this class and add your own features for the console
 * sender easily, which is impossible in the normal bukkit api.
 *
 * @author pxav
 */
public class KelpConsoleSender {

  // the name of the console sender. CONSOLE if it is a console
  // and the player's name if the sender is a player.
  private String name;

  // the bukkit object of the sender.
  private CommandSender bukkitSender;

  private KelpConsoleSenderVersionTemplate versionTemplate;

  KelpConsoleSender(CommandSender bukkitSender, KelpConsoleSenderVersionTemplate versionTemplate) {
    this.bukkitSender = bukkitSender;
    this.versionTemplate = versionTemplate;
  }

  public static KelpConsoleSender create(CommandSender bukkitSender) {
    return new KelpConsoleSender(
      bukkitSender,
      KelpPlugin.getInjector().getInstance(KelpConsoleSenderVersionTemplate.class)
    );
  }

  /**
   * Sends a message to the console sender.
   * @param message The message you want to send (may contain color codes using §)
   */
  public void sendMessage(String message) {
    versionTemplate.sendMessage(this, message);
  }

  /**
   * Sets the name of the sender.
   *
   * @param name The name you want to set.
   * @return The current sender object containing all the data which is currently set.
   */
  public KelpConsoleSender name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the bukkit sender object.
   *
   * @param bukkitSender The {@code CommandSender} object you want to set.
   * @return The current sender object containing all the data which is currently set.
   */
  public KelpConsoleSender bukkitSender(CommandSender bukkitSender) {
    this.bukkitSender = bukkitSender;
    return this;
  }

  /**
   * @return The name of the sender.
   */
  public String getName() {
    return name;
  }

  /**
   * @return The {@code CommandSender} object of bukkit for this sender.
   */
  public CommandSender getBukkitSender() {
    return bukkitSender;
  }

}
