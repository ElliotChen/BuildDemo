# Spring Boot Multiple Project sample

## Root (Parent)

### plugins
```shell
plugins {
	java
	id("org.springframework.boot") version "2.6.2" apply false
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}
```
重點在於```org.springframework.boot```不要每個sub projects都使用，因為會有做為共用library的jar，
所以通常不需要每一個Project都build 成spring boot可執行的jar。

### all Projects

用```allprojects```來控制建構中所有專案的設定（包含root）。

```shell
allprojects {
	group = "tw.elliot"
	version = "1.0"
	
	repositories {
		mavenCentral()
	}
}

tasks.jar {
	enabled = false
}
```

#### repositories

```shell
repositories {
	mavenCentral()
}
```

等於Maven中對於repositories的設定

```xml
<repositories>
	<repository>
		<id>central</id>
		<name>Maven Central</name>
		<layout>default</layout>
		<url>https://repo1.maven.org/maven2/</url>
	</repository>
</repositories>
```

#### disable task jar

```shell
tasks.jar {
	enabled = false
}
```

由於root(Parent) 並不需要進行任何建構，所以將```build jar```這task取消。

### subprojects

subprojects用來設定所有sub projects，主要包含```dependencyManagement```、```unitTest```

```shell
subprojects {
  
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
		....
	}

	....

}
```

#### dependencyManagement

import maven bom 可以同於下列於maven中的設定

```xml
<dependencyManagement>
	<dependencies>
		<!--BOM-->
		<dependency>
			<!-- Import dependency management from Spring Boot -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-dependencies</artifactId>
			<version>${spring.boot.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```
也可用dependencies:dependency 來設定單一的library版本
效果同於maven中在dependencyManagement的設定

```xml
<dependencyManagement>
	<dependencies>
		<!--BOM-->
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>${guava.version}</version>
		</dependency>
	</dependencies>
</dependencyManagement>
```


## core

做為共用library的模組，通常僅需加入要使用的dependencies

```shell
dependencies {
	implementation("com.google.guava:guava")
}
```

## frontend and backend

做為spring boot 的主要模組，必需加入spring boot主要的gradle plugin

```shell
plugins {
	id("org.springframework.boot")
}
```

dependencies 中則需加入共用的模組與spring boot 相關的library

```shell
dependencies {
	implementation(project(":core"))
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
}
```