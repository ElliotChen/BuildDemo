import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.6.2/userguide/tutorial_java_projects.html
 */

plugins {
	// Apply the java plugin to add support for Java
	java

	// Apply the application plugin to add support for building a CLI application
	application

	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

repositories {
	// Use jcenter for resolving dependencies.
	// You can declare any Maven/Ivy/file repository here.
	mavenCentral()
}

dependencies {
	// This dependency is used by the application.
	implementation("com.google.guava:guava:29.0-jre")

	// Use JUnit Jupiter API for testing.
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")

	// Use JUnit Jupiter Engine for testing.
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

application {
	// Define the main class for the application
	mainClass.set("tw.elliot.App")
}

sourceSets {
	main {
		java {
			srcDirs("src/main/java")
		}
		resources {
			srcDirs("src/main/resources")
		}
	}
	test {
		java.srcDirs("src/test/java")
		resources.srcDirs("src/tes/resources")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
