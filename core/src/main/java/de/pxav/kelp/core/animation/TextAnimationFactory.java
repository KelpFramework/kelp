package de.pxav.kelp.core.animation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.common.StringUtils;

/**
 * This factory class is used to produce instances of
 * new {@link TextAnimation}s.
 *
 * This is useful if you fully follow an object-oriented and dependency-
 * injecting design approach. For most cases, it would probably also clean
 * to use static factory methods of the different text animation types such
 * as {@link BuildingTextAnimation}.
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

  public StaticTextAnimation newStaticTextAnimation() {
    return new StaticTextAnimation();
  }

  public StaticTextAnimation newStaticTextAnimation(String text) {
    return new StaticTextAnimation(text);
  }

}
