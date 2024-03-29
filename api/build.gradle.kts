plugins {
    id("java-library-convention")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    compileOnly(libs.google.guava)
    compileOnly(libs.google.gson)
    compileOnly(libs.netty)
    compileOnly(libs.fastutil)

    compileOnly(libs.paper.api)
    implementation(libs.adventure.nbt) // is not part of paper server

    testImplementation(libs.mockbukkit)
}
