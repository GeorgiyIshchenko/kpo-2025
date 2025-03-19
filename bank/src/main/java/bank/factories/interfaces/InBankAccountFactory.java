package bank.factories.interfaces;

import bank.domains.BankAccount;

public interface InBankAccountFactory {

    BankAccount create(int id, String name, double initialBalance);

}
