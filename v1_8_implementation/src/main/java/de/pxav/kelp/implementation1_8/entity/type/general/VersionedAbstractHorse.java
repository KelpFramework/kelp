package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AbstractHorseEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.AbstractHorseInventory;
import de.pxav.kelp.core.inventory.type.HorseInventory;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHorse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VersionedAbstractHorse<T extends AbstractHorseEntity<T>>
  extends VersionedAnimalEntity<T>
  implements AbstractHorseEntity<T> {

  private EntityHorse horseHandle;
  private InventoryVersionTemplate inventoryVersionTemplate;

  public VersionedAbstractHorse(Entity entityHandle,
                                KelpEntityType entityType,
                                Location initialLocation,
                                EntityTypeVersionTemplate entityTypeVersionTemplate,
                                InventoryVersionTemplate inventoryVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.horseHandle = (EntityHorse) entityHandle;
    this.inventoryVersionTemplate = inventoryVersionTemplate;
  }

  @Override
  public int getDomestication() {
    return horseHandle.getTemper();
  }

  @Override
  public HorseInventory getInventory() {
    // in 1.8 there are no Llamas, so there is actually no need to return an abstraction
    // of a horse inventory. In newer versions, an AbstractHorseInventory should be returned here.
    CraftHorse craftHorse = (CraftHorse) horseHandle.getBukkitEntity();
    return (HorseInventory) inventoryVersionTemplate.getStorageInventory(craftHorse.getInventory());
  }

  @Override
  public double getJumpStrength() {
    return horseHandle.getJumpStrength();
  }

  @Override
  public int getMaximumDomestication() {
    return horseHandle.getMaxDomestication();
  }

  @Override
  public T setDomestication(int domesticationLevel) {
    horseHandle.setTemper(domesticationLevel);
    return (T) this;
  }

  @Override
  public T setJumpStrength(double jumpStrength) {
    horseHandle.getAttributeInstance(EntityHorse.attributeJumpStrength).setValue(jumpStrength);
    return (T) this;
  }

  @Override
  public T setMaximumDomestication(int maximumDomestication) {
    horseHandle.maxDomestication = maximumDomestication;
    return (T) this;
  }

  @Override
  public T setTamed(boolean tamed) {
    horseHandle.setTame(tamed);
    return (T) this;
  }

  @Override
  public boolean isTamed() {
    return horseHandle.isTame();
  }

  @Override
  public KelpEntity<?> getOwner() {
    Player player = Bukkit.getPlayer(getOwnerUUID());
    if (player == null) {
      return null;
    }
    return KelpEntity.from(player);
  }

  @Override
  public UUID getOwnerUUID() {
    return UUID.fromString(horseHandle.getOwnerUUID());
  }

  @Override
  public T setOwner(KelpEntity<?> owner) {
    horseHandle.setOwnerUUID(owner.getUUID().toString());
    return (T) this;
  }

}
