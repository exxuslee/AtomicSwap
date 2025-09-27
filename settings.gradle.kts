pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}

rootProject.name = "AtomicSwap"

include(
	":app",
	":core:network",
	":core:database",
	":core:localize",
    ":core:common",
	":domain",
	":data",
	":feature"
)
