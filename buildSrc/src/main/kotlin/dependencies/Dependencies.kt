package dependencies

object Releases {
    const val applicationId = "pl.sose1.pictionary"

    const val versionCode = 1
    const val versionName = "0.1"

    const val minSdk = 24
    const val compileSdk = 29
    const val targetSdk = 29
}

private object Versions {
    const val kotlin = "1.4.10"
    const val kotlinxSerialization = "1.0.0"

    const val coroutines = "1.3.8"
    const val navigation = "2.3.0"
    const val timber = "4.7.1"
    const val koin = "2.1.5"
    const val core = "1.3.1"
    const val constraintLayout = "2.0.0-beta8"
    const val coordinatorLayout = "1.1.0"
    const val databindingCompiler = "4.0.1"
    const val appCompat = "1.1.0"
    const val material = "1.1.0"
    const val recyclerView = "1.1.0"
    const val okHttp = "4.9.0"
}

object Libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    //android
    const val core = "androidx.core:core-ktx:${Versions.core}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayout}"
    const val databinding = "androidx.databinding:databinding-compiler:${Versions.databindingCompiler}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"


    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    //others
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
}

/*object TestLibraries {
    const val junit = "junit:junit:4.12"
    const val testRunner = "com.android.support.test:runner:1.0.2"
    const val mockk = "io.mockk:mockk:1.9"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.2.1"
}*/
