package bank.importing;

import bank.enums.CategoryType;
import bank.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommonDTO {

    public static final String ENTITY_TYPE_ACCOUNT = "ACCOUNT";
    public static final String ENTITY_TYPE_CATEGORY = "CATEGORY";
    public static final String ENTITY_TYPE_OPERATION = "OPERATION";

    private String entityType;

    // Поля, характерные для любого домена
    private int id;
    private String name;

    // BankAccount
    private double balance;

    // Category
    private CategoryType categoryType;

    // Operation
    private OperationType operationType;
    private int bankAccountId;
    private int amount;
    private Date date;
    private String description;
    private int categoryId;

    public CommonDTO() {
    }
}
