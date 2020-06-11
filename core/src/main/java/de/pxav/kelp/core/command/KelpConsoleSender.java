package de.pxav.kelp.core.command;

import de.pxav.kelp.core.command.version.KelpConsoleSenderVersionTemplate;
import org.bukkit.command.CommandSender;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpConsoleSender {

  private String name;
  private CommandSender bukkitSender;

  private KelpConsoleSenderVersionTemplate versionTemplate;

  KelpConsoleSender(CommandSender bukkitSender, KelpConsoleSenderVersionTemplate versionTemplate) {
    this.bukkitSender = bukkitSender;
    this.versionTemplate = versionTemplate;
  }

  public void sendMessage(String message) {
    versionTemplate.sendMessage(this, message);
  }

  public KelpConsoleSender name(String name) {
    this.name = name;
    return this;
  }

  public KelpConsoleSender bukkitSender(CommandSender bukkitSender) {
    this.bukkitSender = bukkitSender;
    return this;
  }

  public String getName() {
    return name;
  }

  public CommandSender getBukkitSender() {
    return bukkitSender;
  }

}
