package zoo.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zoo.factories.HerboFactory;
import zoo.factories.PredatorFactory;
import zoo.factories.ThingFactory;
import zoo.params.HerboParams;
import zoo.params.PredatorParams;
import zoo.params.ThingParams;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class HseZooTest {

    @Autowired
    private HseZoo zoo;

    @Autowired
    private AnimalInventoryProvider hseProvider;

    @Test
    @Order(0)
    @DisplayName("Добавление вещей в зоопарк")
    public void testAddInventory() {
        assertEquals(zoo.getProvider(), hseProvider);
    }

    @Test
    @DisplayName("Проверка сметы еды")
    public void testGetFoodMap(){
        Map<String, Integer> result = zoo.getFoodMap();

        assertEquals(8, result.get("Rabbit"));
        assertEquals(8, result.get("Wolf"));
        assertEquals(30, result.get("Tiger"));
    }


    @Test
    @DisplayName("Подсчет килограммов")
    public void testGetSumFood(){
        assertEquals(46, zoo.getFoodSum());
    }

    @Test
    @DisplayName("Получение контактных животных")
    public void testGetPetting() {
        assertEquals(1, zoo.getPettingAnimals().size());
    }


}
