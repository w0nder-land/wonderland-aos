plugins {
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":data"))

    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
    }

    PagingConfig.run {
        implementation(PAGING_COMMON)
    }

    NetworkConfig.run {
        implementation(RETROFIT)
        implementation(RETROFIT_CONVERTER)
        implementation(platform(OKHTTP_BOM))
        implementation(OKHTTP)
        implementation(OKHTTP_LOGGING_INTERCEPTOR)
    }

    ConverterConfig.run {
        implementation(GSON)
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
    }
}
