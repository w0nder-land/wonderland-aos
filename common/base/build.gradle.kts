@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

applyModuleConfig()
android {
    namespace = "com.wonder.base"
}

dependencies {

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.timber)
}
