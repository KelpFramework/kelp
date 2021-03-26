package de.pxav.kelp.core.inventory.metadata;

import de.pxav.kelp.core.KelpPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface ItemMetadata {

  default ItemStack applyTo(ItemStack itemStack) {
    return KelpPlugin.getInjector().getInstance(ItemMetadataVersionTemplate.class).applyMetadata(itemStack, this);
  }

}
