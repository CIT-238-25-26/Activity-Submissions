plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

// Move misplaced playstore-icon.png from res/ to the module root to fix "file name must end with .xml" error
val misplacedIcon = file("src/main/res/playstore-icon.png")
if (misplacedIcon.exists()) {
    val destination = file("playstore-icon.png")
    if (!destination.exists()) {
        misplacedIcon.renameTo(destination)
    } else {
        misplacedIcon.delete()
    }
}

// Clean up duplicate mipmap resources to fix "Duplicate resources" error
val mipmapDirs = listOf("hdpi", "mdpi", "xhdpi", "xxhdpi", "xxxhdpi")
mipmapDirs.forEach { density ->
    val dir = file("src/main/res/mipmap-$density")
    if (dir.exists()) {
        listOf("ic_launcher.webp", "ic_launcher_round.webp").forEach { fileName ->
            val webpFile = file("${dir.path}/$fileName")
            val pngFile = file("${dir.path}/${fileName.replace(".webp", ".png")}")
            if (webpFile.exists() && pngFile.exists()) {
                webpFile.delete()
            }
        }
    }
}

android {
    namespace = "com.example.exercise_2_music_player"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.exercise_2_music_player"
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
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ExoPlayer for streaming audio from URLs
    implementation("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")
    implementation("androidx.media3:media3-common:1.1.1")
}
