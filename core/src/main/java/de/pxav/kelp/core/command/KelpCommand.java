package de.pxav.kelp.core.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpCommand {

  private Map<KelpCommand, CreateSubCommand> subCommands = Maps.newHashMap();
  private Collection<String> aliases = Lists.newArrayList();
  private String description;
  private String noPermissionMessage;
  private String noPlayerMessage;
  private String noConsoleMessage;
  private String permission;
  private boolean allowCustomParameters;
  private boolean argumentsStartFromZero;

  public void onCommand(KelpPlayer player, String[] args) {}

  public void onCommand(KelpConsoleSender consoleSender, String[] args) {}

  public Map<KelpCommand, CreateSubCommand> getSubCommands() {
    return subCommands;
  }

  public void description(String description) {
    this.description = description;
  }

  public KelpCommand noPermissionMessage(String noPermissionMessage) {
    this.noPermissionMessage = noPermissionMessage;
    return this;
  }

  public KelpCommand noPlayerMessage(String noPlayerMessage) {
    this.noPlayerMessage = noPlayerMessage;
    return this;
  }

  public KelpCommand noConsoleMessage(String noConsoleMessage) {
    this.noConsoleMessage = noConsoleMessage;
    return this;
  }

  public KelpCommand permission(String permission) {
    this.permission = permission;
    return this;
  }

  public KelpCommand setAliases(Collection<String> aliases) {
    this.aliases = aliases;
    return this;
  }

  public KelpCommand setAliases(String... aliases) {
    this.aliases = Arrays.asList(aliases);
    return this;
  }

  public KelpCommand addAlias(String alias) {
    this.aliases.add(alias);
    return this;
  }

  public KelpCommand addAliases(String... aliases) {
    this.aliases.addAll(Arrays.asList(aliases));
    return this;
  }

  public KelpCommand addAliases(Collection<String> aliases) {
    this.aliases.addAll(aliases);
    return this;
  }

  public KelpCommand removeAlias(String alias) {
    this.aliases.remove(alias);
    return this;
  }

  public KelpCommand removeAliases(String... aliases) {
    this.aliases.removeAll(Arrays.asList(aliases));
    return this;
  }

  public KelpCommand removeAliases(Collection<String> aliases) {
    this.aliases.removeAll(aliases);
    return this;
  }

  public KelpCommand subCommands(Map<KelpCommand, CreateSubCommand> subCommands) {
    this.subCommands = subCommands;
    return this;
  }

  public KelpCommand allowCustomParameters(boolean allow) {
    this.allowCustomParameters = allow;
    return this;
  }

  public KelpCommand argumentsStartFromZero(boolean argumentsStartFromZero) {
    this.argumentsStartFromZero = argumentsStartFromZero;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public String getNoPermissionMessage() {
    return noPermissionMessage;
  }

  public String getNoPlayerMessage() {
    return noPlayerMessage;
  }

  public String getNoConsoleMessage() {
    return noConsoleMessage;
  }

  public String getPermission() {
    return permission;
  }

  public boolean customParametersAllowed() {
    return this.allowCustomParameters;
  }

  public boolean shouldArgumentsStartFromZero() {
    return this.argumentsStartFromZero;
  }

  public Collection<String> getAliases() {
    return aliases;
  }
}
