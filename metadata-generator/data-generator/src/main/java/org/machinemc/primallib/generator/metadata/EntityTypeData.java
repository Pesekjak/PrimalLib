package org.machinemc.primallib.generator.metadata;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a data for single entity type.
 *
 * @param name name of the entity type
 * @param inherits name of the entity type this entity type inherits all data from
 * @param fields fields specific for this entity type
 */
public record EntityTypeData(String name, @Nullable String inherits, List<NamedField> fields) {

    /**
     * Deserializes the entity data type from json object.
     *
     * @param json json object
     * @return entity type data
     * @see EntityTypeData#serialize()
     */
    public static EntityTypeData deserialize(JsonObject json) {
        String name = json.getAsJsonPrimitive("name").getAsString();
        String inherits = json.has("inherits")
                ? json.get("inherits").getAsString()
                : null;
        List<NamedField> fields = new ArrayList<>();
        json.getAsJsonArray("fields").asList().stream()
                .map(JsonElement::getAsJsonObject)
                .map(jsonObject -> {
                    String fieldName = jsonObject.has("name")
                            ? jsonObject.getAsJsonPrimitive("name").getAsString()
                            : null;
                    int index = jsonObject.getAsJsonPrimitive("index").getAsInt();
                    Serializer<?> serializer = Serializer.getByName(jsonObject.getAsJsonPrimitive("serializer").getAsString());
                    return new NamedField(fieldName, new EntityData.Field<>(index, serializer));
                })
                .forEach(fields::add);
        return new EntityTypeData(name, inherits, fields);
    }

    public EntityTypeData(String name, @Nullable String inherits) {
        this(name, inherits, new ArrayList<>());
    }

    public EntityTypeData {
        Preconditions.checkNotNull(name, "Name can not be null");
        Preconditions.checkNotNull(fields, "Fields can not be null");
        fields = new ArrayList<>(fields);
        fields.sort(Comparator.comparingInt(f -> f.field.index()));
    }

    /**
     * Serializes the entity type data as json object.
     *
     * @return json object
     * @see EntityTypeData#deserialize(JsonObject)
     */
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        if (inherits != null) json.addProperty("inherits", inherits);
        JsonArray jsonArray = new JsonArray();
        fields.stream()
                .map(field -> {
                    JsonObject jsonObject = new JsonObject();
                    if (field.name() != null) jsonObject.addProperty("name", field.name());
                    jsonObject.addProperty("index", field.field.index());
                    jsonObject.addProperty("serializer", Serializer.getName(field.field.serializer()));
                    return jsonObject;
                })
                .forEach(jsonArray::add);
        json.add("fields", jsonArray);
        return json;
    }

    /**
     * Represents a named entity data field.
     *
     * @param name name of the field
     * @param field field
     */
    public record NamedField(String name, EntityData.Field<?> field) {

        public NamedField {
            Preconditions.checkNotNull(name, "Name of the field can not be null");
            Preconditions.checkNotNull(field, "Field can not be null");
        }

    }

}
