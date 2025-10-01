pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
		maven {
			url = uri("https://jitpack.io")
		}
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
		maven(url = "https://jitpack.io")
	}
}

rootProject.name = "AtomicSwap"

include(
	":app",
	":core:network",
	":core:database",
	":core:localize",
    ":core:common",
    ":core:swap",
	":domain",
	":data",
	":feature"
)
