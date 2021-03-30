package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.inventory.version.WindowPacketTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.Supplier;

/**
 * This is a simple implementation of a {@link KelpInventory}. This inventory
 * type allows you to create a normal inventory without any fancy title animation,
 * which can be achieved with {@link AnimatedInventory}.
 *
 * Although the title is not animated, you can pass a supplier as title, which
 * makes it updatable if you call {@link #updateTitleOnly(KelpPlayer)}.
 *
 * @author pxav
 */
public class SimpleInventory extends KelpInventory<SimpleInventory> {

  // updatable inventory title
  private Supplier<String> title;

  // responsible for updating the inventory title
  private WindowPacketTemplate windowPacketTemplate;

  public static SimpleInventory create() {
    return new SimpleInventory(
      KelpPlugin.getInjector().getInstance(InventoryVersionTemplate.class),
      KelpPlugin.getInjector().getInstance(WindowPacketTemplate.class)
    );
  }

  public SimpleInventory(InventoryVersionTemplate inventoryVersionTemplate,
                         WindowPacketTemplate windowPacketTemplate) {
    super(inventoryVersionTemplate);
    this.windowPacketTemplate = windowPacketTemplate;
    this.title = () -> "§8Inventory";
  }

  public SimpleInventory title(Supplier<String> title) {
    this.title = title;
    return this;
  }

  public SimpleInventory title(String title) {
    this.title = () -> title;
    return this;
  }

  public void updateTitleOnly(KelpPlayer player) {
    this.windowPacketTemplate.updateWindowTitle(player.getBukkitPlayer(), title.get());
  }

  @Override
  public Inventory render(KelpPlayer player) {
    Inventory inventory = inventoryVersionTemplate.createInventory(this.size, title.get());

    for (SimpleWidget current : simpleWidgets) {
      KelpItem item = current.render();
      if (!item.hasTagKey("interactionAllowed")) {
        item.cancelInteractions();
      }
      inventory.setItem(item.getSlot(), item.getItemStack());
    }

    for (GroupedWidget current : groupedWidgets) {
      current.render(player).forEach(item -> {
        if (!item.hasTagKey("interactionAllowed")) {
          item.cancelInteractions();
        }
        inventory.setItem(item.getSlot(), item.getItemStack());
      });
    }

    return inventory;
  }

}
