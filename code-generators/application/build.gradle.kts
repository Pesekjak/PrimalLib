plugins {
    id("java-library-convention")
    application
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":code-generators:commons"))
    compileOnly(libs.paper.api)
}

application {
    mainClass.set("org.machinemc.primallib.generator.GeneratorApplication")
}
