dependencies {
    // Spring Data JPA for database access
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")
    
    // For file handling
    implementation("commons-io:commons-io:2.15.1")
    
    // For service discovery
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    
    // For inter-service communication
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.1")
    }
}