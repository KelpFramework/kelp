package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface Widget {

  Widget player(KelpPlayer player);

  KelpPlayer getPlayer();

}