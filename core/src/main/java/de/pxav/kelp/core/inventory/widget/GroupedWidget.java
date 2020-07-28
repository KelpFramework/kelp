package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface GroupedWidget extends Widget {

  Collection<KelpItem> render();

}
