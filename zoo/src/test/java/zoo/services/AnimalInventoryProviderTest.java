package zoo.services;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zoo.factories.HerboFactory;
import zoo.factories.PredatorFactory;
import zoo.factories.ThingFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AnimalInventoryProviderTest {

    @Autowired
    private AnimalInventoryProvider provider;

    @Autowired
    private HerboFactory herboFactory;

    @Autowired
    private PredatorFactory predatorFactory;

    @Autowired
    private ThingFactory thingFactory;

    @Test
    @DisplayName("Добавление вещи")
    public void testAddInventory() {
        assertEquals(7, provider.getInventoryList().size());
    }

    @Test
    @DisplayName("Получение списка животных")
    public void testGetAnimals() {
        assertEquals(6, provider.getAnimals().size());
    }


    @Test
    @DisplayName("Получение травоядных")
    public void testGetHerbos() {
        assertEquals(2, provider.getHerbos().size());
    }

}
