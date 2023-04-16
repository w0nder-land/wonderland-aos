const val okhttpVersion = "4.10.0"
const val coroutineVersion = "1.6.4"
const val junit5Version = "5.9.0"

object GradleConfig {
    const val GRADLE = "com.android.tools.build:gradle:7.2.2"
    const val KTLINT = "org.jlleitschuh.gradle:ktlint-gradle:10.0.0"
    const val ANDROID_JUNIT5 = "de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1"
}

object KotlinConfig {
    private const val kotlinVersion = "1.7.10"

    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
}

object CoroutineConfig {
    const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
}

object AndroidConfig {
    private const val fragmentKTXVersion = "1.5.2"
    private const val splashScreenVersion = "1.0.0"

    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:$fragmentKTXVersion"
    const val CORE_KTX = "androidx.core:core-ktx:1.8.0"
    const val SPLASH_SCREEN = "androidx.core:core-splashscreen:${splashScreenVersion}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.5.0"
    const val MATERIAL = "com.google.android.material:material:1.6.1"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
}

object ViewPager2Indicator {
    private const val indicatorVersion = "4.3"
    const val DOTS_INDICATOR = "com.tbuonomo:dotsindicator:$indicatorVersion"
}

object NavigationConfig {
    private const val navVersion = "2.5.2"
    const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:$navVersion"
    const val UI_KTX = "androidx.navigation:navigation-ui-ktx:$navVersion"
    const val NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN =
        "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
}

object PagingConfig {
    private const val pagingVersion = "3.1.1"
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:$pagingVersion"
    const val PAGING_COMMON = "androidx.paging:paging-common:$pagingVersion"
}

object CoilConfig {
    const val COIL = "io.coil-kt:coil:2.2.0"
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

    const val RETROFIT = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val RETROFIT_CONVERTER = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    const val OKHTTP_BOM = "com.squareup.okhttp3:okhttp-bom:$okhttpVersion"
    const val OKHTTP = "com.squareup.okhttp3:okhttp"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor"
}

object ConverterConfig {
    private const val gsonVersion = "2.9.1"
    const val GSON = "com.google.code.gson:gson:$gsonVersion"

}

object HiltConfig {
    private const val hiltVersion = "2.43.2"

    const val ANDROID_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val ANDROID = "com.google.dagger:hilt-android:$hiltVersion"
    const val COMPILER = "com.google.dagger:hilt-compiler:$hiltVersion"
    const val CORE = "com.google.dagger:hilt-core:$hiltVersion"
}

/**
 * gradle for test
 * */

object UnitTestConfig {
    const val JUNIT = "junit:junit:4.13.2"
    const val JUNIT_JUPITER = "org.junit.jupiter:junit-jupiter:$junit5Version"
    const val JUNIT_VINTAGE_ENGINE = "org.junit.vintage:junit-vintage-engine:$junit5Version"
    const val TRUTH = "com.google.truth:truth:1.1.3"
    const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
    const val MOCKK = "io.mockk:mockk:1.12.2"
    const val MOCK_WEBSERVER = "com.squareup.okhttp3:mockwebserver:$okhttpVersion"
}

object UITestConfig {
    private const val junit5AndroidTestVersion = "1.2.2"

    const val JUNIT_JUPITER_API = "org.junit.jupiter:junit-jupiter-api:$junit5Version"
    const val JUNIT = "androidx.test.ext:junit:1.1.3"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
    const val JUNIT5_CORE = "de.mannodermaus.junit5:android-test-core:$junit5AndroidTestVersion"
    const val JUNIT5_RUNNER = "de.mannodermaus.junit5:android-test-runner:$junit5AndroidTestVersion"
}
