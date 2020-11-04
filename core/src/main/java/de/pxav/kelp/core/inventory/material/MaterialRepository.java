package de.pxav.kelp.core.inventory.material;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This repository class is used to save all material names of the
 * current server version and their corresponding {@link KelpMaterial}.
 *
 * Using sub ids is optional and only required if you are using version 1.12
 * and below.
 *
 * @author pxav
 */
@Singleton
public class MaterialRepository {

  private BiMap<KelpMaterial, String> materials = HashBiMap.create();
  private Map<KelpMaterial, KelpMaterial> kelpMaterialAliases = Maps.newHashMap();

  /**
   * Gets the bukkit material matching the given kelp material as a string.
   * Sub IDs are not supported by this method.
   *
   * @param kelpMaterial The kelp material you want to get
   *                     the bukkit material of.
   * @return The bukkit material string.
   */
  public String getMaterial(KelpMaterial kelpMaterial) {
    if (kelpMaterialAliases.containsKey(kelpMaterial)) {
      KelpMaterial newKelpMaterial = kelpMaterialAliases.get(kelpMaterial);
      return materials.get(newKelpMaterial);
    }

    return materials.get(kelpMaterial);
  }

  /**
   * Gets the bukkit material matching the given kelp material as a string.
   * Sub IDs are not supported by this method.
   *
   * @param kelpMaterialName The kelp material name/string you want to get
   *                         the bukkit material of.
   * @return The bukkit material string.
   */
  public String getMaterial(String kelpMaterialName) {
    KelpMaterial kelpMaterial = KelpMaterial.valueOf(kelpMaterialName);
    return this.getMaterial(kelpMaterial);
  }

  public MaterialContainer getBukkitMaterial(KelpMaterial kelpMaterial) {
    String[] format = materials.get(kelpMaterial).split(":");
    if (format.length == 1) {
      return new MaterialContainer(format[0], (short) 0);
    }
    return new MaterialContainer(format[0], Short.parseShort(format[1]));
  }

  /**
   * Gets the kelp material matching the given bukkit material name.
   * This method does not support sub IDs.
   *
   * @param bukkitMaterial The name of the bukkit material you want
   *                       to get the kelp material of.
   * @return The final kelp material.
   */
  public KelpMaterial getKelpMaterial(String bukkitMaterial) {
    return materials.inverse().get(bukkitMaterial);
  }

  /**
   * Gets the kelp material matching the given bukkit material name.
   * This method also supports sub ids.
   *
   * @param bukkitMaterial  The name of the bukkit material you want
   *                        to get the kelp material of.
   * @param subId           The sub id of the bukkit item.
   * @return The final kelp material.
   */
  public KelpMaterial getKelpMaterial(String bukkitMaterial, short subId) {
    if (subId == 0) {
      return this.getKelpMaterial(bukkitMaterial);
    }
    return materials.inverse().get(bukkitMaterial + ":" + subId);
  }

  /**
   * Gets the kelp material with the given bukkit material name and sub id.
   * This method supports sub ids, which can also be entered as an integer.
   *
   * @param bukkitMaterial The bukkit material you want to get the kelp
   *                       material of.
   * @param subId          The sub id of the desired bukkit item.
   *
   * @return The kelp material matching the given bukkit material.
   */
  public KelpMaterial getKelpMaterial(String bukkitMaterial, int subId) {
    if (subId == 0) {
      return this.getKelpMaterial(bukkitMaterial);
    }
    return materials.inverse().get(bukkitMaterial + ":" + subId);
  }

  /**
   * Adds a new material to the map. Sub ids can be added by simply
   * appending a {@code :<subId>} at the end of the bukkit materal string.
   *
   * @param kelpMaterial    The kelp material you want to add a link for.
   * @param bukkitMaterial  The string of the bukkit material which should
   *                        be linked to the kelp material (including sub ids
   *                        with {@code :<subId} at the end of the string).
   */
  public void addMaterial(KelpMaterial kelpMaterial, String bukkitMaterial) {
    this.materials.put(kelpMaterial, bukkitMaterial);
  }

  /**
   * Adds a new material alias. Material aliases are practical
   * if you have versions which do not distinguish between material
   * states for example. So in one version there are extra material
   * types for {@code WALL_SIGN, SIGN_POST, SIGN} and another version,
   * where there are only {@code WALL_SIGN, SIGN}, where SIGN is the
   * normal standing sign as well as the item held by a player. Where
   * in the first example SIGN_POST is the standing sign and SIGN is
   * the item held by a player. So in the kelp material class, there
   * are extra materials for {@code WALL_SIGN, SIGN_POST and SIGN}
   * so that the version distinguishing between more types can easily
   * match. In the version implementation of the other version, the
   * developer can simply create an alias from SIGN_POST to SIGN.
   * So when calling SIGN_POST as a kelp material, the developer gets
   * back the mapped value for the normal SIGN. This makes no difference
   * as those are the same items in this specific version.
   *
   * @param source    The material you want to add an alias to.
   * @param newAlias  The material the alias should point to
   *                  when you call the source.
   */
  public void addAlias(KelpMaterial source, KelpMaterial newAlias) {
    this.kelpMaterialAliases.put(source, newAlias);
  }

  /**
   * Removes a material from the version map.
   *
   * @param kelpMaterial The material you want to remove.
   */
  public void removeMaterial(KelpMaterial kelpMaterial) {
    this.materials.remove(kelpMaterial);
  }

  /**
   * Removes the alias related to the given kelp material.
   *
   * @param aliasSource The kelp material to which the alias was given.
   */
  public void removeAlias(KelpMaterial aliasSource) {
    this.kelpMaterialAliases.remove(aliasSource);
  }

}
