import dev.androidbroadcast.vbpd.gradle.androidLibraryConfig
import dev.androidbroadcast.vbpd.gradle.javaVersion
import dev.androidbroadcast.vbpd.gradle.kotlinConfig
import dev.androidbroadcast.vbpd.gradle.kotlinOptions
import dev.androidbroadcast.vbpd.gradle.libs

version = libs.versions.vbpd.version.get()
group = "dev.androidbroadcast.vbpd"

plugins.apply(libs.plugins.jetbrains.kotlin.android.get().pluginId)
plugins.apply(libs.plugins.android.library.get().pluginId)
plugins.apply("maven-publish")
plugins.apply(libs.plugins.vanniktechMavenPublish.get().pluginId)
plugins.apply("vbpdpublish")

val libraryId = "${project.group}.${project.name.replace("vbpd-", "").replace("-", ".")}"

androidLibraryConfig {
    namespace = libraryId
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        aarMetadata {
            minCompileSdk = libs.versions.android.minSdk.get().toInt()
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        val javaVersion = libs.javaVersion()
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
        freeCompilerArgs += listOf("-module-name", libraryId)
    }
}

kotlinConfig {
    jvmToolchain(libs.versions.jvmTarget.get().toInt())
    explicitApi()
}
