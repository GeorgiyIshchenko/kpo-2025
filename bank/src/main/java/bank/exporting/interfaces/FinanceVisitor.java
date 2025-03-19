package bank.exporting.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;

public interface FinanceVisitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
}
