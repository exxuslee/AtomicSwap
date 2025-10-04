import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.exxlexxlee.atomicswap.feature"
    compileSdk = property("version.compileSdk").toString().toInt()

    defaultConfig {
        minSdk = property("version.minSdk").toString().toInt()
    }

    testOptions {
        targetSdk = property("version.targetSdk").toString().toInt()
    }

    buildTypes {
        create("mock") {
            initWith(getByName("debug"))
        }
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:localize"))
    implementation(project(":core:network"))
    implementation(project(":core:walletconnect"))
    implementation(project(":domain"))
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.material.iconsExtended)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.coil.compose)

    implementation(platform(libs.reown.bom))
    implementation(libs.reown.core)
    implementation(libs.reown.appkit)

    debugImplementation(libs.compose.ui.tooling)
}
