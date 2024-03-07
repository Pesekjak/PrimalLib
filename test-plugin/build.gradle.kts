plugins {
    id("java-library-convention")
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":implementation:commons"))
    implementation(project(":implementation:shared"))
    listOf(
        "v1_20_4"
    ).forEach {
        implementation(project(":implementation:$it", "reobf"))
    }

    compileOnly(libs.paper.api)
}
