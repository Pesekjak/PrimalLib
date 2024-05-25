plugins {
    java
    `java-library`
}

val libs = project.rootProject
    .extensions
    .getByType(VersionCatalogsExtension::class)
    .named("libs")

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":implementation:commons"))
    implementation(project(":implementation:shared"))
    listOf(
        "v1_20_6"
    ).forEach {
        implementation(project(":implementation:$it"))
    }

    compileOnly(libs.findLibrary("paper-api").get())
    compileOnly(libs.findLibrary("adventure-nbt").get())
}
