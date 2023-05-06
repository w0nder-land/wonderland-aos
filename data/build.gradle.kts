@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

applyModuleConfig()
android {
    namespace = "com.wonder.data"
}

dependencies {
    implementation(project(Module.CommonModel))
    implementation(project(Module.Domain))

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.coroutines.core)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    api(libs.okhttp.bom)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.room.runtime)
    implementation(libs.room.common)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.timber)
}
