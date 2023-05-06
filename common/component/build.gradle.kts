@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

applyModuleConfig(isComposeEnable = true)
android {
    namespace = "com.wonder.component"
}

dependencies {
    implementation(project(Module.CommonResource))

    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.activity.compose)
    api(libs.androidx.navigation.compose)

    api(libs.dagger.hilt.android)
    api(libs.hilt.navigation.compose)

    api(libs.compose.animation)
    api(libs.compose.ui)
    api(libs.compose.ui.tooling)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.foundation)
    api(libs.compose.foundation.layout)
    api(libs.compose.material)
    api(libs.compose.material.icons.core)
    api(libs.compose.material.icons.extended)
    api(libs.compose.material3)

    api(libs.accompanist.navigation.animation)

    api(libs.coil.compose)

    api(libs.snapper)

    api(libs.timber)
}
