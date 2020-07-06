package de.pxav.kelp.core.inventory.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.inventory.listener.KelpListenerRepository;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
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

  public KelpItem newKelpItem() {
    return new KelpItem(itemVersionTemplate, itemTagVersionTemplate, kelpListenerRepository);
  }

  public KelpItem fromItemStack(ItemStack bukkitItemStack) {
    return itemVersionTemplate.fromItemStack(bukkitItemStack);
  }

}
