package bank.commands.impl.bank_account;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.services.FinanceService;

public class GetBankAccountCommand implements Command<BankAccount> {

    private final FinanceService financeService;
    private final int id;

    public GetBankAccountCommand(FinanceService financeService, int id) {
        this.financeService = financeService;
        this.id = id;
    }

    @Override
    public BankAccount execute() {
        return financeService.getBankAccount(id);
    }
}
