import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import statefullayout.groupId
import statefullayout.versionName
import java.net.URL

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build", "gradle", "3.6.1")
        classpath("com.vanniktech","gradle-maven-publish-plugin","0.11.1")
        classpath("org.jetbrains.kotlin", "kotlin-gradle-plugin", "1.3.70")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:0.10.0")
        classpath("com.jfrog.bintray.gradle","gradle-bintray-plugin","1.8.4")
        classpath(kotlin("gradle-plugin", version = "1.3.71"))
    }
}

plugins {
    id( "org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    group = project.groupId
    version = project.versionName

    repositories {
        mavenCentral()
        jcenter()
    }

    ktlint {
        setVersion(version = "0.36.0")
        disabledRules.add("filename")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-progressive", "-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = setOf(TestLogEvent.SKIPPED, TestLogEvent.PASSED, TestLogEvent.FAILED)
            showStandardStreams = true
        }
    }

    afterEvaluate {
        tasks.withType<DokkaTask> {
            configuration {
                jdkVersion = 8
                reportUndocumented = false
                skipDeprecated = true
                skipEmptyPackages = true

                externalDocumentationLink {
                    url = URL("https://developer.android.com/reference/androidx/")
                    packageListUrl = URL("https://developer.android.com/reference/androidx/package-list")
                }
            }
        }
    }
}

subprojects {
    afterEvaluate {
        extensions.configure<com.android.build.gradle.BaseExtension> {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
