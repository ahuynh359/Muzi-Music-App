plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "com.ahuynh.muzimusic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ahuynh.muzimusic"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //Navigation Graph
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    //Splash
    implementation(libs.core.splashscreen)

    //Dagger Hilt
    implementation (libs.hilt.android)
    annotationProcessor (libs.hilt.compiler)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}