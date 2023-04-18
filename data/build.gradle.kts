plugins {
    id("kotlin")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":domain"))

    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
    }

    PagingConfig.run {
        implementation(PAGING_COMMON)
    }

    HiltConfig.run {
        implementation(DAGGER_HILT_CORE)
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
