import dependencies.*

plugins {
    id("common-android-library")
    kotlin("kapt")
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

    kapt(Libraries.databinding)
}