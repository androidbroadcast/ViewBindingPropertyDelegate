import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("vbpdconfig")
}

dependencies {
    api(libs.androidx.viewbinding)
    implementation(libs.androidx.annotation)
}
