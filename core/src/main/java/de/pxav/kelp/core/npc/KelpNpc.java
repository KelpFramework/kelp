package de.pxav.kelp.core.npc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.npc.version.NpcVersionTemplate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpNpc {

  private Location location;
  private KelpItem itemInHand;

  private int entityId;
  private UUID uuid;
  private String overHeadDisplayName;
  private GameProfile gameProfile;

  private List<String> titles;
  private String skinSignature;
  private String skinTexture;

  private double removeDistance;
  private boolean followHeadRotation;
  private boolean imitateSneaking;
  private boolean isBurning;
  private boolean isInvisible;
  private boolean isSneaking;

  private boolean showInTab = false;
  private String tabListName;
  // armor, ...

  private KelpNpcMeta npcMeta;

  private NpcVersionTemplate npcVersionTemplate;
  private KelpNpcRepository kelpNpcRepository;

  public KelpNpc(NpcVersionTemplate npcVersionTemplate, KelpNpcRepository kelpNpcRepository) {
    this.npcVersionTemplate = npcVersionTemplate;
    this.kelpNpcRepository = kelpNpcRepository;

    this.titles = Lists.newArrayList();
    this.removeDistance = 40;
    this.isBurning = false;
  }

  public KelpNpc location(Location location) {
    this.location = location;
    return this;
  }

  public KelpNpc itemInHand(KelpItem itemInHand) {
    this.itemInHand = itemInHand;
    return this;
  }

  public KelpNpc overHeadDisplayName(String overHeadDisplayName) {
    this.overHeadDisplayName = overHeadDisplayName;
    return this;
  }

  public KelpNpc uuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public KelpNpc uuid(String uuid) {
    this.uuid = UUID.fromString(uuid);
    return this;
  }

  public KelpNpc tabListName(String tabListName) {
    this.tabListName = tabListName;
    return this;
  }

  public KelpNpc addTitleLine(String lineToAdd) {
    this.titles.add(lineToAdd);
    return this;
  }

  public KelpNpc addTitleLine(List<String> linesToAdd) {
    this.titles.addAll(linesToAdd);
    return this;
  }

  public KelpNpc setTitleLines(List<String> lines) {
    this.titles = lines;
    return this;
  }

  public KelpNpc skinTexture(String skinTexture) {
    this.skinTexture = skinTexture;
    return this;
  }

  public KelpNpc skinSignature(String skinSignature) {
    this.skinSignature = skinSignature;
    return this;
  }

  public KelpNpc imitateSneaking() {
    this.imitateSneaking = true;
    return this;
  }

  public KelpNpc doNotImitateSneaking() {
    this.imitateSneaking = false;
    return this;
  }

  public KelpNpc followHeadRotation() {
    this.followHeadRotation = true;
    return this;
  }

  public KelpNpc unfollowHeadRotation() {
    this.imitateSneaking = false;
    return this;
  }

  public KelpNpc addBurningEffect() {
    this.isBurning = true;
    return this;
  }

  public KelpNpc removeBurningEffect() {
    this.isBurning = false;
    return this;
  }

  public KelpNpc setInvisible() {
    this.isInvisible = true;
    return this;
  }

  public KelpNpc setVisible() {
    this.isInvisible = false;
    return this;
  }

  public KelpNpc spawn(Player player) {
    if (this.uuid == null) {
      this.uuid = UUID.randomUUID();
    }

    if (this.overHeadDisplayName == null) {
      this.overHeadDisplayName = " ";
    }

    this.npcMeta = npcVersionTemplate.spawnNpc(this, player);
    gameProfile = npcMeta.getGameProfile();
    entityId = npcMeta.getEntityId();
    overHeadDisplayName = npcMeta.getOverHeadDisplayName();

    this.kelpNpcRepository.addNpc(this, player);
    return this;
  }

  public KelpNpc deSpawn(Player player) {
    npcVersionTemplate.deSpawn(this, player);
    this.npcMeta = null;
    return this;
  }

  public KelpNpc refresh(Player player) {
    npcVersionTemplate.refresh(this, player);
    return this;
  }

  public KelpNpc updateHeadRotation(Player player) {
    return this;
  }

  public KelpNpc sneak() {
    this.isSneaking = true;
    return this;
  }

  public KelpNpc unSneak() {
    this.isSneaking = false;
    return this;
  }

  public boolean isSneaking() {
    return isSneaking;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getOverHeadDisplayName() {
    return overHeadDisplayName;
  }

  public Location getLocation() {
    return location;
  }

  public List<String> getTitles() {
    return titles;
  }

  public String getSkinSignature() {
    return skinSignature;
  }

  public String getSkinTexture() {
    return skinTexture;
  }

  public String getTabListName() {
    return tabListName;
  }

  public KelpItem getItemInHand() {
    return itemInHand;
  }

  public boolean shouldShowInTab() {
    return showInTab;
  }

  public boolean shouldImitateSneaking() {
    return imitateSneaking;
  }

  public boolean shouldFollowHeadRotation() {
    return followHeadRotation;
  }

  public boolean hasBurningEffect() {
    return isBurning;
  }

  public boolean isInvisible() {
    return isInvisible;
  }

  public GameProfile getGameProfile() {
    return gameProfile;
  }

  public int getEntityId() {
    return entityId;
  }

  public KelpNpcMeta getNpcMeta() {
    return npcMeta;
  }

  public Map<Integer, Double> getTitleHeights() {
    // height 1 nearest to npc
    Map<Integer, Double> output = Maps.newHashMap();
    double yAxis = -.3;
    for (int i = 1; i < 15; i++) {
      yAxis += .25;
      output.put(i, yAxis);
    }

    return output;
  }
}
