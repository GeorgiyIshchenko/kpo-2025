rootProject.name = "anti-plagiarism"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.24"
    }
}

include("api-gateway")
include("file-storage-service")
include("file-analysis-service")
include("eureka-server")
