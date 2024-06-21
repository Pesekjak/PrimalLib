plugins {
    id("java-library-convention")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    listOf(
        "commons", // commons module
        "v1_21"
    ).forEach {
        implementation(project(":implementation:$it"))
    }
    compileOnly(libs.paper.api)
}
