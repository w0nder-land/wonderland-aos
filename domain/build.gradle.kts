plugins {
    id("kotlin")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    HiltConfig.run {
        implementation(DAGGER_HILT_CORE)
        kapt(DAGGER_HILT_COMPILER)
    }

    RoomConfig.run {
        implementation(ROOM_COMMON)
    }

    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
    }

    PagingConfig.run {
        implementation(PAGING_COMMON)
    }

    UnitTestConfig.run {
        testImplementation(JUNIT)
        testImplementation(JUNIT_JUPITER)
        testImplementation(JUNIT_VINTAGE_ENGINE)
        testImplementation(TRUTH)
        testImplementation(COROUTINE_TEST)
        testImplementation(MOCKK)
    }
}
