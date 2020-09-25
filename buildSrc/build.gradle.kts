plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

gradlePlugin {
    plugins {
        register("common-android-dynamic-feature") {
            id = "common-android-dynamic-feature"
            implementationClass = "plugins.AndroidDynamicFeaturePlugin"
        }
        register("common-android-library") {
            id = "common-android-library"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("common-kotlin-library") {
            id = "common-kotlin-library"
            implementationClass = "plugins.KotlinLibraryPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
}
