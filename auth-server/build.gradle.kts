plugins {
	`java-library`
	`maven-publish`
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "com.hieptt149"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.oauth2.auth.server)
	developmentOnly(libs.spring.boot.devtools)
	implementation(libs.spring.data.jpa)
	implementation(libs.spring.jdbc)
	runtimeOnly(libs.mysql.connector)

	implementation(libs.lombok)
	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)

	testImplementation(libs.spring.boot.test)
	testRuntimeOnly(libs.junit.platform.launcher)
	testCompileOnly(libs.lombok)
	testAnnotationProcessor(libs.lombok)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
