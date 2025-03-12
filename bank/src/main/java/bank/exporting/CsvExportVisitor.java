package bank.exporting;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.exporting.interfaces.FinanceVisitor;

import java.util.ArrayList;
import java.util.List;

public class CsvExportVisitor implements FinanceVisitor {

    private final List<String> lines = new ArrayList<>();

    @Override
    public void visit(BankAccount account) {
        String line = String.format("ACCOUNT,%d,%s,%.2f",
                account.getId(), account.getName(), account.getBalance());
        lines.add(line);
    }

    @Override
    public void visit(Category category) {
        String line = String.format("CATEGORY,%d,%s,%s",
                category.getId(), category.getName(), category.getType());
        lines.add(line);
    }

    @Override
    public void visit(Operation operation) {
        String line = String.format("OPERATION,%d,%s,%d,%d,%s,%s,%d",
                operation.getId(),
                operation.getOperationType(),
                operation.getBankAccountId(),
                operation.getAmount(),
                operation.getDate(),
                operation.getDescription(),
                operation.getCategoryId());
        lines.add(line);
    }

    public String getCsvResult() {
        return String.join("\n", lines);
    }
}
