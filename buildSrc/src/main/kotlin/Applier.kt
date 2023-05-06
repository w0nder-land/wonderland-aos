@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private val Project.libs get() = the<LibrariesForLibs>()

fun Project.applyBaseConfig() {
    extensions.getByType<BaseExtension>().run {
        compileSdkVersion(libs.versions.compile.sdk.version.get().toInt())

        defaultConfig {
            minSdk = libs.versions.min.sdk.version.get().toInt()
            targetSdk =libs.versions.target.sdk.version.get().toInt()

            vectorDrawables.useSupportLibrary = true

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                languageVersion = JavaVersion.VERSION_1_8.toString()
            }
        }

        variantFilter {
            val buildName = buildType.name
            val flavorName = flavors[0].name

            if (flavorName == "dev" && buildName == "release"
                || flavorName == "prod" && buildName == "debug") {
                ignore = true
            }
        }
    }
}

fun Project.applyComposeConfig() {
    extensions.getByType<BaseExtension>().run {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
        }
    }
}

fun Project.applySigningConfig() {
    extensions.getByType<BaseExtension>().run {
        signingConfigs {
            getByName("debug") {
                storeFile = file(project.rootProject.file(project.properties["DEBUG_KEY_PATH"].toString()))
                storePassword = project.properties["KEYSTORE_PASSWORD"].toString()
                keyAlias = project.properties["DEBUG_KEY_ALIAS"].toString()
                keyPassword = project.properties["KEY_ALIAS_PASSWORD"].toString()
            }
            create("release") {
                storeFile = file(project.rootProject.file(project.properties["RELEASE_KEY_PATH"].toString()))
                storePassword = project.properties["KEYSTORE_PASSWORD"].toString()
                keyAlias = project.properties["RELEASE_KEY_ALIAS"].toString()
                keyPassword = project.properties["KEY_ALIAS_PASSWORD"].toString()
            }
        }
    }
}

fun Project.applyAndroidConfig() {
    extensions.getByType<BaseExtension>().run {
        applyBaseConfig()
        applyComposeConfig()
        applySigningConfig()

        buildTypes {
            getByName("debug") {
                debuggable(true)
                minifyEnabled(false)
                isShrinkResources = false
                proguardFiles(file("${rootProject.rootDir.absolutePath}/proguard-rules.pro"))
                signingConfig = signingConfigs.getByName("debug")
            }
            getByName("release") {
                debuggable(false)
                minifyEnabled(true)
                isShrinkResources = true
                proguardFiles(file("${rootProject.rootDir.absolutePath}/proguard-rules.pro"))
                signingConfig = signingConfigs.getByName("release")
            }
        }

        flavorDimensions("wonder")
        productFlavors {
            create("dev") {
                dimension = "wonder"
                applicationIdSuffix = project.properties["DEBUG_SUFFIX_NAME"].toString()
                versionCode = libs.versions.version.code.debug.get().toInt()
                versionName = libs.versions.version.name.debug.get()
                manifestPlaceholders(mapOf("kakaoNativeAppKey" to project.properties["KAKAO_NATIVE_APP_KEY"].toString()))
            }
            create("prod") {
                dimension = "wonder"
                versionCode = libs.versions.version.code.release.get().toInt()
                versionName = libs.versions.version.name.release.get()
                manifestPlaceholders(mapOf("kakaoNativeAppKey" to project.properties["KAKAO_NATIVE_APP_KEY"].toString()))
            }
        }
    }
}

fun Project.applyModuleConfig(isComposeEnable: Boolean = false) {
    extensions.getByType<LibraryExtension>().run {
        applyBaseConfig()

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(file("${rootProject.rootDir.absolutePath}/proguard-rules.pro"))
            }
        }

        flavorDimensions("wonder")
        productFlavors {
            create("dev") {
                dimension = "wonder"
            }
            create("prod") {
                dimension = "wonder"
            }
        }

        if (isComposeEnable) {
            applyComposeConfig()
        }
    }
}
