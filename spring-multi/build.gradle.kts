import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	id("org.springframework.boot") version "2.6.2" apply false
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	jacoco
}


allprojects {
	/**/
	group = "tw.elliot"
	version = "1.0"

	repositories {
		mavenCentral()
	}

}



subprojects {

	println("Enabling Java And Kotlin in project ${project.name}...")

	println("Enabling Spring Boot Dependency Management in project ${project.name}...")

	apply(plugin = "java")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "jacoco")

	dependencyManagement {
		imports {
			mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
		}

		dependencies {
			dependency("com.google.guava:guava:30.0-jre")
		}
	}

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.withType<KotlinCompile> {
		println("Configuring KotlinCompile  $name in project ${project.name}...")
		kotlinOptions {
			jvmTarget = "16"
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}

	tasks.test {
		useJUnitPlatform()
		finalizedBy(tasks.jacocoTestReport)
	}

	tasks.jacocoTestReport {
		dependsOn(tasks.test) // tests are required to run before generating the report
	}

}

tasks.jar {
	enabled = false
}
