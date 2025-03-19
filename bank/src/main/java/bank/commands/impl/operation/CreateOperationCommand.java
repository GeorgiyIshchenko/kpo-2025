package bank.commands.impl.operation;

import bank.commands.Command;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.services.FinanceService;

import java.util.Date;

public class CreateOperationCommand implements Command<Operation> {

    private final FinanceService financeService;
    private final int id;
    private final OperationType operationType;
    private final int bankAccountId;
    private final int amount;
    private final Date date;
    private final String description;
    private final int categoryId;

    public CreateOperationCommand(FinanceService financeService,
                                  int id,
                                  OperationType operationType,
                                  int bankAccountId,
                                  int amount,
                                  Date date,
                                  String description,
                                  int categoryId) {
        this.financeService = financeService;
        this.id = id;
        this.operationType = operationType;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    @Override
    public Operation execute() {
        return financeService.createOperation(id, operationType, bankAccountId, amount, date, description, categoryId);
    }
}
