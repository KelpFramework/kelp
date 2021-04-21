package de.pxav.kelp.implementation1_8.inventory;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialContainer;
import de.pxav.kelp.core.inventory.type.InventoryOwner;
import de.pxav.kelp.core.inventory.type.StorageInventory;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
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

  public VersionedStorageInventory(Inventory inventory) {
    this.inventory = (CraftInventory) inventory;
    this.inventoryHandle = ((CraftInventory)inventory).getInventory();
  }

  @Override
  public T addItem(KelpItem... items) {
    for (KelpItem item : items) {
      inventory.addItem(item.getItemStack());
    }
    return null;
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
      remove(i);
    }
    return (T) this;
  }

  @Override
  public T remove(int slot) {
    inventoryHandle.setItem(slot, null);
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
    return null;
  }

  @Override
  public boolean isEmpty() {
    return inventoryHandle.getContents().length == 0;
  }

  @Override
  public T remove(KelpItem item) {
    return null;
  }

  @Override
  public T remove(KelpMaterial material) {
    return null;
  }

  @Override
  public T setAllItems(Collection<KelpItem> items) {
    return null;
  }

  @Override
  public T setItem(KelpItem item) {
    ItemStack itemStack = CraftItemStack.asNMSCopy(item.getItemStack());
    inventoryHandle.setItem(item.getSlot(), itemStack);
    return null;
  }

  @Override
  public T setMaxStackSize(int maxStackSize) {
    inventoryHandle.setMaxStackSize(maxStackSize);
    return null;
  }

  @Override
  public T setAllStorageItems(Collection<KelpItem> items) {
    return null;
  }

  @Override
  public Inventory getBukkitInventory() {
    return inventory;
  }

  //  /**
