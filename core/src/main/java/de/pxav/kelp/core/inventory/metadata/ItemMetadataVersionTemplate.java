package de.pxav.kelp.core.inventory.metadata;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

/**
 * This version template is responsible for converting KelpItem-metadata
 * and bukkit item metadata. It can apply KelpItemMetadata on bukkit item
 * stacks and retrieve the metadata on bukkit item stacks and convert it
 * to kelp item meta.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ItemMetadataVersionTemplate {

  /**
   * Applies the given kelp {@link ItemMetadata} to the given bukkit item stack.
   * This method is used by the item metadata interface in {@link ItemMetadata#applyTo(ItemStack)},
   * which is called by {@link KelpItem#getItemStack()} every time a kelp item is converted
   * to a bukkit item stack.
   *
   * @param itemStack The item stack to apply the metadata to.
   * @param metadata  The metadata to apply to the item stack.
   * @return The new item stack containing the new metadata.
   */
  public abstract ItemStack applyMetadata(ItemStack itemStack, ItemMetadata metadata);

  public abstract ItemMetadata getMetadata(ItemMeta itemMeta);

  /**
   * Retrieves the ItemMeta of the given bukkit item stack and converts
   * it to a kelp {@link ItemMetadata}. This is used by {@link KelpItem#from(ItemStack)}
   * for example or every time a bukkit item stack is converted to a kelp item
   * in general.
   *
   * If the given item stack does not have a custom item meta, {@code null}
   * is returned.
   *
   * @param itemStack The item stack to fetch the item meta from.
   * @return The kelp {@link ItemMetadata} equivalent to the kelp item meta.
   */
  public abstract ItemMetadata getMetadata(ItemStack itemStack);

}
