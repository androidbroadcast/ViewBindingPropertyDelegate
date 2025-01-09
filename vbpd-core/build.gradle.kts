import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.library)
    `maven-publish`
    alias(libs.plugins.vanniktechMavenPublish)
}

version = libs.versions.vbpd.version.get()
group = "dev.androidbroadcast.vbpd"

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
        groupId = "dev.androidbroadcast.vbpd",
        artifactId = "vbpd-core",
        version = libs.versions.vbpd.version.get(),
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

