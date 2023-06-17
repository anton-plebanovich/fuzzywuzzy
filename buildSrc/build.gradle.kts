plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    implementation("com.android.tools.build:gradle:7.4.2")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.8.20")
    implementation("org.jetbrains.dokka:dokka-core:1.8.20")
}
