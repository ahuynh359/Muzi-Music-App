plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.ahuynh.muzimusicapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ahuynh.muzimusicapp"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    //Swipe Fresh Layout
    implementation(libs.androidx.swiperefreshlayout)

    // Exoplayer
    implementation (libs.androidx.media3.exoplayer)
    implementation (libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.hls)

    //Eventbus
    implementation(libs.eventbus)

    //Shimmer
    implementation (libs.shimmer)

    //ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    //Glide
    implementation (libs.glide)

    //Circle Image View
    implementation (libs.circleimageview)

    //Navigation Graph
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.database.ktx)
    kapt(libs.hilt.android.compiler)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.ui.android)
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.media)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}