plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("publish")
}

group = "io.github.shabinder"
version = "2.0"

repositories {
    google()
    mavenCentral()
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        publishLibraryVariants("release", "debug")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val appleMain by getting
        val appleTest by getting

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val iosArm64Main by getting
        val iosArm64Test by getting
        
        val iosMain by getting
        val iosSimulatorArm64Main by getting
        val iosSimulatorArm64Test by getting
        val iosTest by getting

        val iosX64Main by getting
        val iosX64Test by getting

        val nativeMain by getting
        val nativeTest by getting
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
