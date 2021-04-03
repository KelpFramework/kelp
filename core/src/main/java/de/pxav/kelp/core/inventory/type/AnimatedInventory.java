package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.animation.CustomTextAnimation;
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Animated inventory is a {@link KelpInventory} implementation used if
 * you want to display an inventory GUI with an animated title.
 *
 * This title animation is done via an async thread, but animated inventories
 * are still considered as being more performance intensive as {@link SimpleInventory simple inventories}.
 * So if you don't need the animated title it is recommended to use them over this
 * implementation.
 *
 * Please also note that animated inventories have some special behavior:
 * - when opening another inventory or GUI prompt on top of an animated inventory,
 * the animated inventory will still be rendered as the update scheduler is still
 * running. So call the {@link KelpPlayer#closeInventory()} method first before
 * you open another GUI.
 *
 * @author pxav
 */
public class AnimatedInventory extends KelpInventory<AnimatedInventory> {

  // saves the animation states of the title animation.
  private TextAnimation title;

  // title update scheduler
  private ScheduledExecutorService scheduledExecutorService;

  // An animation is exported to different states. A "state"
  // can be compared to a "frame" in a video. If you play all
  // frames/states one after another, they make up a fluent animation.
  // This variable saves which state is currently displayed to be
  // able to calculate which to play next.
  private int animationState = 0;

  // the delay between each animation state
  private long animationDelayInMillis = 500;

  // responsible for updating the window title
  private WindowPacketTemplate windowPacketTemplate;

  public AnimatedInventory(WindowPacketTemplate windowPacketTemplate,
                           InventoryVersionTemplate inventoryVersionTemplate) {
    super(inventoryVersionTemplate);
    this.windowPacketTemplate = windowPacketTemplate;
  }

  /**
   * Creates a new, empty {@link AnimatedInventory} instance.
   *
   * @return The new animated inventory.
   */
  public static AnimatedInventory create() {
    return new AnimatedInventory(
      KelpPlugin.getInjector().getInstance(WindowPacketTemplate.class),
      KelpPlugin.getInjector().getInstance(InventoryVersionTemplate.class)
    );
  }

  /**
   * Sets the title of the inventory. This title is passed as {@link TextAnimation}
   * to be able to calculate the individual animation states. If you don't want
   * to use one of the default animations, you can create your own animation algorithms
   * (explained in the wiki) or use {@link CustomTextAnimation#create()}.
   *
   * @param textAnimation The text animation to be displayed as the inventory title.
   * @return An instance of the current inventory for fluent builder design.
   */
  public AnimatedInventory title(TextAnimation textAnimation) {
    this.title = textAnimation;
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

  /**
   * Starts the title animation scheduler for the given player.
   * This method is called by kelp-internal classes and shouldn't
   * be called by an application developer to avoid interference.
   *
   * It is executed automatically when an animated inventory is opened
   * to a {@link KelpPlayer player}.
   *
   * @param playerFor The player you want to start the scheduler for.
   */
  public void scheduleUpdater(Player playerFor) {
    scheduledExecutorService = Executors.newScheduledThreadPool(1);
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      try {
        updateTitleOnly(playerFor, animationState);
        animationState++;

        // restart the animation if it as the end
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
