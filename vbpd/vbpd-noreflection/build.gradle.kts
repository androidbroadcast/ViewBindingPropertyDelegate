plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.library)
}

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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    compileOnly(libs.androidx.viewbinding)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.recyclerview)
    compileOnly(libs.androidx.annotation)
    compileOnly(libs.androidx.activity)
    compileOnly(libs.androidx.savedstate)
    compileOnly(libs.androidx.lifecycle.viewmodel)
    compileOnly(libs.androidx.lifecycle.runtime)
    api(projects.vbpd.vbpdCore)
}

//ext {
//    groupId = 'com.github.kirich1409'
//    artifactId = "viewbindingpropertydelegate-noreflection"
//}

//apply from: rootProject.file('publishing.gradle')
