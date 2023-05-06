@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
}

applyModuleConfig()
android {
    namespace = "com.wonder.model"
}

dependencies {
    implementation(project(Module.CommonComponent))
    implementation(project(Module.Domain))
}
