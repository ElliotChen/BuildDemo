plugins {
	id("org.springframework.boot")
}

dependencies {
	implementation(project(":core"))
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
}
