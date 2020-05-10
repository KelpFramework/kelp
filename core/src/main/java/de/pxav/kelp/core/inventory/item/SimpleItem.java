package de.pxav.kelp.core.inventory.item;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SimpleItem {

  private ItemVersionTemplate itemVersionTemplate;
  private MaterialVersionTemplate materialVersionTemplate;

  private ItemStack itemStack;
  private ItemMeta itemMeta;

  public SimpleItem(ItemVersionTemplate itemVersionTemplate) {
    this.itemVersionTemplate = itemVersionTemplate;
    this.itemStack = this.itemVersionTemplate.newItemStack();
  }

  public SimpleItem fromItemStack(ItemStack itemStack) {
    this.itemStack = itemStack;
    this.itemMeta = itemStack.getItemMeta();
    return this;
  }

  public SimpleItem(Material material, int subId) {
    this.itemStack = new ItemStack(material, 1, (short) subId);
    this.itemMeta = itemStack.getItemMeta();
  }

  public SimpleItem(Material material, short subId) {
    this.itemStack = new ItemStack(material, 1, subId);
    this.itemMeta = itemStack.getItemMeta();
  }

  public SimpleItem(Material material, int amount, int subId) {
    this.itemStack = new ItemStack(material, amount, (short) subId);
    this.itemMeta = itemStack.getItemMeta();
  }

  public SimpleItem amount(int amount) {
    this.itemStack.setAmount(amount);
    return this;
  }

  public SimpleItem material(Material material) {
    this.itemStack.setType(material);
    return this;
  }

  public SimpleItem displayName(String displayName) {
    this.itemMeta.setDisplayName(displayName);
    return this;
  }

  public SimpleItem lore(String... lines) {
    List<String> loreLines = Arrays.asList(lines);
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItem lore(List<String> lines) {
    this.itemMeta.setLore(lines);
    return this;
  }

  public SimpleItem appendLore(String... lines) {
    List<String> loreLines = Lists.newArrayList();
    if (this.itemMeta.getLore() != null) {
      loreLines.addAll(this.itemMeta.getLore());
    }
    loreLines.addAll(Arrays.asList(lines));
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItem appendLore(List<String> lines) {
    List<String> loreLines = this.itemMeta.getLore();
    loreLines.addAll(lines);
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItem appendLore(String line) {
    List<String> loreLines = this.itemMeta.getLore();
    loreLines.add(line);
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItem glowEffect() {
    this.itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
    this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    return this;
  }

  public SimpleItem noGlowEffect() {
    if (!this.itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS))
      return this;
    this.itemMeta.removeEnchant(Enchantment.DURABILITY);
    this.itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
    return this;
  }

  public SimpleItem unbreakable() {
    this.itemMeta.spigot().setUnbreakable(true);
    return this;
  }

  public SimpleItem hideUnbreakability() {
    this.itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    return this;
  }

  public SimpleItem breakable() {
    this.itemMeta.spigot().setUnbreakable(false);
    return this;
  }

  public SimpleItem addItemFlags(ItemFlag... itemFlags) {
    this.itemMeta.addItemFlags(itemFlags);
    return this;
  }

  public SimpleItem removeItemFlags(ItemFlag... itemFlags) {
    this.itemMeta.removeItemFlags(itemFlags);
    return this;
  }

  public SimpleItem enchant(Enchantment enchantment) {
    this.itemMeta.addEnchant(enchantment, 1, true);
    return this;
  }

  public SimpleItem enchant(Enchantment enchantment, int level) {
    this.itemMeta.addEnchant(enchantment, level, true);
    return this;
  }

  public SimpleItem safeEnchant(Enchantment enchantment) {
    this.itemMeta.addEnchant(enchantment, 1, true);
    return this;
  }

  public ItemStack build() {
    this.itemStack.setItemMeta(this.itemMeta);
    return this.itemStack;
  }

}
