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
        vectorDrawables {
            useSupportLibrary = true
        }
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

    buildFeatures {
        viewBinding = true

    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Pagination 3
    implementation ("androidx.paging:paging-runtime:3.1.1")
    //Expandable Text
    implementation ("com.github.giangpham96:expandable-text:2.0.1")
    //Kalaxon
    implementation ("com.beust:klaxon:5.5")
    //Slider Image
    implementation ("com.github.smarteist:autoimageslider:1.4.0")
    //Edittext Pin
    implementation ("com.github.aabhasr1:OtpView:v1.1.2-ktx") // kotlin
    implementation("com.github.roynx98:transition-button-android:69d3513640")
    //Custom Activity On Crash
    implementation("cat.ereza:customactivityoncrash:2.4.0")
    //Swipe Layout
    implementation("com.daimajia.swipelayout:library:1.2.0@aar")
    //Localization
    implementation("com.akexorcist:localization:1.2.11") {
        exclude(group = "androidx.core", module = "core")
    }

    //Lottie animation
    implementation("com.airbnb.android:lottie:6.3.0")

    //Rounded Image View
    implementation("com.makeramen:roundedimageview:2.3.0")

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    //Chip
    implementation(libs.androidx.core.ktx)
    //Gson
    implementation("com.google.code.gson:gson:2.8.8")

    //Retrofit
    implementation(libs.retrofit.v290)
    implementation(libs.converter.moshi)
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
    implementation(libs.moshi.kotlin)

    //Stepper
    implementation("com.github.acefalobi:android-stepper:0.3.0")
    //Pdf Reader
    implementation(libs.itext7.core)

    //Visualizer
    implementation("io.github.gautamchibde:audiovisualizer:2.2.5")

    //Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    //Coil
    implementation(libs.coil)

    //Swipe Fresh Layout
    implementation(libs.androidx.swiperefreshlayout)

    // Exoplayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.hls)

    //Eventbus
    implementation(libs.eventbus)

    //Shimmer
    implementation(libs.shimmer)

    //ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    {
        exclude("com.google.firebase.Timestamp")
    }
    implementation(libs.firebase.messaging)
    {
        exclude("com.google.firebase.Timestamp")
    }

    //Glide
    implementation(libs.glide)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    //Navigation Graph
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Dagger Hilt
    implementation(libs.hilt.android)

    //Messaging
    implementation("com.github.DavidBarbaran:FCM-AndroidToOtherDevice:1.1.2")

    //Retrofit
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.palette.ktx)
    kapt(libs.hilt.android.compiler)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media)
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
