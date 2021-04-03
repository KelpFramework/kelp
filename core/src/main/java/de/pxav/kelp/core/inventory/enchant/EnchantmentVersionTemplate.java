package de.pxav.kelp.core.inventory.enchant;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * This version template is used to handle all version specific operations of
 * {@link KelpEnchantment enchantments} as there are different ways of registering them
 * depending on your server version.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class EnchantmentVersionTemplate {

  /**
   * Makes the server accept further enchantments. Normally, the server
   * opens its registry only early on server startup to allow the server
   * implementation to register the default minecraft enchantments, but
   * for safety reasons this registry accepts no further enchantments after
   * this operation. This method opens the registry again allowing Kelp to
   * inject further enchantments.
   */
  public abstract void openRegistry();

  /**
   * Closes the registry opened by {@link #openRegistry()} again.
   * If you open the registry, you should always close it again after
   * you are done.
   */
  public abstract void closeRegistry();

  /**
   * Removes all custom enchantments from the server registry.
   * This method is called by Kelp on plugin shutdown to avoid enchantment
   * duplications.
   */
  public abstract void clearRegistry();

  /**
   * Adds the given enchantment to the server's enchantment registry.
   *
   * @param enchantment The enchantment to register.
   */
  public abstract void registerEnchantment(KelpEnchantment enchantment);

  /**
   * Applies the given kelp enchantment to the given item stack.
   *
   * @param enchantment The enchantment to apply to the item stack.
   * @param level       The level to apply the enchantment with.
   * @param to          The item to apply the enchantment to.
   * @return The modified/enchanted item stack.
   */
  public abstract ItemStack applyEnchantment(Class<? extends KelpEnchantment> enchantment, int level, ItemStack to);

  /**
   * Converts the given bukkit enchantment to the corresponding KelpEnchantment.
   * This is used when converting a normal bukkit item stack to a {@link de.pxav.kelp.core.inventory.item.KelpItem}
   * for example.
   *
   * @param bukkitEnchantment The bukkit enchantment to convert.
   * @return The class holding the corresponding kelp enchantment.
   */
  public abstract Class<? extends KelpEnchantment> getKelpEnchantment(Enchantment bukkitEnchantment);

}
