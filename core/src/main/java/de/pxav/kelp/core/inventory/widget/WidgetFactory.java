package de.pxav.kelp.core.inventory.widget;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class WidgetFactory {

  private KelpInventoryRepository kelpInventoryRepository;

  @Inject
  public WidgetFactory(KelpInventoryRepository kelpInventoryRepository) {
    this.kelpInventoryRepository = kelpInventoryRepository;
  }

  public ToggleableWidget newToggleableWidget() {
    return new ToggleableWidget();
  }

  public ItemWidget newItemWidget() {
    return new ItemWidget();
  }

  public Pagination newPagination() {
    return new Pagination(this.kelpInventoryRepository);
  }

}
