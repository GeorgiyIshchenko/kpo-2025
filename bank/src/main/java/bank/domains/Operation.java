package bank.domains;

import bank.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Operation {

    public Operation(int id, OperationType operationType, int bankAccountId, int amount, Date date, String description,
                     int categoryId) {
        this.id = id;
        this.operationType = operationType;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Operation() {}

    private int id;
    private OperationType operationType;
    private int bankAccountId;
    private int amount;
    private Date date;
    private String description;
    private int categoryId;

}
