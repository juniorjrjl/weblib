import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

var mapStructVersion = "1.3.1.Final"
var mapStructKotlin  = "1.3.1.2"
var versaoMockito = "3.3.3"
var versaoMockitoKotlin = "2.2.0"

plugins {
	id("idea")
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"
	kotlin("kapt") version "1.3.72"
}

group = "cm.library"
//version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14

val developmentOnly by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")
	kaptTest("org.mapstruct:mapstruct-processor:$mapStructVersion")
	implementation("org.mapstruct:mapstruct:$mapStructVersion")
	api("com.github.pozo:mapstruct-kotlin:$mapStructKotlin")
	kapt("com.github.pozo:mapstruct-kotlin-processor:$mapStructKotlin")
	testImplementation("io.rest-assured:rest-assured")

	testImplementation("org.mockito:mockito-core:${versaoMockito}")
	testImplementation("org.mockito:mockito-junit-jupiter:${versaoMockito}")
	testImplementation("org.mockito:mockito-inline:${versaoMockito}")
	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${versaoMockitoKotlin}")

	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
	implementation("com.sun.xml.bind:jaxb-impl:2.3.2")
	implementation("javax.activation:activation:1.1.1")
	implementation("com.sun.xml.bind:jaxb-osgi:2.3.2")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.liquibase:liquibase-core")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = JavaVersion.VERSION_13.toString()
	}
}

tasks.compileKotlin{
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = JavaVersion.VERSION_13.toString()
	}
}

tasks.compileTestKotlin {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = JavaVersion.VERSION_13.toString()
	}
}

tasks.idea {
	fun kaptMain() = mutableSetOf(file("build/generated/source/kapt/main"))
	idea.module.sourceDirs = kaptMain()
	idea.module.generatedSourceDirs = kaptMain()
}