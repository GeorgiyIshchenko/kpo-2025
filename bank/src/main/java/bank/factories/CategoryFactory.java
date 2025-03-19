package bank.factories;

import bank.domains.Category;
import bank.enums.CategoryType;
import bank.factories.interfaces.InCategoryFactory;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory implements InCategoryFactory {

    public Category create(int id, String name, CategoryType type) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("У категории должно быть имя!");
        }
        return new Category(id, name, type);
    }

}
