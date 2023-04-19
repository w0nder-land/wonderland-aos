plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.wonderland.cache"
}

dependencies {
    implementation(project(Module.Data))

    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
    }

    PagingConfig.run {
        implementation(PAGING_COMMON)
    }

    RoomConfig.run {
        implementation(ROOM_RUNTIME)
        kapt(ROOM_COMPILER)
        implementation(ROOM_KTX)
        implementation(ROOM_PAGING)
    }

    ConverterConfig.run {
        implementation(GSON)
    }

    HiltConfig.run {
        implementation(DAGGER_HILT_ANDROID)
        kapt(DAGGER_HILT_COMPILER)
    }

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(JUNIT_JUPITER)
        testImplementation(JUNIT_VINTAGE_ENGINE)
        testImplementation(TRUTH)
        testImplementation(COROUTINE_TEST)
        testImplementation(MOCKK)
        testImplementation(MOCK_WEBSERVER)
    }
}
