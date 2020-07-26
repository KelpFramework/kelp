package de.pxav.kelp.core.inventory.item;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ItemTagVersionTemplate {

  public abstract ItemStack tagItem(ItemStack itemStack, String key, String value);

  public abstract ItemStack removeTag(ItemStack itemStack, String key);

  public abstract boolean hasTagKey(ItemStack itemStack, String key);

  public abstract String getStringValue(ItemStack itemStack, String key);

  public abstract Collection<String> getTagKeys(ItemStack itemStack);

}
