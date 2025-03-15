package bank.services;

import bank.config.AppTestConfig;
import bank.domains.BankAccount;
import bank.domains.Operation;
import bank.enums.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = AppTestConfig.class)
class DataManagementServiceTest {

    @Autowired
    private FinanceService financeService;

    private DataManagementService dataManagementService;

    @BeforeEach
    void setUp() {
        dataManagementService = new DataManagementService(financeService);
    }

    @Test
    void testRecalculateBalance() {
        BankAccount mockAccount = new BankAccount(1, "MockAcc", 999.0);
        Mockito.when(financeService.getBankAccount(1)).thenReturn(mockAccount);

        Operation op1 = new Operation(101, OperationType.DEPOSIT, 1, 200, null, null, 0);
        Operation op2 = new Operation(102, OperationType.DEPOSIT, 1, 300, null, null, 0);
        Operation op3 = new Operation(103, OperationType.WITHDRAWAL, 1, 100, null, null, 0);
        Mockito.when(financeService.getAllOperations()).thenReturn(Arrays.asList(op1, op2, op3));

        dataManagementService.recalculateBalance(1);

        Mockito.verify(financeService).updateBankAccount(1, "MockAcc", 400.0);

        Mockito.verify(financeService, Mockito.times(1)).getBankAccount(1);
        Mockito.verify(financeService, Mockito.times(1)).getAllOperations();
    }
}
