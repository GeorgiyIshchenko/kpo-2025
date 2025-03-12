package bank.services;

import bank.domains.BankAccount;
import bank.domains.Operation;
import bank.enums.OperationType;
import bank.services.FinanceService;

import java.util.List;

public class DataManagementService {

    private final FinanceService financeService;

    public DataManagementService(FinanceService financeService) {
        this.financeService = financeService;
    }

    public void recalculateBalance(int bankAccountId) {
        BankAccount account = financeService.getBankAccount(bankAccountId);
        if (account == null) {
            throw new IllegalArgumentException("Не найден счет id=" + bankAccountId);
        }

        double newBalance = 0.0;
        List<Operation> ops = financeService.getAllOperations();
        for (Operation op : ops) {
            if (op.getBankAccountId() == bankAccountId) {
                if (op.getOperationType() == OperationType.DEPOSIT) {
                    newBalance += op.getAmount();
                } else {
                    newBalance -= op.getAmount();
                }
            }
        }

        financeService.updateBankAccount(account.getId(), account.getName(), newBalance);
    }
}
