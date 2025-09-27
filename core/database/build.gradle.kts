plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("app.cash.sqldelight")
}

android {
	namespace = "com.example.atomicswap.core.database"
	compileSdk = property("version.compileSdk").toString().toInt()

	defaultConfig {
		minSdk = property("version.minSdk").toString().toInt()
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

	kotlinOptions {
		jvmTarget = "17"
	}
}

dependencies {
	implementation(libs.sqldelight.android.driver)
	implementation(libs.koin.android)
}

sqldelight {
	databases {
		create("AppDatabase") {
			packageName.set("com.example.atomicswap.core.database")
		}
	}
}
