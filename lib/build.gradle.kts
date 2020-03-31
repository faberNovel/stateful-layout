import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import statefullayout.bintrayKey
import statefullayout.bintrayUser
import statefullayout.compileSdk
import statefullayout.minSdk
import statefullayout.targetSdk

plugins {
    id("com.android.library")
    id("com.vanniktech.maven.publish")
    id("com.jfrog.bintray")
    kotlin("android")
    kotlin("android.extensions")
    id("org.jetbrains.dokka")
}

android {
    compileSdkVersion(project.compileSdk)

    defaultConfig {
        minSdkVersion(project.minSdk)
        targetSdkVersion(project.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    libraryVariants.all {
        generateBuildConfigProvider?.configure { enabled = false }
    }
    viewBinding.isEnabled = true

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
}

mavenPublish {
    useLegacyMode = false
}

bintray {
    user = bintrayUser
    key = bintrayKey
    pkg(delegateClosureOf<com.jfrog.bintray.gradle.BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "stateful-layout"
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/faberNovel/stateful-layout"
        issueTrackerUrl = "https://github.com/faberNovel/stateful-layout/issues"
        githubRepo = "faberNovel/stateful-layout"
        setLabels("kotlin", "android")
        version(delegateClosureOf<com.jfrog.bintray.gradle.BintrayExtension.VersionConfig> {
            name = project.version.toString()
            desc = "v.${project.version}"
        })
    })

}

afterEvaluate {
    tasks.withType<DokkaTask> {
        outputDirectory = "$rootDir/docs/api"
        outputFormat = "gfm"
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
