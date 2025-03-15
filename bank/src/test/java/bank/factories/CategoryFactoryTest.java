package bank.factories;

import bank.config.AppTestConfig;
import bank.domains.Category;
import bank.enums.CategoryType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = AppTestConfig.class)
class CategoryFactoryTest {

    @Autowired
    CategoryFactory factory;

    @Test
    void createValidCategory() {
        Category cat = factory.create(1, "Food", CategoryType.EXPENSE);

        assertEquals(1, cat.getId());
        assertEquals("Food", cat.getName());
        assertEquals(CategoryType.EXPENSE, cat.getType());
    }

    @Test
    void createEmptyNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(2, "", CategoryType.INCOME);
        });
    }

    @Test
    void createNullNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(3, null, CategoryType.INCOME);
        });
    }
}
