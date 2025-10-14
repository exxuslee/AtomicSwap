import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	alias(libs.plugins.compose.compiler)
}

android {
	namespace = "com.exxlexxlee.atomicswap.core.common"
	compileSdk = property("version.compileSdk").toString().toInt()

	defaultConfig {
		minSdk = property("version.minSdk").toString().toInt()
	}

	testOptions {
		targetSdk = property("version.targetSdk").toString().toInt()
	}

	buildTypes {
		release {
			isMinifyEnabled = false
		}
		create("mock") {
			initWith(getByName("debug"))
		}
	}

	buildFeatures {
		buildConfig = true
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
	implementation(project(":core:network"))
	implementation(platform(libs.compose.bom))
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.navigation.compose)

	implementation(libs.compose.material3)
	implementation(libs.compose.ui.text.googlefonts)
	implementation(libs.compose.ui)
	implementation(libs.compose.ui.tooling.preview)
	
	implementation(libs.coil.compose)

	implementation(libs.timber)
	implementation(libs.koin.android)

	debugImplementation(libs.compose.ui.tooling)
}
