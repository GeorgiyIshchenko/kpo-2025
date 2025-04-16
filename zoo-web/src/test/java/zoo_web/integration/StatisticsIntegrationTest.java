package zoo_web.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import zoo_web.ZooWebApplication;
import zoo_web.domain.repository.AnimalRepository;
import zoo_web.domain.repository.EnclosureRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = ZooWebApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class StatisticsIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private EnclosureRepository enclosureRepository;

    @BeforeEach
    void setUp() {
        animalRepository.deleteAll();
        enclosureRepository.deleteAll();
    }

    @Test
    void testStatsAfterCreatingAnimals() throws Exception {
        mockMvc.perform(get("/stats/animals/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

        createTestAnimal("PREDATOR", "Simba", "HEALTHY");
        createTestAnimal("HERB", "Marty", "SICK");

        mockMvc.perform(get("/stats/animals/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        mockMvc.perform(get("/stats/animals/sick"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    private void createTestAnimal(String type, String name, String status) throws Exception {
        String json = """
            {
              "type": "%s",
              "name": "%s",
              "dateOfBirth": "2025-04-16T11:55:12.995Z",
              "favoriteFood": "Meat",
              "sex": "MALE",
              "status": "%s",
              "enclosureId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
            }
        """.formatted(type, name, status);

        mockMvc.perform(post("/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }


}
