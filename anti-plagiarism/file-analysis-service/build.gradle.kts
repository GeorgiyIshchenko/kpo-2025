dependencies {
    // Spring Data JPA for database access
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")
    
    // For service discovery
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    
    // For inter-service communication
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    
    // For text analysis
    implementation("org.apache.commons:commons-text:1.11.0")
    
    // For HTTP client (for word cloud API)
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.1")
    }
}