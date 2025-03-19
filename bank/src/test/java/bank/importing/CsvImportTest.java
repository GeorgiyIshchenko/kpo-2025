package bank.importing;

import bank.enums.CategoryType;
import bank.enums.OperationType;
import bank.services.FinanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CsvImportTest {

    private FinanceService financeServiceMock;

    @BeforeEach
    void setUp() {
        financeServiceMock = Mockito.mock(FinanceService.class);
    }

    @Test
    void testImportDataValidCsv() {
        // Тут tricky мув, чтобы воспринимать файл как строку.
        AbstractImportTemplate importer = new CsvImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) throws IOException {
                return """
                       ACCOUNT,1,MainAccount,1000.0
                       CATEGORY,10,Food,EXPENSE
                       OPERATION,100,DEPOSIT,1,300,1695400000000,TestDescription,10
                       """;
            }
        };

        importer.importData("i_hate_sizyh.csv");

        verify(financeServiceMock, times(1))
                .createBankAccount(eq(1), eq("MainAccount"), eq(1000.0));

        verify(financeServiceMock, times(1))
                .createCategory(eq(10), eq("Food"), eq(CategoryType.EXPENSE));

        verify(financeServiceMock, times(1))
                .createOperation(eq(100), eq(OperationType.DEPOSIT), eq(1),
                        eq(300), any(Date.class), eq("TestDescription"), eq(10));

        verifyNoMoreInteractions(financeServiceMock);
    }

    @Test
    void testImportDataEmptyFile() {
        AbstractImportTemplate importer = new CsvImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) {
                return "";
            }
        };
        importer.importData("empty.csv");
        verify(financeServiceMock, never()).createBankAccount(anyInt(), anyString(), anyDouble());
        verify(financeServiceMock, never()).createCategory(anyInt(), anyString(), any());
        verify(financeServiceMock, never()).createOperation(anyInt(), any(), anyInt(), anyInt(), any(), anyString(), anyInt());
    }

    @Test
    void testImportDataInvalidLine() {
        AbstractImportTemplate importer = new CsvImport(financeServiceMock) {
            @Override
            protected String readFile(String filePath) {
                return "WRONG,FORMAT\nACCOUNT,1,Acc,500.0";
            }
        };

        importer.importData("fuck_tvims.csv");

        verify(financeServiceMock, times(1))
                .createBankAccount(eq(1), eq("Acc"), eq(500.0));
        verifyNoMoreInteractions(financeServiceMock);
    }
}
