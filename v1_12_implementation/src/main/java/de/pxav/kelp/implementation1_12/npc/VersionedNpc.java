package de.pxav.kelp.implementation1_12.npc;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.KelpNpcMeta;
import de.pxav.kelp.core.npc.version.NpcVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedNpc extends NpcVersionTemplate {

  private ReflectionUtil reflectionUtil;

  @Inject
  public VersionedNpc(ReflectionUtil reflectionUtil) {
    this.reflectionUtil = reflectionUtil;
  }

  @Override
  public KelpNpcMeta spawnNpc(KelpNpc npc, Player player) {
    PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
    WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
    PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn();
    int entityId = ThreadLocalRandom.current().nextInt(10_000) + 2_000;

    GameProfile gameProfile = new GameProfile(npc.getUuid(), npc.getCustomName());

    if (npc.getSkinTexture() != null && npc.getSkinSignature() != null) {
      gameProfile.getProperties().put("textures", new Property("textures", npc.getSkinTexture(), npc.getSkinSignature()));
    }

    Collections.reverse(npc.getTitles());
    Collection<Integer> armorStandIds = Lists.newArrayList();
    npc.getTitleHeights().forEach((lineId, height) -> {
      if (npc.getTitles().isEmpty() || npc.getTitles().get(0) == null) {
        return;
      }

      EntityArmorStand armorStand = new EntityArmorStand(nmsWorld,
              npc.getSpawnLocation().getX(),
              npc.getSpawnLocation().clone().add(0, height, 0).getY(),
              npc.getSpawnLocation().getZ());
      armorStand.setInvisible(true);
      armorStand.setBasePlate(false);
      armorStand.setNoGravity(true);
      armorStand.setCustomNameVisible(true);
      armorStand.setCustomName(npc.getTitles().get(0));
      npc.getTitles().remove(0);
      armorStandIds.add(armorStand.getId());

      playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorStand));
    });

    KelpNpcMeta npcMeta = new KelpNpcMeta(entityId, gameProfile, npc.getCustomName(), armorStandIds);

    setValue(spawnPacket, "a", entityId);
    setValue(spawnPacket, "b", gameProfile.getId());
    setValue(spawnPacket, "c", MathHelper.floor(npc.getSpawnLocation().getX() * 32.0D));
    setValue(spawnPacket, "d", MathHelper.floor(npc.getSpawnLocation().getY() * 32.0D));
    setValue(spawnPacket, "e", MathHelper.floor(npc.getSpawnLocation().getZ() * 32.0D));
    setValue(spawnPacket, "f", (byte) ((int) (npc.getSpawnLocation().getYaw() * 256.0F / 360.0F)));
    setValue(spawnPacket, "g", (byte) ((int) (npc.getSpawnLocation().getPitch() * 256.0F / 360.0F)));

    if (npc.getItemInHand() != null) {
      setValue(spawnPacket, "h", npc.getItemInHand().getItemStack().getType().getId());
    }

    DataWatcher dataWatcher = new DataWatcher(null);
    this.applyToDataWatcher(dataWatcher, npc);
    setValue(spawnPacket, "i", dataWatcher);

    addToTab(npcMeta, player);
    playerConnection.sendPacket(spawnPacket);

    Bukkit.getScheduler().runTaskLater(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
      removeFromTab(npcMeta, player);
    }, 30L);

    return npcMeta;
  }

  @Override
  public void deSpawn(KelpNpc npc, Player player) {
    npc.getArmorStandEntityIds().forEach(current -> {
      PacketPlayOutEntityDestroy armorStandDestroyPacket = new PacketPlayOutEntityDestroy(current);
      ((CraftPlayer)player).getHandle().playerConnection.sendPacket(armorStandDestroyPacket);
    });

    PacketPlayOutEntityDestroy npcDestroyPacket = new PacketPlayOutEntityDestroy(npc.getEntityId());
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(npcDestroyPacket);
  }

  @Override
  public void walkTo(KelpNpc npc, Player player, Location target, float yaw, float pitch) {
//    Vector vector;
//
//    PacketPlayOutEntity.PacketPlayOutRelEntityMove movePacket = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(
//      npc.getEntityId(),
//      (byte) (dx * 4096),
//      (byte) (dy * 4096),
//      (byte) (dz * 4096),
//      onGround
//    );
//
//    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(movePacket);
    throw new UnsupportedOperationException("The #walkTo method of NPCs is not available yet.");
  }

  @Override
  public void refresh(KelpNpc npc, Player player) {
    DataWatcher dataWatcher = new DataWatcher(null);

    applyToDataWatcher(dataWatcher, npc);
    PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata(npc.getEntityId(), dataWatcher, true);
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(metaPacket);

    teleport(player, npc);
  }

  private void setValue(Object object, String fieldName, Object value) {
    try {
      Field field = object.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(object, value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private void addToTab(KelpNpcMeta npc, Player player) {
    PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo();
    Object o = null;
    try {
      Class<?> infoDataClass = Class.forName("net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.PlayerInfoData");
      Constructor<?> constructor = infoDataClass.getDeclaredConstructor(npc.getGameProfile().getClass(), int.class, EnumGamemode.class, IChatBaseComponent.class);
      constructor.setAccessible(true);
      o = constructor.newInstance(npc.getGameProfile(), 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(npc.getOverHeadDisplayName())[0]);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    //PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = infoPacket.new PlayerInfoData(npc.getGameProfile(), 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(npc.getOverHeadDisplayName())[0]);

    List<Object> players = new ArrayList<>();
    players.add(o);

    setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
    setValue(infoPacket, "b", players);

    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(infoPacket);
  }

  private void removeFromTab(KelpNpcMeta npc, Player player) {
    PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo();
    Object o = null;
    try {
      Class<?> infoDataClass = Class.forName("net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.PlayerInfoData");
      Constructor<?> constructor = infoDataClass.getDeclaredConstructor(npc.getGameProfile().getClass(), int.class, EnumGamemode.class, IChatBaseComponent.class);
      constructor.setAccessible(true);
      o = constructor.newInstance(npc.getGameProfile(), 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(npc.getOverHeadDisplayName())[0]);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    //PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = infoPacket.new PlayerInfoData(npc.getGameProfile(), 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(npc.getOverHeadDisplayName())[0]);

    List<Object> players = new ArrayList<>();
    players.add(o);

    setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
    setValue(infoPacket, "b", players);

    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(infoPacket);
  }

  private DataWatcher applyToDataWatcher(DataWatcher dataWatcher, KelpNpc kelpNpc) {

    if (kelpNpc.hasBurningEffect()) {
      dataWatcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), 0x01);
    }

    if (kelpNpc.isSneaking()) {
      dataWatcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), 0x02);
    }

    if (kelpNpc.isInvisible()) {
      dataWatcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), 0x20);
    }

    if (!kelpNpc.isSneaking() && !kelpNpc.hasBurningEffect() && !kelpNpc.isInvisible()) {
      dataWatcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), 0);
    }

    if (kelpNpc.shouldShowCustomName()) {
      dataWatcher.register(new DataWatcherObject<>(3, DataWatcherRegistry.h), true);
    } else {
      dataWatcher.register(new DataWatcherObject<>(3, DataWatcherRegistry.h), false);
    }

    return dataWatcher;
  }

  private void teleport(Player player, KelpNpc kelpNpc) {
    PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport();

    setValue(teleportPacket, "a", kelpNpc.getEntityId());
    setValue(teleportPacket, "b", MathHelper.floor(kelpNpc.getSpawnLocation().getX() * 32.0D));
    setValue(teleportPacket, "c", MathHelper.floor(kelpNpc.getSpawnLocation().getY() * 32.0D));
    setValue(teleportPacket, "d", MathHelper.floor(kelpNpc.getSpawnLocation().getZ() * 32.0D));
    setValue(teleportPacket, "e", (byte) ((int) (kelpNpc.getSpawnLocation().getYaw() * 256.0F / 360.0F)));
    setValue(teleportPacket, "f", (byte) ((int) (kelpNpc.getSpawnLocation().getPitch() * 256.0F / 360.0F)));

    PacketPlayOutEntityHeadRotation headRotationPacket = new PacketPlayOutEntityHeadRotation();
    setValue(headRotationPacket, "a", kelpNpc.getEntityId());
    setValue(headRotationPacket, "b", (byte) ((int) (kelpNpc.getSpawnLocation().getYaw() * 256.0F / 360.0F)));

    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(teleportPacket);
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(headRotationPacket);
  }

}
