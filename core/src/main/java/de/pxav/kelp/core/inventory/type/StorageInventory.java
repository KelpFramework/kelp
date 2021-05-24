package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public interface StorageInventory<T extends StorageInventory<?>> {

  T addItem(KelpItem... items);

  Collection<KelpItem> allItemsWith(KelpMaterial material);

  Collection<KelpItem> allItemsWith(KelpItem item);

  T clear();

  T remove(int slot);

  default T remove(Set<Integer> slots) {
    for (Integer slot : slots) {
      remove(slot);
    }
    return (T) this;
  }

  boolean contains(KelpItem item);

  boolean contains(KelpMaterial material);

  boolean containsAtLeast(KelpMaterial material, int minimumAmount);

  boolean containsAtLeast(KelpItem item, int combinedMinimumAmount);

  int first(KelpItem item);

  int first(KelpMaterial material);

  int firstEmptySlot();

  Collection<KelpItem> getAllItems();

  Collection<KelpItem> getStorageItems();

  InventoryOwner getInventoryOwner();

  KelpItem getItemAt(int slot);

  int getMaxStackSize();

  int getSize();

  default int getRows() {
    return getSize() / 9;
  }

  Collection<LivingKelpEntity<?>> getViewers();

  boolean isEmpty();

  T remove(KelpItem item);

  T remove(KelpMaterial material);

  T setAllItems(Collection<KelpItem> items);

  default T setAllItems(KelpItem[] items) {
    setAllItems(Arrays.asList(items));
    return (T) this;
  }

  T setItem(KelpItem item);

  T setMaxStackSize(int maxStackSize);

  T setAllStorageItems(Collection<KelpItem> items);

  default T setAllStorageItems(KelpItem[] items) {
    setAllStorageItems(Arrays.asList(items));
    return (T) this;
  }

  Inventory getBukkitInventory();

}
