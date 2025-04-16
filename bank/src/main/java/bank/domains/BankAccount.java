package bank.domains;

import bank.exporting.interfaces.FinanceVisitor;
import bank.exporting.interfaces.Visitable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankAccount implements Visitable {

    public BankAccount(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public BankAccount() {}

    private int id;
    private String name;
    private double balance;

    @Override
    public void accept(FinanceVisitor visitor) {
        visitor.visit(this);
    }

}
