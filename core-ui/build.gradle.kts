plugins {
    id("com.android.library")
    kotlin("android")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.wonderland.core.ui"
}

dependencies {
    AndroidConfig.run {
        implementation(APPCOMPAT)
    }
}
