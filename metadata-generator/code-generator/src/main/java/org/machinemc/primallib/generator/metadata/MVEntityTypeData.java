package org.machinemc.primallib.generator.metadata;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.machinemc.primallib.version.ProtocolVersion;

import java.util.*;

/**
 * Represents entity type data containing information about multiple
 * different implementations of the entity type data across different
 * Minecraft versions.
 */
public final class MVEntityTypeData {

    @Getter
    private final String original;
    @Getter
    private final String relocated;

    private final Set<String> parents = new TreeSet<>();

    private final Map<String, Field> fields = new HashMap<>();

    public MVEntityTypeData(String yarn) {
        original = yarn;
        relocated = renamePackage(yarn);
    }

    /**
     * Adds parent to this entity type data.
     *
     * @param yarn yarn class name of the parent
     */
    public void addParent(String yarn) {
        parents.add(renamePackage(yarn));
    }

    /**
     * Returns list of all fields for this entity data in sorted order.
     *
     * @return sorted fields
     */
    public List<Field> getSortedFields() {
        List<Field> fields = new ArrayList<>(this.fields.values());
        Collections.sort(fields);
        return fields;
    }

    /**
     * Returns sorted list of all parent entity types.
     *
     * @return sorted parent types
     */
    public List<String> getSortedParents() {
        List<String> parents = new ArrayList<>(this.parents);
        Collections.sort(parents);
        return parents;
    }

    /**
     * Returns name with given name of creates a new one in case
     * there is none.
     *
     * @param name name of the field
     * @return field with given name
     */
    public Field getField(String name) {
        return fields.computeIfAbsent(name, Field::new);
    }

    /**
     * Replaces the Minecraft package with PrimalLib's.
     *
     * @param yarn yarn class name
     * @return primal lib class name
     */
    public static String renamePackage(String yarn) {
        String relocated = yarn.replaceFirst("net/minecraft/entity", "org/machinemc/primallib/metadata/model");
        int innerIndex = relocated.lastIndexOf('$');
        if (innerIndex == -1) return relocated;
        return relocated.substring(0, relocated.lastIndexOf('/')) + "/" + relocated.substring(innerIndex + 1);
    }

    /**
     * Represents a field with different mappings across multiple versions.
     */
    @Getter
    @RequiredArgsConstructor
    public static final class Field implements Comparable<Field> {

        private final String name;
        private final Map<ProtocolVersion, Integer> idMapping = new TreeMap<>();
        private final Map<ProtocolVersion, String> serializerMapping = new TreeMap<>();

        /**
         * Inserts new data bound to a protocol version to this field.
         *
         * @param version version
         * @param mapping index of the data
         * @param serializer serializer
         */
        public void insertVersionData(ProtocolVersion version, int mapping, String serializer) {
            idMapping.put(version, mapping);
            serializerMapping.put(version, serializer);
        }

        /**
         * Checks whether this field has different indices across different
         * version implementations.
         *
         * @return true if the field has multiple indices
         */
        public boolean hasMultipleIndices() {
            return new HashSet<>(idMapping.values()).size() > 1;
        }

        /**
         * Returns the index for this field if the field has only one index applicable.
         *
         * @return index of the field
         */
        public int getIndex() {
            Preconditions.checkState(!hasMultipleIndices());
            return idMapping.values().iterator().next();
        }

        /**
         * Checks whether this field supports multiple types and because of that
         * has to be generated with wildcard serializer.
         *
         * @return true if the field supports multiple types across different
         * version implementation.
         */
        public boolean supportsMultipleTypes() {
            return new HashSet<>(serializerMapping.values()).size() > 1;
        }

        /**
         * Returns the serializer for this field if the field has only one serializer applicable.
         *
         * @return serializer of the field
         */
        public String getSerializer() {
            Preconditions.checkState(!supportsMultipleTypes());
            return serializerMapping.values().iterator().next();
        }

        /**
         * Compares whether implementation between two protocol versions are the same.
         *
         * @param first first version
         * @param second second version
         * @return whether the implementation is the same
         */
        public boolean isTheSame(ProtocolVersion first, ProtocolVersion second) {
            return Objects.equals(idMapping.get(first), idMapping.get(second))
                    && Objects.equals(serializerMapping.get(first), serializerMapping.get(second));
        }

        @Override
        public int compareTo(@NotNull MVEntityTypeData.Field o) {
            if (idMapping.isEmpty() || o.idMapping.isEmpty()) return name.compareTo(o.name);
            return Integer.compare(
                    idMapping.values().iterator().next(),
                    o.idMapping.values().iterator().next()
            );
        }

    }

}
