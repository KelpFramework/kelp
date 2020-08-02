package de.pxav.kelp.core.inventory.material;

import de.pxav.kelp.core.application.KelpVersionTemplate;

/**
 * This version template is used to map all material names of the
 * server version with the {@code KelpMaterial} names.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class MaterialVersionTemplate {

  /**
   * Maps the KelpMaterials with the corresponding bukkit materials
   * and saves them into the {@link MaterialRepository}.
   */
  public abstract void defineDefaults();

}
