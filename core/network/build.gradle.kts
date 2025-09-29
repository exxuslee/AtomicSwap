import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.exxlexxlee.atomicswap.core.network"
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
	implementation(libs.ktor.client.core)
	implementation(libs.ktor.client.okhttp)
	implementation(libs.ktor.client.content.negotiation)
	implementation(libs.ktor.serialization.kotlinx.json)
	implementation(libs.koin.android)
}
