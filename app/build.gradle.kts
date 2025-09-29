import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	alias(libs.plugins.compose.compiler)
}

android {
	namespace = "com.example.atomicswap"
	compileSdk = property("version.compileSdk").toString().toInt()

	defaultConfig {
		applicationId = "com.example.atomicswap"
		minSdk = property("version.minSdk").toString().toInt()
		targetSdk = property("version.targetSdk").toString().toInt()
		versionCode = 1
		versionName = "1.0"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
		create("mock") {
			initWith(getByName("debug"))
			applicationIdSuffix = ".mock"
			matchingFallbacks += listOf("debug", "release")
		}
	}

	buildFeatures {
		compose = true
		buildConfig = true
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

	packaging {
		resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
	}
}

dependencies {
	implementation(platform(libs.compose.bom))
	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.appcompat)
	implementation(libs.compose.ui)
	implementation(libs.compose.ui.tooling.preview)
	implementation(libs.compose.material3)
	implementation(libs.androidx.navigation.compose)
	implementation(libs.coil.compose)

	implementation(libs.koin.android)
	implementation(libs.koin.compose)

	implementation(project(":domain"))
	implementation(project(":data"))
	implementation(project(":core:network"))
	implementation(project(":core:database"))
	implementation(project(":core:localize"))
	implementation(project(":core:common"))
	implementation(project(":feature"))

	debugImplementation(libs.compose.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
}
