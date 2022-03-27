import com.cosminpolifronie.gradle.plantuml.tasks.PlantUmlTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
	id("com.cosminpolifronie.gradle.plantuml") version  "1.6.0"
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	`java-test-fixtures`
}

group = "org.help.ukraine.verification"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

//extra["snippetsDir"] = file("build/generated-snippets")
extra["testcontainersVersion"] = "1.16.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.jsonwebtoken:jjwt:0.9.1")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("org.postgresql:postgresql")

	testFixturesApi("org.springframework.boot:spring-boot-starter-test")
	testFixturesApi("org.mockito.kotlin:mockito-kotlin:4.0.0")
	testFixturesApi("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

plantUml {
	render(
		mapOf(
			"input" to "docs/diagrams/*.puml",
			"output" to "build/docs/diagrams",
			"format" to "png"
		)
	)
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	withType<Test> {
		useJUnitPlatform()
//		outputs.dir(property("snippetsDir"))
	}

	asciidoctor {
//		inputs.dir(property("snippetsDir"))
		dependsOn(test)
	}

	register<Copy>("copyDocs") {
		dependsOn(plantUml)
		from( layout.projectDirectory.dir("docs"))
		into(layout.buildDirectory.dir("docs"))
	}
}
