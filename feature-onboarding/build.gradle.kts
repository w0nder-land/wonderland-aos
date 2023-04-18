plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.wonderland.feature.onboarding"
}

dependencies {

    implementation(project(":core-ui"))
    implementation(project(":core-designsystem"))

    AndroidConfig.run {
        implementation(CORE_KTX)
        implementation(APPCOMPAT)
        implementation(CONSTRAINT_LAYOUT)
    }
}
