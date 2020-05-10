package de.pxav.kelp.core.inventory.material;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.Singleton;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class MaterialRepository {

  private BiMap<KelpMaterial, String> materials = HashBiMap.create();

  public String getMaterial(KelpMaterial kelpMaterial) {
    return materials.get(kelpMaterial);
  }

  public String getMaterial(String kelpMaterial) {
    return materials.get(KelpMaterial.valueOf(kelpMaterial));
  }

  public KelpMaterial getKelpMaterial(String bukkitMaterial) {
    return materials.inverse().get(bukkitMaterial);
  }

  public KelpMaterial getKelpMaterial(String bukkitMaterial, short subId) {
    return materials.inverse().get(bukkitMaterial + ":" + subId);
  }

  public KelpMaterial getKelpMaterial(String bukkitMaterial, int subId) {
    return materials.inverse().get(bukkitMaterial + ":" + subId);
  }

  public void addMaterial(KelpMaterial kelpMaterial, String bukkitMaterial) {
    this.materials.put(kelpMaterial, bukkitMaterial);
  }

  public void removeMaterial(KelpMaterial kelpMaterial) {
    this.materials.remove(kelpMaterial);
  }

}
