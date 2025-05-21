package hse.kpo.antiplagiarism.filestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for the File Storage service.
 * This service is responsible for storing and delivering files.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FileStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileStorageApplication.class, args);
    }
}