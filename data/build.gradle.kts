plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.data"
}

dependencies {
    implementation(project(Module.Domain))

    KotlinConfig.run {
        implementation(KOTLIN_STDLIB)
    }

    HiltConfig.run {
        implementation(DAGGER_HILT_ANDROID)
        kapt(DAGGER_HILT_COMPILER)
    }

    NetworkConfig.run {
        implementation(RETROFIT)
        implementation(RETROFIT_CONVERTER)
        api(OKHTTP_BOM)
        implementation(OKHTTP)
        implementation(OKHTTP_LOGGING_INTERCEPTOR)
    }

    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
    }

    TimberConfig.run {
        implementation(TIMBER)
    }
}
