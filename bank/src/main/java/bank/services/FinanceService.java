package bank.services;

import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.CategoryType;
import bank.enums.OperationType;
import bank.factories.interfaces.InBankAccountFactory;
import bank.factories.interfaces.InCategoryFactory;
import bank.factories.interfaces.InOperationFactory;
import bank.services.interfaces.InFinanceService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FinanceService implements InFinanceService {

    private final InBankAccountFactory bankAccountFactory;
    private final InCategoryFactory categoryFactory;
    private final InOperationFactory operationFactory;

    private final Map<Integer, BankAccount> bankAccounts = new HashMap<>();
    private final Map<Integer, Category> categories = new HashMap<>();
    private final Map<Integer, Operation> operations = new HashMap<>();

    public FinanceService(InBankAccountFactory bankAccountFactory,
                          InCategoryFactory categoryFactory,
                          InOperationFactory operationFactory) {
        this.bankAccountFactory = bankAccountFactory;
        this.categoryFactory = categoryFactory;
        this.operationFactory = operationFactory;
    }

    @Override
    public BankAccount createBankAccount(int id, String name, double initialBalance) {
        if (bankAccounts.containsKey(id)) {
            throw new IllegalArgumentException("BankAccount с таким id уже существует: " + id);
        }
        BankAccount newAcc = bankAccountFactory.create(id, name, initialBalance);
        bankAccounts.put(id, newAcc);
        return newAcc;
    }

    @Override
    public BankAccount updateBankAccount(int id, String newName, double newBalance) {
        BankAccount account = bankAccounts.get(id);
        if (account == null) {
            throw new NoSuchElementException("BankAccount с id " + id + " не найден");
        }
        if (newBalance < 0) {
            throw new IllegalArgumentException("Баланс не может быть отрицательным");
        }
        account.setName(newName);
        account.setBalance(newBalance);
        return account;
    }

    @Override
    public void deleteBankAccount(int id) {
        if (!bankAccounts.containsKey(id)) {
            throw new NoSuchElementException("BankAccount с id " + id + " не найден");
        }
        bankAccounts.remove(id);
    }

    @Override
    public BankAccount getBankAccount(int id) {
        return bankAccounts.get(id);
    }

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return new ArrayList<>(bankAccounts.values());
    }

    @Override
    public Category createCategory(int id, String name, CategoryType type) {
        if (categories.containsKey(id)) {
            throw new IllegalArgumentException("Category с таким id уже существует: " + id);
        }
        Category newCat = categoryFactory.create(id, name, type);
        categories.put(id, newCat);
        return newCat;
    }

    @Override
    public Category updateCategory(int id, String newName, CategoryType newType) {
        Category category = categories.get(id);
        if (category == null) {
            throw new NoSuchElementException("Category с id " + id + " не найден");
        }
        category.setName(newName);
        category.setType(newType);
        return category;
    }

    @Override
    public void deleteCategory(int id) {
        if (!categories.containsKey(id)) {
            throw new NoSuchElementException("Category с id " + id + " не найден");
        }
        categories.remove(id);
    }

    @Override
    public Category getCategory(int id) {
        return categories.get(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Operation createOperation(int id,
                                     OperationType operationType,
                                     int bankAccountId,
                                     int amount,
                                     Date date,
                                     String description,
                                     int categoryId) {
        if (operations.containsKey(id)) {
            throw new IllegalArgumentException("Operation с таким id уже существует: " + id);
        }
        Operation newOp = operationFactory.create(id, operationType, bankAccountId, amount, date, description, categoryId);
        operations.put(id, newOp);
        return newOp;
    }

    @Override
    public Operation updateOperation(int id,
                                     OperationType newType,
                                     int newBankAccountId,
                                     int newAmount,
                                     Date newDate,
                                     String newDescription,
                                     int newCategoryId) {
        Operation operation = operations.get(id);
        if (operation == null) {
            throw new NoSuchElementException("Operation с id " + id + " не найден");
        }
        if (newAmount <= 0) {
            throw new IllegalArgumentException("Сумма операции должна быть > 0!");
        }
        if (newDate == null) {
            throw new IllegalArgumentException("У операции должна быть дата!");
        }
        operation.setOperationType(newType);
        operation.setBankAccountId(newBankAccountId);
        operation.setAmount(newAmount);
        operation.setDate(newDate);
        operation.setDescription(newDescription);
        operation.setCategoryId(newCategoryId);
        return operation;
    }

    @Override
    public void deleteOperation(int id) {
        if (!operations.containsKey(id)) {
            throw new NoSuchElementException("Operation с id " + id + " не найден");
        }
        operations.remove(id);
    }

    @Override
    public Operation getOperation(int id) {
        return operations.get(id);
    }

    @Override
    public List<Operation> getAllOperations() {
        return new ArrayList<>(operations.values());
    }

}
