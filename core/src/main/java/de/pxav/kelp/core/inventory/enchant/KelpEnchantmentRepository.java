package de.pxav.kelp.core.inventory.enchant;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.reflect.TypeCriterion;
import de.pxav.kelp.core.reflect.TypeFinder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class KelpEnchantmentRepository {

  private Set<Class<? extends KelpEnchantment>> enchantments = Sets.newHashSet();

  private TypeFinder typeFinder;
  private EnchantmentVersionTemplate enchantmentVersionTemplate;

  @Inject
  public KelpEnchantmentRepository(TypeFinder typeFinder, EnchantmentVersionTemplate versionTemplate) {
    this.typeFinder = typeFinder;
    this.enchantmentVersionTemplate = versionTemplate;
  }

  public void loadEnchantments(String... packages) {
    enchantmentVersionTemplate.openRegistry();
    typeFinder.filter(packages, TypeCriterion.subclassOf(KelpEnchantment.class))
      .forEach(enchantmentClass -> {
        KelpEnchantment enchantment = getEnchantmentFromClass(enchantmentClass);

        enchantmentVersionTemplate.registerEnchantment(enchantment);
        enchantments.add((Class<? extends KelpEnchantment>) enchantmentClass);
      });
    enchantmentVersionTemplate.closeRegistry();
  }

  public void unregisterAll() {
    enchantmentVersionTemplate.clearRegistry();
  }

  private KelpEnchantment getEnchantmentFromClass(Class<?> rawClass) {
    Class<? extends KelpEnchantment> enchantmentClass = (Class<? extends KelpEnchantment>) rawClass;
    return KelpPlugin.getInjector().getInstance(enchantmentClass);
  }

}
