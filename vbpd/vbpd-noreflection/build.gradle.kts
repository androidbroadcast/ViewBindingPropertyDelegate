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
        freeCompilerArgs += listOf("-module-name", "com.github.kirich1409.ViewBindingPropertyDelegate.noreflection")
    }

    buildFeatures {
        androidResources = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    compileOnly(libs.androidx.viewbinding)
    implementation(libs.androidx.recyclerview)
    compileOnly(libs.androidx.annotation)
    compileOnly(libs.androidx.savedstate)
    api(projects.vbpd.vbpdCore)
}

ext {
    set("artifactId", "viewbindingpropertydelegate-noreflection")
    set("groupId", "com.github.kirich1409")
}

apply(from = rootProject.file("publishing.gradle"))
