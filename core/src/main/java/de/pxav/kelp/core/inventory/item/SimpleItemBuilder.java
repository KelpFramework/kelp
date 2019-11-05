package de.pxav.kelp.core.inventory.item;

import com.google.common.collect.Lists;
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
public class SimpleItemBuilder {

  private ItemStack itemStack;
  private ItemMeta itemMeta;

  public SimpleItemBuilder(Material material) {
    this.itemStack = new ItemStack(material);
    this.itemMeta = itemStack.getItemMeta();
  }

  public SimpleItemBuilder(Material material, int subId) {
    this.itemStack = new ItemStack(material, 1, (short) subId);
    this.itemMeta = itemStack.getItemMeta();
  }

  public SimpleItemBuilder(Material material, short subId) {
    this.itemStack = new ItemStack(material, 1, subId);
    this.itemMeta = itemStack.getItemMeta();
  }

  public SimpleItemBuilder(Material material, int amount, int subId) {
    this.itemStack = new ItemStack(material, amount, (short) subId);
    this.itemMeta = itemStack.getItemMeta();
  }

  public SimpleItemBuilder amount(int amount) {
    this.itemStack.setAmount(amount);
    return this;
  }

  public SimpleItemBuilder material(Material material) {
    this.itemStack.setType(material);
    return this;
  }

  public SimpleItemBuilder displayName(String displayName) {
    this.itemMeta.setDisplayName(displayName);
    return this;
  }

  public SimpleItemBuilder lore(String... lines) {
    List<String> loreLines = Arrays.asList(lines);
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItemBuilder lore(List<String> lines) {
    this.itemMeta.setLore(lines);
    return this;
  }

  public SimpleItemBuilder appendLore(String... lines) {
    List<String> loreLines = Lists.newArrayList();
    if (this.itemMeta.getLore() != null) {
      loreLines.addAll(this.itemMeta.getLore());
    }
    loreLines.addAll(Arrays.asList(lines));
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItemBuilder appendLore(List<String> lines) {
    List<String> loreLines = this.itemMeta.getLore();
    loreLines.addAll(lines);
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItemBuilder appendLore(String line) {
    List<String> loreLines = this.itemMeta.getLore();
    loreLines.add(line);
    this.itemMeta.setLore(loreLines);
    return this;
  }

  public SimpleItemBuilder glowEffect() {
    this.itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
    this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    return this;
  }

  public SimpleItemBuilder noGlowEffect() {
    if (!this.itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS))
      return this;
    this.itemMeta.removeEnchant(Enchantment.DURABILITY);
    this.itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
    return this;
  }

  public SimpleItemBuilder unbreakable() {
    this.itemMeta.spigot().setUnbreakable(true);
    return this;
  }

  public SimpleItemBuilder hideUnbreakability() {
    this.itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    return this;
  }

  public SimpleItemBuilder breakable() {
    this.itemMeta.spigot().setUnbreakable(false);
    return this;
  }

  public SimpleItemBuilder addItemFlags(ItemFlag... itemFlags) {
    this.itemMeta.addItemFlags(itemFlags);
    return this;
  }

  public SimpleItemBuilder removeItemFlags(ItemFlag... itemFlags) {
    this.itemMeta.removeItemFlags(itemFlags);
    return this;
  }

  public SimpleItemBuilder enchant(Enchantment enchantment) {
    this.itemMeta.addEnchant(enchantment, 1, true);
    return this;
  }

  public SimpleItemBuilder enchant(Enchantment enchantment, int level) {
    this.itemMeta.addEnchant(enchantment, level, true);
    return this;
  }

  public SimpleItemBuilder safeEnchant(Enchantment enchantment) {
    this.itemMeta.addEnchant(enchantment, 1, true);
    return this;
  }

  public SimpleItemBuilder safeEnchant(Enchantment enchantment) {
    this.itemMeta.addEnchant(enchantment, 1, true);
    return this;
  }




}
