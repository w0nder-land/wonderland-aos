@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
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
