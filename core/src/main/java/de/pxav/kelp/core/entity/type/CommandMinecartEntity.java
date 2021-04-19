package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MinecartEntity;

public interface CommandMinecartEntity extends MinecartEntity<CommandMinecartEntity> {

  String getCommand();

  CommandMinecartEntity setCommand(String command);

}
