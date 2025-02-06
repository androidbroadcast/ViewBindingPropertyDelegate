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
}
