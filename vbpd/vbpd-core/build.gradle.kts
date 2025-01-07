plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.library)
}

version = libs.versions.vbpd.version.get()

android {
    namespace = "com.github.kirich1409.viewbindingpropertydelegate"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
        freeCompilerArgs += listOf("-module-name", "com.github.kirich1409.ViewBindingPropertyDelegate.core")
    }

    buildFeatures {
        androidResources = false
    }
}

dependencies {
    compileOnly(libs.androidx.viewbinding)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.annotation)
}

ext.set("groupId", "com.github.kirich1409")
ext.set("artifactId", "viewbindingpropertydelegate-core")

apply(from = rootProject.file("publishing.gradle"))
