package de.pxav.kelp.core.entity.util;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.version.KelpVersion;

import java.util.Collections;
import java.util.List;

public enum CatType {

  TABBY,
  TUXEDO,
  RED,
  SIAMESE,
  BRITISH_SHORTHAIR,
  CALICO,
  PERSIAN,
  RAGDOLL,
  WHITE,
  JELLIE,
  ALL_BLACK;

  public static CatType randomCatType() {
    List<CatType> availableTypes = Lists.newArrayList();
    availableTypes.add(CatType.ALL_BLACK);
    availableTypes.add(CatType.RED);
    availableTypes.add(CatType.SIAMESE);
    if (KelpServer.getVersion().isHigherThanOrEqualTo(KelpVersion.MC_1_14_0)) {
      availableTypes.add(CatType.TABBY);
      availableTypes.add(CatType.TUXEDO);
      availableTypes.add(CatType.BRITISH_SHORTHAIR);
      availableTypes.add(CatType.CALICO);
      availableTypes.add(CatType.PERSIAN);
      availableTypes.add(CatType.RAGDOLL);
      availableTypes.add(CatType.WHITE);
      availableTypes.add(CatType.JELLIE);
    }
    Collections.shuffle(availableTypes);
    return availableTypes.get(0);
  }

}
