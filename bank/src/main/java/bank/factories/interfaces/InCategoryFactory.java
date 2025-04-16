package bank.factories.interfaces;

import bank.domains.Category;
import bank.enums.CategoryType;

public interface InCategoryFactory {

    Category create(int id, String name, CategoryType type);

}
