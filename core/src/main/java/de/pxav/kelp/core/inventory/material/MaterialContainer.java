package de.pxav.kelp.core.inventory.material;

import org.bukkit.Material;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MaterialContainer {

  private String materialName;
  private short subId;

  public MaterialContainer(String materialName, short subId) {
    this.materialName = materialName;
    this.subId = subId;
  }

  public String getMaterialName() {
    return materialName;
  }

  public Material getBukkitMaterial() {
    return Material.valueOf(materialName);
  }

  public short getSubId() {
    return subId;
  }

}
