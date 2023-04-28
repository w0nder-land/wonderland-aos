plugins {
    id("com.android.library")
    kotlin("android")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.model"
}

dependencies {
    implementation(project(Module.CommonComponent))
    implementation(project(Module.Domain))
}
