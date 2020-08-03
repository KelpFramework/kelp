package de.pxav.kelp.core.inventory.item;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * This version template can be used to handle the tags
 * of an item.
 *
 * Every item can have a tag in minecraft. Those are called NBT tags.
 * If you set a display name or a lore for your item, it automatically
 * has NBT tags saving the name and lore of your item.
 *
 * To define custom tags, there are different ways - depending on the
 * version you are using. Since 1.14 the NBT tag library in Bukkit has
 * been "replaced" by the {@code PersistentDataContainer}, which is why
 * tag handling has become a version dependent thing. That's why there
 * is a version template: It uses either the PersistentData library or
 * the NBT tag library depending on the server version you are using.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ItemTagVersionTemplate {

  /**
   * Adds a string tag to the given item stack.
   *
   * @param itemStack   The item stack you want to apply the tag to.
   * @param key         The key of the tag. This is how you will access the
   *                    tag value later.
   * @param value       The actual value to save inside your tag.
   * @return The new {@code ItemStack} containing the tag data.
   */
  public abstract ItemStack tagItem(ItemStack itemStack, String key, String value);

  /**
   * Removes the tag with the given key from the given item stack.
   *
   * @param itemStack   The item stack you want to remove the tag from.
   * @param key         The key of the tag you want to remove.
   * @return The new {@code ItemStack} containing the updated tag data.
   */
  public abstract ItemStack removeTag(ItemStack itemStack, String key);

  /**
   * Checks whether the given item stack has a tag with the given key.
   *
   * @param itemStack   The item stack you want to check.
   * @param key         The key of the tag you want to check the existence of.
   * @return {@code true} if the item stack has the desired tag.
   */
  public abstract boolean hasTagKey(ItemStack itemStack, String key);

  /**
   * Gets the tag value of the tag with the specified key. This method
   * only accepts string values.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The string value of the desired tag.
   */
  public abstract String getStringValue(ItemStack itemStack, String key);

  /**
   * Gets the keys of all tags the given item stack has.
   *
   * @param itemStack The item stack you want to get the tag keys of.
   * @return A collection of all tag keys of the given item stack.
   */
  public abstract Collection<String> getTagKeys(ItemStack itemStack);

}
