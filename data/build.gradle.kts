plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.example.atomicswap.data"
	compileSdk = property("version.compileSdk").toString().toInt()
	defaultConfig {
		minSdk = property("version.minSdk").toString().toInt()
		targetSdk = property("version.targetSdk").toString().toInt()
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
	implementation(project(":domain"))
	implementation(project(":core:network"))
	implementation(project(":core:database"))
	implementation(libs.koin.android)
	implementation(libs.kotlinx.coroutines.core)
}
