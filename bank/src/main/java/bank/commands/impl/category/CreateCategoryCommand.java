package bank.commands.impl.category;

import bank.commands.Command;
import bank.domains.Category;
import bank.enums.CategoryType;
import bank.services.FinanceService;

public class CreateCategoryCommand implements Command<Category> {

    private final FinanceService financeService;
    private final int id;
    private final String name;
    private final CategoryType type;

    public CreateCategoryCommand(FinanceService financeService,
                                 int id,
                                 String name,
                                 CategoryType type) {
        this.financeService = financeService;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public Category execute() {
        return financeService.createCategory(id, name, type);
    }
}
