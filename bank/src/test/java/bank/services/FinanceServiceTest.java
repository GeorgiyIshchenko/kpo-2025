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

    @Test
    void testUpdateOperation() {
        financeService.createBankAccount(10, "TestAccount", 1000.0);
        Date now = new Date();
        Operation op = financeService.createOperation(200, OperationType.DEPOSIT, 10, 300, now,
                "Initial Operation", 0);
        assertNotNull(op);

        Date newDate = new Date(now.getTime() + 10000);
        Operation updatedOp = financeService.updateOperation(
                200,
                OperationType.WITHDRAWAL,
                10,
                150,
                newDate,
                "Updated Operation",
                1
        );
        assertNotNull(updatedOp);
        assertEquals(200, updatedOp.getId());
        assertEquals(OperationType.WITHDRAWAL, updatedOp.getOperationType());
        assertEquals(150, updatedOp.getAmount());
        assertEquals(newDate, updatedOp.getDate());
        assertEquals("Updated Operation", updatedOp.getDescription());
        assertEquals(1, updatedOp.getCategoryId());
    }

    @Test
    void testUpdateCategory() {
        Category cat = financeService.createCategory(100, "OldCategory", CategoryType.EXPENSE);
        assertNotNull(cat);
        Category updatedCat = financeService.updateCategory(100, "NewCategory", CategoryType.INCOME);
        assertNotNull(updatedCat);
        assertEquals(100, updatedCat.getId());
        assertEquals("NewCategory", updatedCat.getName());
        assertEquals(CategoryType.INCOME, updatedCat.getType());
    }

    @Test
    void testDeleteCategory() {
        Category cat = financeService.createCategory(101, "DeleteCategory", CategoryType.EXPENSE);
        assertNotNull(financeService.getCategory(cat.getId()));
        financeService.deleteCategory(cat.getId());
        assertNull(financeService.getCategory(cat.getId()));
    }

    @Test
    void testDeleteOperation() {
        financeService.createBankAccount(11, "AccountForOp", 1000.0);
        Date now = new Date();
        Operation op = financeService.createOperation(300, OperationType.DEPOSIT, 11, 250, now,
                "To be deleted", 0);
        assertNotNull(financeService.getOperation(op.getId()));
        financeService.deleteOperation(op.getId());
        assertNull(financeService.getOperation(op.getId()));
    }

    @Test
    void testGetAllBankAccounts() {
        financeService.createBankAccount(20, "Acc1", 500.0);
        financeService.createBankAccount(21, "Acc2", 1500.0);
        financeService.createBankAccount(22, "Acc3", 2500.0);

        List<BankAccount> accounts = financeService.getAllBankAccounts();
        assertTrue(accounts.size() >= 3);
        boolean hasAcc20 = accounts.stream().anyMatch(a -> a.getId() == 20);
        boolean hasAcc21 = accounts.stream().anyMatch(a -> a.getId() == 21);
        boolean hasAcc22 = accounts.stream().anyMatch(a -> a.getId() == 22);
        assertTrue(hasAcc20 && hasAcc21 && hasAcc22);
    }


    @Test
    void testGetOperation() {
        financeService.createBankAccount(12, "AccForOp", 800.0);
        Date now = new Date();
        Operation op = financeService.createOperation(400, OperationType.WITHDRAWAL, 12,
                100, now, "Test Op", 2);
        Operation fetchedOp = financeService.getOperation(op.getId());
        assertNotNull(fetchedOp);
        assertEquals(op.getId(), fetchedOp.getId());
    }

    @Test
    void testGetCategory() {
        Category cat = financeService.createCategory(105, "TestCat", CategoryType.INCOME);
        Category fetchedCat = financeService.getCategory(cat.getId());
        assertNotNull(fetchedCat);
        assertEquals("TestCat", fetchedCat.getName());
    }

}