import dependencies.Libraries

plugins {
    id("common-android-library")
    kotlin("kapt")
    id("kotlin-android")
}

dependencies {
    implementation(project(":commons:base"))
    implementation(project(":commons:ui"))
    implementation(project(":core"))

    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.material)

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigation)

    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")

    kapt(Libraries.databinding)
}