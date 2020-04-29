package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ToggleableWidget implements SimpleWidget {

  private boolean condition;
  private int slot;

  private KelpItem whenTrue;
  private KelpItem whenFalse;

  private Runnable whenTrueAction;
  private Runnable whenFalseAction;

  ToggleableWidget() {}

  public ToggleableWidget slot(int slot) {
    this.slot = slot;
    return this;
  }

  public ToggleableWidget condition(boolean condition) {
    this.condition = condition;
    return this;
  }

  public ToggleableWidget whenTrue(KelpItem kelpItem) {
    this.whenTrue = kelpItem;
    return this;
  }

  public ToggleableWidget whenFalse(KelpItem kelpItem) {
    this.whenFalse = kelpItem;
    return this;
  }

  @Override
  public KelpItem render() {
    if (condition) {
      return this.whenTrue.slot(slot);
    } else {
      return this.whenFalse.slot(slot);
    }
  }
}
