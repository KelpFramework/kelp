package de.pxav.kelp.version.material;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class VersionedSpigotMaterial {

  // google guice injector to fetch instances of classes
  @Inject
  private Injector injector;

  // this list contains all your configuration attributes
  public Collection<MaterialData> materials = Lists.newArrayList();

  public abstract void versionMaterials();

  // get missing mat.
  public Collection<KelpMaterial> validate() {
    Collection<KelpMaterial> addedMaterials = Lists.newArrayList(KelpMaterial.values());
    materials.forEach(current -> addedMaterials.remove(current.getKelpMaterial()));

    return addedMaterials;
  }

}
