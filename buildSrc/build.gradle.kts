plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
    implementation(libs.javapoet)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
