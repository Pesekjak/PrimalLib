package org.machinemc.primallib.version;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Used to mark classes that are used by code generators.
 * <p>
 * These classes can not run many parts of the server APIs because the context within
 * code generators is very limited.
 * <p>
 * It is important to use only classes of PrimalLib API marked with this annotation
 * within generators, otherwise exceptions that are very difficult (or impossible?)
 * to debug may occur. All classes newly used within the generators need to be marked
 * with this annotation and their usage needs to be specified with {@link UsedByGenerators#value()}
 * if the class can access parts of the API unavailable within the generation context.
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface UsedByGenerators {

    /**
     * Specification of how the generators can use the part of the API.
     *
     * @return usage specification
     */
    String value() default "";

}
