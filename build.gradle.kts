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
        classpath(HiltConfig.DAGGER_HILT_ADNROID_GRADLE_PLUGIN)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${KotlinConfig.kotlinVersion}")
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
