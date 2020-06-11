package de.pxav.kelp.core.inventory.material;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class MaterialRepository {

  private BiMap<KelpMaterial, String> materials = HashBiMap.create();
  private Map<KelpMaterial, KelpMaterial> kelpMaterialAliases = Maps.newHashMap();

  public String getMaterial(KelpMaterial kelpMaterial) {
    if (kelpMaterialAliases.containsKey(kelpMaterial)) {
      KelpMaterial newKelpMaterial = kelpMaterialAliases.get(kelpMaterial);
      return materials.get(newKelpMaterial);
    }

    return materials.get(kelpMaterial);
  }

  public String getMaterial(String kelpMaterialName) {
    KelpMaterial kelpMaterial = KelpMaterial.valueOf(kelpMaterialName);

    if (kelpMaterialAliases.containsKey(kelpMaterial)) {
      KelpMaterial newKelpMaterial = kelpMaterialAliases.get(kelpMaterial);
      return materials.get(newKelpMaterial);
    }

    return materials.get(kelpMaterial);
  }

  public KelpMaterial getKelpMaterial(String bukkitMaterial)  {
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

  public void addAlias(KelpMaterial source, KelpMaterial newAlias) {
    this.kelpMaterialAliases.put(source, newAlias);
  }

  public void removeMaterial(KelpMaterial kelpMaterial) {
    this.materials.remove(kelpMaterial);
  }

  public void removeAlias(KelpMaterial aliasSource) {
    this.kelpMaterialAliases.remove(aliasSource);
  }

}
