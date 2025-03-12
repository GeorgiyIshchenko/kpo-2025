package bank.exporting.interfaces;

public interface Visitable {
    void accept(FinanceVisitor visitor);
}
