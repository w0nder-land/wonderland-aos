// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        GradleConfig.run {
            classpath(GRADLE)
            classpath(KTLINT)
            classpath(ANDROID_JUNIT5)
        }

        classpath(KotlinConfig.KOTLIN_GRADLE_PLUGIN)
        classpath(HiltConfig.ANDROID_GRADLE_PLUGIN)
        classpath(NavigationConfig.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
