package de.pxav.kelp.core.inventory.listener;

/**
 * This interface is passed with every listener created
 * in order to pass the method which should be executed
 * when the listener is fired.
 *
 * @author pxav
 */
@FunctionalInterface
public interface ClickListener {

  /**
   * This method is executed when the listener is fired.
   *
   * @param event Some information about the event such as
   *             the inventory, the player or the item which
   *             was clicked.
   */
  void onClick(KelpClickEvent event);

}
