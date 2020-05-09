package de.pxav.kelp.implementation1_14.inventory;

import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Material;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedMaterial extends MaterialVersionTemplate {

  @Override
  public void defineDefaults() {
    materials.put(KelpMaterial.APPLE, Material.APPLE.toString());
    materials.put(KelpMaterial.DIRT, Material.DIRT.toString());
    materials.put(KelpMaterial.STONE, Material.STONE.toString());

    materials.put(KelpMaterial.WHITE_WOOL, "WHITE_WOOL");
    materials.put(KelpMaterial.ORANGE_WOOL, "ORANGE_WOOL");

  }

}
