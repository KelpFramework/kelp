package de.pxav.kelp.core.npc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

  private UUID uuid;
  private String overHeadDisplayName;
  private List<String> titles;
  private String skinSignature;
  private String skinTexture;

  private boolean showInTab = false;
  private String tabListName;
  // armor, ...

  private NpcVersionTemplate npcVersionTemplate;

  public KelpNpc(NpcVersionTemplate npcVersionTemplate) {
    this.npcVersionTemplate = npcVersionTemplate;

    this.titles = Lists.newArrayList();
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

  public void spawn(Player player) {
    if (this.uuid == null) {
      this.uuid = UUID.randomUUID();
    }

    if (this.overHeadDisplayName == null) {
      this.overHeadDisplayName = " ";
    }

    npcVersionTemplate.spawnNpc(this, player);
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

  public Map<Integer, Double> getTitleHeights() {
    // height 1 nearest to npc
    Map<Integer, Double> output = Maps.newHashMap();
    double yAxis = -.3;
    for (int i = 1; i < 15; i++) {
      yAxis += .25;
      output.put(i, yAxis);
    }
//    output.put(1, -0.3);
//    output.put(2, -0.05);
//    output.put(3, .2);
//    output.put(4, .45);
//    output.put(5, .70);
//    output.put(6, .95);

    return output;
  }
}
