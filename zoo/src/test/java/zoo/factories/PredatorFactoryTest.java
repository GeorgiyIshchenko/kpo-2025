package zoo.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zoo.domains.*;
import zoo.params.PredatorParams;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PredatorFactoryTest {

    @Autowired
    private PredatorFactory predatorFactory;

    @Test
    @DisplayName("Создание тигра с помощью фабрики и проверка сеттеров")
    public void testCreateTiger() {
        Tiger tiger = (Tiger) predatorFactory.createAnimal("Tiger", new PredatorParams(5, 10));
        assertEquals(5, tiger.getNumber());
        assertEquals(10, tiger.getFoodPerDay());

        tiger.setFoodPerDay(20);
        assertEquals(20, tiger.getFoodPerDay());

        tiger.setNumber(30);
        assertEquals(30, tiger.getNumber());
    }

    @Test
    @DisplayName("Создание волка с помощью фабрики")
    public void testCreateWolf() {
        Wolf wolf = (Wolf) predatorFactory.createAnimal("Wolf", new PredatorParams(6, 8));
        assertEquals(6, wolf.getNumber());
        assertEquals(8, wolf.getFoodPerDay());
    }

}
