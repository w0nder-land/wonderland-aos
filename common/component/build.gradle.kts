@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.component"

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildFeatures {
        compose = true
    }
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
