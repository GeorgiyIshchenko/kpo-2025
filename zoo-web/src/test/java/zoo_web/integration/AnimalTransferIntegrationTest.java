package zoo_web.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import zoo_web.ZooWebApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = ZooWebApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class AnimalTransferIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void transferAnimal_success() throws Exception {
        String enclosure1 = createEnclosure("PREDATOR", 2);

        String enclosure2 = createEnclosure("PREDATOR", 2);

        String createAnimalJson = """
            {
              "type": "PREDATOR",
              "name": "Simba",
              "dateOfBirth": "2025-04-16T11:55:12.995Z",
              "favoriteFood": "Meat",
              "sex": "MALE",
              "status": "HEALTHY",
              "enclosureId": "REPLACE_ME"
            }
        """.replace("REPLACE_ME", extractIdFromJson(enclosure1));

        String animalResponse = mockMvc.perform(
                        post("/animals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createAnimalJson)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String animalId = extractIdFromJson(animalResponse);
        String enclosure2Id = extractIdFromJson(enclosure2);

        String transferRequest = """
            {
              "newEnclosureId": "ENCL_ID"
            }
        """.replace("ENCL_ID", enclosure2Id);

        mockMvc.perform(
                post("/transfer/animals/" + animalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferRequest)
        ).andExpect(status().isOk());

        mockMvc.perform(get("/animals/" + animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enclosureId").value(enclosure2Id));
    }

    private String createEnclosure(String type, int capacity) throws Exception {
        return mockMvc.perform(
                        post("/enclosures")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    {
                                      "type": "REPLACE_TYPE",
                                      "capacity": REPLACE_CAPACITY
                                    }
                                """.replace("REPLACE_TYPE", type).replace("REPLACE_CAPACITY", String.valueOf(capacity)))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private String extractIdFromJson(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);
        return root.path("id").asText();
    }

}
