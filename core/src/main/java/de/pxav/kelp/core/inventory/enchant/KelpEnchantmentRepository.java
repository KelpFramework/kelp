package de.pxav.kelp.core.inventory.enchant;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.reflect.TypeCriterion;
import de.pxav.kelp.core.reflect.TypeFinder;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This repository class is used to register all custom enchantments made with Kelp.
 *
 * @author pxav
 */
@Singleton
public class KelpEnchantmentRepository {

  private TypeFinder typeFinder;
  private EnchantmentVersionTemplate enchantmentVersionTemplate;

  @Inject
  public KelpEnchantmentRepository(TypeFinder typeFinder, EnchantmentVersionTemplate versionTemplate) {
    this.typeFinder = typeFinder;
    this.enchantmentVersionTemplate = versionTemplate;
  }

  /**
   * Finds all classes extending from {@link KelpEnchantment} in the given packages and their sub-packages
   * and loads/registers them to the server so that they can be applied to items. This method has to be
   * called by every plugin creating custom enchantments once on plugin startup.
   *
   * @param packages The packages you want to scan for custom enchantments.
   */
  public void loadEnchantments(String... packages) {
    // make the server accept new enchantments, which is normally only
    // enabled by the server implementation
    enchantmentVersionTemplate.openRegistry();

    typeFinder.filter(packages, TypeCriterion.subclassOf(KelpEnchantment.class))
      .forEach(enchantmentClass -> {
        KelpEnchantment enchantment = getEnchantmentFromClass(enchantmentClass);

        enchantmentVersionTemplate.registerEnchantment(enchantment);
      });

    // don't accept further enchantments (close for safety reaons)
    enchantmentVersionTemplate.closeRegistry();
  }

  /**
   * Removes all custom enchantments from the server's enchantment registry.
   *
   * This method is called automatically by Kelp and does not have to be called
   * by each plugin individually.
   */
  public void unregisterAll() {
    enchantmentVersionTemplate.clearRegistry();
  }

  /**
   * Gets the {@link KelpEnchantment} instance associated with the given enchantment class.
   *
   * @param rawClass
   * @return The enchantment instance of the enchantment associated with the given class.
   */
  private KelpEnchantment getEnchantmentFromClass(Class<?> rawClass) {
    Class<? extends KelpEnchantment> enchantmentClass = (Class<? extends KelpEnchantment>) rawClass;
    return KelpPlugin.getInjector().getInstance(enchantmentClass);
  }

}
