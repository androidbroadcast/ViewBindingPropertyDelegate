import dev.androidbroadcast.vbpd.gradle.androidLibraryConfig
import dev.androidbroadcast.vbpd.gradle.kotlinConfig
import dev.androidbroadcast.vbpd.gradle.kotlinOptions
import dev.androidbroadcast.vbpd.gradle.libs

version = libs.versions.vbpd.get()
group = "dev.androidbroadcast.vbpd"

plugins.apply(libs.plugins.jetbrains.kotlin.android.get().pluginId)
plugins.apply(libs.plugins.android.library.get().pluginId)
plugins.apply("maven-publish")
plugins.apply(libs.plugins.vanniktechMavenPublish.get().pluginId)
plugins.apply("vbpdpublish")
plugins.apply(libs.plugins.detekt.get().pluginId)
plugins.apply(libs.plugins.ktlint.get().pluginId)
plugins.apply(libs.plugins.kover.get().pluginId)

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
        val javaVersion = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
        freeCompilerArgs += listOf("-module-name", libraryId)
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

kotlinConfig {
    jvmToolchain(libs.versions.jvmTarget.get().toInt())
    explicitApi()
}

extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    config.setFrom(rootProject.files("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    parallel = true
}
