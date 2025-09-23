plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	alias(libs.plugins.compose.compiler)
}

android {
	namespace = "com.example.atomicswap.core.ui"
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
	implementation(platform(libs.compose.bom))
	implementation(libs.compose.material3)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.navigation.compose)
}
