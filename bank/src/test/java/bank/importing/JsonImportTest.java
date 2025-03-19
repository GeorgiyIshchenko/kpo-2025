package bank.importing;

import bank.domains.Operation;
import bank.enums.CategoryType;
import bank.enums.OperationType;
import bank.services.FinanceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class JsonImportTest {

    private FinanceService financeServiceMock;

    @BeforeEach
    void setUp() {
        financeServiceMock = Mockito.mock(FinanceService.class);
    }

    @Test
    void testImportDataValidJson() {
        AbstractImportTemplate importer = new JsonImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) throws IOException {
                return """
                       [
                         {
                           "entityType": "ACCOUNT",
                           "id": 1,
                           "name": "MainAcc",
                           "balance": 1200.0
                         },
                         {
                           "entityType": "CATEGORY",
                           "id": 5,
                           "name": "Cafe",
                           "categoryType": "EXPENSE"
                         },
                         {
                           "entityType": "OPERATION",
                           "id": 101,
                           "operationType": "DEPOSIT",
                           "bankAccountId": 1,
                           "amount": 300,
                           "date": 1695400000000,
                           "description": "TestDesc",
                           "categoryId": 5
                         }
                       ]
                       """;
            }
        };

        importer.importData("fake.json");

        verify(financeServiceMock, times(1))
                .createBankAccount(eq(1), eq("MainAcc"), eq(1200.0));
        verify(financeServiceMock, times(1))
                .createCategory(eq(5), eq("Cafe"), eq(CategoryType.EXPENSE));
        verify(financeServiceMock, times(1))
                .createOperation(eq(101), eq(OperationType.DEPOSIT), eq(1),
                        eq(300), any(Date.class), eq("TestDesc"), eq(5));
        verifyNoMoreInteractions(financeServiceMock);
    }

    @Test
    void testImportDataInvalidJson() {
        AbstractImportTemplate importer = new JsonImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) {
                return "{ sex json }";
            }
        };

        assertThrows(RuntimeException.class, () -> {
            importer.importData("broken.json");
        });

        verifyNoMoreInteractions(financeServiceMock);
    }

    @Test
    void testImportDataEmptyList() {
        AbstractImportTemplate importer = new JsonImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) {
                return "[]";
            }
        };
        importer.importData("empty.json");

        verifyNoMoreInteractions(financeServiceMock);
    }
}
