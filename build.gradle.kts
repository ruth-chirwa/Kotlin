plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //id("org.jetbrains.kotlin.kapt")
}


android {
    namespace = "com.example.mykotlinapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mykotlinapp"
        minSdk = 33
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Retrofit for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Room for database
    implementation("androidx.room:room-runtime:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.1")
    // For Kotlin

    implementation("androidx.room:room-runtime:2.5.1")
   // kapt("androidx.room:room-compiler:2.5.1")


// Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")

    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.appcompat:appcompat:1.5.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}
