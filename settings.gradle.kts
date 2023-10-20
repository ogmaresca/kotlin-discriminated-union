rootProject.name = "kotlin-discriminated-union"

pluginManagement {
	repositories {
		gradlePluginPortal()
	}

	resolutionStrategy {
		eachPlugin {
			val requestedVersion = requested.version
			if (requestedVersion?.endsWith("Version") == true) {
				val pluginVersion = settings.providers.gradleProperty(requestedVersion).get()
				logger.info("Resolved plugin $requested to version $pluginVersion")
				useVersion(pluginVersion)
			}
		}
	}
}
