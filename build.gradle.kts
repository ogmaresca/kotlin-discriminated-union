import com.diffplug.spotless.LineEnding
import com.github.spotbugs.snom.Confidence
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

repositories {
	gradlePluginPortal()
	mavenCentral()
}

plugins {
	kotlin("multiplatform") version "kotlinVersion"
	id("com.github.spotbugs") version "spotbugsVersion" apply true
	id("com.diffplug.spotless") version "spotlessVersion" apply true
}

group = "com.ogm.kotlin.discriminatedunion"
version = "0.1.0"

repositories {
	mavenCentral()
}

kotlin {
	@OptIn(ExperimentalKotlinGradlePluginApi::class)
	compilerOptions {
		allWarningsAsErrors = true
		freeCompilerArgs =
			listOf(
				"-Xjsr305=warn",
				// "-Xemit-java-type-annotations",
				"-java-parameters",
				"-Xjvm-default=all-compatibility",
			)
		apiVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_7
		languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0
	}

	jvm {
		@OptIn(ExperimentalKotlinGradlePluginApi::class)
		compilerOptions {
			jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
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

		val jvmMain by getting {
			val spotbugsMain by tasks.registering(com.github.spotbugs.snom.SpotBugsTask::class) {
				sourceDirs = kotlin.sourceDirectories
				classDirs = fileTree("${rootProject.layout.buildDirectory.asFile}/classes")
				reportsDir = file("${layout.buildDirectory.asFile}/reports/spotbugs")

				reports.create("xml") {
					required.set(true)
				}
				reports.create("html") {
					required.set(true)
					setStylesheet("fancy-hist.xsl")
				}
			}
		}

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
	ratchetFrom("remotes/origin/main")

	lineEndings = LineEnding.UNIX
	encoding = Charsets.UTF_8

	val ktlintVersion: String by project

	kotlin {
		target("**/*.kt")
		ktlint(ktlintVersion).setEditorConfigPath("$rootDir/.editorconfig")
	}

	kotlinGradle {
		ktlint(ktlintVersion).setEditorConfigPath("$rootDir/.editorconfig")
	}
}

spotbugs {
	showProgress = true
	effort = com.github.spotbugs.snom.Effort.MAX
	showStackTraces = true
	ignoreFailures = false
	reportLevel = Confidence.LOW
}

tasks.withType<Test>().configureEach {
	testLogging {
		displayGranularity = 2
		events =
			org.gradle.api.tasks.testing.logging.TestLogEvent
				.values()
				.toSet()
		exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
		showCauses = true
		showExceptions = true
		showStackTraces = true
		showStandardStreams = true
	}

	useJUnitPlatform()
}
