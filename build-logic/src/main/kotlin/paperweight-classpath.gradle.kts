import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.io.path.pathString

plugins {
    java
    `java-library`
}

val paperweightJars = collectJarFiles(Path.of("$projectDir/.gradle/caches/paperweight"))

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
