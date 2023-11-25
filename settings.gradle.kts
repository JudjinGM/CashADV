pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {

            version("activity-ktx", "1.8.1")
            version("androidx-junit", "1.1.5")
            version("appcompat", "1.6.1")
            version("constraintlayout", "2.1.4")
            version("core-ktx", "1.12.0")
            version("espresso-core", "3.5.1")
            version("fragment-ktx", "1.6.2")
            version("glide", "4.15.1")
            version("hilt-android", "2.44")
            version("junit", "4.13.2")
            version("kotlinx-coroutines-android", "1.7.1")
            version("kotlinx-serialization-json", "1.6.1")
            version("lifecycle-viewmodel-ktx", "2.6.2")
            version("material", "1.10.0")
            version("navigation-fragment-ktx", "2.7.5")
            version("okhttp", "4.9.1")
            version("retrofit", "2.9.0")
            version("room-compiler", "2.6.0")
            version("timber", "5.0.1")

            // Core
           library(
               "androidx-core-ktx",
               "androidx.core",
               "core-ktx"
           ).versionRef("core-ktx")

            library(
                "androidx-appcompat",
                "androidx.appcompat",
                "appcompat"
            ).versionRef("appcompat")

            library(
                "androidx-constraintlayout",
                "androidx.constraintlayout",
                "constraintlayout"
            ).versionRef("constraintlayout")

//            library(
//                "material",
//                "com.google.android.material",
//                "material"
//            ).versionRef("material")

            library("material", "com.google.android.material:material:1.10.0")

            // Coroutines
            library(
                "kotlinx-coroutines-android",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-android"
            ).versionRef("kotlinx-coroutines-android")

            // Navigation Component
            library(
                "androidx-navigation-fragment-ktx",
                "androidx.navigation",
                "navigation-fragment-ktx"
            ).versionRef("navigation-fragment-ktx")

            library(
                "androidx-navigation-ui-ktx",
                "androidx.navigation",
                "navigation-ui-ktx"
            ).versionRef("navigation-fragment-ktx")

            library(
                "androidx-fragment-ktx",
                "androidx.fragment",
                "fragment-ktx"
            ).versionRef("fragment-ktx")

            // Timber
            library(
                "timber",
                "com.jakewharton.timber",
                "timber"
            ).versionRef("timber")

            // ViewModel + Lifecycle
            library(
                "androidx-lifecycle-viewmodel-ktx",
                "androidx.lifecycle",
                "lifecycle-viewmodel-ktx"
            ).versionRef("lifecycle-viewmodel-ktx")

            library(
                "androidx-activity-ktx",
                "androidx.activity",
                "activity-ktx"
            ).versionRef("activity-ktx")

            // Network
            library(
                "retrofit",
                "com.squareup.retrofit2",
                "retrofit"
            ).versionRef("retrofit")

            library(
                "kotlinx-serialization-json",
                "org.jetbrains.kotlinx",
                "kotlinx-serialization-json"
            ).versionRef("kotlinx-serialization-json")

            library(
                "okhttp",
                "com.squareup.okhttp3",
                "okhttp"
            ).versionRef("okhttp")

            library(
                "logging-interceptor",
                "com.squareup.okhttp3",
                "logging-interceptor"
            ).versionRef("okhttp")
        }
    }

}


rootProject.name = "Cash Advisor"
include(":app")
