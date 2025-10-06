import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("com.google.devtools.ksp")
	alias(libs.plugins.kotlin.serialization)
}

android {
	namespace = "com.exxlexxlee.atomicswap.core.database"
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
			matchingFallbacks += listOf("debug", "release")
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
	implementation(project(":core:swap"))
	implementation(libs.androidx.room.runtime)
	ksp(libs.androidx.room.compiler)
	implementation(libs.androidx.room.ktx)
	implementation(libs.koin.android)
	implementation(libs.gson)
}

