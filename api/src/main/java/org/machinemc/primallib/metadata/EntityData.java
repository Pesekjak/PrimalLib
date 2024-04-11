package org.machinemc.primallib.metadata;

import com.google.common.base.Preconditions;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.machinemc.primallib.version.UsedByGenerators;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents packed entity metadata.
 * <p>
 * Does not contain information about the mob type, but just
 * the data mapped to their numerical ID values.
 */
@UsedByGenerators
@NoArgsConstructor
public final class EntityData {

    private final Map<Field<?>, Object> map = new TreeMap<>();

    /**
     * Creates copy of provided entity data.
     *
     * @param entityData entity data
     */
    public EntityData(EntityData entityData) {
        map.putAll(entityData.map);
    }

    /**
     * Returns unmodifiable map view of this entity data.
     *
     * @return map view
     */
    public @UnmodifiableView Map<Field<?>, Object> getMap() {
        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns value from this data or provides the default value if there is no
     * value using the data provided from the field.
     * <p>
     * This can be used to easily access individual values using entity data field constants.
     *
     * @param field field
     * @return value
     * @param <T> value type
     */
    public <T> @Nullable T get(Field<T> field) {
        Preconditions.checkNotNull(field, "Field can not be null");
        if (!field.isValid()) return null;
        Object o = map.get(field);
        if (o == null) return null;
        Class<T> type = field.serializer.getType();
        if (!type.isAssignableFrom(o.getClass())) return null;
        return type.cast(o);
    }

    /**
     * Returns value from this data from an index and serializer.
     *
     * @param index index
     * @param serializer serializer
     * @return value
     * @param <T> value type
     */
    public <T> @Nullable T get(int index, Serializer<T> serializer) {
        return get(new Field<>(index, serializer));
    }

    /**
     * Sets a new value for this entity data from given field.
     *
     * @param field new value
     */
    public <T> void set(Field<T> field, @Nullable T o) {
        Preconditions.checkNotNull(field, "Field can not be null");
        if (!field.isValid()) return;
        if (o == null) {
            map.remove(field);
            return;
        }
        map.put(field, o);
    }

    /**
     * Sets new value for this entity data.
     *
     * @param index index of the value
     * @param serializer serializer for the value
     * @param value new value
     * @param <T> value type
     */
    public <T> void set(int index, Serializer<T> serializer, T value) {
        set(new Field<>(index, serializer), value);
    }

    /**
     * Represents metadata field applicable to an entity.
     * <p>
     * Different entity types have different data fields.
     *
     * @param name name of the field
     * @param index index of the data
     * @param serializer used serializer
     * @param <T> value type
     */
    @UsedByGenerators
    public record Field<T>(@Nullable String name, int index, Serializer<T> serializer) implements Comparable<Field<?>> {

        public static <T> Field<T> invalid() {
            return new Field<>("INVALID", -1, null);
        }

        public Field(int index, Serializer<T> serializer) {
            this(null, index, serializer);
        }

        public Field {
            if (index >= 0) Preconditions.checkNotNull(serializer, "Serializer can not be null");
        }

        @Override
        public Serializer<T> serializer() {
            return Preconditions.checkNotNull(serializer, "Invalid field");
        }

        @Override
        public int compareTo(@NotNull EntityData.Field<?> o) {
            return Integer.compare(index, o.index);
        }

        /**
         * Returns whether the field is valid for the version of currently running server.
         *
         * @return whether the field is valid
         */
        public boolean isValid() {
            return index >= 0;
        }

    }

}
