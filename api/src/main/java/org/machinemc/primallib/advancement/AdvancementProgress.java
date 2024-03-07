package org.machinemc.primallib.advancement;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.time.Instant;
import java.util.*;

/**
 * Represents progress of an advancement.
 */
public interface AdvancementProgress {

    /**
     * Returns advancement progress from set of requirements and completed requirements.
     *
     * @param completed completed requirements
     * @param requirements requirements
     * @return progress
     */
    static AdvancementProgress from(Collection<Criterion> completed, Collection<String> requirements) {
        return new AdvancementProgressImpl(new HashSet<>(completed), new ArrayList<>(requirements));
    }

    /**
     * Returns advancement progress from criteria and completed requirements.
     *
     * @param completed completed requirements
     * @param criteria criteria
     * @return progress
     */
    static AdvancementProgress from(Collection<Criterion> completed, AdvancementCriteria criteria) {
        List<String> requirements = new ArrayList<>();
        criteria.requirements().forEach(requirements::addAll);
        return from(completed, requirements);
    }

    /**
     * Returns advancement progress from criteria with given number of completed
     * requirements.
     *
     * @param criteria criteria
     * @param completed number of completed requirements
     * @param date date of the criteria completion
     * @return progress
     */
    static AdvancementProgress from(AdvancementCriteria criteria, int completed, Date date) {
        List<String> requirements = new ArrayList<>();
        criteria.requirements().forEach(requirements::addAll);
        AdvancementProgress progress = new AdvancementProgressImpl(Collections.emptySet(), requirements);
        return progress.withCompleted(completed, date);
    }

    /**
     * Converts bukkit advancement progress.
     *
     * @param bukkit bukkit advancement progress
     * @return progress
     * @deprecated Bukkit does not store information about how individual criteria are grouped together
     */
    @Deprecated
    static AdvancementProgress fromBukkit(org.bukkit.advancement.AdvancementProgress bukkit) {
        Preconditions.checkNotNull(bukkit, "Advancement progress can not be null");
        var completed = bukkit.getAwardedCriteria();
        var required = new ArrayList<>(completed);
        required.addAll(bukkit.getRemainingCriteria());
        List<Criterion> criteria = completed.stream()
                .map(c -> new Criterion(c, bukkit.getDateAwarded(c)))
                .toList();
        return from(new HashSet<>(criteria), AdvancementCriteria.ofAny(required));
    }

    /**
     * Returns number of already completed criteria.
     *
     * @return completed criteria
     */
    int completed();

    /**
     * Returns list of all completed requirements.
     *
     * @return completed requirements
     */
    @Unmodifiable Set<Criterion> completedCriteria();

    /**
     * Returns copy of this progress with new number of completed
     * criteria.
     *
     * @param completed number of completed criteria
     * @return copy
     */
    @Contract(pure = true)
    AdvancementProgress withCompleted(int completed, Date date);

    /**
     * Returns copy of this progress with new completed
     * criteria.
     *
     * @param completed completed criteria
     * @return copy
     */
    @Contract(pure = true)
    AdvancementProgress withCompleted(Collection<Criterion> completed);

    /**
     * Returns copy of this progress that is fully completed.
     *
     * @return completed copy
     */
    @Contract(pure = true)
    AdvancementProgress fullyCompleted(Date date);

    /**
     * Returns copy of this progress that already has been completed
     * long time ago and won't display toast when sent to player.
     *
     * @return completed copy
     */
    @Contract(pure = true)
    default AdvancementProgress alreadyFullyCompleted() {
        return fullyCompleted(Date.from(Instant.EPOCH));
    }

    /**
     * Returns copy of this progress that has been recently
     * completed and will display toast when sent to player.
     *
     * @return completed copy
     */
    @Contract(pure = true)
    default AdvancementProgress nowFullyCompleted() {
        return fullyCompleted(Date.from(Instant.now()));
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
    @Unmodifiable List<String> requirements();

    /**
     * Returns copy of these criteria with new requirements.
     *
     * @param requirements new requirements
     * @return new criteria
     */
    @Contract(pure = true)
    AdvancementProgress withRequirements(Collection<String> requirements);

}

record AdvancementProgressImpl(Set<Criterion> completedCriteria, List<String> requirements) implements AdvancementProgress {

    AdvancementProgressImpl {
        Preconditions.checkNotNull(completedCriteria, "Completed requirements can not be null");
        Preconditions.checkNotNull(requirements, "Requirements can not be null");
        completedCriteria = Collections.unmodifiableSet(completedCriteria);
        requirements = Collections.unmodifiableList(requirements);
    }

    @Override
    public int completed() {
        return completedCriteria.size();
    }

    @Override
    public AdvancementProgress withCompleted(int completed, Date date) {
        if (completed == 0) return withCompleted(Collections.emptyList());
        completed = Math.max(completed, required());
        List<Criterion> criteria = requirements.subList(0, completed).stream()
                .map(requirement -> new Criterion(requirement, date))
                .toList();
        return withCompleted(criteria);
    }

    @Override
    public AdvancementProgress withCompleted(Collection<Criterion> completed) {
        return new AdvancementProgressImpl(new HashSet<>(completed), requirements);
    }

    @Override
    public AdvancementProgress fullyCompleted(Date date) {
        return withCompleted(completed(), date);
    }

    @Override
    public int required() {
        return requirements.size();
    }

    @Override
    public AdvancementProgress withRequirements(Collection<String> requirements) {
        return new AdvancementProgressImpl(completedCriteria, new ArrayList<>(requirements));
    }

}
