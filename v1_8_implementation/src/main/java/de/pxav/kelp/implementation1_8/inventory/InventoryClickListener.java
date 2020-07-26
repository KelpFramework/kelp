package de.pxav.kelp.implementation1_8.inventory;

import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.item.ItemTagVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.item.KelpItemFactory;
import de.pxav.kelp.core.inventory.listener.KelpClickEvent;
import de.pxav.kelp.core.inventory.listener.KelpListenerRepository;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class InventoryClickListener {

  private KelpPlayerRepository playerRepository;
  private KelpListenerRepository listenerRepository;
  private ItemTagVersionTemplate itemTagVersionTemplate;
  private KelpItemFactory itemFactory;

  @Inject
  public InventoryClickListener(KelpPlayerRepository playerRepository,
                                KelpListenerRepository listenerRepository,
                                ItemTagVersionTemplate itemTagVersionTemplate,
                                KelpItemFactory itemFactory) {
    this.playerRepository = playerRepository;
    this.listenerRepository = listenerRepository;
    this.itemTagVersionTemplate = itemTagVersionTemplate;
    this.itemFactory = itemFactory;
  }

  @EventHandler
  public void handleInventoryClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player)
      || event.getClickedInventory() == null
      || event.getCurrentItem() == null
      || event.getCurrentItem().getType() == Material.AIR) {
      return;
    }

    ItemStack itemStack = event.getCurrentItem();

    if (itemTagVersionTemplate.hasTagKey(itemStack, "interactionCancelled")) {
      event.setCancelled(true);
    }

    for (String current : itemTagVersionTemplate.getTagKeys(itemStack)) {
      if (!current.startsWith("listener-")) {
        continue;
      }

      KelpPlayer player = playerRepository.getKelpPlayer((Player) event.getWhoClicked());
      KelpItem item = itemFactory.fromItemStack(itemStack).slot(event.getSlot());

      String listenerId = itemTagVersionTemplate.getStringValue(itemStack, current);
      listenerRepository.fireListener(listenerId, new KelpClickEvent(player, null, item));
    }

//
//    if (itemTagVersionTemplate.hasTagKey(itemStack, "listenerId")) {
//      KelpPlayer player = playerRepository.getKelpPlayer((Player) event.getWhoClicked());
//      KelpItem item = itemFactory.fromItemStack(itemStack).slot(event.getSlot());
//
//      String listenerId = itemTagVersionTemplate.getStringValue(itemStack, "listenerId");
//      listenerRepository.fireListener(listenerId, new KelpClickEvent(player, null, item));
//    }

  }

}
