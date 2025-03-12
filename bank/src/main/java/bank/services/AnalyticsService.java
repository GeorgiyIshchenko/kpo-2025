package bank.services;

import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.OperationType;

import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsService {

    private final FinanceService financeService;

    public AnalyticsService(FinanceService financeService) {
        this.financeService = financeService;
    }

    public double getIncomeExpenseDifference(Date from, Date to) {
        List<Operation> allOps = financeService.getAllOperations();
        double income = 0.0;
        double expense = 0.0;

        for (Operation op : allOps) {
            if (isInRange(op.getDate(), from, to)) {
                if (op.getOperationType() == OperationType.DEPOSIT) {
                    income += op.getAmount();
                } else {
                    expense += op.getAmount();
                }
            }
        }
        return income - expense;
    }

    public Map<Category, List<Operation>> groupOperationsByCategory(Date from, Date to) {
        List<Operation> allOps = financeService.getAllOperations();
        Map<Category, List<Operation>> result = new HashMap<>();

        for (Operation op : allOps) {
            if (isInRange(op.getDate(), from, to)) {
                Category cat = financeService.getCategory(op.getCategoryId());
                if (cat != null) {
                    result.computeIfAbsent(cat, k -> new ArrayList<>()).add(op);
                }
            }
        }
        return result;
    }

    public List<Operation> getOperationsSortedByDate(Date from, Date to) {
        return financeService.getAllOperations().stream()
                .filter(op -> isInRange(op.getDate(), from, to))
                .sorted(Comparator.comparing(Operation::getDate))
                .collect(Collectors.toList());
    }

    private boolean isInRange(Date date, Date from, Date to) {
        // Оно может показаться странным, но это нужно, чтобы включить границы
        return !date.before(from) && !date.after(to);
    }
}
