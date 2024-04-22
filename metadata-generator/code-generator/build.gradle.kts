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

    implementation(libs.javapoet)
    implementation(libs.paper.api)
}

application {
    mainClass.set("org.machinemc.primallib.generator.metadata.CodeGenerator")
}
