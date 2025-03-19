package bank.commands.impl.operation;

import bank.commands.Command;
import bank.domains.Operation;
import bank.services.FinanceService;

public class GetOperationCommand implements Command<Operation> {

    private final FinanceService financeService;
    private final int id;

    public GetOperationCommand(FinanceService financeService, int id) {
        this.financeService = financeService;
        this.id = id;
    }

    @Override
    public Operation execute() {
        return financeService.getOperation(id);
    }
}
