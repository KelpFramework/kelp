package de.pxav.kelp.implementation1_8.command;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import de.pxav.kelp.core.command.*;
import de.pxav.kelp.core.command.version.CommandRegistryVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.PlayerFactory;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedCommandRegistry extends CommandRegistryVersionTemplate {

  private KelpConsoleSenderFactory consoleSenderFactory;
  private PlayerFactory playerFactory;
  private JavaPlugin kelpPlugin;

  @Inject
  public VersionedCommandRegistry(KelpConsoleSenderFactory consoleSenderFactory, PlayerFactory playerFactory, JavaPlugin kelpPlugin) {
    this.consoleSenderFactory = consoleSenderFactory;
    this.playerFactory = playerFactory;
    this.kelpPlugin = kelpPlugin;
  }

  @Override
  public void registerCommand(KelpCommand command, CreateCommand commandAnnotation) {
    Command bukkitCommand =
      new Command(commandAnnotation.name()) {

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
          if (args.length == 0) {
            return checkExecutorsAndExecute(sender, args, command, commandAnnotation.executorType());
          }
          KelpCommand parentCommand = command;
          for (int i = 1; i < args.length + 1; i++) {
            for (Map.Entry<KelpCommand, CreateSubCommand> subCommandEntry : parentCommand.getSubCommands().entrySet()) {
              KelpCommand subCommand = subCommandEntry.getKey();
              CreateSubCommand subCommandAnnotation = subCommandEntry.getValue();
              String subCommandName = args[i - 1];
              if (subCommandAnnotation.name().equalsIgnoreCase(subCommandName)) {
                if (i == args.length) {
                  return checkExecutorsAndExecute(sender, args, subCommand, subCommandAnnotation.executorType());
                } else {
                  parentCommand = subCommand;
                }
              }
            }
          }

          if (parentCommand.customParametersAllowed()) {
            ExecutorType executorType;
            if (parentCommand.equals(command)) {
              CreateCommand annotation = parentCommand.getClass().getAnnotation(CreateCommand.class);
              executorType = annotation.executorType();
            } else {
              CreateSubCommand annotation = parentCommand.getClass().getAnnotation(CreateSubCommand.class);
              executorType = annotation.executorType();
            }
            return checkExecutorsAndExecute(sender, args, parentCommand, executorType);
          }

          return false;
        }
      }.setAliases(Lists.newArrayList(command.getAliases()));

    CommandMap commandMap = ((CraftServer) Bukkit.getServer()).getCommandMap();

    if (commandMap.getCommand(commandAnnotation.name()) != null) {
      commandMap.getCommand(commandAnnotation.name()).unregister(commandMap);
    }

    try {
      final Field f = commandMap.getClass().getDeclaredField("knownCommands");
      f.setAccessible(true);
      Map<String, Command> knownCommands = (Map<String, Command>) f.get(commandMap);
      if (knownCommands.containsKey(bukkitCommand)) {
        knownCommands.remove(bukkitCommand);
        f.set(commandMap, knownCommands);
      }
    } catch (IllegalAccessException | NoSuchFieldException ex) {
      return;
    }

    commandMap.register(kelpPlugin.getName(), bukkitCommand);
  }



  private boolean checkExecutorsAndExecute(CommandSender sender, String[] args, KelpCommand command, ExecutorType executorType) {
    if (executorType == ExecutorType.CONSOLE_ONLY && sender instanceof Player) {
      if (command.getNoConsoleMessage() == null) {
        sender.sendMessage("Default no console message");
      } else {
        sender.sendMessage(command.getNoConsoleMessage());
      }
      return false;
    } else if(executorType == ExecutorType.CONSOLE_ONLY) {
      command.onCommand(consoleSenderFactory.newKelpConsoleSender(sender), args);
      return true;
    }

    if (executorType == ExecutorType.PLAYER_ONLY && !(sender instanceof Player)) {
      if (command.getNoPlayerMessage() == null) {
        sender.sendMessage("Default no player message");
      } else {
        sender.sendMessage(command.getNoPlayerMessage());
      }
      return false;
    }

    if (executorType == ExecutorType.PLAYER_ONLY) {
      return this.checkPlayerPermissionsAndExecute(sender, args, command, ExecutorType.PLAYER_ONLY);
    }

    if (executorType == ExecutorType.PLAYER_AND_CONSOLE) {
      if (sender instanceof Player) {
        return this.checkPlayerPermissionsAndExecute(sender, args, command, ExecutorType.PLAYER_AND_CONSOLE);
      }
      command.onCommand(consoleSenderFactory.newKelpConsoleSender(sender), args);
      return true;
    }
    return false;
  }

  private boolean checkPlayerPermissionsAndExecute(CommandSender sender, String[] args, KelpCommand command, ExecutorType executorType) {
    Player bukkitPlayer = (Player) sender;
    KelpPlayer player = playerFactory.newKelpPlayer(bukkitPlayer);
    if (command.getPermission() != null) {
      if (bukkitPlayer.hasPermission(command.getPermission())) {
        if (executorType == ExecutorType.PLAYER_AND_CONSOLE) {
          command.onCommand(consoleSenderFactory.newKelpConsoleSender(sender), args);
        } else if (executorType == ExecutorType.PLAYER_ONLY) {
          command.onCommand(player, args);
        }
        return true;
      } else if (command.getNoPermissionMessage() != null) {
        bukkitPlayer.sendMessage(command.getNoPermissionMessage());
      } else {
        bukkitPlayer.sendMessage("Default no permission message.");
      }
      return false;
    } else {
      if (executorType == ExecutorType.PLAYER_AND_CONSOLE) {
        command.onCommand(consoleSenderFactory.newKelpConsoleSender(sender), args);
      } else if (executorType == ExecutorType.PLAYER_ONLY) {
        command.onCommand(player, args);
      }
      return true;
    }
  }

}
