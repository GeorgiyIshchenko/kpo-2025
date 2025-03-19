package bank.importing;

import bank.enums.CategoryType;
import bank.enums.OperationType;
import bank.services.FinanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class YamlImportTest {

    private FinanceService financeServiceMock;

    @BeforeEach
    void setUp() {
        financeServiceMock = Mockito.mock(FinanceService.class);
    }

    @Test
    void testImportDataValidYaml() {
        AbstractImportTemplate importer = new YamlImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) throws IOException {
                return """
                  - entityType: "ACCOUNT"
                    id: 1
                    name: "Main Account"
                    balance: 1000.0
                  - entityType: "CATEGORY"
                    id: 10
                    name: "Food"
                    type: "EXPENSE"
                  - entityType: "OPERATION"
                    id: 100
                    operationType: "DEPOSIT"
                    bankAccountId: 1
                    amount: 300
                    date: 1695400000000
                    description: "Desc"
                    categoryId: 10
                """;
            }
        };

        importer.importData("fake.yaml");

        verify(financeServiceMock, times(1))
                .createBankAccount(eq(1), eq("Main Account"), eq(1000.0));

        verify(financeServiceMock, times(1))
                .createCategory(eq(10), eq("Food"), eq(CategoryType.EXPENSE));

        verify(financeServiceMock, times(1))
                .createOperation(eq(100),
                        eq(OperationType.DEPOSIT),
                        eq(1),
                        eq(300),
                        any(Date.class),
                        eq("Desc"),
                        eq(10));

        verifyNoMoreInteractions(financeServiceMock);
    }

    @Test
    void testImportDataEmptyContent() {
        AbstractImportTemplate importer = new YamlImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) {
                return "";
            }
        };
        importer.importData("empty.yaml");
        verifyNoInteractions(financeServiceMock);
    }

    @Test
    void testImportDataBadYaml() {
        AbstractImportTemplate importer = new YamlImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) {
                return "{ trap: yaml, missing array }";
            }
        };

        assertThrows(RuntimeException.class, () -> {
            importer.importData("broken.yaml");
        });

        verifyNoInteractions(financeServiceMock);
    }
}