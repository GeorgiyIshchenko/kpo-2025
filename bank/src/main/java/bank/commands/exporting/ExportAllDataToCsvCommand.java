package bank.commands.exporting;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.exporting.CsvExportVisitor;
import bank.services.FinanceService;

import java.util.List;

public class ExportAllDataToCsvCommand implements Command<String> {

    private final FinanceService financeService;

    public ExportAllDataToCsvCommand(FinanceService financeService) {
        this.financeService = financeService;
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

        return visitor.getCsvResult();
    }
}
