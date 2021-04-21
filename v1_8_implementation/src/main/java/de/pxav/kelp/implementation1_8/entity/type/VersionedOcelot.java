package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.OcelotEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedTameableAnimal;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftOcelot;
import org.bukkit.entity.Ocelot;

public class VersionedOcelot extends VersionedTameableAnimal<OcelotEntity> implements OcelotEntity {

  public VersionedOcelot(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    CraftOcelot craftOcelot = (CraftOcelot) entityHandle.getBukkitEntity();
    craftOcelot.setCatType(Ocelot.Type.WILD_OCELOT);
  }

}
