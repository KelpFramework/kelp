package de.pxav.kelp.testing.sidebar;

import de.pxav.kelp.core.animation.BuildingTextAnimation;
import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.component.EmptyLineComponent;
import de.pxav.kelp.core.sidebar.component.LineSeparatorComponent;
import de.pxav.kelp.core.sidebar.component.StatefulTextComponent;
import de.pxav.kelp.core.sidebar.component.StatelessTextComponent;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.sidebar.type.SimpleSidebar;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@CreateSubCommand(name = "show",
  executorType = ExecutorType.PLAYER_ONLY,
  parentCommand = KSidebarCommand.class)
public class ShowCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    allowCustomParameters(true);
    argumentsStartFromZero(true);
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    if (args.length == 0) {
      player.sendMessage("§cPlease provide an example id!");
      return;
    }

    int id = 1;

    try {
      id = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      player.sendMessage("§cExample id has to be a numeric value!");
      return;
    }

    if (id < 1 || id > 3) {
      player.sendMessage("§cExample id is out of range!");
      return;
    }

    switch (id) {
      case 1:
        SimpleSidebar sidebar1 = SimpleSidebar.create();
        sidebar1.staticTitle("§2KELP EXAMPLE SIDEBAR 1");

        sidebar1.addComponent(LineSeparatorComponent.create().line(20));
        sidebar1.addComponent(EmptyLineComponent.create().line(19));
        sidebar1.addComponent(StatelessTextComponent.create().line(18).text("§2§lServer"));
        sidebar1.addComponent(StatelessTextComponent.create().line(17).text("§8» §7Lobby-1"));
        sidebar1.addComponent(EmptyLineComponent.create().line(16));

        sidebar1.addComponent(StatelessTextComponent.create().line(15).text("§2§lTeamSpeak"));
        sidebar1.addComponent(StatelessTextComponent.create().line(14).text("§8» §7ts.serverdomain.com"));
        sidebar1.addComponent(EmptyLineComponent.create().line(13));

        sidebar1.addComponent(StatelessTextComponent.create().line(12).text("§2§lDate"));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        sidebar1.addComponent(StatelessTextComponent.create().line(11).text("§8» §7" + format.format(new Date())));
        sidebar1.addComponent(EmptyLineComponent.create().line(10));

        sidebar1.render(player);

        break;
      case 2:
        SimpleSidebar sidebar2 = SimpleSidebar.create();
        sidebar2.title(() -> "§2KELP EXAMPLE " + ThreadLocalRandom.current().nextInt(100));

        sidebar2.addComponent(LineSeparatorComponent.create().line(20));
        sidebar2.addComponent(EmptyLineComponent.create().line(19));
        sidebar2.addComponent(StatelessTextComponent.create().line(18).text("§2§lExperience"));
        sidebar2.addComponent(StatelessTextComponent.create().line(17).text("§8» §7" + player.getLevel()));
        sidebar2.addComponent(EmptyLineComponent.create().line(16));

        sidebar2.addComponent(StatelessTextComponent.create().line(15).text("§2§lPlayers Online"));
        sidebar2.addComponent(StatefulTextComponent.create().line(14).text(() -> "§8» §7" + Bukkit.getOnlinePlayers().size()));
        sidebar2.addComponent(EmptyLineComponent.create().line(13));

        sidebar2.addComponent(StatelessTextComponent.create().line(12).text("§2§lTime"));
        SimpleDateFormat dateTime = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        sidebar2.addComponent(StatefulTextComponent.create().line(11).text(() -> "§8» §7" + dateTime.format(new Date())));
        sidebar2.addComponent(EmptyLineComponent.create().line(10));

        sidebar2.render(player);

        break;
      case 3:
        AnimatedSidebar sidebar3 = AnimatedSidebar.create();
        sidebar3.title(BuildingTextAnimation.create().text("§2§lKELP EXAMPLE SIDEBAR"));
        sidebar3.titleAnimationInterval(150);
        sidebar3.clusterId("example_sidebar_3");

        sidebar3.addComponent(LineSeparatorComponent.create().line(20));
        sidebar3.addComponent(EmptyLineComponent.create().line(19));
        sidebar3.addComponent(StatelessTextComponent.create().line(18).text("§2§lExperience"));
        sidebar3.addComponent(StatefulTextComponent.create().line(17).text(() -> "§8» §7" + player.getLevel()));
        sidebar3.addComponent(EmptyLineComponent.create().line(16));

        sidebar3.addComponent(StatelessTextComponent.create().line(15).text("§2§lPlayers Online"));
        sidebar3.addComponent(StatefulTextComponent.create().line(14).text(() -> "§8» §7" + Bukkit.getOnlinePlayers().size()));
        sidebar3.addComponent(EmptyLineComponent.create().line(13));

        sidebar3.addComponent(StatelessTextComponent.create().line(12).text("§2§lTime"));
        SimpleDateFormat dateTime2 = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        sidebar3.addComponent(StatefulTextComponent.create().line(11).text(() -> "§8» §7" + dateTime2.format(new Date())));
        sidebar3.addComponent(EmptyLineComponent.create().line(10));

        sidebar3.render(player);

        break;
    }

    player.sendMessage("§8[§2Kelp§8] §7Successfully rendered sidebar §a" + id + " §7to you.");

  }
}
