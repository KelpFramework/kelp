package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractArrowEntity;

// subtype of abstract arrow
public interface SpectralArrowEntity extends AbstractArrowEntity<SpectralArrowEntity> {

  int getGlowingTicks();

  SpectralArrowEntity setGlowingTicks(int durationInTicks);

}
