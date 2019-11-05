package de.pxav.kelp.core.animation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.common.StringUtils;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public final class TextAnimationFactory {

  private StringUtils stringUtils;

  @Inject
  public TextAnimationFactory(StringUtils stringUtils) {
    this.stringUtils = stringUtils;
  }

  public BuildingTextAnimation newBuildingTextAnimation() {
    return new BuildingTextAnimation(stringUtils);
  }

}
