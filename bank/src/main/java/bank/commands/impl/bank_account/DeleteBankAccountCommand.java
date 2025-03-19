package bank.commands.impl.bank_account;

import bank.commands.Command;
import bank.services.FinanceService;

public class DeleteBankAccountCommand implements Command<Void> {

    private final FinanceService financeService;
    private final int id;

    public DeleteBankAccountCommand(FinanceService financeService, int id) {
        this.financeService = financeService;
        this.id = id;
    }

    @Override
    public Void execute() {
        financeService.deleteBankAccount(id);
        return null; // Ничего не возвращаем
    }
}