plugins {
    id("java-library-convention")
    id("paperweight-classpath")
    alias(libs.plugins.paperweight.userdev)
    application
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

val serverVersion = "1.20.4-R0.1-SNAPSHOT"

dependencies {
    implementation(project(":api"))

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.fabric.mappingio)

    paperweight.paperDevBundle(serverVersion)

    implementation(libs.paper.api)
    implementation(libs.asm)
    implementation(libs.adventure.nbt)
}

application {
    mainClass.set("org.machinemc.primallib.generator.metadata.DataGenerator")
}
