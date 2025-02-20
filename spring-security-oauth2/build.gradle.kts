plugins {
	`java-library`
	`maven-publish`
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "com.hieptt149"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.oauth2.client)
	implementation(libs.spring.oauth2.resource.server)
	implementation(libs.spring.boot.web)
	implementation(libs.spring.boot.security)
	developmentOnly(libs.spring.boot.devtools)

	testImplementation(libs.spring.boot.test)
	testImplementation(libs.spring.security.test)
	testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
