plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

    implementation(libs.dagger.hilt.core)

    implementation(libs.coroutines.core)

    implementation(libs.timber)
}
