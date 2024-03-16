plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.softnepo.testresource"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
    testImplementation("junit:junit:4.13.2")
    api(files("libs/android-all-7.jar"))
    api(kotlin("reflect"))
    api("junit:junit:4.13.2")
    api("io.mockk:mockk:1.13.10")
    api("org.hamcrest:hamcrest:2.2")
    api("org.robolectric:robolectric:4.11.1")
}