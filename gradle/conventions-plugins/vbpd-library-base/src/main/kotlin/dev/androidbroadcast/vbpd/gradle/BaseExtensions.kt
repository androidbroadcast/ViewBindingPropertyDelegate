package dev.androidbroadcast.vbpd.gradle

import com.android.build.api.dsl.LibraryExtension
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

private val Project.androidLibraryExtension: LibraryExtension
    get() {
        return extensions.findByType(LibraryExtension::class)
            ?: error(
                "\"Project.androidLibraryExtension\" value may be called only from" +
                        " android library gradle script"
            )
    }

fun Project.androidLibraryConfig(block: LibraryExtension.() -> Unit): Unit = block(androidLibraryExtension)

fun Project.kotlinConfig(configure: Action<KotlinAndroidProjectExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlin", configure)

@Suppress("DEPRECATION")
fun LibraryExtension.kotlinOptions(configure: Action<org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", configure)

val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

fun Project.vanniktechMavenPublishingConfig(configure: Action<MavenPublishBaseExtension>) {
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("mavenPublishing", configure)
}

fun Project.mavenPublishingConfig(configure: Action<PublishingExtension>) {
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("publishing", configure)
}
