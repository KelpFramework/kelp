package de.pxav.kelp.testing.inventory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.testing.config.DebugCommandConfig;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
@CreateCommand(name = "kmaterial", executorType = ExecutorType.PLAYER_ONLY)
public class KMaterialCommand extends KelpCommand {

  private DebugCommandConfig config;

  @Inject
  public KMaterialCommand(DebugCommandConfig config) {
    this.config = config;
  }

  @Override
  public void onCommandRegister() {
    permission(config.getStringValue("permission.kmaterial"));
    noPlayerMessage(config.getStringValue("msg.noPlayer"));
    noPermissionMessage(config.getStringValue("msg.noPermission"));
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    player.sendMessage(config.getStringValue("msg.materialHelp"));
  }

}
