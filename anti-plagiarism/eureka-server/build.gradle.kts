dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    // implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.1")
    }
}
