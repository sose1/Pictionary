import dependencies.Libraries
import extensions.kapt

plugins {
    id("common-android-library")
    kotlin("kapt")
}

dependencies {
    implementation(Libraries.recyclerView)

    kapt(Libraries.databinding)
}