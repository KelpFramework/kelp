package de.pxav.kelp.core.inventory.widget;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.inventory.item.KelpItem;

import java.util.function.Supplier;

/**
 * This widget is used to handle button interactions
 * toggling a certain setting.
 *
 * They take a condition (boolean) as an input and display
 * an item depending on the boolean value. If the condition
 * is {@code true}, the item for {@code true} will be displayed.
 * The same for false.
 *
 * Furthermore you can configure whether a click on the widget
 * should toggle the given boolean value.
 *
 * Example: You have an admin GUI controlling the game mode of
 * your player. The widget is green if the player is in
 * creative mode and red if they are in survival mode. If the player
 * clicks on the item, it changes color and the player's game mode
 * changes to either survival or creative.
 *
 * @author pxav
 */
public class ToggleableWidget extends AbstractWidget<ToggleableWidget> implements SimpleWidget {

  // the condition to be checked every time the widget updates
  private Supplier<Boolean> condition;

  // The slot of the inventory, where the widget should finally be located.
  private int slot;

  // The item which is displayed when the condition is true.
  private KelpItem whenTrue;

  // The item which is displayed when the condition is false.
  private KelpItem whenFalse;

  ToggleableWidget() {}

  public static ToggleableWidget create() {
    return new ToggleableWidget();
  }

  /**
   * Defines the slot, where the widget should be located
   * later in the final inventory.
   *
   * The slot of the given {@code KelpItems} is ignored as you
   * would have to pass it with every item.
   *
   * @param slot The slot, where the widget should be located.
   * @return Current instance of the widget.
   */
  public ToggleableWidget slot(int slot) {
    this.slot = slot;
    return this;
  }

  /**
   * Defines the condition which has to be fulfilled to make the
   * 'onTrue' item appear.
   *
   * @param condition The condition to be fulfilled.
   * @return Current instance of the widget.
   */
  public ToggleableWidget condition(Supplier<Boolean> condition) {
    this.condition = condition;
    return this;
  }

  /**
   * Defines the item to be displayed when the given condition is {@code true}.
   *
   * @param kelpItem  The {@code KelpItem} to be displayed when the condition is true.
   *                  Note that the slot of the item is ignored and should be passed
   * @return Current instance of the widget.
   */
  public ToggleableWidget whenTrue(KelpItem kelpItem) {
    this.whenTrue = kelpItem;

    addClickListener(whenTrue, event -> event.getPlayer().updateKelpInventory());

    return this;
  }

  /**
   * Defines the item to be displayed when the given condition is {@code true}.
   *
   * This method furthermore offers the opportunity to provide a {@code Runnable},
   * which is executed when the item is clicked.
   *
   * @param kelpItem  The {@code KelpItem} to be displayed when the condition is true.
   *                  Note that the slot of the item is ignored and should be passed
   *                  by using the {@code #slot(int slot)} method instead.
   * @param action    The action which should be executed when the item is clicked.
   * @return Current instance of the widget.
   */
  public ToggleableWidget whenTrue(KelpItem kelpItem, Runnable action) {
    Preconditions.checkNotNull(action);

    this.whenTrue = kelpItem;

    addClickListener(whenTrue, event -> {
      action.run();

      // in case of the condition changes inside the runnable, update the inventory.
      event.getPlayer().updateKelpInventory();
    });

    return this;
  }

  /**
   * Defines the item to be displayed when the given condition is {@code false}.
   *
   * @param kelpItem  The {@code KelpItem} to be displayed when the condition is false.
   *                  Note that the slot of the item is ignored and should be passed
   * @return Current instance of the widget.
   */
  public ToggleableWidget whenFalse(KelpItem kelpItem) {
    this.whenFalse = kelpItem;

    addClickListener(whenFalse, event -> event.getPlayer().updateKelpInventory());

    return this;
  }

  /**
   * Defines the item to be displayed when the given condition is {@code false}.
   *
   * This method furthermore offers the opportunity to provide a {@code Runnable},
   * which is executed when the item is clicked.
   *
   * @param kelpItem  The {@code KelpItem} to be displayed when the condition is false.
   *                  Note that the slot of the item is ignored and should be passed
   *                  by using the {@code #slot(int slot)} method instead.
   * @param action    The action which should be executed when the item is clicked.
   * @return Current instance of the widget.
   */
  public ToggleableWidget whenFalse(KelpItem kelpItem, Runnable action) {
    Preconditions.checkNotNull(action);

    this.whenFalse = kelpItem;

    addClickListener(whenFalse, event -> {
      action.run();

      // in case of the condition changes inside the runnable, update the inventory.
      event.getPlayer().updateKelpInventory();
    });

    return this;
  }

  /**
   * Renders the widget and converts all the given information
   * and configuration to a single {@code KelpItem}, which can
   * be put into the inventory.
   *
   * @return The final {@code KelpItem}.
   */
  @Override
  public KelpItem render() {
    if (condition.get()) {
      return this.whenTrue.slot(slot);
    } else {
      return this.whenFalse.slot(slot);
    }
  }
}
