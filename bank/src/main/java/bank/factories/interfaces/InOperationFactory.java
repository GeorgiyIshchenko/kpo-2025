package bank.factories.interfaces;

import bank.domains.Operation;
import bank.enums.OperationType;

import java.util.Date;

public interface InOperationFactory {

     Operation create(
            int id,
            OperationType type,
            int bankAccountId,
            int amount,
            Date date,
            String description,
            int categoryId);

}
