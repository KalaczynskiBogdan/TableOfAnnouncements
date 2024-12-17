plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Google
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.googleGmsGoogleServices)
}
android {
    namespace = "com.example.tableofannouncements"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tableofannouncements"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
}

dependencies {

    // Junit
    testImplementation(libs.junit)

    // *** AndroidX ***

    // Default
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Fragment
    implementation(libs.androidx.fragment.ktx)

    // Lifecycles
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Work Manager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    // Room TODO: Remove from :app
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // *** Google ***

    // Material
    implementation(libs.google.material)

    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(platform(libs.firebase.bom))


    implementation(libs.play.services)

    implementation (libs.credentials)
    implementation (libs.credentials.play.services)
    implementation (libs.googleid)

    // Hilt
    ksp(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    // GSON TODO: Remove from :app
    implementation(libs.google.gson)

    // *** KotlinX ***

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // *** Other ***

    // Retrofit + OkHTTP TODO: Remove from :app
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.okhttp)

    // Glide
    implementation(libs.glide)

    //pixImagePicker
    implementation(libs.piximagepicker)

    implementation(libs.play.services.ads)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}