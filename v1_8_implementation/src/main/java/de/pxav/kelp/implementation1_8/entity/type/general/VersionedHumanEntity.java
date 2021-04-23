package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.HumanEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.world.KelpLocation;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class VersionedHumanEntity<T extends HumanEntity<T>>
  extends VersionedMobileEntity<T>
  implements HumanEntity<T> {

  private final EntityHuman humanHandle;
  private final CraftHumanEntity craftHumanEntity;

  public VersionedHumanEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.humanHandle = (EntityHuman) entityHandle;
    this.craftHumanEntity = (CraftHumanEntity) entityHandle.getBukkitEntity();
  }

  @Override
  public GameMode getGameMode() {
    return humanHandle.getBukkitEntity().getGameMode();
  }

  @Override
  public T setGameMode(GameMode gameMode) {
    humanHandle.getBukkitEntity().setGameMode(gameMode);
    return (T) this;
  }

  @Override
  public KelpLocation getCurrentBedLocation() {
    return KelpLocation.from(
      humanHandle.getWorld().getWorldData().getName(),
      humanHandle.getBed().getX(),
      humanHandle.getBed().getY(),
      humanHandle.getBed().getZ());
  }

  @Override
  public int getExpToLevel() {
    return craftHumanEntity.getExpToLevel();
  }

  @Override
  public T wakeUp() {
    humanHandle.sleepTicks = 0;
    return (T) this;
  }

  @Override
  public T wakeUpAndSetSpawnLocation() {
    humanHandle.world.everyoneSleeping();
    humanHandle.sleepTicks = 0;
    return (T) this;
  }

  @Override
  public T sleep(KelpLocation bedLocation) {
    BlockPosition bedPosition = new BlockPosition(
      bedLocation.getX(),
      bedLocation.getY(),
      bedLocation.getZ());

    PacketPlayOutBed bedPacket = new PacketPlayOutBed();
    ReflectionUtil.setValue(bedPacket, "a", getEntityId());
    ReflectionUtil.setValue(bedPacket, "b", bedPosition);

    for (Player player : Bukkit.getOnlinePlayers()) {
      ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bedPacket);
    }
    return (T) this;
  }

}
