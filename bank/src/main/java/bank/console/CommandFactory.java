package bank.console;

import bank.commands.Command;
import bank.commands.CommandRegistry;
import bank.commands.impl.analytics.GetIncomeExpenseDifferenceCommand;
import bank.commands.impl.analytics.GetSortedOperationsCommand;
import bank.commands.impl.analytics.GroupOperationsByCategoryCommand;
import bank.commands.impl.bank_account.*;
import bank.commands.impl.category.*;
import bank.commands.impl.exporting.ExportAllDataToCsvCommand;
import bank.commands.impl.exporting.ExportAllDataToJsonCommand;
import bank.commands.impl.exporting.ExportAllDataToYamlCommand;
import bank.commands.impl.importing.ImportFromCsvCommand;
import bank.commands.impl.importing.ImportFromJsonCommand;
import bank.commands.impl.importing.ImportFromYamlCommand;
import bank.commands.impl.management.RecalculateBalanceCommand;
import bank.commands.impl.operation.*;
import bank.enums.CategoryType;
import bank.enums.OperationType;
import bank.importing.CsvImport;
import bank.services.AnalyticsService;
import bank.services.DataManagementService;
import bank.services.FinanceService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Hardcoded command factory: creates command objects by name + list of arguments,
 * no reflection needed.
 */
public class CommandFactory {

    private final CommandRegistry registry;
    private final FinanceService financeService;
    private final AnalyticsService analyticsService;
    private final DataManagementService dataManagementService;

    public CommandFactory(CommandRegistry registry,
                          FinanceService financeService,
                          AnalyticsService analyticsService,
                          DataManagementService dataManagementService) {
        this.registry = registry;
        this.financeService = financeService;
        this.analyticsService = analyticsService;
        this.dataManagementService = dataManagementService;
    }

