plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.practicafinal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practicafinal"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.1.2")

    implementation (platform("com.google.firebase:firebase-bom:33.9.0"))

    // Firebase Firestore
    implementation ("com.google.firebase:firebase-firestore-ktx")

    // Firebase Realtime Database (sin especificar versión)
    implementation ("com.google.firebase:firebase-database-ktx")

    // Firebase Storage (sin especificar versión)
    implementation ("com.google.firebase:firebase-storage-ktx")

/*

    //Firebase
    implementation ("com.google.firebase:firebase-database:20.0.2")
    implementation ("com.google.firebase:firebase-storage:20.0.0")
*/
    implementation ("com.google.firebase:firebase-core:20.0.0")

    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.firebase:firebase-analytics:20.0.2")
    implementation ("com.google.android.gms:play-services-auth:20.1.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.1")

    // implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    // implementation("com.google.firebase:firebase-analytics") no lo quiero verdad
}