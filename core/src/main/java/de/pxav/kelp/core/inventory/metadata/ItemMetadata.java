package de.pxav.kelp.core.inventory.metadata;

import de.pxav.kelp.core.KelpPlugin;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the metadata of a {@link de.pxav.kelp.core.inventory.item.KelpItem}.
 * In contrast to normal bukkit items, {@link de.pxav.kelp.core.inventory.item.KelpItem kelp items}
 * store metadata like the lore and display name directly in the item class and not in a seperate
 * meta class, but if you want to give an item material-specific metadata, you have to assign it an
 * {@code ItemMetadata}, which can be the color of a leather armor or a custom texture for a skull.
 *
 * Every item can only have one specific metadata object at a time, which is logical, because an
 * item cannot be a colored leather armor, a potion or a skull at the same time.
 *
 * @author pxav
 */
public interface ItemMetadata {

  /**
   * Applies the current item meta to the given item stack.
   * This method is used by {@link de.pxav.kelp.core.inventory.item.KelpItem} to
   * apply the changes when it is converted to an {@link ItemStack} in a
   * version independent way. In the background, this method simply calls
   * the {@link ItemMetadataVersionTemplate#applyMetadata(ItemStack, ItemMetadata)}
   * method.
   *
   * @param itemStack The item stack you want to apply the current metadata to.
   * @return The new item stack with the newly added item meta.
   */
  default ItemStack applyTo(ItemStack itemStack) {
    return KelpPlugin.getInjector().getInstance(ItemMetadataVersionTemplate.class).applyMetadata(itemStack, this);
  }

}
