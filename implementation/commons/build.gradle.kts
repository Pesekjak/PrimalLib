plugins {
    id("java-library-convention")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    compileOnly(libs.google.guava)
    compileOnly(libs.google.gson)
    compileOnly(libs.netty)
    compileOnly(libs.fastutil)

    compileOnly(libs.paper.api)
    compileOnly(libs.adventure.nbt)
}
