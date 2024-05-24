import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.io.path.pathString

plugins {
    id("java-library-convention")
    alias(libs.plugins.paperweight.userdev)
    application
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

val serverVersion = "1.20.6-R0.1-SNAPSHOT"

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

val paperweightJars = collectJarFiles(Path.of("$projectDir/runtime-dependencies"))

dependencies {
    paperweightJars.forEach { implementation(files(it.pathString)) }
}

fun collectJarFiles(path: Path): List<Path> {
    if (!path.toFile().exists()) return emptyList()
    Files.walk(path).use { walk ->
        return walk.filter(Files::isRegularFile)
            .filter { p -> p.toString().endsWith(".jar") }
            .filter { p -> !p.pathString.contains("setupCache") }
            .collect(Collectors.toList())
    }
}
