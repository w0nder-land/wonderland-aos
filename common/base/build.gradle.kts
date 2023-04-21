plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.base"
}

dependencies {
    AndroidConfig.run {
        implementation(LIFECYCLE_VIEWMODEL_KTX)
    }

    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
        implementation(COROUTINES_ANDROID)
    }

    TimberConfig.run {
        implementation(TIMBER)
    }
}
