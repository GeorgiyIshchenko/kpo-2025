package bank.commands.operation;

import bank.commands.Command;
import bank.domains.Operation;
import bank.services.FinanceService;

import java.util.List;

public class GetAllOperationsCommand implements Command<List<Operation>> {

    private final FinanceService financeService;

    public GetAllOperationsCommand(FinanceService financeService) {
        this.financeService = financeService;
    }

    @Override
    public List<Operation> execute() {
        return financeService.getAllOperations();
    }
}
