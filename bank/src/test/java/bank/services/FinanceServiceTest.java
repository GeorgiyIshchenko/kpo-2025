package bank.services;

import bank.config.AppTestConfig;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.CategoryType;
import bank.enums.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = AppTestConfig.class)
class FinanceServiceTest {

    @Autowired
    private FinanceService financeService;

    @BeforeEach
    void setUp() {
        financeService.clear();
    }

    @Test
    void testCreateAndGetBankAccount() {
        BankAccount acc = financeService.createBankAccount(1, "Main", 1000.0);
        assertNotNull(acc);
        assertEquals(1, acc.getId());
        assertEquals("Main", acc.getName());
        assertEquals(1000.0, acc.getBalance(), 0.0001);

        BankAccount found = financeService.getBankAccount(1);
        assertNotNull(found);
        assertEquals(acc, found);
    }

    @Test
    void testDeleteBankAccount() {
        financeService.createBankAccount(2, "WillDelete", 500.0);
        assertNotNull(financeService.getBankAccount(2));

        financeService.deleteBankAccount(2);
        assertNull(financeService.getBankAccount(2));
    }

    @Test
    void testCreateAndGetCategory() {
        Category cat = financeService.createCategory(10, "Food", CategoryType.EXPENSE);
        assertNotNull(cat);
        assertEquals(10, cat.getId());
        assertEquals("Food", cat.getName());

        Category found = financeService.getCategory(10);
        assertEquals(cat, found);
    }

    @Test
    void testCreateAndGetOperation() {
        financeService.createBankAccount(5, "OpAcc", 300.0);

        Operation op = financeService.createOperation(
                100,
                OperationType.DEPOSIT,
                5,
                200,
                new Date(),
                "Test op",
                0
        );
        assertNotNull(op);
        assertEquals(100, op.getId());
        assertEquals(OperationType.DEPOSIT, op.getOperationType());
        assertEquals(5, op.getBankAccountId());
    }

    @Test
    void testUpdateBankAccount() {
        financeService.createBankAccount(3, "OldName", 999.0);
        BankAccount updated = financeService.updateBankAccount(3, "NewName", 1500.0);
        assertNotNull(updated);
        assertEquals("NewName", updated.getName());
        assertEquals(1500.0, updated.getBalance(), 0.0001);
    }

}