package bank.commands.impl.category;

import bank.commands.Command;
import bank.domains.Category;
import bank.services.FinanceService;

public class GetCategoryCommand implements Command<Category> {

    private final FinanceService financeService;
    private final int id;

    public GetCategoryCommand(FinanceService financeService, int id) {
        this.financeService = financeService;
        this.id = id;
    }

    @Override
    public Category execute() {
        return financeService.getCategory(id);
    }
}
