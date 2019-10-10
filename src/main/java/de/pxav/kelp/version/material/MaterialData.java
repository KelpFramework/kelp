package de.pxav.kelp.version.material;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MaterialData {

  private KelpMaterial kelpMaterial;
  private String versionedName;

  public MaterialData(KelpMaterial kelpMaterial, String versionedName) {
    this.kelpMaterial = kelpMaterial;
    this.versionedName = versionedName;
  }

  public MaterialData kelpMaterial(KelpMaterial kelpMaterial) {
    this.kelpMaterial = kelpMaterial;
    return this;
  }

  public MaterialData versionedName(String versionedName) {
    this.versionedName = versionedName;
    return this;
  }

  public KelpMaterial getKelpMaterial() {
    return kelpMaterial;
  }

  public String getVersionedName() {
    return versionedName;
  }

}
