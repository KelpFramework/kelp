package de.pxav.kelp.implementation1_8.inventory;

import com.google.inject.Inject;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.item.ItemTagVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.listener.KelpClickEvent;
import de.pxav.kelp.core.inventory.listener.KelpListenerRepository;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class InventoryClickListener {

  private KelpListenerRepository listenerRepository;
  private ItemTagVersionTemplate itemTagVersionTemplate;

  @Inject
  public InventoryClickListener(KelpListenerRepository listenerRepository,
                                ItemTagVersionTemplate itemTagVersionTemplate) {
    this.listenerRepository = listenerRepository;
    this.itemTagVersionTemplate = itemTagVersionTemplate;
  }

  @EventHandler
  public void handleInventoryClick(InventoryClickEvent event) {
    if (event.getClickedInventory() == null
      || event.getCurrentItem() == null
      || event.getCurrentItem().getType() == Material.AIR) {
        return;
    }

    Inventory inventory = event.getInventory();
    ItemStack itemStack = event.getCurrentItem();

    if (itemTagVersionTemplate.hasTagKey(itemStack, "interactionCancelled")) {
      event.setCancelled(true);
    }

    KelpPlayer player = KelpPlayer.from(event.getWhoClicked().getUniqueId());
    KelpItem item = KelpItem.from(itemStack).slot(event.getSlot());

    for (String current : itemTagVersionTemplate.getTagKeys(itemStack)) {
      if (!current.startsWith("listener-")) {
        continue;
      }

      String listenerId = itemTagVersionTemplate.getStringValue(itemStack, current);

      listenerRepository.fireListener(listenerId, new KelpClickEvent(player, item));
    }

    if (player.getBukkitPlayer().getGameMode() == GameMode.CREATIVE
      && player.getBukkitPlayer().getInventory().equals(event.getInventory())
      && itemTagVersionTemplate.hasTagKey(itemStack, "interactionCancelled")) {
        player.getBukkitPlayer().closeInventory();

        Bukkit.getScheduler().runTaskLater(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
          player.getBukkitPlayer().getInventory().remove(itemStack);

          if (inventory.getItem(event.getSlot()) == null) {
            inventory.setItem(event.getSlot(), itemStack);
          }

          player.getBukkitPlayer().updateInventory();
        }, 1L);
    }

  }

}
