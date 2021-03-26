package de.pxav.kelp.implementation1_8.inventory;

import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.item.ItemTagVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.listener.KelpListenerRepository;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialRepository;
import de.pxav.kelp.core.inventory.metadata.ItemMetadata;
import de.pxav.kelp.core.inventory.metadata.ItemMetadataVersionTemplate;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapPalette;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedItem extends ItemVersionTemplate {

  private MaterialRepository materialRepository;
  private ItemTagVersionTemplate itemTagVersionTemplate;
  private ItemMetadataVersionTemplate metadataVersionTemplate;

  @Inject
  public VersionedItem(MaterialRepository materialRepository,
                       ItemTagVersionTemplate itemTagVersionTemplate,
                       ItemMetadataVersionTemplate metadataVersionTemplate) {
    this.materialRepository = materialRepository;
    this.itemTagVersionTemplate = itemTagVersionTemplate;
    this.metadataVersionTemplate = metadataVersionTemplate;
  }

  @Override
  public KelpItem fromItemStack(ItemStack itemStack) {
    KelpItem output = KelpItem.create();

    for (String tagKey : itemTagVersionTemplate.getTagKeys(itemStack)) {
      Object value = itemTagVersionTemplate.getAnyValue(itemStack, tagKey);
      if (value == null) {
        continue;
      }
      output.addTag(tagKey, value);
    }

    short subId = itemStack.getDurability();
    KelpMaterial material;
    if (subId == 0) {
      material = materialRepository.getKelpMaterial(itemStack.getType().toString());
    } else {
      material = materialRepository.getKelpMaterial(itemStack.getType().toString(), subId);
    }

    ItemMeta itemMeta = itemStack.getItemMeta();

    if (itemMeta.getDisplayName() != null) {
      output.displayName(itemMeta.getDisplayName());
    }

    if (itemMeta.getLore() != null) {
      output.itemDescription(itemMeta.getLore());
    }

    ItemMetadata metadata = metadataVersionTemplate.getMetadata(itemStack);

    if (metadata != null) {
      output.metadata(metadata);
    }

    return output
      .material(material)
      .amount(itemStack.getAmount());
  }

  @Override
  public ItemStack newItemStack() {
    return new ItemStack(Material.STONE);
  }

  @Override
  public ItemStack newItemStack(KelpMaterial material) {
    String materialName = materialRepository.getMaterial(material);
    String[] materialElements = materialName.split(":");
    Material bukkitMaterial = Material.valueOf(materialElements[0]);
    if (materialElements.length == 2) {
      short subId = Short.parseShort(materialElements[1]);
      return new ItemStack(bukkitMaterial, 1, subId);
    }
    return new ItemStack(bukkitMaterial);

  }

  @Override
  public ItemStack setDisplayName(ItemStack itemStack, String displayName) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(displayName);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  @Override
  public ItemStack setLore(ItemStack itemStack, List<String> itemLore) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setLore(itemLore);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  @Override
  public ItemStack makeUnbreakable(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.spigot().setUnbreakable(true);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  @Override
  public ItemStack makeBreakable(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.spigot().setUnbreakable(false);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  @Override
  public ItemStack addEnchantment(Enchantment enchantment, int level, ItemStack to) {
    return null;
  }

  @Override
  public ItemStack addUnsafeEnchantment(Enchantment enchantment, int level, ItemStack to) {
    return null;
  }

  @Override
  public ItemStack removeEnchantment(Enchantment enchantment, ItemStack from) {
    return null;
  }

  @Override
  public ItemStack addItemFlag(ItemFlag itemFlag, ItemStack from) {
    return null;
  }

  @Override
  public ItemStack removeItemFlag(ItemFlag itemFlag, ItemStack from) {
    return null;
  }
}
