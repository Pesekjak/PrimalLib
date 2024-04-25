package org.machinemc.primallib.generator.metadata;

import com.squareup.javapoet.*;
import org.jetbrains.annotations.Nullable;
import org.machinemc.primallib.metadata.EntityData;
import org.machinemc.primallib.metadata.Serializer;
import org.machinemc.primallib.version.ProtocolVersion;

import javax.lang.model.element.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Util class for generating code for individual entity data fields.
 *
 * @param field entity data field
 */
public record FieldCodeGenerator(MVEntityTypeData.Field field) {

    /**
     * Creates field specification for this field.
     *
     * @return field specification
     */
    public FieldSpec createFieldSpec(@Nullable CodeBlock initializer, boolean javaDocs, Modifier... modifiers) throws Exception {
        TypeName type;
        if (!field.supportsMultipleTypes()) {
            Type serializerType = ((ParameterizedType) Serializer.class.getField(field.getSerializer().toUpperCase()).getGenericType()).getActualTypeArguments()[0];
            type = ParameterizedTypeName.get(EntityData.Field.class, serializerType);
        } else {
            TypeName wildcard = WildcardTypeName.subtypeOf(Object.class);
            ClassName cls = ClassName.get(EntityData.Field.class);
            type = ParameterizedTypeName.get(cls, wildcard);
        }

        FieldSpec.Builder fieldSpec = FieldSpec.builder(type, field.getName())
                .addModifiers(modifiers);

        if (initializer != null) fieldSpec.initializer(initializer);
        if (!javaDocs) return fieldSpec.build();

        fieldSpec.addJavadoc("Metadata field for $L state of the entity", field.getName());

        if (field.supportsMultipleTypes()) {
            fieldSpec.addJavadoc("\n<p> \nThis field uses different serializers across different Minecraft versions");
            String last = null;
            for (ProtocolVersion version : field.getSerializerMapping().keySet()) {
                String next = field.getSerializerMapping().get(version);
                if (Objects.equals(last, next)) continue;
                fieldSpec.addJavadoc(
                        "\n<p> for {@link $T#$L} and above: {@link $T#$L}",
                        ProtocolVersion.class, version.name(), Serializer.class, next.toUpperCase());
                last = next;
            }
        }

        return fieldSpec.build();
    }

    /**
     * Create code block for initializer's static block for this field.
     *
     * @return code block for static block of data initializer
     */
    public CodeBlock createStaticBlock() {
        CodeBlock.Builder code = CodeBlock.builder();

        if (!field.supportsMultipleTypes() && !field.hasMultipleIndices()) {
            code.addStatement(
                     "$L = new $T<>($L, $T.$L)",
                    field.getName(), EntityData.Field.class, field.getIndex(), Serializer.class, field.getSerializer().toUpperCase()
            );
            return code.build();
        }

        List<ProtocolVersion> versions = new ArrayList<>(List.of(ProtocolVersion.values()));
        versions.remove(ProtocolVersion.UNKNOWN);
        Collections.reverse(versions);

        ProtocolVersion last = versions.getFirst();

        for (int i = 0; i < versions.size(); i++) {
            ProtocolVersion current = versions.get(i);

            if (field.isTheSame(current, last)) {
                last = current;
                continue;
            }

            String flow = last == versions.getFirst() ? "if" : "else if";
            code.beginControlFlow(
                    flow + " ($T.get().noLessThan($T.$L))",
                    ProtocolVersion.class, ProtocolVersion.class, last.name()
            );
            fieldStaticInitialize(code, last);
            code.endControlFlow();

            if (current == versions.getLast()) {
                code.beginControlFlow("else");
                fieldStaticInitialize(code, current);
                code.endControlFlow();
            }
        }

        return code.build();
    }

    /**
     * Writes code for the field initialization for given version.
     *
     * @param code code block
     * @param version version
     */
    private void fieldStaticInitialize(CodeBlock.Builder code, ProtocolVersion version) {
        if (field.getIdMapping().containsKey(version) && field.getSerializerMapping().containsKey(version)) {
            fieldMapping(code, version);
        } else {
            invalidField(code);
        }
    }

    /**
     * Writes code for the field initialization using mappings for given version.
     *
     * @param code code block
     * @param version version
     */
    private void fieldMapping(CodeBlock.Builder code, ProtocolVersion version) {
        code.addStatement(
                "$L = new $T<>($L, $T.$L)",
                field.getName(), EntityData.Field.class, field.getIdMapping().get(version), Serializer.class, field.getSerializerMapping().get(version).toUpperCase()
        );
    }

    /**
     * Writes code for invalid field initialization.
     *
     * @param code code block
     */
    private void invalidField(CodeBlock.Builder code) {
        code.addStatement("$L = $T.invalid()", field.getName(), EntityData.Field.class);
    }

}
