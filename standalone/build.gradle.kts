plugins {
    id("java-library-convention")
    id("primal-lib-plugin")
    alias(libs.plugins.shadow)
}

tasks {

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName = "PrimalLib"
        archiveClassifier = ""
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }

}
