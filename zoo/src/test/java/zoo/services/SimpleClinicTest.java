package zoo.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zoo.domains.Animal;
import zoo.domains.Monkey;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SimpleClinicTest {

    @Autowired
    SimpleClinic clinic;

    @Test
    @DisplayName("Тест проверки подходящего животного")
    public void testIsSuitable() {
        Animal monkey = new Monkey(1, 7, 3);
        assertTrue(clinic.isSuitable(monkey));
    }

    @Test
    @DisplayName("Тест проверки неподходящего животного")
    public void testIsNotSuitable() {
        Animal monkey = new Monkey(1, 2, 3);
        assertFalse(clinic.isSuitable(monkey));
    }

}
