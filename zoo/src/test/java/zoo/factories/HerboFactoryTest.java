package zoo.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zoo.domains.Monkey;
import zoo.domains.Rabbit;
import zoo.params.HerboParams;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class HerboFactoryTest {

    @Autowired
    private HerboFactory herboFactory;

    @Test
    @DisplayName("Создание кролика с помощью фабрики")
    public void testCreateRabbit() {
        Rabbit rabbit = (Rabbit) herboFactory.createInstance("Rabbit", new HerboParams( 5, 7), 3);
        assertEquals(3, rabbit.getNumber());
        assertEquals(5, rabbit.getFoodPerDay());
        assertEquals(7, rabbit.getKindness());
    }

    @Test
    @DisplayName("Создание обезьяны с помощью фабрики")
    public void testCreateMonkey() {
        Monkey monkey = (Monkey) herboFactory.createInstance("Monkey", new HerboParams( 2, 3), 4);
        assertEquals(4, monkey.getNumber());
        assertEquals(2, monkey.getFoodPerDay());
        assertEquals(3, monkey.getKindness());
    }

}
