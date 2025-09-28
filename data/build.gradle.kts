import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.example.atomicswap.data"
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
			matchingFallbacks += listOf("debug", "release")
		}
		release {
			isMinifyEnabled = false
		}
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
	implementation(project(":domain"))
	implementation(project(":core:network"))
	implementation(project(":core:database"))
	implementation(libs.koin.android)
	implementation(libs.sqldelight.coroutines)
	implementation(libs.kotlinx.coroutines.core)
}
