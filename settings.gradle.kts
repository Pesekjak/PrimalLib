rootProject.name = "PrimalLib"

include("api")
include("standalone")
include("test-plugin")
listOf(
    "commons", // commons module
    "shared", // shared module
    // v stands for version
    "v1_20_4"
).forEach {
    include("implementation:$it")
    findProject(":implementation:$it")?.name = it
}

listOf(
    "code-generator",
    "data-generator"
).forEach {
    include("metadata-generator:$it")
    findProject(":metadata-generator:$it")?.name = it
}

pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    versionCatalogs {

        create("libs") {
            val jetbrainsAnnotations: String by settings
            library("jetbrains-annotations", "org.jetbrains:annotations:$jetbrainsAnnotations")

            val junit: String by settings
            library("junit-api", "org.junit.jupiter:junit-jupiter-api:$junit")
            library("junit-engine", "org.junit.jupiter:junit-jupiter-engine:$junit")
            library("junit-params", "org.junit.jupiter:junit-jupiter-params:$junit")

            val lombok: String by settings
            library("lombok", "org.projectlombok:lombok:$lombok")

            val asm: String by settings
            library("asm", "org.ow2.asm:asm:$asm")

            val googleGuava: String by settings
            library("google-guava", "com.google.guava:guava:$googleGuava")

            val googleGson: String by settings
            library("google-gson", "com.google.code.gson:gson:$googleGson")

            val netty: String by settings
            library("netty", "io.netty:netty-all:$netty")

            val fastUtil: String by settings
            library("fastutil", "it.unimi.dsi:fastutil:$fastUtil")

            val fabricMappingIO: String by settings
            library("fabric-mappingio", "net.fabricmc:mapping-io:$fabricMappingIO")

            val paperApi: String by settings
            library("paper-api", "io.papermc.paper:paper-api:$paperApi")

            val adventure: String by settings
            library("adventure-nbt", "net.kyori:adventure-nbt:$adventure")

            val mockBukkit: String by settings
            library("mockbukkit", "com.github.seeseemelk:MockBukkit-v1.20:$mockBukkit")

            val javaPoet: String by settings
            library("javapoet", "com.squareup:javapoet:$javaPoet")

            val shadow: String by settings
            plugin("shadow", "io.github.goooler.shadow").version(shadow)

            val paperweightUserDev: String by settings
            plugin("paperweight.userdev", "io.papermc.paperweight.userdev").version(paperweightUserDev)
        }

    }
}
