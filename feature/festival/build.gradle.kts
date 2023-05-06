@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

applyModuleConfig(isComposeEnable = true)
android {
    namespace = "com.wonder.feature.festival"
}

dependencies {
    implementation(project(Module.CommonBase))
    implementation(project(Module.CommonModel))
    implementation(project(Module.CommonComponent))
    implementation(project(Module.CommonResource))
    implementation(project(Module.Domain))

    kapt(libs.dagger.hilt.compiler)
}
