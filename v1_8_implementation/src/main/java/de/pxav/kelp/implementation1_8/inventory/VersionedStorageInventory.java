package de.pxav.kelp.implementation1_8.inventory;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialContainer;
import de.pxav.kelp.core.inventory.type.InventoryOwner;
import de.pxav.kelp.core.inventory.type.StorageInventory;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

import java.util.Collection;

/**
 * This class implements all version specific actions performed
 * on a {@link org.bukkit.inventory.PlayerInventory}.
 *
 * @author pxav
 */
@Versioned
public class VersionedStorageInventory<T extends StorageInventory<T>> implements StorageInventory<T> {

  protected IInventory inventoryHandle;
  private CraftInventory inventory;
  private EntityTypeVersionTemplate entityTypeVersionTemplate;

  public VersionedStorageInventory(Inventory inventory) {
    this.inventory = (CraftInventory) inventory;
    this.inventoryHandle = ((CraftInventory)inventory).getInventory();
    this.entityTypeVersionTemplate = KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class);
  }

  @Override
  public T addItem(KelpItem... items) {
    for (KelpItem item : items) {
      inventory.addItem(item.getItemStack());
    }
    inventoryHandle.update();
    return (T) this;
  }

  @Override
  public Collection<KelpItem> allItemsWith(KelpMaterial material) {
    MaterialContainer container = KelpMaterial.convert(material);
    Collection<KelpItem> output = Lists.newArrayList();

    for (ItemStack mcItem : inventoryHandle.getContents()) {
      org.bukkit.inventory.ItemStack itemStack = CraftItemStack.asBukkitCopy(mcItem);
      if (itemStack.getType() == container.getBukkitMaterial()) {
        if (container.getSubId() != 0 && itemStack.getDurability() != container.getSubId()) {
          continue;
        }
        output.add(KelpItem.from(itemStack));
      }
    }
    return output;
  }

  @Override
  public Collection<KelpItem> allItemsWith(KelpItem item) {
    Collection<KelpItem> output = Lists.newArrayList();

    for (ItemStack mcItem : inventoryHandle.getContents()) {
      org.bukkit.inventory.ItemStack itemStack = CraftItemStack.asBukkitCopy(mcItem);
      KelpItem currentItem = KelpItem.from(itemStack);
      if (currentItem.equals(item)) {
        output.add(currentItem);
      }
    }
    return output;
  }

  @Override
  public T clear() {
    for (int i = 0; i < inventoryHandle.getSize(); i++) {
      inventoryHandle.setItem(i, null);
    }
    inventoryHandle.update();
    return (T) this;
  }

  @Override
  public T remove(int slot) {
    inventoryHandle.setItem(slot, null);
    inventoryHandle.update();
    return (T) this;
  }

  @Override
  public boolean contains(KelpItem item) {
    return getAllItems().contains(item);
  }

  @Override
  public boolean contains(KelpMaterial material) {
    for (KelpItem item : getAllItems()) {
      if (item.getMaterial() == material) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAtLeast(KelpMaterial material, int minimumAmount) {
    int totalAmount = 0;
    for (KelpItem item : getAllItems()) {
      if (item.getMaterial() == material) {
        if ((item.getAmount() + totalAmount) >= minimumAmount) {
          return true;
        }
        totalAmount += item.getAmount();
      }
    }
    return false;
  }

  @Override
  public boolean containsAtLeast(KelpItem sourceItem, int combinedMinimumAmount) {
    int totalAmount = 0;
    for (KelpItem item : getAllItems()) {
      if (item.equals(sourceItem)) {
        if ((item.getAmount() + totalAmount) >= combinedMinimumAmount) {
          return true;
        }
        totalAmount += item.getAmount();
      }
    }
    return false;
  }

  @Override
  public int first(KelpItem item) {
    for (int i = 0; i < inventory.getSize(); i++) {
      if (getItemAt(i).equals(item)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int first(KelpMaterial material) {
    for (int i = 0; i < inventory.getSize(); i++) {
      if (getItemAt(i).getMaterial().equals(material)) {
        return i;
      }
    }
    return 0;
  }

  @Override
  public int firstEmptySlot() {
    for (int i = 0; i < getSize(); i++) {
      if (inventoryHandle.getItem(i) == null) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public Collection<KelpItem> getAllItems() {
    Collection<KelpItem> output = Lists.newArrayList();
    for (int slot = 0; slot < inventoryHandle.getSize(); slot++) {
      org.bukkit.inventory.ItemStack craftItemStack = CraftItemStack.asBukkitCopy(inventoryHandle.getItem(slot));
      output.add(KelpItem.from(craftItemStack).slot(slot));
    }
    return output;
  }

  @Override
  public Collection<KelpItem> getStorageItems() {
    return null;
  }

  @Override
  public InventoryOwner getInventoryOwner() {
    return InventoryOwner.from(inventoryHandle.getOwner());
  }

  @Override
  public KelpItem getItemAt(int slot) {
    org.bukkit.inventory.ItemStack itemStack = CraftItemStack.asBukkitCopy(inventoryHandle.getItem(slot));
    return KelpItem.from(itemStack);
  }

  @Override
  public int getMaxStackSize() {
    return inventory.getMaxStackSize();
  }

  @Override
  public int getSize() {
    return inventory.getSize();
  }

  @Override
  public Collection<LivingKelpEntity<?>> getViewers() {
    Collection<LivingKelpEntity<?>> output = Lists.newArrayList();
    for (HumanEntity viewer : inventoryHandle.getViewers()) {
      output.add((LivingKelpEntity<?>) entityTypeVersionTemplate.getKelpEntity(viewer));
    }
    return output;
  }

  @Override
  public boolean isEmpty() {
    return inventoryHandle.getContents().length == 0;
  }

  @Override
  public T remove(KelpItem item) {
    MaterialContainer material = KelpMaterial.convert(item.getMaterial());
    for (int slot = 0; inventoryHandle.getSize() > slot; slot++) {
      if (inventoryHandle.getItem(slot) == null) {
        continue;
      }
      org.bukkit.inventory.ItemStack itemStack = CraftItemStack.asBukkitCopy(inventoryHandle.getItem(slot));

      if (itemStack.getType() != material.getBukkitMaterial()) {
        continue;
      }

      if (material.getBukkitMaterial().getMaxDurability() != itemStack.getType().getMaxDurability()
        || material.getSubId() != itemStack.getDurability()) {
        continue;
      }

      if (!item.getDisplayName().equalsIgnoreCase(itemStack.getItemMeta().getDisplayName())) {
        continue;
      }

      if (!item.getItemDescription().equals(itemStack.getItemMeta().getLore())) {
        continue;
      }

      remove(slot);

    }
    inventoryHandle.update();
    return (T) this;
  }

  @Override
  public T remove(KelpMaterial material) {
    MaterialContainer container = KelpMaterial.convert(material);
    for (int slot = 0; inventoryHandle.getSize() > slot; slot++) {
      if (inventoryHandle.getItem(slot) == null) {
        continue;
      }

      org.bukkit.inventory.ItemStack itemStack = CraftItemStack.asBukkitCopy(inventoryHandle.getItem(slot));

      if (container.getBukkitMaterial() == itemStack.getType()) {
        if (material.getMaxDurability() == 0 && itemStack.getDurability() == container.getSubId()) {
          remove(slot);
          continue;
        }
        if (container.getSubId() != 0) {
          remove(slot);
        }
      }
    }
    inventoryHandle.update();
    return (T) this;
  }

  @Override
  public T setAllItems(Collection<KelpItem> items) {

    return (T) this;
  }

  @Override
  public T setItem(KelpItem item) {
    getBukkitInventory().setItem(item.getSlot(), item.getItemStack());
    return null;
  }

  @Override
  public T setMaxStackSize(int maxStackSize) {
    inventoryHandle.setMaxStackSize(maxStackSize);
    return null;
  }

  @Override
  public T setAllStorageItems(Collection<KelpItem> items) {
    return (T) this;
  }

  @Override
  public Inventory getBukkitInventory() {
    return inventory;
  }

}
