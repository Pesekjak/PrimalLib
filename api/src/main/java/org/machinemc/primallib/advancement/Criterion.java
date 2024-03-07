package org.machinemc.primallib.advancement;

import com.google.common.base.Preconditions;
import lombok.With;

import java.time.Instant;
import java.util.Date;

/**
 * Represents a completed advancement criterion.
 *
 * @param name name of the criterion
 * @param achieved when it has been achieved
 */
public record Criterion(String name, @With Date achieved) {

    public Criterion {
        Preconditions.checkNotNull(name, "Name of criterion can not be null");
        Preconditions.checkNotNull(achieved, "Date of achieving can not be null");
    }

    /**
     * Returns criterion with current achieving date.
     *
     * @param name name of the criterion
     * @return criterion
     */
    public static Criterion current(String name) {
        return new Criterion(name, Date.from(Instant.now()));
    }

    /**
     * Returns criterion with minimal achieving date.
     *
     * @param name name of the criterion
     * @return criterion
     */
    public static Criterion old(String name) {
        return new Criterion(name, Date.from(Instant.EPOCH));
    }

}
