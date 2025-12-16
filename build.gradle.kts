import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24

plugins {
    idea
    alias(libs.plugins.versions)
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.timakden"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.tinylog)

    testImplementation(libs.bundles.kotest)
}

kotlin {
    jvmToolchain(24)
}

java {
    toolchain {
        targetCompatibility = JavaVersion.VERSION_24
    }
}

tasks {
    compileKotlin {
        compilerOptions {
            jvmTarget.set(JVM_24)
        }
    }
    test {
        useJUnitPlatform()
    }
    wrapper {
        gradleVersion = "9.2.1"
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
