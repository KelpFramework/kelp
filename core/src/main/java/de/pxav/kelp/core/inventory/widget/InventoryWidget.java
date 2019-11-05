package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.LocatableItem;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface InventoryWidget {

  LocatableItem item();

  Runnable onClick();

}
