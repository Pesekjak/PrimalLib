package org.machinemc.primallib.internal;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Util for operations related to classes.
 */
public final class ClassUtils {

    private ClassUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns all classes that are assignable from given source
     * class.
     *
     * @param source source class
     * @return all its parent classes
     */
    public static List<Class<?>> collectParents(@Nullable Class<?> source) {
        if (source == null) return Collections.emptyList();
        List<Class<?>> all = new ArrayList<>();
        all.add(source);
        all.addAll(collectParents(source.getSuperclass()));
        Arrays.stream(source.getInterfaces()).forEach(i -> all.addAll(collectParents(i)));
        return all.stream().distinct().toList();
    }

}
