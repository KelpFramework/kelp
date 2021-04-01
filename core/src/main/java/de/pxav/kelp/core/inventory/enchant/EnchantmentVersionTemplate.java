package de.pxav.kelp.core.inventory.enchant;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@KelpVersionTemplate
public abstract class EnchantmentVersionTemplate {

  public abstract void openRegistry();

  public abstract void closeRegistry();

  public abstract void clearRegistry();

  public abstract void registerEnchantment(KelpEnchantment enchantment);

  public abstract ItemStack applyEnchantment(Class<? extends KelpEnchantment> enchantment, int level, ItemStack to);

  public abstract Class<? extends KelpEnchantment> getKelpEnchantment(Enchantment bukkitEnchantment);

}
