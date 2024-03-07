package org.machinemc.primallib.version;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Used to mark services or events that are dependant for each different protocol version.
 * <p>
 * The purpose of this annotation is to mark classes that need specific implementation
 * for each different version, so they can be easily tracked.
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface VersionDependant {
}
