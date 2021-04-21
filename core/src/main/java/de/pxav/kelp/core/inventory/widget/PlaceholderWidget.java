package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * A placeholder widget can be used to fill empty space in your inventory.
 * You can add line separators this way for example. You define a base item
 * and a list of slots where this item should be duplicated to. By default this
 * item is a gray stained glass pane with no display name or item description.
 *
 * @author pxav
 */
public class PlaceholderWidget extends AbstractWidget<PlaceholderWidget> implements GroupedWidget {

  // the base item to put on the specified slots.
  // if no other item is set, a gray stained glass pane will be used.
  private KelpItem baseItem = null;

  // all slots to put the base item to (the placeholder slots)
  private Set<Integer> slots = Sets.newHashSet();

  /**
   * Creates a new {@code PlaceholderWidget} instance.
   *
   * @return The new and empty placeholder widget instance.
   */
  public static PlaceholderWidget create() {
    return new PlaceholderWidget();
  }

  /**
   * The base item to put on all the specified place holder slots.
   * You don't have to specify an item here, if you want to use the
   * default gray stained glass pane.
   *
   * @param baseItem The base item to put on all the slots.
   * @return An instance of the widget for fluent builder design.
   */
  public PlaceholderWidget baseItem(KelpItem baseItem) {
    this.baseItem = baseItem;
    return this;
  }

  /**
   * Adds a collection of slots to the slot collection where
   * the items should be put. This method won't overwrite any
   * slots. Duplicates are removed automatically.
   *
   * @param slots The slots you want to add to the collection.
   * @return An instance of the widget for fluent builder design.
   */
  public PlaceholderWidget addSlots(Collection<Integer> slots) {
    this.slots.addAll(slots);
    return this;
  }

  /**
   * Adds an array of slots to the slot collection where
   * the items should be put later. This does not overwrite
   * any other slots. Duplicates are automatically removed.
   *
   * @param slots The slots to add to the slot collection.
   * @return An instance of the widget for fluent builder design.
   */
  public PlaceholderWidget addSlots(Integer... slots) {
    this.slots.addAll(Arrays.asList(slots));
    return this;
  }

  /**
   * Removes a collection of slots, so that the items
   * won't be put at those slots anymore.
   *
   * @param slots The slots to remove from the collection.
   * @return An instance of the widget for fluent builder design.
   */
  public PlaceholderWidget removeSlots(Collection<Integer> slots) {
    this.slots.removeAll(slots);
    return this;
  }

  /**
   * Empties the collection of slots.
   *
   * @return An instance of the widget for fluent builder design.
   */
  public PlaceholderWidget clearSlots() {
    this.slots.clear();
    return this;
  }

  @Override
  public boolean isStateful() {
    return false;
  }

  @Override
  public Collection<KelpItem> render(KelpPlayer player) {
    Collection<KelpItem> output = Lists.newArrayList();

    if (baseItem == null) {
      this.baseItem = KelpItem.create()
        .material(KelpMaterial.GRAY_STAINED_GLASS_PANE)
        .displayName(" ");
    }

    slots.forEach(current
      -> output.add(baseItem.clone().slot(current)));

    return output;
  }

  @Override
  public Set<Integer> getCoveredSlots() {
    return this.slots;
  }

}
