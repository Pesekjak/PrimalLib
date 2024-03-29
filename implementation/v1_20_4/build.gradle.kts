plugins {
    id("java-library-convention")
    alias(libs.plugins.paperweight.userdev)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

val serverVersion = "1.20.4-R0.1-SNAPSHOT"

dependencies {
    implementation(project(":api"))
    implementation(project(":implementation:commons"))

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    paperweight.paperDevBundle(serverVersion)
    compileOnly(libs.adventure.nbt)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}
