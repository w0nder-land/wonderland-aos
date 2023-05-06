@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.resource"
}
