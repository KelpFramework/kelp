package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.KillerBunnyEntity;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.entity.Rabbit;

public class VersionedKillerBunny extends VersionedRabbit implements KillerBunnyEntity {

  public VersionedKillerBunny(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, EntityConstantsVersionTemplate entityConstantsVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, entityConstantsVersionTemplate);
    craftRabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
  }

}