    public Command<?> createCommand(String className, List<String> argStrings) throws Exception {
        if (registry.getCommandClass(className) == null) {
            throw new ClassNotFoundException("Unknown command class: " + className);
        }

        switch (className) {
            case "CreateBankAccount" -> {
                if (argStrings.size() < 3) {
                    throw new IllegalArgumentException("CreateBankAccountCommand requires 3 arguments: id, name, balance " + "given " + argStrings.size());
                }
                int id = parseInt(argStrings.get(0));
                String name = stripQuotes(argStrings.get(1));
                double balance = parseDouble(argStrings.get(2));
                return new CreateBankAccountCommand(financeService, id, name, balance);
            }
            case "DeleteBankAccount" -> {
                if (argStrings.size() < 1) {
                    throw new IllegalArgumentException("DeleteBankAccountCommand requires 1 argument: id");
                }
                int id = parseInt(argStrings.get(0));
                return new DeleteBankAccountCommand(financeService, id);
            }
            case "GetAllBankAccounts" -> {
                return new GetAllBankAccountsCommand(financeService);
            }
            case "GetBankAccount" -> {
                if (argStrings.size() < 1) {
                    throw new IllegalArgumentException("GetBankAccountCommand requires 1 argument: id");
                }
                int id = parseInt(argStrings.get(0));
                return new GetBankAccountCommand(financeService, id);
            }
            case "UpdateBankAccount" -> {
                if (argStrings.size() < 3) {
                    throw new IllegalArgumentException("UpdateBankAccountCommand requires 3 arguments: id, newName, newBalance");
                }
                int id = parseInt(argStrings.get(0));
                String newName = stripQuotes(argStrings.get(1));
                double newBalance = parseDouble(argStrings.get(2));
                return new UpdateBankAccountCommand(financeService, id, newName, newBalance);
            }

            case "CreateCategory" -> {
                if (argStrings.size() < 3) {
                    throw new IllegalArgumentException("CreateCategoryCommand requires 3 arguments: id, name, type");
                }
                int id = parseInt(argStrings.get(0));
                String name = stripQuotes(argStrings.get(1));
                CategoryType type = parseCategoryType(argStrings.get(2));
                return new CreateCategoryCommand(financeService, id, name, type);
            }
            case "DeleteCategory" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("DeleteCategoryCommand requires 1 argument: id");
                }
                int id = parseInt(argStrings.get(0));
                return new DeleteCategoryCommand(financeService, id);
            }
            case "GetAllCategories" -> {
                return new GetAllCategoriesCommand(financeService);
            }
            case "GetCategory" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("GetCategoryCommand requires 1 argument: id");
                }
                int id = parseInt(argStrings.get(0));
                return new GetCategoryCommand(financeService, id);
            }
            case "UpdateCategory" -> {
                if (argStrings.size() < 3) {
                    throw new IllegalArgumentException("UpdateCategoryCommand requires 3 arguments: id, newName, newType");
                }
                int catId = parseInt(argStrings.get(0));
                String newName = stripQuotes(argStrings.get(1));
                CategoryType newType = parseCategoryType(argStrings.get(2));
                return new UpdateCategoryCommand(financeService, catId, newName, newType);
            }

            case "CreateOperation" -> {
                if (argStrings.size() < 7) {
                    throw new IllegalArgumentException("CreateOperationCommand requires 7 arguments: id, operationType, bankAccountId, amount, date, desc, categoryId");
                }
                int opId = parseInt(argStrings.get(0));
                OperationType opType = parseOperationType(argStrings.get(1));
                int bankAccId = parseInt(argStrings.get(2));
                int amount = parseInt(argStrings.get(3));
                Date date = parseDate(argStrings.get(4));
                String desc = stripQuotes(argStrings.get(5));
                int catId = parseInt(argStrings.get(6));
                return new CreateOperationCommand(financeService, opId, opType, bankAccId, amount, date, desc, catId);
            }
            case "DeleteOperation" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("DeleteOperationCommand requires 1 argument: operationId");
                }
                int opId = parseInt(argStrings.get(0));
                return new DeleteOperationCommand(financeService, opId);
            }
            case "GetAllOperations" -> {
                return new GetAllOperationsCommand(financeService);
            }
            case "GetOperation" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("GetOperationCommand requires 1 argument: operationId");
                }
                int opId = parseInt(argStrings.get(0));
                return new GetOperationCommand(financeService, opId);
            }
            case "UpdateOperation" -> {
                if (argStrings.size() < 7) {
                    throw new IllegalArgumentException("UpdateOperationCommand requires 7 arguments: id, newType, newBankAccId, newAmount, newDate, newDesc, newCatId");
                }
                int opId = parseInt(argStrings.get(0));
                OperationType newOpType = parseOperationType(argStrings.get(1));
                int newBankAccId = parseInt(argStrings.get(2));
                int newAmount = parseInt(argStrings.get(3));
                Date newDate = parseDate(argStrings.get(4));
                String newDesc = stripQuotes(argStrings.get(5));
                int newCatId = parseInt(argStrings.get(6));
                return new UpdateOperationCommand(financeService, opId, newOpType, newBankAccId, newAmount, newDate, newDesc, newCatId);
            }

            case "GetIncomeExpenseDifference" -> {
                if (argStrings.size() < 2) {
                    throw new IllegalArgumentException("GetIncomeExpenseDifferenceCommand requires 2 arguments (fromDate, toDate)");
                }
                Date from = parseDate(argStrings.get(0));
                Date to = parseDate(argStrings.get(1));
                return new GetIncomeExpenseDifferenceCommand(analyticsService, from, to);
            }
            case "GetSortedOperations" -> {
                if (argStrings.size() < 2) {
                    throw new IllegalArgumentException("GetSortedOperationsCommand requires 2 arguments (fromDate, toDate)");
                }
                Date from = parseDate(argStrings.get(0));
                Date to = parseDate(argStrings.get(1));
                return new GetSortedOperationsCommand(analyticsService, from, to);
            }
            case "GroupOperationsByCategory" -> {
                if (argStrings.size() < 2) {
                    throw new IllegalArgumentException("GroupOperationsByCategoryCommand requires 2 arguments (fromDate, toDate)");
                }
                Date from = parseDate(argStrings.get(0));
                Date to = parseDate(argStrings.get(1));
                return new GroupOperationsByCategoryCommand(analyticsService, from, to);
            }

            case "RecalculateBalance" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("RecalculateBalanceCommand requires 1 argument: bankAccountId");
                }
                int bAccId = parseInt(argStrings.get(0));
                return new RecalculateBalanceCommand(dataManagementService, bAccId);
            }

            case "ExportAllDataToCsv" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("ExportAllDataToCsv requires 1 argument: path");
                } else {
                    String filePath = argStrings.get(0);
                    return new ExportAllDataToCsvCommand(financeService, filePath);
                }
            }
            case "ExportAllDataToJson" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("ExportAllDataToJson requires 1 argument: path");
                } else {
                    String filePath = argStrings.get(0);
                    return new ExportAllDataToJsonCommand(financeService, filePath);
                }
            }
            case "ExportAllDataToYaml" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("ExportAllDataToYaml requires 1 argument: path");
                } else {
                    String filePath = argStrings.get(0);
                    return new ExportAllDataToYamlCommand(financeService, filePath);
                }
            }
            case "ImportFromCsv" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("ImportFromCsvCommand requires 1 argument: filePath");
                }
                String filePath = argStrings.get(0);
                CsvImport csvImport = new CsvImport(financeService);
                return new ImportFromCsvCommand(csvImport, filePath);
            }
            case "ImportFromJson" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("ImportFromJsonCommand requires 1 argument: filePath");
                }
                String filePath = argStrings.get(0);
                return new ImportFromJsonCommand(financeService, filePath);
            }
            case "ImportFromYaml" -> {
                if (argStrings.isEmpty()) {
                    throw new IllegalArgumentException("ImportFromYamlCommand requires 1 argument: filePath");
                }
                String filePath = argStrings.get(0);
                return new ImportFromYamlCommand(financeService, filePath);
            }

            default -> throw new ClassNotFoundException("Unknown or unsupported command class: " + className);
        }
    }

    private int parseInt(String s) {
        return Integer.parseInt(s);
    }

    private double parseDouble(String s) {
        return Double.parseDouble(s);
    }

    private String stripQuotes(String s) {
        return s.replaceAll("^\"|\"$", "");
    }

    private Date parseDate(String s) throws ParseException {
        try {
            long millis = Long.parseLong(s);
            return new Date(millis);
        } catch (NumberFormatException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            return sdf.parse(s);
        }
    }

    private CategoryType parseCategoryType(String s) {
        return CategoryType.valueOf(s.toUpperCase());
    }

    private OperationType parseOperationType(String s) {
        return OperationType.valueOf(s.toUpperCase());
    }
}
