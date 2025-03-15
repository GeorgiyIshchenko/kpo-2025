package bank.factories;

import bank.config.AppTestConfig;
import bank.domains.BankAccount;
import bank.factories.interfaces.InBankAccountFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = AppTestConfig.class)
class BankAccountFactoryTest {

    @Autowired
    BankAccountFactory factory;

    @Test
    void createValidAccount() {
        BankAccount acc = factory.create(1, "Main", 500.0);

        assertEquals(1, acc.getId());
        assertEquals("Main", acc.getName());
        assertEquals(500.0, acc.getBalance());
    }

    @Test
    void createNegativeBalanceShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(2, "BadBalance", -100.0);
        });
    }
}
