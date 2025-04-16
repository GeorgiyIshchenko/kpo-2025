


package bank.commands.impl.exporting;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.exporting.CsvExportVisitor;
import bank.services.FinanceService;

import java.util.List;

public class ExportAllDataToCsvCommand implements Command<String> {

    private final FinanceService financeService;
    private final String path;

    public ExportAllDataToCsvCommand(FinanceService financeService, String path) {
        this.financeService = financeService;
        this.path = path;
    }

    @Override
    public String execute() {
        List<BankAccount> accounts = financeService.getAllBankAccounts();
        List<Category> categories = financeService.getAllCategories();
        List<Operation> operations = financeService.getAllOperations();

        CsvExportVisitor visitor = new CsvExportVisitor();

        accounts.forEach(acc -> acc.accept(visitor));
        categories.forEach(cat -> cat.accept(visitor));
        operations.forEach(op -> op.accept(visitor));

        if (path != null && !path.isEmpty()) {
            visitor.writeToFile(path);
        }

        return visitor.getCsvResult();
    }
}
