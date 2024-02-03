import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    idea
    id("com.github.ben-manes.versions") version "0.51.0"
    kotlin("jvm") version "1.9.22"
}

group = "ru.timakden"
version = "1.0"

repositories {
    mavenCentral()
}

val kotestVersion = "5.8.0"
val tinylogVersion = "2.7.0"

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("org.tinylog:tinylog-api-kotlin:$tinylogVersion")
    implementation("org.tinylog:tinylog-impl:$tinylogVersion")

    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

kotlin {
    jvmToolchain(21)
}

tasks {
    compileKotlin {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    test {
        useJUnitPlatform()
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
