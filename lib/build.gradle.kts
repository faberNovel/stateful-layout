import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("com.github.dcendents.android-maven")
}

// For library publishing on jitpack
group = "com.github.faberNovel"

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    viewBinding.isEnabled = true

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 5
        versionName = "1.0-RC05"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/ktx")
        getByName("main").res.srcDirs("src/main/res-public")
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    implementation("androidx.appcompat", "appcompat", "1.1.0")
    implementation("androidx.constraintlayout", "constraintlayout", "1.1.3")
    implementation("androidx.core", "core-ktx", "1.2.0")

    testImplementation("junit", "junit", "4.12")
    androidTestImplementation("androidx.test.ext", "junit", "1.1.1")
    androidTestImplementation("androidx.test.espresso", "espresso-core", "3.2.0")
}
