package de.pxav.kelp.implementation1_8.npc;

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
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import java.util.*;
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
      armorStand.setGravity(false);
      armorStand.setCustomNameVisible(true);
      armorStand.setCustomName(npc.getTitles().get(0));
      npc.getTitles().remove(0);
      armorStandIds.add(armorStand.getId());

      playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorStand));
    });

    if (npc.getCustomName() == null || npc.getCustomName().equalsIgnoreCase("")) {
      npc.customName("KelpNpc" + ThreadLocalRandom.current().nextInt(10_000));
    }
    KelpNpcMeta npcMeta = new KelpNpcMeta(entityId, gameProfile, npc.getCustomName(), armorStandIds);

    reflectionUtil.setValue(spawnPacket, "a", entityId);
    reflectionUtil.setValue(spawnPacket, "b", gameProfile.getId());
    reflectionUtil.setValue(spawnPacket, "c", MathHelper.floor(npc.getSpawnLocation().getX() * 32.0D));
    reflectionUtil.setValue(spawnPacket, "d", MathHelper.floor(npc.getSpawnLocation().getY() * 32.0D));
    reflectionUtil.setValue(spawnPacket, "e", MathHelper.floor(npc.getSpawnLocation().getZ() * 32.0D));
    reflectionUtil.setValue(spawnPacket, "f", (byte) ((int) (npc.getSpawnLocation().getYaw() * 256.0F / 360.0F)));
    reflectionUtil.setValue(spawnPacket, "g", (byte) ((int) (npc.getSpawnLocation().getPitch() * 256.0F / 360.0F)));

    if (npc.getItemInHand() != null) {
      reflectionUtil.setValue(spawnPacket, "h", npc.getItemInHand().getItemStack().getType().getId());
    }

    DataWatcher dataWatcher = new DataWatcher(null);
    this.applyToDataWatcher(dataWatcher, npc);
    reflectionUtil.setValue(spawnPacket, "i", dataWatcher);

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
  public void moveRelativeDistance(KelpNpc npc, Player player, double x, double y, double z, float absoluteYaw, float absolutePitch) {

    npc.getArmorStandEntityIds().forEach(armorStand -> {
      PacketPlayOutEntity.PacketPlayOutRelEntityMove movePacket = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(
        armorStand,
        (byte) MathHelper.floor(x * 32.0D),
        (byte) MathHelper.floor(y * 32.0D),
        (byte) MathHelper.floor(z * 32.0D),
        true
      );
      ((CraftPlayer)player).getHandle().playerConnection.sendPacket(movePacket);
    });

    PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook moveLookPacket = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
      npc.getEntityId(),
      (byte) MathHelper.floor(x * 32.0D),
      (byte) MathHelper.floor(y * 32.0D),
      (byte) MathHelper.floor(z * 32.0D),
      (byte) ((int) (absoluteYaw * 256.0F / 360.0F)),
      (byte) ((int) (absolutePitch * 256.0F / 360.0F)),
      true
    );

    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(moveLookPacket);
  }

  @Override
  public void teleport(KelpNpc npc, Location location) {
    CraftPlayer craftPlayer = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;

    PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport();

    reflectionUtil.setValue(teleportPacket, "a", npc.getEntityId());
    reflectionUtil.setValue(teleportPacket, "b", MathHelper.floor(location.getX() * 32.0D));
    reflectionUtil.setValue(teleportPacket, "c", MathHelper.floor(location.getY() * 32.0D));
    reflectionUtil.setValue(teleportPacket, "d", MathHelper.floor(location.getZ() * 32.0D));
    reflectionUtil.setValue(teleportPacket, "e", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
    reflectionUtil.setValue(teleportPacket, "f", (byte) ((int) (location.getPitch() * 256.0F / 360.0F)));

    double yIndex = -.3;
    for (Integer entityId : npc.getArmorStandEntityIds()) {
      PacketPlayOutEntityTeleport teleportArmorStandPacket = new PacketPlayOutEntityTeleport();
      reflectionUtil.setValue(teleportArmorStandPacket, "a", entityId);
      reflectionUtil.setValue(teleportArmorStandPacket, "b", MathHelper.floor(location.getX() * 32.0D));

      reflectionUtil.setValue(teleportArmorStandPacket, "c", MathHelper.floor((location.getY() + yIndex) * 32.0D));

      reflectionUtil.setValue(teleportArmorStandPacket, "d", MathHelper.floor(location.getZ() * 32.0D));
      reflectionUtil.setValue(teleportArmorStandPacket, "e", (byte) 0);
      reflectionUtil.setValue(teleportArmorStandPacket, "f", (byte) 0);
      playerConnection.sendPacket(teleportArmorStandPacket);
      yIndex += .25;
    }

    PacketPlayOutEntityHeadRotation headRotationPacket = new PacketPlayOutEntityHeadRotation();
    reflectionUtil.setValue(headRotationPacket, "a", npc.getEntityId());
    reflectionUtil.setValue(headRotationPacket, "b", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));

    playerConnection.sendPacket(teleportPacket);
    playerConnection.sendPacket(headRotationPacket);
  }

  @Override
  public void updateCustomName(KelpNpc npc) {
    CraftPlayer craftPlayer = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;

    ScoreboardTeam team = new ScoreboardTeam(((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), craftPlayer.getName());

    if (npc.isCustomNameShown()) {
      team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
    } else {
      team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
    }

    Collection<String> toHide = Lists.newArrayList(npc.getCustomName());

    playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
    playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
    playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, toHide,3));
  }

  @Override
  public void refreshMetadata(KelpNpc npc, Player player) {
    DataWatcher dataWatcher = new DataWatcher(null);

    applyToDataWatcher(dataWatcher, npc);
    PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata(npc.getEntityId(), dataWatcher, true);
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(metaPacket);
  }

  private void addToTab(KelpNpcMeta npc, Player player) {
    PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo();
    PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = infoPacket.new PlayerInfoData(npc.getGameProfile(), 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(npc.getOverHeadDisplayName())[0]);

    List<PacketPlayOutPlayerInfo.PlayerInfoData> players = new ArrayList<>();
    players.add(playerInfoData);

    reflectionUtil.setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
    reflectionUtil.setValue(infoPacket, "b", players);

    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(infoPacket);
  }

  private void removeFromTab(KelpNpcMeta npc, Player player) {
    PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo();
    PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = infoPacket.new PlayerInfoData(npc.getGameProfile(), 1, WorldSettings.EnumGamemode.NOT_SET, CraftChatMessage.fromString(npc.getOverHeadDisplayName())[0]);
    List<PacketPlayOutPlayerInfo.PlayerInfoData> players = new ArrayList<>();
    players.add(playerInfoData);

    reflectionUtil.setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
    reflectionUtil.setValue(infoPacket, "b", players);

    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(infoPacket);
  }

  private DataWatcher applyToDataWatcher(DataWatcher dataWatcher, KelpNpc kelpNpc) {

    if (kelpNpc.hasBurningEffect()) {
      dataWatcher.a(0, (byte) 0x01);
    }

    if (kelpNpc.isSneaking()) {
      dataWatcher.a(0, (byte) 0x02);
    }

    if (kelpNpc.isSprinting()) {
      dataWatcher.a(0, (byte) 0x08);
    }

    if (kelpNpc.isInvisible()) {
      dataWatcher.a(0, (byte) 0x20);
    }

    if (!kelpNpc.isSneaking() && !kelpNpc.hasBurningEffect() && !kelpNpc.isInvisible() && !kelpNpc.isSprinting()) {
      dataWatcher.a(0, (byte) 0);
    }

    if (kelpNpc.isCustomNameShown()) {
      dataWatcher.a(11, (byte) 2);
    } else {
      dataWatcher.a(11, (byte) 0);
    }

    return dataWatcher;
  }

}
