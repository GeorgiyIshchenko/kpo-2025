package bank.factories;

import bank.domains.Operation;
import bank.enums.OperationType;
import bank.factories.interfaces.InOperationFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OperationFactory implements InOperationFactory {

    public Operation create(
            int id,
            OperationType type,
            int bankAccountId,
            int amount,
            Date date,
            String description,
            int categoryId)
    {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма операции должна быть > 0!");
        }
        if (date == null) {
            throw new IllegalArgumentException("У операции должна быть дата!");
        }
        return new Operation(id, type, bankAccountId, amount, date, description, categoryId);
    }

}
