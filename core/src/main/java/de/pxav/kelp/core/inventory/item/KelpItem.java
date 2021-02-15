package de.pxav.kelp.core.inventory.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.listener.ClickListener;
import de.pxav.kelp.core.inventory.listener.KelpListenerRepository;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code KelpItem} can be compared with the {@code ItemStack} of the
 * bukkit library. It represents an item including its metadata. Unlike in
 * the bukkit library, no extra {@code ItemMeta} is needed to control things
 * like the display name or lore. It is all handled via one class.
 *
 * @author pxav
 */
public class KelpItem {

  private ItemVersionTemplate itemVersionTemplate;
  private ItemTagVersionTemplate itemTagVersionTemplate;
  private KelpListenerRepository listenerRepository;

  public KelpItem(ItemVersionTemplate itemVersionTemplate,
                  ItemTagVersionTemplate itemTagVersionTemplate,
                  KelpListenerRepository listenerRepository) {
    this.itemVersionTemplate = itemVersionTemplate;
    this.itemTagVersionTemplate = itemTagVersionTemplate;
    this.listenerRepository = listenerRepository;
  }

  public static KelpItem create() {
    return new KelpItem(
      KelpPlugin.getInjector().getInstance(ItemVersionTemplate.class),
      KelpPlugin.getInjector().getInstance(ItemTagVersionTemplate.class),
      KelpPlugin.getInjector().getInstance(KelpListenerRepository.class)
    );
  }

  // the material of the item. If none is set, stone will be used
  private KelpMaterial material = KelpMaterial.STONE;

  // the amount of the item (stack), 1 by default
  private int amount = 1;

  // the slot inside the inventory where it is stored.
  // This attribute is ignored by some inventory widgets as they
  // have their own item sorting methods.
  private int slot;

  // the display name of the item
  private String displayName;

  // the item description - aka. the item lore.
  // Those are some lines of text below the display name.
  private List<String> itemDescription = Lists.newArrayList();

  private Collection<ItemFlag> itemFlags = Lists.newArrayList();
  private Map<Enchantment, Integer> enchantments = Maps.newHashMap();

  private boolean glowing = false;
  private boolean unbreakable = false;

  private Map<String, String> nbtTagStrings = Maps.newHashMap();
  private Collection<String> tagsToRemove = Lists.newArrayList();

