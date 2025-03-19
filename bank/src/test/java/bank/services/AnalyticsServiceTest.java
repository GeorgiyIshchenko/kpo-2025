package bank.services;

import bank.config.AppTestConfig;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = AppTestConfig.class)
class AnalyticsServiceTest {

    @Autowired
    private FinanceService financeService; // реальный сервис
    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        financeService.clear();
        analyticsService = new AnalyticsService(financeService);
    }

    @Test
    void testGetDEPOSITExpenseDifference() {
        financeService.createBankAccount(1, "TestAcc", 0);

        Date now = new Date();
        financeService.createOperation(100, OperationType.DEPOSIT, 1, 200, now, "Salary", 0);
        financeService.createOperation(101, OperationType.DEPOSIT, 1, 300, now, "Bonus", 0);
        financeService.createOperation(102, OperationType.WITHDRAWAL, 1, 100, now, "Food", 0);

        double difference = analyticsService.getIncomeExpenseDifference(now, now);

        assertEquals(400.0, difference, 0.001);
    }

    @Test
    void testGetOperationsSortedByDate() throws InterruptedException {
        financeService.createBankAccount(2, "Acc2", 0);

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JANUARY, 1, 10, 0); // 2025-01-01 10:00
        Date date1 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 1, 9, 0);
        Date date2 = cal.getTime();

        financeService.createOperation(200, OperationType.DEPOSIT, 2, 500, date1, "Op1", 0);
        financeService.createOperation(201, OperationType.WITHDRAWAL, 2, 100, date2, "Op2", 0);

        List<Operation> sorted = analyticsService.getOperationsSortedByDate(date2, date1);
        assertEquals(201, sorted.get(0).getId());
        assertEquals(200, sorted.get(1).getId());
    }

    @Test
    void testGroupOperationsByCategory() throws InterruptedException {
        financeService.createBankAccount(3, "Acc3", 0);

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JANUARY, 1, 10, 0); // 2025-01-01 10:00
        Date date1 = cal.getTime();

        cal.set(2025, Calendar.JANUARY, 1, 9, 0);
        Date date2 = cal.getTime();

        Category cat1 = financeService.createCategory(1, "TestCategory1", CategoryType.INCOME);
        Category cat2 = financeService.createCategory(2, "TestCategory2", CategoryType.EXPENSE);
        financeService.createOperation(300, OperationType.DEPOSIT, 3, 500, date1, "Op1", cat1.getId());
        financeService.createOperation(301, OperationType.WITHDRAWAL, 3, 100, date2, "Op2", cat2.getId());

        Map<Integer, List<Operation>> categorized = analyticsService.groupOperationsByCategoryId(date2, date1);

        assertEquals(1, categorized.get(cat1.getId()).size());
        assertEquals(300, categorized.get(cat1.getId()).getFirst().getId());
    }

}
