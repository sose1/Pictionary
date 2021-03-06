import dependencies.Libraries

plugins {
    id("common-android-library")
    kotlin("kapt")
    id("kotlin-android")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Libraries.okHttp)
    implementation(Libraries.retrofit)
    implementation(Libraries.moshi)
    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.kotlinxSerialization)
}