  /**
   * Sets the material type of the item.
   *
   * @param material The material type you want to set.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem material(KelpMaterial material) {
    this.material = material;
    return this;
  }

  /**
   * Sets the amount of items to be stacked.
   *
   * @param amount The item amount you want to set.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem amount(int amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Sets the slot of the item, where it should be located
   * in the parent inventory.
   *
   * @param slot The slot location you want to set.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem slot(int slot) {
    this.slot = slot;
    return this;
  }

  /**
   * Sets the display name of the item.
   *
   * @param displayName The display name you want to set.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Adds a new string tag to the item.
   *
   * @param key     The key of the tag. This key is important
   *                to be able to access the value later.
   * @param value   The value you want to assign to the given key.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem addTag(String key, String value) {
    this.nbtTagStrings.put(key, value);
    return this;
  }

  /**
   * Removes the tag with the given key from the item.
   *
   * @param key The key of the item tag you want to remove.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem removeTag(String key) {
    this.tagsToRemove.add(key);
    return this;
  }

  /**
   * Sets the description/lore of the item. This method overwrites
   * the description which has been set before and replaces
   * it with the given list.
   *
   * @param description The new description of the item.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem itemDescription(List<String> description) {
    this.itemDescription = description;
    return this;
  }

  /**
   * Sets the description/lore of the item. This method overwrites
   * the description which has been set before and replaces
   * it with the given string.
   *
   * Using {@code \n} won't create a new line in all versions,
   * so it is recommended to use for example {@link #itemDescription(List)}
   * if you want to set multiple lines.
   *
   * @param description The new description of the item.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem itemDescription(String description) {
    this.itemDescription = Collections.singletonList(description);
    return this;
  }

  /**
   * Sets the description/lore of the item. This method overwrites
   * the description which has been set before and replaces
   * it with the given array.
   *
   * @param description The new description of the item.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem itemDescription(String... description) {
    this.itemDescription = Lists.newArrayList(description);
    return this;
  }

  /**
   * Adds a new line to the item's description. This method does not
   * override the old description, but appends a new line to it.
   *
   * @param description The new description line you want to append.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem addItemDescription(String description) {
    this.itemDescription.add(description);
    return this;
  }

  /**
   * Adds new lines to the item's description. This method does not
   * override the old description, but appends new lines to it.
   *
   * @param description The new description lines you want to append.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem addItemDescription(List<String> description) {
    this.itemDescription.addAll(description);
    return this;
  }

  /**
   * Adds new lines to the item's description. This method does not
   * override the old description, but appends new lines to it.
   *
   * @param description The new description lines you want to append.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem addItemDescription(String... description) {
    this.itemDescription.addAll(Arrays.asList(description));
    return this;
  }

  /**
   * Cancels interactions when a player clicks on the item. This
   * avoids that items can be taken out of inventories for example.
   * This flag is given to items by default, so if you have not removed
   * it manually before {@link #allowInteractions()}, there is no need to use this method.
   *
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem cancelInteractions() {
    this.addTag("interactionCancelled", "true");
    return this;
  }

  /**
   * Makes item interactions allowed again. If you click on an item
   * as if you want to take it out of the inventory, this interaction
   * is canceled by default and the item remains in the inventory.
   *
   * By executing this method, you can remove this cancellation flag.
   *
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem allowInteractions() {
    this.removeTag("interactionCancelled");
    return this;
  }

  /**
   * Adds a new listener to to the item. The listener is fired when the
   * given player clicks on the item.
   *
   * @param player    The player who should be able to click on the item. This is also used to
   *                  clear the listener cache of a specific player if
   *                  they quit the inventory or server.
   * @param listener  The listener interface containing the code you want
   *                  to execute when the item is clicked.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem addListener(KelpPlayer player, ClickListener listener) {
    String listenerId = listenerRepository.registerListener(player.getUUID(), listener);
    this.addTag("listener-" + ThreadLocalRandom.current().nextInt(1, 1000), listenerId);
    return this;
  }

  /**
   * Adds a new listener to to the item. The listener is fired when the
   * given player clicks on the item.
   *
   * @param player    The {@link UUID} of the player who should be
   *                  able to click on the item. This is also used to
   *                  clear the listener cache of a specific player if
   *                  they quit the inventory or server.
   * @param listener  The listener interface containing the code you want
   *                  to execute when the item is clicked.
   * @return Instance of the current {@code KelpItem} object.
   */
  public KelpItem addListener(UUID player, ClickListener listener) {
    String listenerId = listenerRepository.registerListener(player, listener);
    this.addTag("listener-" + ThreadLocalRandom.current().nextInt(1, 1000), listenerId);
    return this;
  }

  /**
   * Converts all the given data about the item into a bukkit
   * {@link ItemStack} which can be put into "real" bukkit inventories.
   *
   * @return The {@link ItemStack} equivalent to the data of the kelp item.
   */
  public ItemStack getItemStack() {
    ItemStack itemStack = itemVersionTemplate.newItemStack(this.material);
    itemStack.setAmount(this.amount);

    // set the display name
    if (this.displayName != null) {
      itemStack = itemVersionTemplate.setDisplayName(itemStack, displayName);
    }

    // make the item unbreakable if needed.
    if (this.unbreakable) {
      itemStack = itemVersionTemplate.makeUnbreakable(itemStack);
    }

    // add a flag to cancel interactions by default, if nothing else has been defined
    if (!this.nbtTagStrings.containsKey("interactionCancelled") && !tagsToRemove.contains("interactionCancelled")) {
      this.cancelInteractions();
    }

    // add string tags
    for (Map.Entry<String, String> tagEntry : this.nbtTagStrings.entrySet()) {
      itemStack = itemTagVersionTemplate.tagItem(itemStack, tagEntry.getKey(), tagEntry.getValue());
    }

    // remove tags, which should be removed
    for (String currentTag : this.tagsToRemove) {
      itemStack = itemTagVersionTemplate.removeTag(itemStack, currentTag);
    }

    return itemStack;
  }

  /**
   * Gets the slot, where the item should be placed
   * in the parent-inventory.
   *
   * @return The item's slot.
   */
  public int getSlot() {
    return slot;
  }

  /**
   * Gets the material type of the item.
   *
   * @return The item's material.
   */
  public KelpMaterial getMaterial() {
    return material;
  }

  /**
   * Gets the display name of the item.
   *
   * @return The item's display name.
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Gets the entire item description.
   *
   * @return A list of all lines containing the item description.
   */
  public List<String> getItemDescription() {
    return itemDescription;
  }

  /**
   * Gets only a specific line of the item description.
   *
   * @param line The number of the line you want to get.
   * @return The requested line of the item description.
   */
  public String getItemDescriptionAt(int line) {
    return itemDescription.get(line);
  }

  /**
   * Gets the amount of items to be stacked.
   *
   * @return The item amount.
   */
  public int getAmount() {
    return amount;
  }
}
