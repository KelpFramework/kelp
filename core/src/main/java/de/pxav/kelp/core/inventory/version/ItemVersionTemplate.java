package de.pxav.kelp.core.inventory.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ItemVersionTemplate {

  public abstract KelpItem fromItemStack(ItemStack itemStack);

  public abstract ItemStack newItemStack();

  public abstract ItemStack newItemStack(KelpMaterial material);

  public abstract ItemStack setDisplayName(ItemStack itemStack, String displayName);

  public abstract ItemStack setLore(ItemStack itemStack, List<String> itemLore);

  public abstract ItemStack makeUnbreakable(ItemStack itemStack);

  public abstract ItemStack makeBreakable(ItemStack itemStack);

  public abstract ItemStack addEnchantment(Enchantment enchantment, int level, ItemStack to);

  public abstract ItemStack addUnsafeEnchantment(Enchantment enchantment, int level, ItemStack to);

  public abstract ItemStack removeEnchantment(Enchantment enchantment, ItemStack from);

  public abstract ItemStack addItemFlag(ItemFlag itemFlag, ItemStack from);

  public abstract ItemStack removeItemFlag(ItemFlag itemFlag, ItemStack from);

}
