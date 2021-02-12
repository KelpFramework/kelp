package de.pxav.kelp.implementation1_8.npc;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.event.kelpevent.npc.NpcSpawnEvent;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.KelpNpcMeta;
import de.pxav.kelp.core.npc.NpcAnimation;
import de.pxav.kelp.core.npc.activity.WalkToTargetActivity;
import de.pxav.kelp.core.npc.version.NpcVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
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

    // set random custom name if null
    if (npc.getCustomName() == null || npc.getCustomName().equalsIgnoreCase("")) {
      npc.customName("n" + ThreadLocalRandom.current().nextInt(10_000));
    }

    GameProfile gameProfile = new GameProfile(npc.getUuid(), npc.getCustomName());

    if (npc.getSkinTexture() != null && npc.getSkinSignature() != null) {
      gameProfile.getProperties().put("textures", new Property("textures", npc.getSkinTexture(), npc.getSkinSignature()));
    }

    List<String> currentTitles = npc.getCurrentTitles();
    Collections.reverse(currentTitles);
    Collection<Integer> armorStandIds = Lists.newArrayList();
    for (int i = 0; i < currentTitles.size(); i++) {
      double height = npc.getTitleHeights(i);

      EntityArmorStand armorStand = new EntityArmorStand(nmsWorld,
        npc.getLocation().getX(),
        npc.getLocation().clone().add(0, height, 0).getY(),
        npc.getLocation().getZ());
      armorStand.setInvisible(true);
      armorStand.setBasePlate(false);
      armorStand.setGravity(false);
      armorStand.setCustomNameVisible(true);
      armorStand.setCustomName(currentTitles.get(i));
      armorStandIds.add(armorStand.getId());

      playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorStand));
    }

    KelpNpcMeta npcMeta = new KelpNpcMeta(entityId, gameProfile, npc.getCustomName(), armorStandIds);

    reflectionUtil.setValue(spawnPacket, "a", entityId);
    reflectionUtil.setValue(spawnPacket, "b", gameProfile.getId());
    reflectionUtil.setValue(spawnPacket, "c", MathHelper.floor(npc.getLocation().getX() * 32.0D));
    reflectionUtil.setValue(spawnPacket, "d", MathHelper.floor(npc.getLocation().getY() * 32.0D));
    reflectionUtil.setValue(spawnPacket, "e", MathHelper.floor(npc.getLocation().getZ() * 32.0D));
    reflectionUtil.setValue(spawnPacket, "f", (byte) ((int) (npc.getLocation().getYaw() * 256.0F / 360.0F)));
    reflectionUtil.setValue(spawnPacket, "g", (byte) ((int) (npc.getLocation().getPitch() * 256.0F / 360.0F)));

    if (npc.getItemInHand() != null) {
      reflectionUtil.setValue(spawnPacket, "h", npc.getItemInHand().getItemStack().getType().getId());
    }

    DataWatcher dataWatcher = new DataWatcher(null);
    this.applyToDataWatcher(dataWatcher, npc);

    reflectionUtil.setValue(spawnPacket, "i", dataWatcher);

    // add npc to tab
    if (!npc.shouldShowInTab()) {
      npc.showInTab(true);
      updateTab(npc, gameProfile);
      npc.showInTab(false);
    } else {
      updateTab(npc, gameProfile);
    }

    // remove npc from tab and hide custom name if needed
    Bukkit.getScheduler().runTaskLater(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
      if (!npc.shouldShowInTab()) {
        updateTab(npc, gameProfile);
      }
      if (!npc.isCustomNameShown()) {
        updateCustomName(npc);
      }

      setHelmet(npc);
      setChestPlate(npc);
      setLeggings(npc);
      setBoots(npc);
    }, 2L);

    playerConnection.sendPacket(spawnPacket);
    Bukkit.getPluginManager().callEvent(new NpcSpawnEvent(npc, !npc.isInitiallySpawned()));

    return npcMeta;
  }

  @Override
  public void deSpawn(KelpNpc npc, Player player) {
    npc.getNpcMeta().getArmorStandEntityIds().forEach(current -> {
      PacketPlayOutEntityDestroy armorStandDestroyPacket = new PacketPlayOutEntityDestroy(current);
      ((CraftPlayer)player).getHandle().playerConnection.sendPacket(armorStandDestroyPacket);
    });

    PacketPlayOutEntityDestroy npcDestroyPacket = new PacketPlayOutEntityDestroy(npc.getEntityId());
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(npcDestroyPacket);
  }

  @Override
  public void moveRelativeDistance(KelpNpc npc, Player player, double x, double y, double z, float absoluteYaw, float absolutePitch) {

    npc.getNpcMeta().getArmorStandEntityIds().forEach(armorStand -> {
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

    PacketPlayOutEntityHeadRotation headRotationPacket = new PacketPlayOutEntityHeadRotation();
    reflectionUtil.setValue(headRotationPacket, "a", npc.getEntityId());
    reflectionUtil.setValue(headRotationPacket, "b", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));

    playerConnection.sendPacket(teleportPacket);
    playerConnection.sendPacket(headRotationPacket);

    int index = 0;
    for (Integer entityId : npc.getNpcMeta().getArmorStandEntityIds()) {
      double height = npc.getTitleHeights(index);
      PacketPlayOutEntityTeleport teleportArmorStandPacket = new PacketPlayOutEntityTeleport();
      reflectionUtil.setValue(teleportArmorStandPacket, "a", entityId);
      reflectionUtil.setValue(teleportArmorStandPacket, "b", MathHelper.floor(location.getX() * 32.0D));

      reflectionUtil.setValue(teleportArmorStandPacket, "c", MathHelper.floor((location.getY() + height) * 32.0D));

      reflectionUtil.setValue(teleportArmorStandPacket, "d", MathHelper.floor(location.getZ() * 32.0D));
      reflectionUtil.setValue(teleportArmorStandPacket, "e", (byte) 0);
      reflectionUtil.setValue(teleportArmorStandPacket, "f", (byte) 0);
      playerConnection.sendPacket(teleportArmorStandPacket);
      index++;
    }
  }

  @Override
  public void updateCustomName(KelpNpc npc) {
    CraftPlayer craftPlayer = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;

    // parameters: scoreboard, team name
    ScoreboardTeam team = new ScoreboardTeam(((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), npc.getCustomName());

    if (npc.isCustomNameShown()) {
      team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
    } else {
      team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
    }

    Collection<String> toHide = Lists.newArrayList(npc.getCustomName());

    // the int stands for the mode:
    // 0 = create
    // 1 = remove
    // 3 = add/remove players
    playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
    playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
    playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, toHide,3));
  }

  @Override
  public void updateTitleLines(KelpNpc npc) {
    CraftPlayer player = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    WorldServer nmsWorld = ((CraftWorld) npc.getLocation().getWorld()).getHandle();

    npc.getNpcMeta().getArmorStandEntityIds().forEach(current -> {
      PacketPlayOutEntityDestroy armorStandDestroyPacket = new PacketPlayOutEntityDestroy(current);
      player.getHandle().playerConnection.sendPacket(armorStandDestroyPacket);
    });

    List<String> currentTitles = npc.getCurrentTitles();
    Collections.reverse(currentTitles);
    Collection<Integer> armorStandIds = Lists.newArrayList();

    for (int i = 0; i < currentTitles.size(); i++) {
      double height = npc.getTitleHeights(i);

      EntityArmorStand armorStand = new EntityArmorStand(nmsWorld,
        npc.getLocation().getX(),
        npc.getLocation().clone().add(0, height, 0).getY(),
        npc.getLocation().getZ());
      armorStand.setInvisible(true);
      armorStand.setBasePlate(false);
      armorStand.setGravity(false);
      armorStand.setCustomNameVisible(true);
      armorStand.setCustomName(currentTitles.get(i));
      armorStandIds.add(armorStand.getId());

      player.getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorStand));
    }
    npc.setArmorStandEntityIds(armorStandIds);
  }

  @Override
  public void playAnimation(KelpNpc npc, NpcAnimation animation) {
    CraftPlayer player = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    PlayerConnection connection = player.getHandle().playerConnection;

    PacketPlayOutAnimation animationPacket = new PacketPlayOutAnimation();
    reflectionUtil.setValue(animationPacket, "a", npc.getEntityId());

    PacketPlayOutEntityStatus statusPacket = new PacketPlayOutEntityStatus();
    reflectionUtil.setValue(statusPacket, "a", npc.getEntityId());

    if (animation == NpcAnimation.TAKE_DAMAGE) {
      reflectionUtil.setValue(animationPacket, "b", 1);
      connection.sendPacket(animationPacket);
      return;
    }

    if (animation == NpcAnimation.MAIN_HAND_SWING) {
      reflectionUtil.setValue(animationPacket, "b", 0);
      connection.sendPacket(animationPacket);
      return;
    }

    if (animation == NpcAnimation.LEAVE_BED) {
      reflectionUtil.setValue(animationPacket, "b", 2);
      connection.sendPacket(animationPacket);
      return;
    }

    if (animation == NpcAnimation.EAT) {
      reflectionUtil.setValue(animationPacket, "b", 3);
      connection.sendPacket(animationPacket);
      return;
    }

    if (animation == NpcAnimation.CRITICAL_EFFECT) {
      reflectionUtil.setValue(animationPacket, "b", 4);
      connection.sendPacket(animationPacket);
      return;
    }

    if (animation == NpcAnimation.MAGIC_CRITICAL_EFFECT) {
      reflectionUtil.setValue(animationPacket, "b", 5);
      connection.sendPacket(animationPacket);
      return;
    }

    if (animation == NpcAnimation.ENTITY_DEATH) {
      reflectionUtil.setValue(statusPacket, "b", (byte) 3);
      connection.sendPacket(statusPacket);
      return;
    }

  }

  @Override
  public void makeCorpse(KelpNpc npc) {
    CraftPlayer player = (CraftPlayer) npc.getPlayer().getBukkitPlayer();

    Location bedLocation = new Location(
      npc.getLocation().getWorld(),
      npc.getLocation().getX(),
      npc.getLocation().getY(),
      npc.getLocation().getZ());

    BlockPosition blockPosition = new BlockPosition(
      bedLocation.getX(),
      bedLocation.getY(),
      bedLocation.getZ());

    player.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte) 0);

    PacketPlayOutBed bedPacket = new PacketPlayOutBed();
    reflectionUtil.setValue(bedPacket, "a", npc.getEntityId());
    reflectionUtil.setValue(bedPacket, "b", blockPosition);

    player.getHandle().playerConnection.sendPacket(bedPacket);
  }

  @Override
  public void sleep(KelpNpc npc, Location bedLocation) {
    CraftPlayer player = (CraftPlayer) npc.getPlayer().getBukkitPlayer();

    BlockPosition blockPosition = new BlockPosition(
      bedLocation.getX(),
      bedLocation.getY(),
      bedLocation.getZ());

    PacketPlayOutBed bedPacket = new PacketPlayOutBed();
    reflectionUtil.setValue(bedPacket, "a", npc.getEntityId());
    reflectionUtil.setValue(bedPacket, "b", blockPosition);

    player.getHandle().playerConnection.sendPacket(bedPacket);
  }

  @Override
  public void wakeUp(KelpNpc npc) {
    CraftPlayer player = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    playAnimation(npc, NpcAnimation.LEAVE_BED);

    if (npc.isCorpse()) {
      Material serverMaterial = npc.getLocation().getBlock().getType();
      player.sendBlockChange(npc.getLocation(), serverMaterial, npc.getLocation().getBlock().getData());
    }

    // walk back to initial location for a more smooth transition
    npc.addActivity(WalkToTargetActivity.create().target(npc.getLocation()));
  }

  @Override
  public void setItemInHand(KelpNpc npc) {
    if (npc.getItemInHand() == null) {
      return;
    }
    giveEquipment(npc, 0, npc.getItemInHand());
  }

  @Override
  public void setHelmet(KelpNpc npc) {
    giveEquipment(npc, 4, npc.getHelmet());
  }

  @Override
  public void setChestPlate(KelpNpc npc) {
    giveEquipment(npc, 3, npc.getChestPlate());
  }

  @Override
  public void setLeggings(KelpNpc npc) {
    giveEquipment(npc, 2, npc.getLeggings());
  }

  @Override
  public void setBoots(KelpNpc npc) {
    giveEquipment(npc, 1, npc.getBoots());
  }

  @Override
  public void refreshMetadata(KelpNpc npc, Player player) {
    DataWatcher dataWatcher = new DataWatcher(null);

    applyToDataWatcher(dataWatcher, npc);
    PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata(npc.getEntityId(), dataWatcher, true);
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(metaPacket);
  }

  @Override
  public void updateTab(KelpNpc npc, @Nullable GameProfile gameProfile) {
    CraftPlayer player = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    PacketPlayOutPlayerInfo infoPacket = new PacketPlayOutPlayerInfo();

    // if no custom tab name is set, use the npc's custom name
    String listName = npc.getTabListName() == null ? npc.getCustomName() : npc.getTabListName();

    GameProfile profile = gameProfile == null ? npc.getNpcMeta().getGameProfile() : gameProfile;

    PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = infoPacket.new PlayerInfoData(
      profile,
      1,
      WorldSettings.EnumGamemode.NOT_SET,
      CraftChatMessage.fromString(listName)[0]);

    List<PacketPlayOutPlayerInfo.PlayerInfoData> players = new ArrayList<>();
    players.add(playerInfoData);

    if (npc.shouldShowInTab()) {
      reflectionUtil.setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
    } else {
      reflectionUtil.setValue(infoPacket, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
    }

    reflectionUtil.setValue(infoPacket, "b", players);

    player.getHandle().playerConnection.sendPacket(infoPacket);
  }

  private void giveEquipment(KelpNpc npc, int slot, KelpItem item) {
    if (item == null) {
      return;
    }
    CraftPlayer player = (CraftPlayer) npc.getPlayer().getBukkitPlayer();
    ItemStack itemStack = CraftItemStack.asNMSCopy(item.getItemStack());
    PacketPlayOutEntityEquipment equipmentPacket = new PacketPlayOutEntityEquipment(
      npc.getEntityId(),
      slot,
      itemStack
    );
    player.getHandle().playerConnection.sendPacket(equipmentPacket);
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
