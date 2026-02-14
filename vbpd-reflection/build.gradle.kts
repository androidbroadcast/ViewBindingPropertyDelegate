plugins {
    id("vbpdconfig")
}

android {

    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
        }
    }
}

dependencies {
    // Use compileOnly dependencies because usage
    // ViewBindingPropertyDelegate without adding them in the project
    compileOnly(libs.androidx.fragment)
    compileOnly(libs.androidx.recyclerview)
    compileOnly(libs.androidx.activity)
    api(projects.vbpd)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.androidx.core)
    testImplementation(libs.test.androidx.runner)
    testImplementation(libs.androidx.fragment)
    testImplementation(libs.androidx.activity)
    testImplementation(libs.androidx.recyclerview)
    testImplementation(libs.androidx.appcompat)
}
