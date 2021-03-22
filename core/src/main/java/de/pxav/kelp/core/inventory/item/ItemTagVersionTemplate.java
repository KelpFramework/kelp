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
  public abstract ItemStack tagItem(ItemStack itemStack, String key, Object value);

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
   * Gets the tag value of the tag with the specified key. This method
   * only accepts integer values.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The integer value of the desired tag.
   */
  public abstract int getIntegerValue(ItemStack itemStack, String key);

  /**
   * Gets the tag value of the tag with the specified key. This method
   * only accepts double values.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The double value of the desired tag.
   */
  public abstract double getDoubleValue(ItemStack itemStack, String key);

  /**
   * Gets the tag value of the tag with the specified key. This method
   * only accepts long values.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The long value of the desired tag.
   */
  public abstract long getLongValue(ItemStack itemStack, String key);

  /**
   * Gets the tag value of the tag with the specified key. This method
   * only accepts float values.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The float value of the desired tag.
   */
  public abstract float getFloatValue(ItemStack itemStack, String key);

  /**
   * Gets the tag value of the tag with the specified key. This method
   * only accepts byte values.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The string byte of the desired tag.
   */
  public abstract byte getByteValue(ItemStack itemStack, String key);

  /**
   * Gets the tag value of the tag with the specified key. This method
   * only accepts boolean values.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The boolean value of the desired tag.
   */
  public abstract boolean getBooleanValue(ItemStack itemStack, String key);

  /**
   * Gets the value associated with the given tag key. For this method,
   * the type does not matter as the value is returned as a generic
   * object. This can be used to get the value of a tag without being sure
   * which data type the tag is holding.
   *
   * @param itemStack   The item stack you want to get the tag from.
   * @param key         The key of the tag you want to get the value from.
   * @return The value of the desired tag.
   */
  public abstract Object getAnyValue(ItemStack itemStack, String key);

  /**
   * Gets the keys of all tags the given item stack has.
   *
   * @param itemStack The item stack you want to get the tag keys of.
   * @return A collection of all tag keys of the given item stack.
   */
  public abstract Collection<String> getTagKeys(ItemStack itemStack);

}
