package hse.kpo.antiplagiarism.fileanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for the File Analysis service.
 * This service is responsible for analyzing files, storing results, and issuing them.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FileAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileAnalysisApplication.class, args);
    }
}