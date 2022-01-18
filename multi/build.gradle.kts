import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("java")
	kotlin("jvm") version "1.6.10" apply false
}

allprojects {
	group = "tw.elliot"
	version = "1.0"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "kotlin")

	tasks.withType<KotlinCompile> {
		println("Configuring KotlinCompile  $name in project ${project.name}...")
		kotlinOptions {
			jvmTarget = "17"
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}

	println("Enabling Dependency Management in project ${project.name}...")

	dependencies {
		compileOnly("javax.servlet:javax.servlet-api:4.0.1")
		implementation("com.google.guava:guava:28.0-jre")
		testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

tasks.forEach {
	it.enabled = false
}