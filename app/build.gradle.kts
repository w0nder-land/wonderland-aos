plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config.gradle")
android {
    namespace = "com.wonder.wonderland"
}

dependencies {
    implementation(project(Module.CommonBase))
    implementation(project(Module.CommonModel))
    implementation(project(Module.CommonComponent))
    implementation(project(Module.CommonResource))
    implementation(project(Module.Domain))
    implementation(project(Module.Data))
    implementation(project(Module.FeatureSplash))
    implementation(project(Module.FeatureOnboarding))
    implementation(project(Module.FeatureFestival))

    AndroidConfig.run {
        implementation(CORE_KTX)
        implementation(ACTIVITY_KTX)
        implementation(APPCOMPAT)
        implementation(SPLASH_SCREEN)
        implementation(LIFECYCLE_VIEWMODEL_KTX)
        implementation(LIFECYCLE_VIEWMODEL_COMPOSE)
        implementation(LIFECYCLE_EXTENSIONS)
        implementation(CONSTRAINT_LAYOUT)
        implementation(MATERIAL)
    }

    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
        implementation(COROUTINES_ANDROID)
    }

    HiltConfig.run {
        implementation(DAGGER_HILT_ANDROID)
        kapt(DAGGER_HILT_COMPILER)
        implementation(HILT_COMMON)
        kapt(HILT_COMPILER)
        implementation(HILT_NAVIGATION_COMPOSE)
    }

    KakaoConfig.run {
        implementation(KAKAO_LOGIN)
    }

    TimberConfig.run {
        implementation(TIMBER)
    }

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(JUNIT_JUPITER)
        testImplementation(JUNIT_VINTAGE_ENGINE)
        testImplementation(TRUTH)
        testImplementation(MOCKK)
        testImplementation(COROUTINE_TEST)
    }

    UITestConfig.run {
        androidTestImplementation(JUNIT_JUPITER_API)
        androidTestImplementation(JUNIT)
        androidTestImplementation(ESPRESSO_CORE)
        androidTestImplementation(JUNIT5_CORE)
        androidTestRuntimeOnly(JUNIT5_RUNNER)
    }
}
