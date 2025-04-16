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
class EnclosureIntegrationTest {

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
    void crudEnclosure() throws Exception {
        String createJson = """
            {
              "type": "PREDATOR",
              "capacity": 5
            }
        """;

        String response = mockMvc.perform(
                        post("/enclosures")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value("PREDATOR"))
                .andExpect(jsonPath("$.capacity").value(5))
                .andReturn()
                .getResponse()
                .getContentAsString();


        String createdId = extractIdFromJson(response);

        mockMvc.perform(get("/enclosures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(createdId))
                .andExpect(jsonPath("$[0].capacity").value(5))
                .andExpect(jsonPath("$[0].type").value("PREDATOR"));

        mockMvc.perform(get("/enclosures/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.capacity").value(5))
                .andExpect(jsonPath("$.type").value("PREDATOR"));

        mockMvc.perform(delete("/enclosures/" + createdId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/enclosures"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    private String extractIdFromJson(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);
        return root.path("id").asText();
    }

}
