plugins {
    id("vbpdconfig")
}

dependencies {
    // Use compileOnly dependencies because usage
    // ViewBindingPropertyDelegate without adding them in the project
    compileOnly(libs.androidx.fragment)
    compileOnly(libs.androidx.activity)
    compileOnly(libs.androidx.recyclerview)
    api(projects.vbpdCore)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.androidx.core)
    testImplementation(libs.test.androidx.runner)
    testImplementation(libs.test.fragment)
    testImplementation(libs.androidx.fragment)
    testImplementation(libs.androidx.activity)
    testImplementation(libs.androidx.recyclerview)
    testImplementation(libs.androidx.appcompat)
}
