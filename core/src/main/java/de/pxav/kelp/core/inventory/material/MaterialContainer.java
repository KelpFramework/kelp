package de.pxav.kelp.core.inventory.material;

import org.bukkit.Material;

/**
 * A MaterialContainer represents material information for all versions
 * from 1.8 to 1.12., where subIds were used to identify variations of
 * certain items. Instead of RED_STAINED_GLASS you wrote STAINED_GLASS:13
 * for instance. Those sub ids are not directly represented by the {@link KelpMaterial}
 * class, but they are mapped by the individual version implementations.
 *
 * So to convert a KelpMaterial to a bukkit material in versions up to 1.12 it
 * is recommended to use methods like {@link KelpMaterial#convert(KelpMaterial)}
 * as they return the material name (e. g. STAINED_GLASS) as well as the corresponding
 * sub id (e.g. 13).
 *
 * This way you can do {@code new ItemStack(materialContainer.getBukkitMaterial(), 1, materialContainer.getSubId());}
 *
 * If you are developing a plugin for 1.13+ only, this class is not important for you.
 *
 * @author pxav
 */
public class MaterialContainer {

  // the name of the material that is represented.
  // this is the exact name that is also used in bukkit's Material
  // class to represent this material.
  private String materialName;

  // the sub id of the given material.
  private short subId;

  public MaterialContainer(String materialName, short subId) {
    this.materialName = materialName;
    this.subId = subId;
  }

  /**
   * Gets the name of the material. This name is equivalent to the name
   * used in bukkit's {@code Material} enum to represent this material.
   * You can get the enum type directly by using {@link #getBukkitMaterial()},
   * which uses this name inside the {@code valueOf()} method of bukkit's
   * material class.
   *
   * @return The name of the material contained by this object.
   */
  public String getMaterialName() {
    return materialName;
  }

  /**
   * Directly gets the enum type of the material contained by this object.
   * It simply uses the name returned by {@link #getMaterialName()} and converts
   * it into a bukkit material enum type.
   *
   * @return The bukkit material enum type of this material
   */
  public Material getBukkitMaterial() {
    return Material.valueOf(materialName);
  }

  /**
   * Gets the subId of the represented material.
   * The subId defines special properties of an item such as its
   * color.
   *
   * @return The subId of the material contained by this object.
   */
  public short getSubId() {
    return subId;
  }

}
