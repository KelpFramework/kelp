package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.version.KelpVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks all potion effect types that exist by
 * default in the Minecraft game. On server startup, the
 * {@link de.pxav.kelp.core.application.inject.VersionBinderModule kelp binder}
 * searches for all classes with this annotation and checks if they
 * are available in the current server version using the {@link #since()} method.
 *
 * If the effect type is not available it will check if there is an
 * implementation to emulate this effect and eventually bind it, so that
 * it can be used as if it existed natively in the game. If there also
 * is no implementation, the effect is unavailable for the current server
 * version.
 *
 * If you create a custom effect, you should not annotate it with
 * this class.
 *
 * @author pxav
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MinecraftPotion {

  /**
   * Gets the version since when the effect type exists
   * in the game. This can be used to determine whether
   * the effect type is available on the current server
   * version.
   *
   * @return The version since when the effect type exists in the game.
   */
  KelpVersion since();

}
