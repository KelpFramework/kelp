package de.pxav.kelp.implementation1_8.inventory;

import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialRepository;
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

  private MaterialRepository materialRepository;

  @Inject
  public VersionedMaterial(MaterialRepository materialRepository) {
    this.materialRepository = materialRepository;
  }

  @Override
  public void defineDefaults() {
    materialRepository.addMaterial(KelpMaterial.APPLE, Material.APPLE.toString());
    materialRepository.addMaterial(KelpMaterial.DIRT, Material.DIRT.toString());
    materialRepository.addMaterial(KelpMaterial.STONE, Material.STONE.toString());

    materialRepository.addMaterial(KelpMaterial.WHITE_WOOL, Material.WOOL.toString() + ":0");
    materialRepository.addMaterial(KelpMaterial.ORANGE_WOOL, Material.WOOL.toString() + ":1");

    System.out.println("Successfully defined item names for 1.8 version");
  }

}
