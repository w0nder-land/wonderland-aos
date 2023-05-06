@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

applyModuleConfig()
android {
    namespace = "com.wonder.auth"
}

dependencies {

    implementation(libs.kakao.user)

    implementation(libs.timber)
}
