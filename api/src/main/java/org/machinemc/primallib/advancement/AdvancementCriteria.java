package org.machinemc.primallib.advancement;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.bukkit.advancement.Advancement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

/**
 * Represents criteria of an advancement.
 */
public interface AdvancementCriteria {

    /**
     * Returns new single advancement criteria.
     *
     * @return advancement criteria
     */
    static AdvancementCriteria single() {
        return ofAll(1);
    }

    /**
     * Returns new advancement criteria from given criteria data.
     *
     * @param requirements requirements
     * @return advancement criteria
     */
    @SuppressWarnings("unchecked")
    static AdvancementCriteria of(List<List<String>> requirements) {
        return of((Collection<Collection<String>>) (Collection<?>) requirements);
    }

    /**
     * Returns new advancement criteria from given criteria data.
     *
     * @param requirements requirements
     * @return advancement criteria
     */
    static AdvancementCriteria of(Collection<Collection<String>> requirements) {
        List<List<String>> list = requirements.stream()
                .map(r -> (List<String>) ImmutableList.copyOf(r))
                .toList();
        return new AdvancementCriteriaImpl(list);
    }

    /**
     * Returns new advancement criteria with fixed amount of individual requirements.
     *
     * @param required number of required criteria
     * @return advancement criteria
     */
    static AdvancementCriteria ofAll(int required) {
        Set<String> criteria = new HashSet<>();
        for (int i = 0; i < required; i++)
            criteria.add("requirement_" + i);
        return ofAll(criteria);
    }

    /**
     * Returns new advancement criteria where all given requirements and individually
     * required.
     *
     * @param requirements requirements
     * @return advancement criteria
     */
    static AdvancementCriteria ofAll(Collection<String> requirements) {
        List<List<String>> criteria = new ArrayList<>();
        for (String requirement : requirements)
            criteria.add(Collections.singletonList(requirement));
        return new AdvancementCriteriaImpl(criteria);
    }

    /**
     * Returns new advancement criteria with fixed amount of connected requirements.
     *
     * @param required number of required criteria
     * @return advancement criteria
     */
    static AdvancementCriteria ofAny(int required) {
        Set<String> criteria = new HashSet<>();
        for (int i = 0; i < required; i++)
            criteria.add("requirement_" + i);
        return ofAny(criteria);
    }

    /**
     * Returns new advancement criteria where at least one of given requirements is
     * required.
     *
     * @param requirements requirements
     * @return advancement criteria
     */
    static AdvancementCriteria ofAny(Collection<String> requirements) {
        List<String> criteria = new ArrayList<>(requirements);
        return new AdvancementCriteriaImpl(new ArrayList<>(Collections.singleton(criteria)));
    }

    /**
     * Returns advancement criteria for given bukkit advancement.
     *
     * @param bukkit bukkit advancement
     * @return advancement criteria
     * @deprecated Bukkit does not store information about how individual criteria are grouped together
     */
    @Deprecated
    static AdvancementCriteria fromBukkit(Advancement bukkit) {
        Preconditions.checkNotNull(bukkit, "Advancement can not be null");
        return ofAny(bukkit.getCriteria());
    }

    /**
     * Returns number of required criteria.
     *
     * @return required criteria
     */
    int required();

    /**
     * Returns list of all requirements.
     *
     * @return requirements
     */
    @Unmodifiable List<List<String>> requirements();

    /**
     * Returns copy of these criteria with new requirements.
     *
     * @param requirements new requirements
     * @return new criteria
     */
    @Contract(pure = true)
    AdvancementCriteria withRequirements(Collection<Collection<String>> requirements);

}

record AdvancementCriteriaImpl(List<List<String>> requirements) implements AdvancementCriteria {

    AdvancementCriteriaImpl {
        Preconditions.checkNotNull(requirements, "Requirements can not be null");
        requirements = requirements.stream().map(Collections::unmodifiableList).toList();
    }

    @Override
    public int required() {
        return requirements.size();
    }

    @Override
    public AdvancementCriteria withRequirements(Collection<Collection<String>> requirements) {
        return new AdvancementCriteriaImpl(requirements.stream().map(c -> (List<String>) ImmutableList.copyOf(c)).toList());
    }

}
