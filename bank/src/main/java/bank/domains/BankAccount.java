package bank.domains;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankAccount {

    public BankAccount(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public BankAccount() {}

    private int id;
    private String name;
    private double balance;

}
