package bank.commands.impl.category;

import bank.commands.Command;
import bank.domains.Category;
import bank.services.FinanceService;

import java.util.List;

public class GetAllCategoriesCommand implements Command<List<Category>> {

    private final FinanceService financeService;

    public GetAllCategoriesCommand(FinanceService financeService) {
        this.financeService = financeService;
    }

    @Override
    public List<Category> execute() {
        return financeService.getAllCategories();
    }
}
