package de.pxav.kelp.core.version;

import com.google.common.collect.Lists;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum KelpVersion {

  MC_1_8_0(1, "1.8-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_8_3(4, "1.8.3-R0.1-SNAPSHOT"),
  MC_1_8_4(5, "1.8.4-R0.1-SNAPSHOT"),
  MC_1_8_5(6, "1.8.5-R0.1-SNAPSHOT"),
  MC_1_8_6(7, "1.8.6-R0.1-SNAPSHOT"),
  MC_1_8_7(8, "1.8.7-R0.1-SNAPSHOT"),
  MC_1_8_8(9, "1.8.8-R0.1-SNAPSHOT"),
  MC_1_9_0(11, "1.9-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_9_2(13, "1.9.2-R0.1-SNAPSHOT"),
  MC_1_9_4(15, "1.9.4-R0.1-SNAPSHOT"),
  MC_1_10_0(16, "1.10-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_10_2(18, "1.10.2-R0.1-SNAPSHOT"),
  MC_1_11_0(19, "1.11-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_11_1(20, "1.11.1-R0.1-SNAPSHOT"),
  MC_1_11_2(21, "1.11.2-R0.1-SNAPSHOT"),
  MC_1_12_0(22, "1.12-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_12_1(23, "1.12.1-R0.1-SNAPSHOT"),
  MC_1_12_2(24, "1.12.2-R0.1-SNAPSHOT"),
  MC_1_13_0(25, "1.13-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_13_1(26, "1.13.1-R0.1-SNAPSHOT"),
  MC_1_13_2(27, "1.13.2-R0.1-SNAPSHOT"),
  MC_1_14_0(28, "1.14-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_14_1(29, "1.14.1-R0.1-SNAPSHOT"),
  MC_1_14_2(30, "1.14.2-R0.1-SNAPSHOT"),
  MC_1_14_3(31, "1.14.3-R0.1-SNAPSHOT"),
  MC_1_14_4(32, "1.14.4-R0.1-SNAPSHOT"),
  MC_1_15_0(33, "1.15-R0.1-SNAPSHOT") {
    public boolean isFullVersion() { return true; }
  },
  MC_1_15_1(34, "1.15.1-R0.1-SNAPSHOT"),
  MC_1_15_2(35, "1.15.2-R0.1-SNAPSHOT")
  ;

  private int id;
  private String bukkitVersion;

  KelpVersion(int id, String bukkitVersion) {
    this.id = id;
    this.bukkitVersion = bukkitVersion;
  }

  public int getId() {
    return id;
  }

  public String getBukkitVersion() {
    return bukkitVersion;
  }

  public boolean isFullVersion() {
    return false;
  }

  public boolean isHigherThan(KelpVersion compare) {
    return higherVersion(this, compare) == this;
  }

  public boolean isHigherThanOrEqualTo(KelpVersion compare) {
    return compare == this || higherVersion(this, compare) == this;
  }

  public boolean isLowerThanOrEqualTo(KelpVersion compare) {
    return compare == this || lowerVersion(this, compare) == this;
  }

  public boolean isLowerThan(KelpVersion compare) {
    return lowerVersion(this, compare) == this;
  }

  public static KelpVersion higherVersion(KelpVersion original, KelpVersion toCompare) {
    return original.getId() > toCompare.getId() ? original : toCompare;
  }

  public static KelpVersion lowerVersion(KelpVersion original, KelpVersion toCompare) {
    return original.getId() > toCompare.getId() ? toCompare : original;
  }

  public static KelpVersion highestVersion() {
    KelpVersion output = KelpVersion.values()[0];
    for (KelpVersion version : KelpVersion.values()) {
      if (version.getId() > output.getId()) {
        output = version;
      }
    }
    return output;
  }

  public static KelpVersion lowestVersion() {
    KelpVersion output = KelpVersion.values()[0];
    for (KelpVersion version : KelpVersion.values()) {
      if (version.getId() < output.getId()) {
        output = version;
      }
    }
    return output;
  }

  public static KelpVersion nextFullVersion(KelpVersion relatedTo) {
    for (KelpVersion value : values()) {
      if (higherVersion(value, relatedTo) == value && value.isFullVersion()) {
        return value;
      }
    }
    return lowestVersion();
  }

  public static KelpVersion highestVersionOf(KelpVersion... versions) {
    KelpVersion output = versions[0];
    for (KelpVersion version : versions) {
      if (higherVersion(output, version) == version) {
        output = version;
      }
    }
    return output;
  }

  public static KelpVersion lowestVersionOf(KelpVersion... versions) {
    KelpVersion output = versions[0];
    for (KelpVersion version : versions) {
      if (lowerVersion(output, version) == version) {
        output = version;
      }
    }
    return output;
  }

  // null if not supported
  public static KelpVersion withBukkitVersion(String bukkitVersion) {
    for (KelpVersion version : KelpVersion.values()) {
      if (version.getBukkitVersion().equalsIgnoreCase(bukkitVersion)) {
        return version;
      }
    }
    return null;
  }

  public static boolean versionImplementationExists(KelpVersion version) {
    KelpVersion[] implementedVersions = new KelpVersion[] {
      KelpVersion.MC_1_8_0,
      KelpVersion.MC_1_8_3,
      KelpVersion.MC_1_8_4,
      KelpVersion.MC_1_8_5,
      KelpVersion.MC_1_8_6,
      KelpVersion.MC_1_8_7,
      KelpVersion.MC_1_8_8
    };
    return Lists.newArrayList(implementedVersions).contains(version);
  }

  public static KelpVersion get(int index) {
    return KelpVersion.values()[index];
  }

}
