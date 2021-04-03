package de.pxav.kelp.core.inventory.enchant;

import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.inventory.enchant.minecraft.EfficiencyEnchantment;
import de.pxav.kelp.core.inventory.enchant.minecraft.InfinityEnchantment;
import de.pxav.kelp.core.inventory.enchant.minecraft.MendingEnchantment;
import de.pxav.kelp.core.inventory.enchant.minecraft.UnbreakingEnchantment;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Represents an enchantment that can be applied to a {@link de.pxav.kelp.core.inventory.item.KelpItem}.
 * Enchantments are additional properties that can be given to an item resulting in new abilities such
 * as an increased dig speed ({@link #EFFICIENCY}) or increased item durability {@link #UNBREAKING}.
 *
 * Minecraft provides various enchantments by default, which can be directly accessed via the
 * static fields contained by this class. But you can create your own enchantments as well by simply
 * extending from this class and overriding the abstract methods documented below. On server startup,
 * you will have to call {@link KelpEnchantmentRepository#loadEnchantments(String...)} for your plugin
 * to register all your enchantments at once. To actually give your item an ability, it is recommended to
 * simply create a normal {@link org.bukkit.event.EventHandler} in your enchantment class listening
 * for the appropriate event for your enchantment. This makes it more obvious for other developers which
 * event goes with which enchantment. You can also let inject some dependencies via Guice if needed, but
 * don't mark the class as a {@link javax.inject.Singleton}.
 *
 * An enchantment can be applied directly from the {@link de.pxav.kelp.core.inventory.item.KelpItem} class
 * by calling {@link de.pxav.kelp.core.inventory.item.KelpItem#enchant(Class, int)}, where the class stands
 * for the class of your (custom) enchantment and the integer is the level for the enchantment. To remove
 * an enchantment, call {@link de.pxav.kelp.core.inventory.item.KelpItem#removeEnchantment(Class)} again.
 *
 * @author pxav
 */
public abstract class KelpEnchantment {

  /**
   * Increases mining speed of the tool it is applied to.
   */
  public static final Class<EfficiencyEnchantment> EFFICIENCY = EfficiencyEnchantment.class;

  /**
   * Increases the durability of the item it is applied to.
   */
  public static final Class<UnbreakingEnchantment> UNBREAKING = UnbreakingEnchantment.class;

  /**
   * Infinite arrows on a bow if at least one arrow is inside the player's inventory.
   */
  public static final Class<InfinityEnchantment> INFINITY = InfinityEnchantment.class;

  /**
   * Repairs player items at the cost of experience orbs collected from any source.
   */
  public static final Class<MendingEnchantment> MENDING = MendingEnchantment.class;

  /**
   * Gets the name of the enchantment which is used to generate
   * the lore/item description entry for this enchantment for example.
   * It should be unique among all your custom enchantments as it also
   * serves as an identifier.
   *
   * @return The name of this enchantment.
   */
  public abstract String getName();

  /**
   * Gets the minimum/start level of this enchantment. When applying
   * the enchantment to an item, you have to pass the power/level of
   * the enchantment. If this power is lower than the level defined here,
   * an error will be thrown.
   *
   * @return The minimum level to be applied to this enchantment.
   */
  public abstract int getStartLevel();

  /**
   * Gets the maximum level of this enchantment. When applying the enchantment to
   * an item, the provided level may not exceed this number. This is useful when your
   * enchantment adds an ability which is not upgradable (such as putting an item directly
   * to a player's inventory on block break) - then your max level would be 1 and anything
   * above this threshold wouldn't make any sense. But it can be also useful if you
   * want to limit the power of your enchantment to avoid it can become too overpowered.
   *
   * @return The maximum level of the enchantment.
   */
  public abstract int getMaxLevel();

  /**
   * Checks if this enchantment can be applied to the given material. Here you can
   * check whether the item is a pickaxe or tool in general for example.
   *
   * @param material The material to be checked.
   * @return {@code true} if the material can be enchanted.
   */
  public abstract boolean canEnchant(KelpMaterial material);

  /**
   * If your enchantment is incompatible with another enchantment. This
   * is useful if you want to balance your enchantments and want to avoid
   * overpowered combinations such as Mending + Infinity on a bow.
   *
   * @param enchantment The name of the enchantment provided by the
   *                    {@link #getName()} method of the corresponding class.
   * @return {@code true} if the given enchantment is incompatible with this enchantment.
   */
  public abstract boolean conflictsWith(Class<? extends KelpEnchantment> enchantment);

  /**
   * Checks whether the the enchantment should be mentioned in the item
   * description/lore of the target item. This method is only applicable to
   * custom enchantments as they are not added to the lore by default. Minecraft
   * enchantments will always be added by default (unless you hide them manually).
   *
   * @return  {@code true} if the custom enchantment should be added to the
   *          lore of the target item.
   */
  public abstract boolean addToItemDescription();

  /**
   * Checks whether this enchantment is a default Minecraft/Bukkit enchantment.
   * The output of this method depends on your server version: While Mending for example
   * did not exist in 1.8, it does in 1.12, so this method calls {@link #bukkitEnchantmentUnsafe(KelpVersion)}
   * in the background, which is overridden by every bukkit enchantment class and returns
   * if the enchantment is a bukkit enchantment depending on the current server version. This is the
   * reason why this method is declared final and should not be overridden by any class for version
   * safety reasons.
   *
   * @return {@code true} if the enchantment is a default Minecraft enchantment on the current server version.
   */
  public final boolean isBukkitEnchantment() {
    return bukkitEnchantmentUnsafe(KelpServer.getVersion());
  }

  /**
   * Checks whether this enchantment is a default Minecraft/Bukkit enchantment
   * depending on the given server version. This method should be overridden by
   * every bukkit enchantment class such as {@link EfficiencyEnchantment} or {@link UnbreakingEnchantment}, etc.
   *
   * The output of this method is used by {@link #isBukkitEnchantment()}, which automatically
   * injects the current server version as parameter. So, if you want to check if this is a bukkit
   * enchantment, it is not safe to call this method, but use {@link #isBukkitEnchantment()} instead.
   *
   * @param version The server version you want to check the enchantment existence of.
   * @return {@code true} if the enchantment is a default minecraft enchantment for the given server version.
   */
  protected boolean bukkitEnchantmentUnsafe(KelpVersion version) {
    return false;
  }

}
