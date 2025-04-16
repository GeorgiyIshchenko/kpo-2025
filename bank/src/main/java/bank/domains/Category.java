package bank.domains;

import bank.exporting.interfaces.FinanceVisitor;
import bank.exporting.interfaces.Visitable;
import bank.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Category implements Visitable {

    public Category(int id, String name, CategoryType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    private int id;
    private String name;
    private CategoryType type;

    @Override
    public void accept(FinanceVisitor visitor) {
        visitor.visit(this);
    }

}
