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
    implementation(project(":common:component"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-ui"))
    implementation(project(":feature-onboarding"))

    AndroidConfig.run {
        implementation(CORE_KTX)
        implementation(APPCOMPAT)
        implementation(SPLASH_SCREEN)
        implementation(LIFECYCLE_VIEWMODEL_KTX)
        implementation(LIFECYCLE_VIEWMODEL_COMPOSE)
        implementation(LIFECYCLE_EXTENSIONS)
        implementation(CONSTRAINT_LAYOUT)
        implementation(MATERIAL)
    }

    ComposeConfig.run {
        implementation(COMPOSE_MATERIAL3)
        implementation(COMPOSE_NAVIGATION)
        implementation(COMPOSE_NAVIGATION_ANIMATION)
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
