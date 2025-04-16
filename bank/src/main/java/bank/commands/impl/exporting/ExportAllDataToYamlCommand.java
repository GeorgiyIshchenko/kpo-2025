package bank.commands.impl.exporting;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.exporting.YamlExportVisitor;
import bank.services.FinanceService;

import java.util.List;


public class ExportAllDataToYamlCommand implements Command<Void> {

    private final FinanceService financeService;
    private final String filePath;

    public ExportAllDataToYamlCommand(FinanceService financeService, String filePath) {
        this.financeService = financeService;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        List<BankAccount> accounts = financeService.getAllBankAccounts();
        List<Category> categories = financeService.getAllCategories();
        List<Operation> operations = financeService.getAllOperations();

        YamlExportVisitor visitor = new YamlExportVisitor();

        accounts.forEach(a -> a.accept(visitor));
        categories.forEach(c -> c.accept(visitor));
        operations.forEach(o -> o.accept(visitor));

        String yamlResult = visitor.getYamlResult();

        if (filePath != null && !filePath.isEmpty()) {
            System.out.println("Data exported to YAML file: " + filePath);
        }

        System.out.println("=== YAML Export Result ===");
        System.out.println(yamlResult);

        if (filePath != null && !filePath.isEmpty()) {
            visitor.writeToFile(filePath);
        }

        return null;
    }
}