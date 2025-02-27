plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.dagger)

}

android {
    namespace = "com.example.moviesappproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moviesappproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += "-Xjvm-default=all"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.4.0")  // For mocking
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")  // Kotlin support for Mockito
    testImplementation("androidx.arch.core:core-testing:2.2.0")  // LiveData and ViewModel testing

    // Coroutines Testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")  // Test coroutines

    // Mock Web Server for API Testing
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")  // Mock API responses

    // Room Database Testing
    testImplementation("androidx.room:room-testing:2.5.0")  // Room in-memory database

    // Android Instrumentation Testing (If needed)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Dagger Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.kapt)

    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Glide
    implementation(libs.glide)

    // Coil
    implementation(libs.coil)

    // Paging
    implementation(libs.androidx.paging.runtime)

    // Moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit.moshi)

    // OkHttp Logging Interceptor
    implementation(libs.okhttp.logging.interceptor)

    // Auto Image Slider
//    implementation(libs.autoimageslider)
//    implementation(libs.imageSlideShow)

    //View Pager 2
    implementation(libs.viewPager2)
}