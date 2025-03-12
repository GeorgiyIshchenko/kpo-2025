package bank.commands.bank_account;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.services.FinanceService;

public class UpdateBankAccountCommand implements Command<BankAccount> {

    private final FinanceService financeService;
    private final int id;
    private final String newName;
    private final double newBalance;

    public UpdateBankAccountCommand(FinanceService financeService,
                                    int id,
                                    String newName,
                                    double newBalance) {
        this.financeService = financeService;
        this.id = id;
        this.newName = newName;
        this.newBalance = newBalance;
    }

    @Override
    public BankAccount execute() {
        return financeService.updateBankAccount(id, newName, newBalance);
    }
}
