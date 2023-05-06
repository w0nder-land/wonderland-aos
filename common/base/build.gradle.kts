@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.base"
}

dependencies {

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.timber)
}
