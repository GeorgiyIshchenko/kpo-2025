package bank.commands.impl.exporting;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.exporting.JsonExportVisitor;
import bank.services.FinanceService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class ExportAllDataToJsonCommand implements Command<Void> {

    private final FinanceService financeService;
    private final String filePath;

    public ExportAllDataToJsonCommand(FinanceService financeService, String filePath) {
        this.financeService = financeService;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        List<BankAccount> accounts = financeService.getAllBankAccounts();
        List<Category> categories = financeService.getAllCategories();
        List<Operation> operations = financeService.getAllOperations();

        JsonExportVisitor visitor = new JsonExportVisitor();

        accounts.forEach(a -> a.accept(visitor));
        categories.forEach(c -> c.accept(visitor));
        operations.forEach(o -> o.accept(visitor));

        String jsonResult = null;
        try {
            jsonResult = visitor.getJsonResult();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (filePath != null && !filePath.isEmpty()) {
            System.out.println("Data exported to JSON file: " + filePath);
        }

        System.out.println("=== JSON Export Result ===");
        System.out.println(jsonResult);

        if (filePath != null && !filePath.isEmpty()) {
            visitor.writeToFile(filePath);
        }

        return null;
    }
}