package bank.commands.category;

import bank.commands.Command;
import bank.domains.Category;
import bank.enums.CategoryType;
import bank.services.FinanceService;

public class UpdateCategoryCommand implements Command<Category> {

    private final FinanceService financeService;
    private final int id;
    private final String newName;
    private final CategoryType newType;

    public UpdateCategoryCommand(FinanceService financeService,
                                 int id,
                                 String newName,
                                 CategoryType newType) {
        this.financeService = financeService;
        this.id = id;
        this.newName = newName;
        this.newType = newType;
    }

    @Override
    public Category execute() {
        return financeService.updateCategory(id, newName, newType);
    }
}
