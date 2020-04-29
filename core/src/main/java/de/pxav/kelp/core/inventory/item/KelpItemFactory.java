package de.pxav.kelp.core.inventory.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpItemFactory {

  private MaterialVersionTemplate versionedMaterial;
  private ItemVersionTemplate itemVersionTemplate;

  @Inject
  public KelpItemFactory(MaterialVersionTemplate versionedMaterial,
                         ItemVersionTemplate itemVersionTemplate) {
    this.versionedMaterial = versionedMaterial;
    this.itemVersionTemplate = itemVersionTemplate;
  }

  public KelpItem newKelpItem() {
    return new KelpItem(versionedMaterial, itemVersionTemplate);
  }

}
