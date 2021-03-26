package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.PlayerInventoryVersionTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Set;
import java.util.UUID;

public class PlayerInventory {

  private PlayerInventoryVersionTemplate versionTemplate;
  private KelpPlayer player;

  private static ConcurrentSetMultimap<UUID, SimpleWidget> simpleWidgets = ConcurrentSetMultimap.create();
  private static ConcurrentSetMultimap<UUID, GroupedWidget> groupedWidgets = ConcurrentSetMultimap.create();

  public static PlayerInventory of(KelpPlayer player) {
    return new PlayerInventory(
      player,
      KelpPlugin.getInjector().getInstance(PlayerInventoryVersionTemplate.class)
    );
  }

  private PlayerInventory(KelpPlayer player, PlayerInventoryVersionTemplate versionTemplate) {
    this.player = player;
    this.versionTemplate = versionTemplate;
  }

  public Set<KelpItem> getItems() {
    return versionTemplate.getItems(player.getBukkitPlayer());
  }

  public KelpItem getItemAt(int slot) {
    return versionTemplate.getItemAt(player.getBukkitPlayer(), slot);
  }

  public Set<KelpItem> getHotBarItems() {
    return versionTemplate.getHotBarItems(player.getBukkitPlayer());
  }

  public PlayerInventory setHelmet(KelpItem helmet) {
    versionTemplate.setHelmet(player.getBukkitPlayer(), helmet);
    return this;
  }

  public PlayerInventory setChestPlate(KelpItem chestPlate) {
    versionTemplate.setChestPlate(player.getBukkitPlayer(), chestPlate);
    return this;
  }

  public PlayerInventory setLeggings(KelpItem leggings) {
    versionTemplate.setLeggings(player.getBukkitPlayer(), leggings);
    return this;
  }

  public PlayerInventory setBoots(KelpItem boots) {
    versionTemplate.setBoots(player.getBukkitPlayer(), boots);
    return this;
  }

  public KelpItem getHelmet() {
    return versionTemplate.getHelmet(player.getBukkitPlayer());
  }

  public KelpItem getChestPlate() {
    return versionTemplate.getChestPlate(player.getBukkitPlayer());
  }

  public KelpItem getLeggings() {
    return versionTemplate.getLeggings(player.getBukkitPlayer());
  }

  public KelpItem getBoots() {
    return versionTemplate.getBoots(player.getBukkitPlayer());
  }

  public PlayerInventory setItem(int slot, KelpItem item) {
    versionTemplate.setItem(player.getBukkitPlayer(), slot, item);
    return this;
  }

  public PlayerInventory addItem(KelpItem item) {
    versionTemplate.addItem(player.getBukkitPlayer(), item);
    return this;
  }

  public KelpItem getItemInHand() {
    return versionTemplate.getItemInHand(player.getBukkitPlayer());
  }

  public PlayerInventory setItemInHand(KelpItem item) {
    versionTemplate.setItemInHand(player.getBukkitPlayer(), item);
    return this;
  }

  public KelpItem getItemInOffHand() {
    return versionTemplate.getItemInOffHand(player.getBukkitPlayer());
  }

  public PlayerInventory setItemInOffHand(KelpItem item) {
    versionTemplate.setItemInOffHand(player.getBukkitPlayer(), item);
    return this;
  }

  public PlayerInventory addWidget(SimpleWidget simpleWidget) {
    simpleWidgets.put(player.getUUID(), simpleWidget);
    return this;
  }

  public PlayerInventory addWidget(GroupedWidget groupedWidget) {
    groupedWidgets.put(player.getUUID(), groupedWidget);
    return this;
  }

  public PlayerInventory removeSimpleWidget(Class<? extends SimpleWidget> widgetClass) {
    simpleWidgets.get(player.getUUID()).forEach(widget -> {
      if (widgetClass.getName().equalsIgnoreCase(widget.getClass().getName())) {
        removeWidget(widget);
      }
    });
    return this;
  }

  public PlayerInventory removeGroupedWidget(Class<? extends GroupedWidget> widgetClass) {
    groupedWidgets.get(player.getUUID()).forEach(widget -> {
      if (widgetClass.getName().equalsIgnoreCase(widget.getClass().getName())) {
        removeWidget(widget);
      }
    });
    return this;
  }

  public PlayerInventory removeWidget(SimpleWidget widget) {
    player.getBukkitPlayer().getInventory().clear(widget.getCoveredSlot());
    widget.onRemove();
    simpleWidgets.remove(player.getUUID(), widget);
    return this;
  }

  public PlayerInventory removeWidget(GroupedWidget widget) {
    widget.getCoveredSlots().forEach(slot -> player.getBukkitPlayer().getInventory().clear(slot));
    widget.onRemove();
    groupedWidgets.remove(player.getUUID(), widget);
    return this;
  }

  public PlayerInventory removeAllWidgets() {
    simpleWidgets.getOrEmpty(player.getUUID()).forEach(this::removeWidget);
    groupedWidgets.getOrEmpty(player.getUUID()).forEach(this::removeWidget);
    return this;
  }

  public PlayerInventory updateWidgets() {
    for (SimpleWidget current : simpleWidgets.getOrEmpty(player.getUUID())) {
      KelpItem item = current.render();
      if (!item.hasTagKey("interactionAllowed")) {
        item.cancelInteractions();
      }
      setItem(item.getSlot(), item);
      if (!current.isStateful()) {
        simpleWidgets.remove(player.getUUID(), current);
      }
    }

    for (GroupedWidget current : groupedWidgets.getOrEmpty(player.getUUID())) {
      current.render(player).forEach(item -> {
        if (!item.hasTagKey("interactionAllowed")) {
          item.cancelInteractions();
        }
        setItem(item.getSlot(), item);
      });
      if (!current.isStateful()) {
        groupedWidgets.remove(player.getUUID(), current);
      }
    }

    return this;
  }

  public KelpPlayer getPlayer() {
    return player;
  }

}
