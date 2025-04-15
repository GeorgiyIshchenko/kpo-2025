package zoo_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ZooWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZooWebApplication.class, args);
    }
}
