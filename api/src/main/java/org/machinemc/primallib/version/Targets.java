package org.machinemc.primallib.version;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to mark parts of code that targets only some Minecraft versions.
 * <p>
 * The purpose of this annotation is to mark classes (or their parts) that are required
 * only for some Minecraft versions, so it is easier for developers to support
 * multiple versions at the same time.
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface Targets {

    /**
     * The first supported protocol version by the annotated element.
     * <p>
     * If no version is specified, it is assumed the {@link ProtocolVersion#oldest()} is supported.
     *
     * @return the first supported version
     */
    ProtocolVersion from() default ProtocolVersion.UNKNOWN;

    /**
     * The last supported protocol version by the annotated element.
     * <p>
     * If no version is specified, it is assumed the {@link ProtocolVersion#latest()} is supported.
     *
     * @return the last supported version
     */
    ProtocolVersion to() default ProtocolVersion.UNKNOWN;

    /**
     * Utils for the {@link Targets} annotation.
     */
    final class Utils {

        private Utils() {
            throw new UnsupportedOperationException();
        }

        /**
         * Returns an actual protocol version from targets annotation.
         *
         * @param targets targets annotation
         * @return the earliest supported version
         */
        public static ProtocolVersion getFrom(Targets targets) {
            return targets.from() == ProtocolVersion.UNKNOWN
                    ? ProtocolVersion.oldest()
                    : targets.from();
        }

        /**
         * Returns an actual protocol version from targets annotation.
         *
         * @param targets targets annotation
         * @return the latest supported version
         */
        public static ProtocolVersion getTo(Targets targets) {
            return targets.to() == ProtocolVersion.UNKNOWN
                    ? ProtocolVersion.latest()
                    : targets.to();
        }

    }

}
