package bank.commands.impl.operation;

import bank.commands.Command;
import bank.services.FinanceService;

public class DeleteOperationCommand implements Command<Void> {

    private final FinanceService financeService;
    private final int id;

    public DeleteOperationCommand(FinanceService financeService, int id) {
        this.financeService = financeService;
        this.id = id;
    }

    @Override
    public Void execute() {
        financeService.deleteOperation(id);
        return null;
    }
}
