import java.util.Properties

plugins {
    id ("kotlin-kapt")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.example.wiseforecast"
    compileSdk = 34

    defaultConfig {
        val apiKeyFiles = rootProject.file("local.properties")
        val properties = Properties()
        properties.load(apiKeyFiles.inputStream())
        val apiKey = properties.getProperty("API_KEY") ?: "Missing api key"

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey
        )

        applicationId = "com.example.wiseforecast"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Retrofit
    implementation(libs.retrofitGson)
    implementation(libs.retrofit)

    //okHttp/Logging
    implementation(libs.okHttp)
    implementation(libs.okHttpLoggingInterceptor)
    //UI
    implementation(libs.viewPager)
    implementation(libs.swipeRefreshLayout)

    //Hilt
    implementation (libs.hilt)
    kapt (libs.hiltCompiler)
    implementation (libs.hiltNavigationCompose)

    //ViewModel
    implementation (libs.viewModelCompose)
    implementation (libs.liveData)
    implementation (libs.savedState)

    //Glide
    implementation (libs.glide)

    //Location services
    implementation (libs.locationServices)

    //Accompanist (permissions)
    implementation(libs.accompanist)

    //Compose LiveData
    implementation(libs.composeLiveData)

}

kapt {
    correctErrorTypes = true
}
