package bank.commands;

import bank.commands.impl.analytics.GetIncomeExpenseDifferenceCommand;
import bank.commands.impl.analytics.GetSortedOperationsCommand;
import bank.commands.impl.analytics.GroupOperationsByCategoryCommand;
import bank.commands.impl.bank_account.*;
import bank.commands.impl.category.*;
import bank.commands.impl.exporting.ExportAllDataToCsvCommand;
import bank.commands.impl.exporting.ExportAllDataToJsonCommand;
import bank.commands.impl.importing.ImportFromCsvCommand;
import bank.commands.impl.importing.ImportFromJsonCommand;
import bank.commands.impl.management.RecalculateBalanceCommand;
import bank.commands.impl.operation.*;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private final Map<String, Class<? extends Command<?>>> commandMap = new HashMap<>();

    public CommandRegistry() {
        register("GetIncomeExpenseDifference", GetIncomeExpenseDifferenceCommand.class);
        register("GetSortedOperations", GetSortedOperationsCommand.class);
        register("GroupOperationsByCategory", GroupOperationsByCategoryCommand.class);
        
        register("CreateBankAccount", CreateBankAccountCommand.class);
        register("DeleteBankAccount", DeleteBankAccountCommand.class);
        register("GetAllBankAccounts", GetAllBankAccountsCommand.class);
        register("GetBankAccount", GetBankAccountCommand.class);
        register("UpdateBankAccount", UpdateBankAccountCommand.class);
        
        register("CreateCategory", CreateCategoryCommand.class);
        register("DeleteCategory", DeleteCategoryCommand.class);
        register("GetAllCategories", GetAllCategoriesCommand.class);
        register("GetCategory", GetCategoryCommand.class);
        register("UpdateCategory", UpdateCategoryCommand.class);
        
        register("ExportAllDataToCsv", ExportAllDataToCsvCommand.class);
        register("ExportAllDataToJson", ExportAllDataToJsonCommand.class);
        register("ExportAllDataToYaml", ExportAllDataToJsonCommand.class);
        
        register("ImportFromCsv", ImportFromCsvCommand.class);
        register("ImportFromJson", ImportFromJsonCommand.class);
        register("ImportFromYaml", ImportFromJsonCommand.class);
        
        register("RecalculateBalance", RecalculateBalanceCommand.class);
        
        register("CreateOperation", CreateOperationCommand.class);
        register("DeleteOperation", DeleteOperationCommand.class);
        register("GetAllOperations", GetAllOperationsCommand.class);
        register("GetOperation", GetOperationCommand.class);
        register("UpdateOperation", UpdateOperationCommand.class);
    }

    private void register(String shortName, Class<? extends Command<?>> cmdClass) {
        commandMap.put(shortName, cmdClass);
    }

    public Class<? extends Command<?>> getCommandClass(String shortName) {
        return commandMap.get(shortName);
    }
}