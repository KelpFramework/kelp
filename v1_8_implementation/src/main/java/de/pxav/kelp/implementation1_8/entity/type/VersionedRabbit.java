package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.RabbitEntity;
import de.pxav.kelp.core.entity.util.RabbitType;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedBreedableAnimal;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftRabbit;
import org.bukkit.entity.Rabbit;

public class VersionedRabbit extends VersionedBreedableAnimal<RabbitEntity> implements RabbitEntity {

  protected CraftRabbit craftRabbit;
  private EntityConstantsVersionTemplate entityConstantsVersionTemplate;

  public VersionedRabbit(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, EntityConstantsVersionTemplate entityConstantsVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.entityConstantsVersionTemplate = entityConstantsVersionTemplate;
    this.craftRabbit = (CraftRabbit) entityHandle.getBukkitEntity();
  }

  @Override
  public RabbitType getRabbitType() {
    return entityConstantsVersionTemplate.getRabbitType(craftRabbit.getRabbitType().name());
  }

  @Override
  public RabbitEntity setRabbitType(RabbitType rabbitType) {
    craftRabbit.setRabbitType(Rabbit.Type.valueOf(entityConstantsVersionTemplate.getRabbitType(rabbitType)));
    return this;
  }

}
