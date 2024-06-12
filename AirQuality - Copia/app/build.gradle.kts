import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.example.airquality"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.airquality"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        //resValue("string", "aqi_api_key", gradleLocalProperties(rootDir).getProperty("aqi_api_key"))
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
}


dependencies {

    implementation(libs.play.services.location)
    implementation(libs.places)


    implementation (libs.navigation.fragment)
    implementation (libs.navigation.ui)


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.lottie.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.database)
    implementation(libs.play.services.auth)
    //implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation(libs.commons.validator)

    //implementation (libs.work.runtime)
    implementation ("androidx.work:work-runtime:2.7.1")


}

