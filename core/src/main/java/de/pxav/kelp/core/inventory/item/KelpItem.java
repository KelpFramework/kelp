package de.pxav.kelp.core.inventory.item;

import de.pxav.kelp.core.version.material.KelpMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpItem {

  private KelpMaterial material;
  private int amount;
  private int slot;
  private Inventory parent;

  private String displayName;
  private List<String> lore;

  private Collection<ItemFlag> itemFlags;
  private Collection<Enchantment> enchantments;
  private Collection<NbtTag> nbtTags;

  private boolean glowing;
  private boolean unbreakable;

  public KelpItem material(KelpMaterial material) {
    this.material = material;
    return this;
  }

  public KelpItem amount(int amount) {
    this.amount = amount;
    return this;
  }

  public KelpItem nbtTag(NbtTag nbtTag) {
    this.nbtTags.add(nbtTag);
    return this;
  }

  public ItemStack build() {
    SimpleItemBuilder itemBuilder = new SimpleItemBuilder(this.material);

  }




}
