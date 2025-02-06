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
	implementation(libs.spring.boot.web)
	implementation(libs.spring.boot.security)
	developmentOnly(libs.spring.boot.devtools)
	implementation(libs.spring.data.jpa)
	implementation(libs.spring.jdbc)
	runtimeOnly(libs.mysql.connector)

	testImplementation(libs.spring.boot.test)
	testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
