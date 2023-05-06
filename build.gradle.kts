plugins {
    id(libs.plugins.android.application.get().pluginId) apply false
    id(libs.plugins.android.library.get().pluginId) apply false
    id(libs.plugins.kotlin.android.get().pluginId) apply false
    id(libs.plugins.kotlin.kapt.get().pluginId) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ktlint) apply false
    base
}
