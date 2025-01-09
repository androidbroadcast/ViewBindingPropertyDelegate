plugins {
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.vanniktechMavenPublish) apply false
}

//ext {
//    preDexEnabled = "true" != System.getenv("PRE_DEX_DISABLED")
//}
