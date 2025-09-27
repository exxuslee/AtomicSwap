plugins {
	id("org.jetbrains.kotlin.android")
	id("com.android.library")
}

android {
	namespace = "com.example.atomicswap.domain"
	compileSdk = property("version.compileSdk").toString().toInt()

	defaultConfig {
		minSdk = property("version.minSdk").toString().toInt()
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

	kotlinOptions {
		jvmTarget = "17"
	}

}

dependencies {
	implementation(libs.koin.android)
	implementation(libs.kotlinx.coroutines.core)
}
