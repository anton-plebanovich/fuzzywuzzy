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

kotlin {
    android()
    jvm()
    macosX64()
    iosArm32()
    iosArm64()
    iosX64()
    watchosArm32()
    watchosArm64()
    watchosX86()
    watchosX64()
    watchosDeviceArm64()
    tvosArm64()
    tvosX64()

    macosArm64()
    iosSimulatorArm64()
    watchosSimulatorArm64()
    tvosSimulatorArm64()
    watchosDeviceArm64()

    mingwX64()
    mingwX86()
    linuxX64()
    linuxArm32Hfp()
    linuxMips32()

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    val commonMain by sourceSets.getting
    val commonTest by sourceSets.getting

    val commonJvmMain by sourceSets.creating {
        dependsOn(commonMain)
    }
    val commonJvmTest by sourceSets.creating {
        dependsOn(commonTest)
        dependsOn(commonJvmMain)
    }

    val jvmMain by sourceSets.getting {
        dependsOn(commonMain)
    }
    val jvmTest by sourceSets.getting {
        dependsOn(commonTest)
    }

    val nativeCommonMain by sourceSets.creating
    nativeCommonMain.dependsOn(commonMain)
    val nativeCommonTest by sourceSets.creating
    nativeCommonTest.dependsOn(commonTest)

    val darwinMain by sourceSets.creating
    darwinMain.dependsOn(nativeCommonMain)

    val pthreadMain by sourceSets.creating
    pthreadMain.dependsOn(nativeCommonMain)

    val mingwMain by sourceSets.creating
    mingwMain.dependsOn(nativeCommonMain)

    val pthreadAndroidMain by sourceSets.creating
    pthreadAndroidMain.dependsOn(nativeCommonMain)

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().all {
        val mainSourceSet = compilations.getByName("main").defaultSourceSet
        val testSourceSet = compilations.getByName("test").defaultSourceSet

        mainSourceSet.dependsOn(when {
            konanTarget.family.isAppleFamily -> darwinMain
            konanTarget.family == org.jetbrains.kotlin.konan.target.Family.LINUX -> pthreadMain
            konanTarget.family == org.jetbrains.kotlin.konan.target.Family.MINGW -> mingwMain
            konanTarget.family == org.jetbrains.kotlin.konan.target.Family.ANDROID -> pthreadAndroidMain
            else -> nativeCommonMain
        })

        testSourceSet.dependsOn(nativeCommonTest)
    }

    commonMain.dependencies {}

    commonTest.dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}
