package bank.factories;

import bank.domains.BankAccount;
import bank.factories.interfaces.InBankAccountFactory;
import org.springframework.stereotype.Component;

@Component
public class BankAccountFactory implements InBankAccountFactory {

    public BankAccount create(int id, String name, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Баланс не может быть отрицательным!");
        }
        return new BankAccount(id, name, initialBalance);
    }

}
