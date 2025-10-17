import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.exxlexxlee.atomicswap.data"
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
	implementation(project(":core:swap"))

	implementation(libs.gson)
	implementation(libs.timber)
	implementation(libs.koin.android)
	implementation(libs.androidx.room.ktx)
	implementation(libs.kotlinx.coroutines.core)

	implementation("io.reactivex.rxjava2:rxjava:2.2.19")
	implementation("com.github.horizontalsystems:market-kit-android:8bd85cc")
}
