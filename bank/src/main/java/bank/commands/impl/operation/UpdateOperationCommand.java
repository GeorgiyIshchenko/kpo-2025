package bank.commands.impl.operation;

import bank.commands.Command;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.services.FinanceService;

import java.util.Date;

public class UpdateOperationCommand implements Command<Operation> {

    private final FinanceService financeService;
    private final int id;
    private final OperationType newType;
    private final int newBankAccountId;
    private final int newAmount;
    private final Date newDate;
    private final String newDescription;
    private final int newCategoryId;

    public UpdateOperationCommand(FinanceService financeService,
                                  int id,
                                  OperationType newType,
                                  int newBankAccountId,
                                  int newAmount,
                                  Date newDate,
                                  String newDescription,
                                  int newCategoryId) {
        this.financeService = financeService;
        this.id = id;
        this.newType = newType;
        this.newBankAccountId = newBankAccountId;
        this.newAmount = newAmount;
        this.newDate = newDate;
        this.newDescription = newDescription;
        this.newCategoryId = newCategoryId;
    }

    @Override
    public Operation execute() {
        return financeService.updateOperation(
                id,
                newType,
                newBankAccountId,
                newAmount,
                newDate,
                newDescription,
                newCategoryId
        );
    }
}
