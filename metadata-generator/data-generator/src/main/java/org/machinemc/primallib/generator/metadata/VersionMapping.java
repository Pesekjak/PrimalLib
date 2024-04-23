package org.machinemc.primallib.generator.metadata;

import com.google.common.base.Preconditions;
import net.fabricmc.mappingio.tree.MappingTree;
import net.fabricmc.mappingio.tree.VisitableMappingTree;

/**
 * Represents mappings for a server version.
 *
 * @param yarn yarn mappings
 * @param vanilla vanilla mappings
 */
public record VersionMapping(VisitableMappingTree yarn, VisitableMappingTree vanilla) {

    /**
     * Returns class entry for this mapping from vanilla name.
     *
     * @param vanilla vanilla name
     * @return entry
     */
    public Entry fromVanilla(String vanilla) {
        vanilla = vanilla.replace('.', '/');
        MappingTree.ClassMapping yarnMapping = yarn.getClass(vanilla);
        Preconditions.checkNotNull(yarnMapping, "There is no class for vanilla name '" + vanilla + "'");
        return fromYarnMapping(yarnMapping);
    }

    /**
     * Returns class entry for this mapping from intermediary name.
     *
     * @param intermediary intermediary name
     * @return entry
     */
    public Entry fromIntermediary(String intermediary) {
        intermediary = intermediary.replace('.', '/');
        MappingTree.ClassMapping yarnMapping = yarn.getClass(intermediary, 0);
        Preconditions.checkNotNull(yarnMapping, "There is no class for intermediary name '" + intermediary + "'");
        return fromYarnMapping(yarnMapping);
    }

    /**
     * Returns class entry for this mapping from yarn name.
     *
     * @param yarn yarn name
     * @return entry
     */
    public Entry fromYarn(String yarn) {
        yarn = yarn.replace('.', '/');
        MappingTree.ClassMapping yarnMapping = this.yarn.getClass(yarn, 1);
        Preconditions.checkNotNull(yarnMapping, "There is no class for yarn name '" + yarn + "'");
        return fromYarnMapping(yarnMapping);
    }

    /**
     * Returns class entry for this mapping from mojang name.
     *
     * @param mojMap mojang name
     * @return entry
     */
    public Entry fromMojMap(String mojMap) {
        mojMap = mojMap.replace('.', '/');
        MappingTree.ClassMapping mojangMapping = vanilla.getClass(mojMap);
        Preconditions.checkNotNull(mojangMapping, "There is no class for yarn name '" + yarn + "'");
        return fromVanilla(mojangMapping.getName(0));
    }

    /**
     * Represents a class entry of a version mapping.
     *
     * @param vanilla vanilla name of the class
     * @param intermediary intermediary name of the class
     * @param yarn yarn name of the class
     * @param mojMap mojang name of the class
     */
    public record Entry(String vanilla, String intermediary, String yarn, String mojMap) {

        public Entry {
            Preconditions.checkNotNull(vanilla);
            Preconditions.checkNotNull(intermediary);
            Preconditions.checkNotNull(yarn);
            Preconditions.checkNotNull(mojMap);
        }

    }

    private Entry fromYarnMapping(MappingTree.ClassMapping yarnMapping) {
        MappingTree.ClassMapping mojMapping = vanilla.getClass(yarnMapping.getSrcName(), 0);
        Preconditions.checkNotNull(mojMapping, "Mojang mapping is null for " + yarnMapping.getSrcName());
        return new Entry(
                yarnMapping.getSrcName(),
                yarnMapping.getName(0),
                yarnMapping.getName(1),
                mojMapping.getSrcName()
        );
    }

}
