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

    implementation(libs.google.guava)
    implementation(libs.google.gson)
    implementation(libs.netty)
    implementation(libs.fastutil)

    implementation(libs.paper.api)
    implementation(libs.adventure.nbt)
}
