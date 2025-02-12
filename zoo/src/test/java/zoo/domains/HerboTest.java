package zoo.domains;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class HerboTest {

    @Test
    @DisplayName("Создание обезьяны и установка правильной доброты")
    public void testCreateMonkey() {
        Monkey monkey = new Monkey(1, 2, 3);
        assertEquals(1, monkey.getNumber());
        assertEquals(2, monkey.getFoodPerDay());
        assertEquals(3, monkey.getKindness());

        monkey.setKindness(4);
        assertEquals(4, monkey.getKindness());
    }

    @Test
    @DisplayName("Создание обезьяны и установка неправильной доброты")
    public void testCreateMonkey2() {
        Monkey monkey = new Monkey(2, 2, 3);
        assertEquals(2, monkey.getNumber());
        assertEquals(2, monkey.getFoodPerDay());
        assertEquals(3, monkey.getKindness());

        assertThrows(IllegalArgumentException.class, () -> monkey.setKindness(11));

        assertEquals(3, monkey.getKindness());
    }

}
