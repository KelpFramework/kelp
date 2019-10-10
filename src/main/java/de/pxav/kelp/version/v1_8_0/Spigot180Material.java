package de.pxav.kelp.version.v1_8_0;

import com.google.inject.Singleton;
import de.pxav.kelp.version.KelpVersion;
import de.pxav.kelp.version.material.KelpMaterial;
import de.pxav.kelp.version.material.MaterialData;
import de.pxav.kelp.version.material.VersionedMaterial;
import de.pxav.kelp.version.material.VersionedSpigotMaterial;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
@VersionedMaterial({KelpVersion.MC_1_8_0,
        KelpVersion.MC_1_8_3,
        KelpVersion.MC_1_8_4,
        KelpVersion.MC_1_8_5,
        KelpVersion.MC_1_8_6,
        KelpVersion.MC_1_8_7,
        KelpVersion.MC_1_8_8})
public class Spigot180Material extends VersionedSpigotMaterial {

  @Override
  public void versionMaterials() {
    materials.add(new MaterialData(KelpMaterial.STONE, "STONE"));
    materials.add(new MaterialData(KelpMaterial.DIRT, "DIRT"));
  }

}
