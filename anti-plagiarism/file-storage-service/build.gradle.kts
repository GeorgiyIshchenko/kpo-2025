plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    jacoco
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

repositories { mavenCentral() }

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.1") // Moorgate
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("io.minio:minio:8.5.12")
    implementation("org.postgresql:postgresql:42.7.3")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.springframework.kafka:spring-kafka:3.3.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter:1.19.7")
    testImplementation("org.testcontainers:postgresql:1.19.7")
    testImplementation("org.testcontainers:minio:1.19.7")
}

tasks.test { useJUnitPlatform() }
tasks.jacocoTestReport { reports { xml.required.set(true); html.required.set(true) } }
tasks.bootJar  { archiveFileName.set("${project.name}.jar") }
