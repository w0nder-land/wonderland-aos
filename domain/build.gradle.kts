plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    CoroutineConfig.run {
        implementation(COROUTINES_CORE)
    }

    HiltConfig.run {
        implementation(DAGGER_HILT_CORE)
    }

    TimberConfig.run {
        implementation(TIMBER)
    }
}