//   * Gets all items stored in this player inventory. This includes
//   * all slots (main storage, armor and hot bar).
//   *
//   * @param player The player to get the items of.
//   * @return A set of all items that are currently in the player's inventory.
//   */
//  @Override
//  public Set<KelpItem> getItems(Player player) {
//    Set<KelpItem> result = Sets.newHashSet();
//    for (int i = 0; i < 41; i++) {
//      ItemStack itemStack = player.getInventory().getItem(i);
//      if (itemStack != null) {
//        result.add(KelpItem.from(itemStack).slot(i));
//      }
//    }
//    return result;
//  }
//
//  /**
//   * Gets all items stored in the player's hotbar. The hotbar
//   * are the first nine slots of a player's inventory (0-8) and it
//   * is always visible at the screen, although the inventory is closed.
//   *
//   * @param player The player to get the hotbar items of
//   * @return A set of all items stored in the player's hotbar.
//   */
//  @Override
//  public Set<KelpItem> getHotBarItems(Player player) {
//    Set<KelpItem> result = Sets.newHashSet();
//    for (int i = 0; i < 9; i++) {
//      ItemStack item = player.getInventory().getItem(i);
//      if (item != null) {
//        result.add(KelpItem.from(item).slot(i));
//      }
//    }
//    return result;
//  }
//
//  /**
//   * Gets an item at a specific slot. The slot count starts at the
//   * first hotbar slot from 0-8 and then goes on from the uppermost
//   * line of the main storage.
//   *
//   * If there is no item at the given slot, {@code null} will be returned.
//   *
//   * @param player  The player to get the item of.
//   * @param slot    The slot of the item you want to get.
//   * @return The item stored at the given slot in the player inventory.
//   */
//  @Override
//  public KelpItem getItemAt(Player player, int slot) {
//    ItemStack itemStack = player.getInventory().getItem(slot);
//    return itemStack == null ? null : KelpItem.from(itemStack);
//  }
//
//  /**
//   * Gets the helmet the player is currently wearing.
//   * Unlike the other armor parts, the helmet can also be of
//   * materials other than normal helmets, but also banners and some
//   * blocks.
//   *
//   * If the player has no helmet, {@code null} will be returned.
//   *
//   * @param player The player to get the helmet of.
//   * @return The item representing the player's helmet.
//   */
//  @Override
//  public KelpItem getHelmet(Player player) {
//    ItemStack itemStack = player.getInventory().getHelmet();
//    return itemStack == null ? null : KelpItem.from(itemStack);
//  }
//
//  /**
//   * Gets the chest plate the player is currently wearing.
//   *
//   * If the player has no chest plate, {@code null} will be returned.
//   *
//   * @param player The player to get the chest plate of.
//   * @return The item representing the player's chest plate.
//   */
//  @Override
//  public KelpItem getChestPlate(Player player) {
//    ItemStack itemStack = player.getInventory().getChestplate();
//    return itemStack == null ? null : KelpItem.from(itemStack);
//  }
//
//  /**
//   * Gets the leggings the player is currently wearing.
//   *
//   * If the player has no leggings, {@code null} will be returned.
//   *
//   * @param player The player to get the leggings of.
//   * @return The item representing the player's chest plate.
//   */
//  @Override
//  public KelpItem getLeggings(Player player) {
//    ItemStack itemStack = player.getInventory().getLeggings();
//    return itemStack == null ? null : KelpItem.from(itemStack);
//  }
//
//  /**
//   * Gets the boots the player is currently wearing.
//   *
//   * If the player has no boots, {@code null} will be returned.
//   *
//   * @param player The player to get the boots of.
//   * @return The item representing the player's chest plate.
//   */
//  @Override
//  public KelpItem getBoots(Player player) {
//    ItemStack itemStack = player.getInventory().getBoots();
//    return itemStack == null ? null : KelpItem.from(itemStack);
//  }
//
//  /**
//   * Sets the helmet the player is wearing to the given item.
//   * Specifically for helmets, you can not only use the normal armor items
//   * such as leather helmet, gold helmet, etc. but also blocks, heads or banners
//   * to set them as a player head.
//   *
//   * @param player  The player to set the helmet of.
//   * @param item    The item of the helmet you want to set. This can be
//   *                a normal armor helmet or any banner, head or even some blocks.
//   */
//  @Override
//  public void setHelmet(Player player, KelpItem item) {
//    player.getInventory().setHelmet(item.getItemStack());
//  }
//
//  /**
//   * Sets the chest plate of the player owning this inventory.
//   * Unlike in the helmet method, you cannot pass other item materials
//   * than chest plates here.
//   *
//   * @param player   The player to set the chest plate of.
//   * @param item     The chest plate item you want to set.
//   */
//  @Override
//  public void setChestPlate(Player player, KelpItem item) {
//    player.getInventory().setChestplate(item.getItemStack());
//  }
//
//  /**
//   * Sets the leggings of the player owning this inventory.
//   * Unlike in the helmet method, you cannot pass other item materials
//   * than leggings here.
//   *
//   * @param player   The player to set the leggings of.
//   * @param item     The leggings item you want to set.
//   */
//  @Override
//  public void setLeggings(Player player, KelpItem item) {
//    player.getInventory().setLeggings(item.getItemStack());
//  }
//
//  /**
//   * Sets the boots of the player owning this inventory.
//   * Unlike in the helmet method, you cannot pass other item materials
//   * than boots here.
//   *
//   * @param player  The player to set the boots of.
//   * @param item    The chest plate item you want to set.
//   */
//  @Override
//  public void setBoots(Player player, KelpItem item) {
//    player.getInventory().setBoots(item.getItemStack());
//  }
//
//  /**
//   * Adds a new item to the player's inventory.
//   * This method won't overwrite any existing items, but try to find free
//   * space in the inventory. If there is already a {@link KelpItem} with
//   * the same data (same display name, item description, material, tags, etc.)
//   * and this item has not yet exceeded its maximum stack size, it will add
//   * the item to an existing stack.
//   *
//   * @param player  The player to add the item to.
//   * @param item    The item to add.
//   */
//  @Override
//  public void addItem(Player player, KelpItem item) {
//    player.getInventory().addItem(item.getItemStack());
//  }
//
//  /**
//   * Stores an item in the player inventory at the given slot location.
//   * If there already is an item present at the given location, this
//   * item will be overwritten. If you want to avoid this and rather
//   * search for free inventory space use {@link #addItem(Player, KelpItem)} instead.
//   *
//   * @param player  The player to set the item to.
//   * @param slot    The slot to store the item at.
//   * @param item    The item to store at the given slot.
//   */
//  @Override
//  public void setItem(Player player, int slot, KelpItem item) {
//    player.getInventory().setItem(slot, item.getItemStack());
//  }
//
//  /**
//   * Gets the item that is currently stored in the player's
//   * main hand (the player's right hand).
//   *
//   * If the player has no item in hand, {@code null} will be returned.
//   *
//   * @param player  The player to get the main hand item of.
//   * @return The item the player is holding right now.
//   */
//  @Override
//  public KelpItem getItemInHand(Player player) {
//    ItemStack itemStack = player.getInventory().getItemInHand();
//    return itemStack == null ? null : KelpItem.from(itemStack);
//  }
//
//  /**
//   * Sets the item in the player's main hand (the player's right hand).
//   *
//   * Unlike the off hand, the main hand can be used in all server versions.
//   *
//   * @param player The player to set the main hand item of.
//   * @param item The item to set in the player's main hand.
//   */
//  @Override
//  public void setItemInHand(Player player, KelpItem item) {
//    player.setItemInHand(item.getItemStack());
//  }
//
//  /**
//   * Gets the item that is currently in the player's off hand
//   * (the player's left hand).
//   *
//   * Please note that off hands were introduced in {code MC 1.9} and are
//   * therefore not available on 1.8 servers. But this method won't throw
//   * an error if you do it anyways.
//   *
//   * If the player has no item in his off hand, {@code null} will be returned.
//   *
//   * @param player The player to get the off hand item of.
//   * @return The item that is stored in the player's off hand right now.
//   */
//  @Override
//  public KelpItem getItemInOffHand(Player player) {
//    return null;
//  }
//
//  /**
//   * Sets the item in the player's off hand (the player's left hand).
//   *
//   * Please note that off hands were introduced in {code MC 1.9} and are
//   * therefore not available on 1.8 servers. But this method won't throw
//   * an error if you do it anyways.
//   *
//   * @param player  The player to set the off hand item of.
//   * @param item    The item to set in the player's off hand.
//   */
//  @Override
//  public void setItemInOffHand(Player player, KelpItem item) {}

}
