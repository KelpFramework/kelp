package de.pxav.kelp.core.player;

import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpPlayer {

  private PlayerVersionTemplate playerVersionTemplate;
  private SidebarRepository sidebarRepository;
  private KelpInventoryRepository inventoryRepository;

  private Player bukkitPlayer;

  public KelpPlayer(Player bukkitPlayer,
                    PlayerVersionTemplate playerVersionTemplate,
                    SidebarRepository sidebarRepository,
                    KelpInventoryRepository inventoryRepository) {
    this.bukkitPlayer = bukkitPlayer;
    this.playerVersionTemplate = playerVersionTemplate;
    this.sidebarRepository = sidebarRepository;
    this.inventoryRepository = inventoryRepository;
  }

  public KelpPlayer openKelpSidebar(String identifier) {
    this.sidebarRepository.openSidebar(identifier, bukkitPlayer);
    return this;
  }

  public KelpPlayer removeKelpSidebar() {
    this.sidebarRepository.removeSidebar(bukkitPlayer);
    return this;
  }

  public KelpPlayer openInventory(KelpInventory inventory) {
    this.inventoryRepository.openInventory(inventory, this);
    return this;
  }

  public KelpPlayer closeInventory() {
    this.inventoryRepository.closeInventory(this);
    return this;
  }

  public KelpPlayer forceInventoryClose() {
    bukkitPlayer.closeInventory();
    return this;
  }

  public KelpPlayer sendTitle(String title, String subTitle) {
    playerVersionTemplate.sendTitle(bukkitPlayer, title, subTitle, 20, 60, 20);
    return this;
  }

  public KelpPlayer sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
    playerVersionTemplate.sendTitle(bukkitPlayer, title, subTitle, fadeIn, stay, fadeOut);
    return this;
  }


  public KelpPlayer sendActionbar(String message) {
    playerVersionTemplate.sendActionBar(bukkitPlayer, message);
    return this;
  }

  public UUID getUUID() {
    return playerVersionTemplate.getUniqueId(bukkitPlayer);
  }

  public KelpPlayer teleport(Location location) {
    playerVersionTemplate.teleport(bukkitPlayer, location);
    return this;
  }

  public KelpPlayer setHealth(int health) {
    playerVersionTemplate.setHealth(bukkitPlayer, health);
    return this;
  }

  public Player getBukkitPlayer() {
    return bukkitPlayer;
  }
}
