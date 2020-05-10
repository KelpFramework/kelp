package de.pxav.kelp.core.application;

import de.pxav.kelp.core.application.inject.VersionBinderModule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to annotate each class which
 * represents a new version template.
 *
 * Version templates are usually abstract classes which
 * have abstracts methods that execute version-specific
 * code (for example packet handling).
 *
 * Kelp has so-called {@code implementation modules} which
 * implement these template classes and put the version specific
 * code in them. These modules get compiled and the server
 * administrator drags them into the kelp version directory.
 *
 * On each server startup kelp scans the jar files and checks
 * which version they implement. If the version matches the
 * current server version it will load the classes to the
 * classpath of the main plugin and bind them to the template
 * class.
 *
 * This allows developers of the kelp core module to depend on
 * the template classes and simply let them inject by Guice,
 * because of this technique.
 *
 * Please note that template classes which are not annotated
 * with this annotation will be ignored in the binding process
 * and there will be no version-specific code for them. So
 * always put this annotation on top of each version template class.
 *
 * @author pxav
 * @see VersionBinderModule
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface KelpVersionTemplate {}
