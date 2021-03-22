package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.PlayerInventoryVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Set;

public class PlayerInventory {

  private PlayerInventoryVersionTemplate versionTemplate;
  private KelpPlayer player;

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

  public KelpPlayer getPlayer() {
    return player;
  }

}
