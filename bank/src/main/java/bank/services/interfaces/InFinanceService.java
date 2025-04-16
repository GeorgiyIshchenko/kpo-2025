package bank.services.interfaces;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.CategoryType;
import bank.enums.OperationType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface InFinanceService {
    BankAccount createBankAccount(int id, String name, double initialBalance);

    BankAccount updateBankAccount(int id, String newName, double newBalance);

    void deleteBankAccount(int id);

    BankAccount getBankAccount(int id);

    List<BankAccount> getAllBankAccounts();

    Category createCategory(int id, String name, CategoryType type);

    Category updateCategory(int id, String newName, CategoryType newType);

    void deleteCategory(int id);

    Category getCategory(int id);

    List<Category> getAllCategories();

    Operation createOperation(int id,
                              OperationType operationType,
                              int bankAccountId,
                              int amount,
                              Date date,
                              String description,
                              int categoryId);

    Operation updateOperation(int id,
                              OperationType newType,
                              int newBankAccountId,
                              int newAmount,
                              Date newDate,
                              String newDescription,
                              int newCategoryId);

    void deleteOperation(int id);

    Operation getOperation(int id);

    List<Operation> getAllOperations();
}
