package de.pxav.kelp.implementation1_8.inventory;

import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.inventory.metadata.ItemMetadata;
import de.pxav.kelp.core.inventory.metadata.ItemMetadataVersionTemplate;
import de.pxav.kelp.core.inventory.metadata.LeatherArmorMetadata;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@Versioned
public class VersionedItemMetadata extends ItemMetadataVersionTemplate {

  @Override
  public ItemStack applyMetadata(ItemStack itemStack, ItemMetadata metadata) {

    if (metadata instanceof LeatherArmorMetadata) {
      LeatherArmorMetadata kelpMeta = (LeatherArmorMetadata) metadata;
      LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
      armorMeta.setColor(kelpMeta.getColor().getBukkitColor());
      itemStack.setItemMeta(armorMeta);
      return itemStack;
    }

    return itemStack;
  }

  @Override
  public ItemMetadata getMetadata(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();

    if (itemMeta instanceof LeatherArmorMeta) {
      LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemMeta;
      LeatherArmorMetadata kelpMeta = LeatherArmorMetadata.create();
      kelpMeta.setColor(Color.fromBukkit(armorMeta.getColor()));
      return kelpMeta;
    }

    return null;
  }

}
