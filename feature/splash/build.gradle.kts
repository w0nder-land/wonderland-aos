plugins {
    id("com.android.library")
    kotlin("android")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.feature.splash"

    composeOptions {
        kotlinCompilerExtensionVersion = ComposeConfig.composeCompilerVersion
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(Module.CommonComponent))
    implementation(project(Module.CommonResource))
}
