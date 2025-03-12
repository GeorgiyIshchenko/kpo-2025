package bank.commands.management;

import bank.commands.Command;
import bank.services.DataManagementService;

public class RecalculateBalanceCommand implements Command<Void> {

    private final DataManagementService dataManagementService;
    private final int bankAccountId;

    public RecalculateBalanceCommand(DataManagementService dataManagementService, int bankAccountId) {
        this.dataManagementService = dataManagementService;
        this.bankAccountId = bankAccountId;
    }

    @Override
    public Void execute() {
        dataManagementService.recalculateBalance(bankAccountId);
        return null;
    }
}
