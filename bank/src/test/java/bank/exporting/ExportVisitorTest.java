package bank.exporting;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.CategoryType;
import bank.enums.OperationType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExportVisitorTest {

    private List<BankAccount> testAccounts;
    private List<Category> testCategories;
    private List<Operation> testOperations;

    @BeforeEach
    void setUp() {
        testAccounts = new ArrayList<>();
        testAccounts.add(new BankAccount(1, "Main Account", 1000.0));
        testAccounts.add(new BankAccount(2, "Savings", 5000.0));

        testCategories = new ArrayList<>();
        testCategories.add(new Category(10, "Food", CategoryType.EXPENSE));
        testCategories.add(new Category(11, "Salary", CategoryType.INCOME));

        testOperations = new ArrayList<>();
        testOperations.add(new Operation(100, OperationType.DEPOSIT, 1, 300, new Date(1695300000000L), "Description1", 11));
        testOperations.add(new Operation(101, OperationType.WITHDRAWAL, 1, 50, new Date(1695303600000L), "Description2", 10));
        testOperations.add(new Operation(102, OperationType.DEPOSIT, 2, 200, new Date(1695307200000L), "Description3", 11));
        testOperations.add(new Operation(103, OperationType.WITHDRAWAL, 2, 100, new Date(1695310800000L), "Description4", 10));
    }

    @Test
    void testCsvExport() {
        CsvExportVisitor csvVisitor = new CsvExportVisitor();
        testAccounts.forEach(a -> a.accept(csvVisitor));
        testCategories.forEach(c -> c.accept(csvVisitor));
        testOperations.forEach(o -> o.accept(csvVisitor));

        String csvResult = csvVisitor.getCsvResult();
        assertNotNull(csvResult);
        String[] lines = csvResult.split("\n");
        assertEquals(8, lines.length);

        assertTrue(lines[0].startsWith("ACCOUNT,1,Main Account,1000"));
        assertTrue(lines[1].startsWith("ACCOUNT,2,Savings,5000"));

        assertTrue(lines[2].contains("CATEGORY,10,Food,EXPENSE"));
        assertTrue(lines[3].contains("CATEGORY,11,Salary,INCOME"));

        assertTrue(lines[4].contains("OPERATION,100,DEPOSIT,1,300"));
        assertTrue(lines[5].contains("OPERATION,101,WITHDRAWAL,1,50"));
    }

    @Test
    void testCsvExportEmptyLists() {
        CsvExportVisitor csvVisitor = new CsvExportVisitor();
        String result = csvVisitor.getCsvResult();
        assertNotNull(result);
        assertTrue(result.isEmpty(), "CSV должен быть пустым при отсутствии объектов");
    }

    @Test
    void testJsonExport() throws Exception {
        JsonExportVisitor jsonVisitor = new JsonExportVisitor();
        testAccounts.forEach(a -> a.accept(jsonVisitor));
        testCategories.forEach(c -> c.accept(jsonVisitor));
        testOperations.forEach(o -> o.accept(jsonVisitor));

        String json = jsonVisitor.getJsonResult();
        assertNotNull(json);

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<>() {});
        // 2 аккаунта 2 категории 4 операции
        assertEquals(8, list.size());

        Map<String, Object> account1 = list.stream()
                .filter(e -> "ACCOUNT".equals(e.get("entityType")) && (int) e.get("id") == 1)
                .findFirst()
                .orElse(null);
        assertNotNull(account1);
        assertEquals("Main Account", account1.get("name"));
        assertEquals(1000.0, (double) account1.get("balance"), 0.0001);

        Map<String, Object> cat10 = list.stream()
                .filter(e -> "CATEGORY".equals(e.get("entityType")) && (int) e.get("id") == 10)
                .findFirst()
                .orElse(null);
        assertNotNull(cat10);
        assertEquals("Food", cat10.get("name"));

        assertEquals("EXPENSE", cat10.get("type"));

        Map<String, Object> op100 = list.stream()
                .filter(e -> "OPERATION".equals(e.get("entityType")) && (int) e.get("id") == 100)
                .findFirst()
                .orElse(null);
        assertNotNull(op100);
        assertEquals("DEPOSIT", op100.get("operationType"));
        assertEquals(300, (int) op100.get("amount"));
    }

    @Test
    void testJsonExportEmptyLists() throws Exception {
        JsonExportVisitor jsonVisitor = new JsonExportVisitor();
        String result = jsonVisitor.getJsonResult();
        assertEquals("[]", result);
    }

    @Test
    @SuppressWarnings("unchecked") // Понятно, что в промышленном коде не надо так делать, но сейчас я уверен + на яйцах
    void testYamlExport() {
        YamlExportVisitor yamlVisitor = new YamlExportVisitor();
        testAccounts.forEach(a -> a.accept(yamlVisitor));
        testCategories.forEach(c -> c.accept(yamlVisitor));
        testOperations.forEach(o -> o.accept(yamlVisitor));

        String yaml = yamlVisitor.getYamlResult();
        assertNotNull(yaml);

        Yaml snake = new Yaml();
        List<Map<String, Object>> list = (List<Map<String, Object>>) snake.load(yaml);
        assertEquals(8, list.size());

        Map<String, Object> account2 = list.stream()
                .filter(e -> "ACCOUNT".equals(e.get("entityType")) && (int) e.get("id") == 2)
                .findFirst()
                .orElse(null);
        assertNotNull(account2);
        assertEquals("Savings", account2.get("name"));
        assertEquals(5000.0, (double) account2.get("balance"), 0.0001);

        Map<String, Object> op101 = list.stream()
                .filter(e -> "OPERATION".equals(e.get("entityType")) && (int) e.get("id") == 101)
                .findFirst()
                .orElse(null);
        assertNotNull(op101);
        assertEquals(OperationType.WITHDRAWAL, op101.get("operationType"));
        assertEquals(50, (int) op101.get("amount"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testYamlExportEmptyLists() {
        YamlExportVisitor yamlVisitor = new YamlExportVisitor();
        String yaml = yamlVisitor.getYamlResult();
        assertNotNull(yaml);
        Yaml snake = new Yaml();
        List<Map<String, Object>> list = (List<Map<String, Object>>) snake.load(yaml);
        assertTrue(list == null || list.isEmpty());
    }
}
