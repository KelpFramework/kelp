package de.pxav.kelp.testing.command.kbossbar;

import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.bossbar.BossBarColor;
import de.pxav.kelp.core.player.bossbar.BossBarStyle;
import org.bukkit.ChatColor;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@CreateCommand(name = "kbossbar", executorType = ExecutorType.PLAYER_ONLY)
public class TestBossBarCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    noPlayerMessage("§cYou have to be a player to execute this command");
    permission("kelp.test.bossbar");
    allowCustomParameters(true);
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {

    if (args.length >= 2 && args[0].equalsIgnoreCase("create")) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 1; i < args.length; i++) {
        stringBuilder.append(args[i]).append(" ");
      }
      String message = ChatColor.translateAlternateColorCodes('&', stringBuilder.toString());
      player.sendBossBar(message);
      player.sendMessage("§8[§2Kelp§8] §7A boss bar with the message §a" + message + " §7has been created");
      return;
    }

    if (args.length >= 2 && args[0].equalsIgnoreCase("createprop")) {
      BossBarColor color = getColor(args[1]);
      BossBarStyle style = getStyle(args[2]);

      if (color == null) {
        player.sendMessage("§8[§2Kelp§8] §cUnknown color (§7'" + args[1] + "'§c) §cuse §7/kbossbar colors §cto see all colors available.");
        return;
      }

      if (style == null) {
        player.sendMessage("§8[§2Kelp§8] §cUnknown style (§7'" + args[2] + "'§c) §cuse §7/kbossbar styles §cto see all styles available.");
        return;
      }

      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 3; i < args.length; i++) {
        stringBuilder.append(args[i]).append(" ");
      }
      String message = ChatColor.translateAlternateColorCodes('&', stringBuilder.toString());
      player.sendBossBar(message, 300f, color, style);
      player.sendMessage("§8[§2Kelp§8] §7A boss bar with the message §a" + message + " §7has been created. ");
      return;
    }

    if (args.length == 2 && args[0].equalsIgnoreCase("progress")) {
      double percentage;
      try {
        percentage = Double.parseDouble(args[1]);
      } catch (NumberFormatException e) {
        player.sendMessage("§8[§2Kelp§8] §cInvalid percentage values. ");
        return;
      }

      if (percentage < 0 || percentage > 1) {
        player.sendMessage("§8[§2Kelp§8] §cValue out of range. Please choose a value between 0 and 1.");
      }

      player.setBossBarProgress(percentage);
      player.sendMessage("§8[§2Kelp§8] §7Progress of bossbar set. Current value §a" + (percentage * 100) + "%");

      return;
    }

    if (args.length == 3 && args[0].equalsIgnoreCase("progress")) {
      int current, max;
      try {
        max = Integer.parseInt(args[2]);
        current = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        player.sendMessage("§8[§2Kelp§8] §cInvalid numerical values.");
        return;
      }

      player.setBossBarProgress(current, max);
      player.sendMessage("§8[§2Kelp§8] §7Progress of bossbar set. Current value §a" + current + " §7of §a" + max);
      return;
    }

    if (args.length == 1 && args[0].equalsIgnoreCase("colors")) {
      player.sendMessage("§8[§2Kelp§8] §7The following colors are available§8:");
      for (BossBarColor value : BossBarColor.values()) {
        player.sendMessage("§8[§2Kelp§8] §8- §a" + value.name());
      }
      return;
    }

    if (args.length == 1 && args[0].equalsIgnoreCase("styles")) {
      player.sendMessage("§8[§2Kelp§8] §7The following styles are available§8:");
      for (BossBarStyle value : BossBarStyle.values()) {
        player.sendMessage("§8[§2Kelp§8] §8- §a" + value.name());
      }
      return;
    }

    if (args.length == 1 && args[0].equalsIgnoreCase("remove")) {
      player.removeBossBar();
      player.sendMessage("§8[§2Kelp§8] §7The boss bar has been §aremoved");
      return;
    }
    
    this.sendHelp(player);

  }

  private BossBarColor getColor(String color) {
    for (BossBarColor value : BossBarColor.values()) {
      if (value.name().equalsIgnoreCase(color)) {
        return value;
      }
    }
    return null;
  }

  private BossBarStyle getStyle(String style) {
    for (BossBarStyle value : BossBarStyle.values()) {
      if (value.name().equalsIgnoreCase(style)) {
        return value;
      }
    }
    return null;
  }
  
  private void sendHelp(KelpPlayer player) {
    player.sendPrefixedMessages("§8[§2Kelp§8] ",
      "§8§m-------------------------------",
      "§7/kbossar create <Message>",
      "§7/kbossar createprop <Color> <Style> <Message> ",
      "§7/kbossar progress <percentage>",
      "§7/kbossar progress <current> <max>",
      "§7/kbossar remove",
      "§7/kbossar colors",
      "§7/kbossar styles",
      "§8§m-------------------------------");
  }

}
