package zoo_web.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AnimalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void crudAnimal() throws Exception {
        String createJson = """
            {
              "type": "PREDATOR",
              "name": "Simba",
              "dateOfBirth": "2025-04-16T11:55:12.995Z",
              "favoriteFood": "Meat",
              "sex": "MALE",
              "status": "HEALTHY",
              "enclosureId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
            }
        """;

        String response = mockMvc.perform(
                        post("/animals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value("PREDATOR"))
                .andExpect(jsonPath("$.name").value("Simba"))
                .andExpect(jsonPath("$.favoriteFood").value("Meat"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andReturn()
                .getResponse()
                .getContentAsString();


        String createdId = extractIdFromJson(response);

        mockMvc.perform(get("/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(createdId))
                .andExpect(jsonPath("$[0].name").value("Simba"));

        mockMvc.perform(get("/animals/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.type").value("PREDATOR"));

        mockMvc.perform(delete("/animals/" + createdId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/animals"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    private String extractIdFromJson(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);
        return root.path("id").asText();
    }

}
