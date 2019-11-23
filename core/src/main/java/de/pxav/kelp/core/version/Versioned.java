package de.pxav.kelp.core.version;

import de.pxav.kelp.core.application.inject.VersionBinderModule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate each version implementation class should be
 * annotated with this annotation so that the {@code VersionBinderModule}
 * recognizes this class as an implementation class.
 *
 * @author pxav
 * @see VersionBinderModule
 * @see de.pxav.kelp.core.application.KelpVersionTemplate
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Versioned {}
