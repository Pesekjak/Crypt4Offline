plugins {
    id("java-library-convention")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    annotationProcessor(libs.velocity.api)
    compileOnly(files("../libs/velocity-3.3.0-SNAPSHOT-392.jar"))
}