import com.android.utils.TraceUtils.simpleId
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.library)
    `maven-publish`
    alias(libs.plugins.vanniktechMavenPublish)
}

version = libs.versions.vbpd.version.get()
group = "dev.androidbroadcast.vbpd"

val libraryId = "dev.androidbroadcast.vbpd.core"

android {
    namespace = libraryId
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        aarMetadata {
            minCompileSdk = libs.versions.android.minSdk.get().toInt()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
        freeCompilerArgs += listOf("-module-name", libraryId)
    }
}

kotlin {
    explicitApi()
}

dependencies {
    api(libs.androidx.viewbinding)
    implementation(libs.androidx.annotation)
}

publishing {
    publications {
        repositories {
            mavenLocal()

            maven(url = uri(rootProject.layout.buildDirectory.file("maven-repo"))) {
                name = "BuildDir"
            }
        }
    }
}

mavenPublishing {
    // Публикация в https://central.sonatype.com/
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(
        groupId = project.group.toString(),
        artifactId = project.name,
        version = project.version.toString(),
    )

    pom {
        name = "ViewBindingPropertyDelegate"
        url = "https://github.com/androidbroadcast/ViewBindingPropertyDelegate"
        description = "Make work with Android View Binding simpler"

        scm {
            connection = "scm:git:git://github.com/androidbroadcast/ViewBindingPropertyDelegate.git"
            developerConnection = "scm:git:ssh://github.com/androidbroadcast/ViewBindingPropertyDelegate.git"
            url = "https://github.com/androidbroadcast/ViewBindingPropertyDelegate"
            tag = libs.versions.vbpd.version.get()
        }

        ciManagement {
            system = "GitHub Actions"
            url = "https://github.com/androidbroadcast/ViewBindingPropertyDelegate/actions"
        }

        issueManagement {
            system = "GitHub"
            url = "https://github.com/androidbroadcast/ViewBindingPropertyDelegate/issues"
        }

        developers {
            developer {
                id = "kirich1409"
                name = "Kirill Rozov"
                email = "kirill@androidbroadcast.dev"
            }
        }

        licenses {
            license {
                name = "Apache License 2.0"
                url = "https://github.com/androidbroadcast/ViewBindingPropertyDelegate/blob/master/LICENSE.md"
            }
        }
    }
}

