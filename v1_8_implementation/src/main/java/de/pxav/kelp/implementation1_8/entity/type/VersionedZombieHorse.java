package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ZombieHorse;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractHorse;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.entity.Horse;

public class VersionedZombieHorse extends VersionedAbstractHorse<ZombieHorse> implements ZombieHorse {

  public VersionedZombieHorse(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, InventoryVersionTemplate inventoryVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, inventoryVersionTemplate);
    ((CraftHorse)entityHandle.getBukkitEntity()).setVariant(Horse.Variant.UNDEAD_HORSE);
  }

}
