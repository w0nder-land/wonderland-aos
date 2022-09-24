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

    implementation(CoroutineConfig.CORE)
    implementation(PagingConfig.PAGING_COMMON)

    NetworkConfig.run {
        implementation(RETROFIT)
        implementation(RETROFIT_CONVERTER)
        implementation(platform(OKHTTP_BOM))
        implementation(OKHTTP)
        implementation(OKHTTP_LOGGING_INTERCEPTOR)
    }

    implementation(ConverterConfig.GSON)

    HiltConfig.run {
        implementation(CORE)
        kapt(COMPILER)
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
