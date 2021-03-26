package de.pxav.kelp.core.inventory.metadata;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

@KelpVersionTemplate
public abstract class ItemMetadataVersionTemplate {

  public abstract ItemStack applyMetadata(ItemStack itemStack, ItemMetadata metadata);

  public abstract ItemMetadata getMetadata(ItemStack itemStack);

}
