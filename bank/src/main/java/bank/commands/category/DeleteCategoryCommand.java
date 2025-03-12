package bank.commands.category;

import bank.commands.Command;
import bank.services.FinanceService;

public class DeleteCategoryCommand implements Command<Void> {

    private final FinanceService financeService;
    private final int id;

    public DeleteCategoryCommand(FinanceService financeService, int id) {
        this.financeService = financeService;
        this.id = id;
    }

    @Override
    public Void execute() {
        financeService.deleteCategory(id);
        return null;
    }
}
