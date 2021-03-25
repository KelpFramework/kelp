package de.pxav.kelp.core.inventory.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.animation.StaticTextAnimation;
import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.inventory.version.WindowPacketTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class AnimatedInventory extends KelpInventory {

  private TextAnimation title;

  private ScheduledExecutorService scheduledExecutorService;
  private int animationState = 0;
  private long animationDelayInMillis = 500;

  private WindowPacketTemplate windowPacketTemplate;
  private InventoryVersionTemplate inventoryVersionTemplate;

  public AnimatedInventory(WindowPacketTemplate windowPacketTemplate,
                           InventoryVersionTemplate inventoryVersionTemplate) {
    this.windowPacketTemplate = windowPacketTemplate;
    this.inventoryVersionTemplate = inventoryVersionTemplate;
    this.simpleWidgets = Lists.newArrayList();
    this.groupedWidgets = Lists.newArrayList();
    this.size = 54;
  }

  public static AnimatedInventory create() {
    return new AnimatedInventory(
      KelpPlugin.getInjector().getInstance(WindowPacketTemplate.class),
      KelpPlugin.getInjector().getInstance(InventoryVersionTemplate.class)
    );
  }

  public AnimatedInventory size(int size) {
    this.size = size;
    return this;
  }

  public AnimatedInventory rows(int rows) {
    this.size = rows * 9;
    return this;
  }

  public AnimatedInventory title(TextAnimation textAnimation) {
    this.title = textAnimation;
    return this;
  }

  public AnimatedInventory addWidget(SimpleWidget widget) {
    this.simpleWidgets.add(widget);
    return this;
  }

  public AnimatedInventory addWidget(GroupedWidget widget) {
    this.groupedWidgets.add(widget);
    return this;
  }

  public void updateTitleOnly(Player player, int state) {
    this.windowPacketTemplate.updateWindowTitle(player, title.states().get(state));
  }

  @Override
  public Inventory render(KelpPlayer player) {
    if (this.title == null) {
      this.title = StaticTextAnimation.create().text("§8Inventory");
    }

    Inventory inventory = inventoryVersionTemplate.createInventory(this.size, title.states().get(0));

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

  @Override
  public void update(KelpPlayer toUpdate) {
    Inventory playerInventory = toUpdate.getBukkitPlayer().getOpenInventory().getTopInventory();
    playerInventory.clear();

    for (SimpleWidget current : simpleWidgets) {
      KelpItem item = current.render();
      if (!item.hasTagKey("interactionAllowed")) {
        item.cancelInteractions();
      }
      playerInventory.setItem(item.getSlot(), item.getItemStack());
    }

    for (GroupedWidget current : groupedWidgets) {
      current.render(toUpdate).forEach(item -> {
        if (!item.hasTagKey("interactionAllowed")) {
          item.cancelInteractions();
        }
        playerInventory.setItem(item.getSlot(), item.getItemStack());
      });
    }

    toUpdate.getBukkitPlayer().updateInventory();
  }

  public void scheduleUpdater(Player playerFor) {
    scheduledExecutorService = Executors.newScheduledThreadPool(1);
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      try {
        updateTitleOnly(playerFor, animationState);
        animationState++;
        if (animationState == title.states().size()) {
          animationState = 0;
        }
      } catch (Exception ignore) {}
    }, 0, animationDelayInMillis, TimeUnit.MILLISECONDS);
  }

  @Override
  public void onClose() {
    scheduledExecutorService.shutdown();
  }

}
