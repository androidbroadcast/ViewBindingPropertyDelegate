import com.vanniktech.maven.publish.SonatypeHost
import dev.androidbroadcast.vbpd.gradle.libs
import dev.androidbroadcast.vbpd.gradle.mavenPublishingConfig
import dev.androidbroadcast.vbpd.gradle.vanniktechMavenPublishingConfig
import java.util.Properties

// generate Kotlin function read from local.properties
private fun readProperties(): Properties {
    val localProperties = Properties()
    project.rootProject.file("local.properties").inputStream().use(localProperties::load)
    return localProperties
}

val properties = readProperties()
project.extra["signing.keyId"] = properties.getProperty("signing.keyId")
project.extra["signing.secretKeyRingFile"] = properties.getProperty("signing.secretKeyRingFile")
project.extra["signing.password"] = properties.getProperty("signing.password")

project.extra["mavenCentralUsername"] = properties.getProperty("mavenCentralUsername")
project.extra["mavenCentralPassword"] = properties.getProperty("mavenCentralPassword")

mavenPublishingConfig {
    publications {
        repositories {
            mavenLocal()

            maven(url = uri(rootProject.layout.buildDirectory.file("maven-repo"))) {
                name = "BuildDir"
            }
        }
    }
}

vanniktechMavenPublishingConfig {
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
            connection = "scm:git:https://github.com/androidbroadcast/ViewBindingPropertyDelegate.git"
            developerConnection = "scm:git:git@github.com:androidbroadcast/ViewBindingPropertyDelegate.git"
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

        organization {
            name = "Android Broadcast"
            url = "https://github.com/androidbroadcast"
        }
    }
}
