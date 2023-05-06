@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.feature.festival"

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(Module.CommonBase))
    implementation(project(Module.CommonModel))
    implementation(project(Module.CommonComponent))
    implementation(project(Module.CommonResource))
    implementation(project(Module.Domain))

    kapt(libs.dagger.hilt.compiler)
}
