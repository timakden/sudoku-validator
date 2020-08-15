import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    id("com.github.ben-manes.versions") version "0.29.0"
    kotlin("jvm") version "1.3.72"
}

group = "ru.timakden"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.1.3") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.1.3") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-property-jvm:4.1.3") // for kotest property test
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
