import dependencies.Libraries
import extensions.kapt

plugins {
    id("common-android-library")
    kotlin("kapt")
}

dependencies {
    kapt(Libraries.databinding)

    implementation(Libraries.core)
    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.recyclerView)
    implementation(Libraries.material)

}