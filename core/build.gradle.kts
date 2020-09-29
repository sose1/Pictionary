import dependencies.Libraries

plugins {
    id("common-android-library")
    kotlin("kapt")
    id("kotlin-android")
}

dependencies {
    implementation(Libraries.okHttp)
    implementation(Libraries.gson)
    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
}