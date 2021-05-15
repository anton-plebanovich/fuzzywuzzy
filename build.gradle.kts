plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("publish")
}

group = "io.github.shabinder"
version = "1.0"

repositories {
    google()
    mavenCentral()
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
                useIR = true
            }
        }
    }
    jvm {
        compilations.all {
            kotlinOptions{
                jvmTarget = "1.8"
                useIR = true
            }
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(BOTH) {
        browser {
            testTask {
                useMocha {
                    timeout = "30000"
                }
            }
        }
        nodejs {
            testTask {
                useMocha {
                    timeout = "30000"
                }
            }
        }
    }
    ios()
    macosX64()
    mingwX64()
    linuxX64()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
            dependencies {}
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val iosMain by getting
        val iosTest by getting

        val mingwX64Main by getting
        val macosX64Main by getting
        val linuxX64Main by getting
        configure(listOf(iosMain,mingwX64Main, macosX64Main, linuxX64Main)) {
            dependsOn(nativeMain)
        }

        val mingwX64Test by getting
        val macosX64Test by getting
        val linuxX64Test by getting
        configure(listOf(iosTest, macosX64Test, linuxX64Test)) {
            dependsOn(nativeMain)
        }
    }
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
