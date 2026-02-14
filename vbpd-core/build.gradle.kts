plugins {
    id("vbpdconfig")
}

dependencies {
    api(libs.androidx.viewbinding)
    implementation(libs.androidx.annotation)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.androidx.core)
}
