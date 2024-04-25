plugins {
    id("java-library-convention")
    application
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

    implementation(libs.google.gson)
    implementation(libs.javapoet)
    implementation(libs.paper.api)

    implementation(libs.jetbrains.annotations) // for annotating generated classes
}

application {
    mainClass.set("org.machinemc.primallib.generator.metadata.CodeGenerator")
}
