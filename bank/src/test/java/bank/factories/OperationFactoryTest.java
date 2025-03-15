package bank.factories;

import bank.config.AppTestConfig;
import bank.domains.Operation;
import bank.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = AppTestConfig.class)
class OperationFactoryTest {

    @Autowired
    OperationFactory factory;

    @Test
    void createValidOperation() {
        Date now = new Date();
        Operation op = factory.create(
                100,                // id
                OperationType.DEPOSIT,
                1,                  // bankAccountId
                500,                // amount
                now,                // date
                "Test operation",   // description
                10                  // categoryId
        );

        assertEquals(100, op.getId());
        assertEquals(OperationType.DEPOSIT, op.getOperationType());
        assertEquals(500, op.getAmount());
        assertEquals(now, op.getDate());
        assertEquals("Test operation", op.getDescription());
        assertEquals(10, op.getCategoryId());
    }

    @Test
    void createZeroOrNegativeAmountShouldThrowException() {
        Date now = new Date();
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(101, OperationType.WITHDRAWAL, 1, 0, now, "Zero amount", 20);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(102, OperationType.WITHDRAWAL, 1, -50, now, "Negative", 20);
        });
    }

    @Test
    void createNullDateShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(103, OperationType.WITHDRAWAL, 1, 300, null, "No date", 30);
        });
    }
}
