object GradleConfig {
    const val GRADLE = "com.android.tools.build:gradle:7.4.2"
    const val KTLINT = "org.jlleitschuh.gradle:ktlint-gradle:10.0.0"
    const val ANDROID_JUNIT5 = "de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1"
}

object KotlinConfig {
    const val kotlinVersion = "1.8.0"

    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
}

object AndroidConfig {
    private const val coreVersion = "1.8.0"
    private const val activityKtxVersion = "1.6.1"
    private const val splashScreenVersion = "1.0.0"
    private const val lifecycleVersion = "2.4.1"
    private const val lifecycleExtensionVersion = "2.2.0"
    private const val materialVersion = "1.6.0-alpha02"
    private const val constraintLayoutVersion = "2.1.3"

    const val CORE_KTX = "androidx.core:core-ktx:$coreVersion"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:$activityKtxVersion"
    const val SPLASH_SCREEN = "androidx.core:core-splashscreen:$splashScreenVersion"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.5.0"

    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val LIFECYCLE_VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$lifecycleExtensionVersion"
    const val LIFECYCLE_COMPOSE = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"

    const val MATERIAL = "com.google.android.material:material:$materialVersion"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
}

object ComposeConfig {
    const val composeVersion = "1.4.0"
    const val composeCompilerVersion = "1.4.0"
    const val composeMaterial3Version = "1.1.0-alpha01"
    const val composeNavigationVersion = "2.5.2"
    const val composeActivityVersion = "1.4.0"
    const val composeCoilVersion = "2.2.2"
    const val accompanistVersion = "0.30.0"

    const val COMPOSE_UI = "androidx.compose.ui:ui:$composeVersion"
    const val COMPOSE_UI_TOOL = "androidx.compose.ui:ui-tooling:$composeVersion"
    const val COMPOSE_UI_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:$composeActivityVersion"
    const val COMPOSE_RUNTIME = "androidx.compose.runtime:runtime:$composeVersion"
    const val COMPOSE_FOUNDATION_LAYOUT = "androidx.compose.foundation:foundation-layout:$composeVersion"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:$composeVersion"
    const val COMPOSE_MATERIAL_CORE = "androidx.compose.material:material-icons-core:$composeVersion"
    const val COMPOSE_MATERIAL_ICON = "androidx.compose.material:material-icons-extended:$composeVersion"
    const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:$composeVersion"
    const val COMPOSE_ANIMATION = "androidx.compose.animation:animation:$composeVersion"
    const val COMPOSE_COIL = "io.coil-kt:coil-compose:$composeCoilVersion"

    const val COMPOSE_MATERIAL3 = "androidx.compose.material3:material3:$composeMaterial3Version"
    const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:$composeNavigationVersion"
    const val COMPOSE_NAVIGATION_ANIMATION = "com.google.accompanist:accompanist-navigation-animation:$accompanistVersion"
}

object CoroutineConfig {
    const val coroutineVersion = "1.6.4"

    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
}

object PagingConfig {
    private const val pagingVersion = "3.1.1"
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:$pagingVersion"
    const val PAGING_COMMON = "androidx.paging:paging-common:$pagingVersion"
}

object RoomConfig {
    private const val roomVersion = "2.4.3"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:$roomVersion"
    const val ROOM_COMMON = "androidx.room:room-common:$roomVersion"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$roomVersion"
    const val ROOM_KTX = "androidx.room:room-ktx:$roomVersion"
    const val ROOM_PAGING = "androidx.room:room-paging:$roomVersion"
}

object NetworkConfig {
    private const val retrofitVersion = "2.9.0"
    const val okhttpVersion = "4.9.1"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val RETROFIT_CONVERTER = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    const val OKHTTP_BOM = "com.squareup.okhttp3:okhttp-bom:$okhttpVersion"
    const val OKHTTP = "com.squareup.okhttp3:okhttp"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor"
}

object HiltConfig {
    const val daggerHiltVersion = "2.44.2"
    const val hiltVersion = "1.0.0"

    const val DAGGER_HILT_ADNROID_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$daggerHiltVersion"
    const val DAGGER_HILT_CORE = "com.google.dagger:hilt-core:$daggerHiltVersion"
    const val DAGGER_HILT_ANDROID = "com.google.dagger:hilt-android:$daggerHiltVersion"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-compiler:$daggerHiltVersion"
    const val HILT_COMMON = "androidx.hilt:hilt-common:$hiltVersion"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:$hiltVersion"
    const val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:$hiltVersion"
}

object TimberConfig {
    private const val timberVersion = "4.7.1"

    const val TIMBER = "com.jakewharton.timber:timber:$timberVersion"
}

object ExternalLibConfig {
    private const val snapperVersion = "0.3.0"
    private const val bottomSheetDialogVersion = "1.2.1"

    const val SNAPPER = "dev.chrisbanes.snapper:snapper:$snapperVersion"
    const val BOTTOM_SHEET_DIALOG = "com.holix.android:bottomsheetdialog-compose:$bottomSheetDialogVersion"
}

/**
 * gradle for test
 * */

object UnitTestConfig {
    const val junit5Version = "5.9.0"

    const val JUNIT = "junit:junit:4.13.2"
    const val JUNIT_JUPITER = "org.junit.jupiter:junit-jupiter:$junit5Version"
    const val JUNIT_VINTAGE_ENGINE = "org.junit.vintage:junit-vintage-engine:$junit5Version"
    const val TRUTH = "com.google.truth:truth:1.1.3"
    const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${CoroutineConfig.coroutineVersion}"
    const val MOCKK = "io.mockk:mockk:1.12.2"
    const val MOCK_WEBSERVER = "com.squareup.okhttp3:mockwebserver:${NetworkConfig.okhttpVersion}"
}

object UITestConfig {
    private const val junit5AndroidTestVersion = "1.2.2"

    const val JUNIT_JUPITER_API = "org.junit.jupiter:junit-jupiter-api:${UnitTestConfig.junit5Version}"
    const val JUNIT = "androidx.test.ext:junit:1.1.3"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
    const val JUNIT5_CORE = "de.mannodermaus.junit5:android-test-core:$junit5AndroidTestVersion"
    const val JUNIT5_RUNNER = "de.mannodermaus.junit5:android-test-runner:$junit5AndroidTestVersion"
}
