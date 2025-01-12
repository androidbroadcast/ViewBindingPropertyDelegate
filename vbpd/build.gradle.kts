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
}
