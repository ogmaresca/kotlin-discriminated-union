repositories {
	gradlePluginPortal()
	mavenCentral()
}

plugins {
	kotlin("multiplatform") version "kotlinVersion"
	id("com.github.spotbugs") version "spotbugsVersion"
	id("com.diffplug.spotless") version "spotlessVersion"
}

apply(plugin = "com.github.spotbugs")
apply(plugin = "com.diffplug.spotless")

group = "com.ogm.kotlin.discriminatedunion"
version = "0.1.0"

repositories {
	mavenCentral()
}

kotlin {
	jvm {
		compilations.all {
			kotlinOptions {
				allWarningsAsErrors = true
				freeCompilerArgs = listOf(
					"-Xjsr305=warn",
					// "-Xemit-java-type-annotations",
					"-java-parameters",
					"-Xjvm-default=all-compatibility",
				)
				apiVersion = "1.9"
				languageVersion = "1.9"
				jvmTarget = "1.8"
			}
		}
	}

	sourceSets {
		val commonMain by getting {}

		val commonTest by getting {
			dependencies {
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}

		val jvmMain by getting {}

		val jvmTest by getting {
			dependencies {
				val assertJVersion: String by project
				val junitVersion: String by project

				implementation(kotlin("test"))
				implementation("org.assertj:assertj-core:$assertJVersion")
				implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
				implementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
				runtimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
			}
		}
	}
}

spotless {
	var editorconfigLineIsForKotlin = false
	val editorConfigRules = file(".editorconfig")
		.readLines()
		.mapNotNull {
			if (it.startsWith('[') && it.endsWith(']')) {
				editorconfigLineIsForKotlin = it == "[*]" || it == "[{*.kt, *.kts}]"
				null
			} else if (editorconfigLineIsForKotlin && it.contains('=')) {
				val (rule, value) = it.split('=')
				rule.trim() to value.trim()
			} else {
				null
			}
		}
		.toMap()

	println("editorConfigRules == $editorConfigRules")

	kotlin {
		target("**/*.kt")
		ktlint().editorConfigOverride(editorConfigRules)
	}

	kotlinGradle {
		ktlint().editorConfigOverride(editorConfigRules)
	}
}

tasks {
	withType<Test>().configureEach {
		testLogging {
			displayGranularity = 2
			events = org.gradle.api.tasks.testing.logging.TestLogEvent.values().toSet()
			exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
			showCauses = true
			showExceptions = true
			showStackTraces = true
			showStandardStreams = true
		}

		useJUnitPlatform()
	}

	withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
		showProgress.set(true)
		effort.set(com.github.spotbugs.snom.Effort.MAX)
		showStackTraces = true
		reports.create("xml") {
			required.set(true)
			setStylesheet("fancy-hist.xsl")
		}
		reports.create("html") {
			required.set(true)
			setStylesheet("fancy-hist.xsl")
		}
	}
}
