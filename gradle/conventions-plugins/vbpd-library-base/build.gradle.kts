plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "dev.androidbroadcast.vbpd.gradle"

dependencies {
    implementation(libs.gradleplugins.android)
    implementation(libs.gradleplugins.kotlin)
    implementation(libs.gradleplugins.vanniktechMavenPublish)
    implementation(libs.gradleplugins.detekt)
    implementation(libs.gradleplugins.ktlint)
    implementation(libs.gradleplugins.kover)

    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

java {
    targetCompatibility = JavaVersion.VERSION_21
    sourceCompatibility = JavaVersion.VERSION_21
}
