import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

fun getApiKey(key: String): String = gradleLocalProperties(rootDir, providers).getProperty(key)

android {
    namespace = "com.binbean.binbean_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.binbean.binbean_android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_REST_API_KEY", "\"${getApiKey("KAKAO_REST_API_KEY")}\"")
        buildConfigField("String", "KAKAO_API_KEY", "\"${getApiKey("KAKAO_API_KEY")}\"")
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
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain")
    implementation(project(":presentation:user:container"))
    implementation(project(":presentation:user:map"))
    implementation(project(":presentation:user:bookmark"))
    implementation(project(":presentation:user:mypage"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.kakao.sdk:v2-all:2.21.0")
    implementation("com.kakao.maps.open:android:2.12.8")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation(project(":presentation:admin:main"))
}