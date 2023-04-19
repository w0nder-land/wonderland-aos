plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.component"

    composeOptions {
        kotlinCompilerExtensionVersion = ComposeConfig.composeCompilerVersion
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(Module.CommonResource))

    HiltConfig.run {
        api(DAGGER_HILT_ANDROID)
        api(HILT_NAVIGATION_COMPOSE)
    }

    ComposeConfig.run {
        api(COMPOSE_UI)
        api(COMPOSE_FOUNDATION)
        api(COMPOSE_MATERIAL3)
        api(COMPOSE_MATERIAL)
        api(COMPOSE_MATERIAL_CORE)
        api(COMPOSE_MATERIAL_ICON)
        api(COMPOSE_UI_TOOL)
        api(COMPOSE_UI_PREVIEW)
        api(COMPOSE_ANIMATION)
        api(COMPOSE_FOUNDATION_LAYOUT)
        api(COMPOSE_COIL)
        api(COMPOSE_ACTIVITY)
        api(COMPOSE_MATERIAL3)
        api(COMPOSE_NAVIGATION)
        api(COMPOSE_NAVIGATION_ANIMATION)
    }

    TimberConfig.run {
        implementation(TIMBER)
    }
}
