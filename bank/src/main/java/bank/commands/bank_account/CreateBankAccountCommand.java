package bank.commands.bank_account;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.services.FinanceService;

public class CreateBankAccountCommand implements Command<BankAccount> {

    private final FinanceService financeService;
    private final int id;
    private final String name;
    private final double initialBalance;

    public CreateBankAccountCommand(FinanceService financeService,
                                    int id,
                                    String name,
                                    double initialBalance) {
        this.financeService = financeService;
        this.id = id;
        this.name = name;
        this.initialBalance = initialBalance;
    }

    @Override
    public BankAccount execute() {
        return financeService.createBankAccount(id, name, initialBalance);
    }
}
