package bank.commands.impl.bank_account;

import bank.commands.Command;
import bank.domains.BankAccount;
import bank.services.FinanceService;

import java.util.List;

public class GetAllBankAccountsCommand implements Command<List<BankAccount>> {

    private final FinanceService financeService;

    public GetAllBankAccountsCommand(FinanceService financeService) {
        this.financeService = financeService;
    }

    @Override
    public List<BankAccount> execute() {
        return financeService.getAllBankAccounts();
    }
}
