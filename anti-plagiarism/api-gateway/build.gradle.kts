plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    jacoco
}

repositories { mavenCentral() }

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
}

val nettyVersion = "4.1.109.Final"

dependencies {
    implementation(platform("io.netty:netty-bom:$nettyVersion"))
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:$nettyVersion:osx-aarch_64")

    implementation("org.springframework.cloud:spring-cloud-starter-gateway:4.2.2")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.5.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage")
    }
    testImplementation("io.projectreactor:reactor-test")
}

tasks.test { useJUnitPlatform() }
tasks.jacocoTestReport { reports { xml.required.set(true); html.required.set(true) } }
tasks.bootJar { archiveFileName.set("${project.name}.jar") }
