package de.pxav.kelp.implementation1_8.inventory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.common.MathUtils;
import de.pxav.kelp.core.inventory.enchant.EnchantmentVersionTemplate;
import de.pxav.kelp.core.inventory.enchant.KelpEnchantment;
import de.pxav.kelp.core.inventory.enchant.minecraft.EfficiencyEnchantment;
import de.pxav.kelp.core.inventory.enchant.minecraft.UnbreakingEnchantment;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.metadata.ItemMetadata;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Versioned
@Singleton
public class VersionedEnchantment extends EnchantmentVersionTemplate {

  private BiMap<Class<? extends KelpEnchantment>, Enchantment> enchantments = HashBiMap.create();

  private KelpLogger logger;

  @Inject
  public VersionedEnchantment(KelpLogger logger) {
    this.logger = logger;
  }

  @Override
  public void openRegistry() {
    try {
      Field acceptingField = Enchantment.class.getDeclaredField("acceptingNew");
      acceptingField.setAccessible(true);
      acceptingField.set(null, true);
    } catch (IllegalAccessException | NoSuchFieldException exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void closeRegistry() {
    try {
      Field acceptingField = Enchantment.class.getDeclaredField("acceptingNew");
      acceptingField.setAccessible(true);
      acceptingField.set(null, false);
    } catch (IllegalAccessException | NoSuchFieldException exception) {
      exception.printStackTrace();
    }  }

  @Override
  public void clearRegistry() {
    try {
      Field byIdField = Enchantment.class.getDeclaredField("byId");
      Field byNameField = Enchantment.class.getDeclaredField("byName");

      byIdField.setAccessible(true);
      byNameField.setAccessible(true);

      HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
      HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byNameField.get(null);

      enchantments.values().forEach(enchantment -> {
        byId.remove(enchantment.getId());
        byName.remove(enchantment.getName());
      });
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void registerEnchantment(KelpEnchantment enchantment) {
    // if the enchantment is a default bukkit enchantment it does not have to be registered.
    if (enchantment.isBukkitEnchantment()) {
      return;
    }

    int id = enchantments.size() + 63;
    if (id >= 255) {
      logger.log(LogLevel.ERROR, "Cannot register more than 256 enchantments!");
      return;
    }

    Enchantment bukkitEnchantment = new Enchantment(id) {
      @Override
      public String getName() {
        return enchantment.getName();
      }

      @Override
      public int getMaxLevel() {
        return enchantment.getMaxLevel();
      }

      @Override
      public int getStartLevel() {
        return enchantment.getStartLevel();
      }

      @Override
      public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
      }

      @Override
      public boolean conflictsWith(Enchantment other) {
        return enchantment.conflictsWith(other.getName());
      }

      @Override
      public boolean canEnchantItem(ItemStack item) {
        return enchantment.canEnchant(KelpMaterial.from(item.getType()));
      }
    };

    this.enchantments.put(enchantment.getClass(), bukkitEnchantment);

    Enchantment.registerEnchantment(bukkitEnchantment);

  }

  @Override
  public ItemStack applyEnchantment(Class<? extends KelpEnchantment> enchantment, int level, ItemStack to) {
    KelpEnchantment kelpEnchantment = KelpPlugin.getInjector().getInstance(enchantment);

    if (kelpEnchantment.isBukkitEnchantment()) {
      if (kelpEnchantment instanceof EfficiencyEnchantment) {
        to.addUnsafeEnchantment(Enchantment.DIG_SPEED, level);
      } else if (kelpEnchantment instanceof UnbreakingEnchantment) {
        to.addUnsafeEnchantment(Enchantment.DURABILITY, level);
      }

      else {
        logger.log(LogLevel.ERROR, "Enchantment " + kelpEnchantment.getName() + " is not available for this server version!");
      }

      return to;
    }

    Enchantment bukkitEnchantment = enchantments.get(enchantment);
    to.addUnsafeEnchantment(bukkitEnchantment, level);

    if (kelpEnchantment.addToItemDescription()) {
      ItemMeta itemMeta = to.getItemMeta();
      List<String> lore = itemMeta.getLore() == null ? Lists.newArrayList() : itemMeta.getLore();
      lore.add("§7" + kelpEnchantment.getName() + " " + MathUtils.getRomanNumber(level));
      itemMeta.setLore(lore);
      to.setItemMeta(itemMeta);
    }

    return to;
  }

  @Override
  public Class<? extends KelpEnchantment> getKelpEnchantment(Enchantment bukkitEnchantment) {
    return enchantments.inverse().get(bukkitEnchantment);
  }

}
