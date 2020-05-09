package de.pxav.kelp.implementation1_8.inventory;

import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialRepository;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedItem extends ItemVersionTemplate {

  private MaterialRepository materialRepository;

  @Inject
  public VersionedItem(MaterialRepository materialRepository) {
    this.materialRepository = materialRepository;
  }

  @Override
  public ItemStack newItemStack() {
    return new ItemStack(Material.STONE);
  }

  @Override
  public ItemStack newItemStack(KelpMaterial material) {
    String materialName = materialRepository.getMaterial(material);
    System.out.println("mat name " + materialName);
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
  public ItemStack setLore(ItemStack itemStack, String itemLore) {
    return null;
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
