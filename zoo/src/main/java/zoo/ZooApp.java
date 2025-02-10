package zoo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zoo.factories.HerboFactory;

/**
 * Console app for zoo management.
 */
@SpringBootApplication
@SpringBootApplication
public class ZooApp {

    @Autowired
    private HerboFactory herboFactory;

    @Test
    @DisplayName("Test HerboFactory")
    public void testHerboFactory() {
        herboFactory.createAnimal("Rabbit", 10);
    }

    public static void main(String[] args) {
        SpringApplication.run(ZooApp.class, args);
    }

}
