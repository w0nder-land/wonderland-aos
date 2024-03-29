@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ktlint)
}

applyAndroidConfig()
android {
    namespace = "com.wonder.wonderland"
}

dependencies {
    implementation(project(Module.CommonBase))
    implementation(project(Module.CommonModel))
    implementation(project(Module.CommonComponent))
    implementation(project(Module.CommonResource))
    implementation(project(Module.Domain))
    implementation(project(Module.Data))
    implementation(project(Module.FeatureSplash))
    implementation(project(Module.FeatureOnboarding))
    implementation(project(Module.FeatureHome))
    implementation(project(Module.FeatureSearch))
    implementation(project(Module.FeatureCalendar))
    implementation(project(Module.FeatureMy))
    implementation(project(Module.FeatureFestival))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.android.material)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.kakao.user)

    implementation(libs.timber)

    implementation(libs.junit)
    implementation(libs.truth)
    implementation(libs.coroutines.test)
}

ktlint {
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
