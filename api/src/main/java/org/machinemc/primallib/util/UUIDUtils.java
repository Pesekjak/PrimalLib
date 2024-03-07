package org.machinemc.primallib.util;

import com.google.common.base.Preconditions;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class related to operations with uuids.
 */
public final class UUIDUtils {

    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^(\\p{XDigit}{8})-?"
                    + "(\\p{XDigit}{4})-?"
                    + "(\\p{XDigit}{4})-?"
                    + "(\\p{XDigit}{4})-?"
                    + "(\\p{XDigit}{12})$");
    private static final @RegExp String DASHES_UUID_REPLACE = "$1-$2-$3-$4-$5";

    private UUIDUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts an int array into a UUID.
     *
     * @param integers The integers (must be an array of 4 integers)
     * @return The UUID
     */
    public static UUID uuidFromIntArray(final int[] integers) {
        Preconditions.checkArgument(integers.length == 4, "The length of integer array must be 4");
        return new UUID(
                (long) integers[0] << 32 | (long) integers[1] & 4294967295L,
                (long) integers[2] << 32 | (long) integers[3] & 4294967295L
        );
    }

    /**
     * Converts a UUID into an int array of 4 integers.
     *
     * @param uuid The UUID
     * @return The int array
     */
    public static int[] uuidToIntArray(final UUID uuid) {
        final long most = uuid.getMostSignificantBits();
        final long least = uuid.getLeastSignificantBits();
        return leastMostToIntArray(most, least);
    }

    /**
     * Converts the most and least bits of a UUID into an int array.
     *
     * @param most The most bits of a UUID
     * @param least The least bits of a UUID
     * @return The int array
     */
    private static int[] leastMostToIntArray(final long most, final long least) {
        return new int[]{(int) (most >> 32), (int) most, (int) (least >> 32), (int) least};
    }

    /**
     * Parses uuid string both with or without dashes to a classic uuid.
     *
     * @param string the string uuid
     * @return parsed uuid
     */
    public static Optional<UUID> parseUUID(final @Nullable String string) {
        if (string == null) return Optional.empty();
        final Matcher matcher = UUID_PATTERN.matcher(string);
        if (!matcher.matches()) return Optional.empty();
        return Optional.of(UUID.fromString(matcher.replaceFirst(DASHES_UUID_REPLACE)));
    }

}
