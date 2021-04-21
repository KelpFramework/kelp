package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.CommandMinecartEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractMinecart;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMinecartCommand;

public class VersionedCommandMinecartEntity extends VersionedAbstractMinecart<CommandMinecartEntity> implements CommandMinecartEntity {

  CraftMinecartCommand craftMinecart;

  public VersionedCommandMinecartEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftMinecart = (CraftMinecartCommand) entityHandle.getBukkitEntity();
  }

  @Override
  public String getCommand() {
    return craftMinecart.getCommand();
  }

  @Override
  public CommandMinecartEntity setCommand(String command) {
    craftMinecart.setCommand(command);
    return this;
  }

}
