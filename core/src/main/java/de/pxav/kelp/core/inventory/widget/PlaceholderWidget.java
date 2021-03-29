package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class PlaceholderWidget extends AbstractWidget<PlaceholderWidget> implements GroupedWidget {

  private KelpItem baseItem = null;
  private Set<Integer> slots = Sets.newHashSet();

  public static PlaceholderWidget create() {
    return new PlaceholderWidget();
  }

  public PlaceholderWidget baseItem(KelpItem baseItem) {
    this.baseItem = baseItem;
    return this;
  }

  public PlaceholderWidget addSlots(Collection<Integer> slots) {
    this.slots.addAll(slots);
    return this;
  }

  public PlaceholderWidget addSlots(Integer... slots) {
    this.slots.addAll(Arrays.asList(slots));
    return this;
  }

  public PlaceholderWidget removeSlots(Collection<Integer> slots) {
    this.slots.removeAll(slots);
    return this;
  }

  public PlaceholderWidget clearSlots() {
    this.slots.clear();
    return this;
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
      -> output.add(baseItem.slot(current)));
    return output;
  }

  @Override
  public Set<Integer> getCoveredSlots() {
    return this.slots;
  }

}
