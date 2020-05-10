package de.pxav.kelp.core.inventory.widget;

import com.google.inject.Singleton;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class WidgetFactory {

  public ToggleableWidget newToggleableWidget() {
    return new ToggleableWidget();
  }

  public ItemWidget newItemWidget() {
    return new ItemWidget();
  }

}
