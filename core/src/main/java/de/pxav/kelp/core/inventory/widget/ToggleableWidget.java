package de.pxav.kelp.core.inventory.widget;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ToggleableWidget implements SimpleWidget {

  private KelpPlayer player;
  private Condition condition;
  private int slot;

  private KelpItem whenTrue;
  private KelpItem whenFalse;

  private Runnable whenTrueAction;
  private Runnable whenFalseAction;

  ToggleableWidget() {}

  public ToggleableWidget slot(int slot) { // TODO: Keine NPE, aber updaten muss noch gefixt werden (es passiert nichts)
    this.slot = slot;
    return this;
  }

  public ToggleableWidget condition(Condition condition) {
    this.condition = condition;
    return this;
  }

  public ToggleableWidget whenTrue(KelpItem kelpItem) {
    this.whenTrue = kelpItem;
    whenTrue.addListener(player, event -> {
      player.updateKelpInventory();
    });
    return this;
  }

  public ToggleableWidget whenTrue(KelpItem kelpItem, Runnable action) {
    Preconditions.checkNotNull(action);
    this.whenTrue = kelpItem;
    this.whenTrueAction = action;
    whenTrue.addListener(player, event -> {
      action.run();
      player.updateKelpInventory();
    });
    return this;
  }

  public ToggleableWidget whenFalse(KelpItem kelpItem) {
    this.whenFalse = kelpItem;
    whenFalse.addListener(player, event -> {
      player.updateKelpInventory();
    });
    return this;
  }

  public ToggleableWidget whenFalse(KelpItem kelpItem, Runnable action) {
    Preconditions.checkNotNull(action);
    this.whenFalse = kelpItem;
    this.whenFalseAction = action;
    whenFalse.addListener(player, event -> {
      action.run();
      player.updateKelpInventory();
    });
    return this;
  }

  @Override
  public ToggleableWidget player(KelpPlayer player) {
    this.player = player;
    return this;
  }

  @Override
  public KelpPlayer getPlayer() {
    return player;
  }

  @Override
  public KelpItem render() {
    if (condition.getCondition()) {
      return this.whenTrue.slot(slot);
    } else {
      return this.whenFalse.slot(slot);
    }
  }

}
