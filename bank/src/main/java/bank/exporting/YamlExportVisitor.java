package bank.exporting;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.exporting.interfaces.FinanceVisitor;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class YamlExportVisitor implements FinanceVisitor {

    private final List<Map<String, Object>> exportData = new ArrayList<>();

    @Override
    public void visit(BankAccount account) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("entityType", "ACCOUNT");
        item.put("id", account.getId());
        item.put("name", account.getName());
        item.put("balance", account.getBalance());
        exportData.add(item);
    }

    @Override
    public void visit(Category category) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("entityType", "CATEGORY");
        item.put("id", category.getId());
        item.put("name", category.getName());
        item.put("type", category.getType());
        exportData.add(item);
    }

    @Override
    public void visit(Operation operation) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("entityType", "OPERATION");
        item.put("id", operation.getId());
        item.put("operationType", operation.getOperationType());
        item.put("bankAccountId", operation.getBankAccountId());
        item.put("amount", operation.getAmount());
        item.put("date", operation.getDate());
        item.put("description", operation.getDescription());
        item.put("categoryId", operation.getCategoryId());
        exportData.add(item);
    }

    public String getYamlResult() {
        Yaml yaml = new Yaml();
        return yaml.dump(exportData);
    }
}
