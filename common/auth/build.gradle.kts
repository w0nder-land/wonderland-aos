plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "${rootProject.rootDir.absolutePath}/config_module.gradle")
android {
    namespace = "com.wonder.auth"
}

dependencies {
    KakaoConfig.run {
        implementation(KAKAO_LOGIN)
    }

    TimberConfig.run {
        implementation(TIMBER)
    }
}
