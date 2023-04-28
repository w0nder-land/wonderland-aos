plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.feature.festival"

    composeOptions {
        kotlinCompilerExtensionVersion = ComposeConfig.composeCompilerVersion
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

    HiltConfig.run {
        kapt(DAGGER_HILT_COMPILER)
    }
}
