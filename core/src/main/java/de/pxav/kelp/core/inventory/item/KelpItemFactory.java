package de.pxav.kelp.core.inventory.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.inventory.listener.KelpListenerRepository;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import org.bukkit.inventory.ItemStack;

/**
 * This class is used to create and handle item
 * instances from either bukkit or Kelp.
 *
 * You can create new {@code KelpItems} by yourself or
 * based on a specific bukkit item stack.
 *
 * @author pxav
 * @see KelpItem
 */
@Singleton
public class KelpItemFactory {

  private MaterialVersionTemplate versionedMaterial;
  private ItemVersionTemplate itemVersionTemplate;
  private ItemTagVersionTemplate itemTagVersionTemplate;
  private KelpListenerRepository kelpListenerRepository;

  @Inject
  public KelpItemFactory(MaterialVersionTemplate versionedMaterial,
                         ItemVersionTemplate itemVersionTemplate,
                         ItemTagVersionTemplate itemTagVersionTemplate,
                         KelpListenerRepository kelpListenerRepository) {
    this.versionedMaterial = versionedMaterial;
    this.itemVersionTemplate = itemVersionTemplate;
    this.itemTagVersionTemplate = itemTagVersionTemplate;
    this.kelpListenerRepository = kelpListenerRepository;
  }

  /**
   * Creates a new {@link KelpItem} instance.
   *
   * @return The newly created instance.
   */
  public KelpItem newKelpItem() {
    return new KelpItem(itemVersionTemplate, itemTagVersionTemplate, kelpListenerRepository);
  }

  /**
   * Creates a new {@link KelpItem} instance from a given {@code ItemStack}.
   * The {@code KelpItem} will have the same metadata as your input item
   * stack like the display name, lore, etc.
   *
   * @param bukkitItemStack The bukkit item stack from which you want to take
   *                        the data from.
   * @return The kelp item.
   */
  public KelpItem fromItemStack(ItemStack bukkitItemStack) {
    return itemVersionTemplate.fromItemStack(bukkitItemStack);
  }

}
