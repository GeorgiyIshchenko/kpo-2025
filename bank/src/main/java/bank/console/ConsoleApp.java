package bank.console;

import bank.commands.Command;
import bank.commands.CommandBatch;
import bank.commands.CommandRegistry;
import bank.services.AnalyticsService;
import bank.services.DataManagementService;
import bank.services.FinanceService;
import bank.factories.BankAccountFactory;
import bank.factories.CategoryFactory;
import bank.factories.OperationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("all")
public class ConsoleApp {

    private final Scanner scanner;
    private final CommandBatch commandBatch;
    private final CommandFactory commandFactory;
    private final FinanceService financeService;
    private final AnalyticsService analyticsService;
    private final DataManagementService dataManagementService;

    public ConsoleApp() {
        this.scanner = new Scanner(System.in);
        this.commandBatch = new CommandBatch();

        FinanceService financeService = new FinanceService(
                new BankAccountFactory(),
                new CategoryFactory(),
                new OperationFactory()
        );
        this.financeService = financeService;

        this.analyticsService = new AnalyticsService(financeService);
        this.dataManagementService = new DataManagementService(financeService);

        CommandRegistry commandRegistry = new CommandRegistry();

        this.commandFactory = new CommandFactory(
                commandRegistry,
                financeService,
                analyticsService,
                dataManagementService
        );
    }

    public void run() {
        printHelp();

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Shutting down the application.");
                break;
            } else if (line.equalsIgnoreCase("commit")) {
                if (commandBatch.isEmpty()) {
                    System.out.println("No commands to execute (batch is empty).");
                } else {
                    commandBatch.executeAll();
                }
            } else if (line.startsWith("create ")) {
                String[] parts = line.split("\\s+");
                if (parts.length < 2) {
                    System.out.println("Please specify the command class name after 'create'.");
                    continue;
                }
                String commandClassName = parts[1];
                List<String> argStrings = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    argStrings.add(parts[i]);
                }

                try {
                    Command<?> cmd = commandFactory.createCommand(commandClassName, argStrings);
                    commandBatch.addCommand(cmd);
                    System.out.println("Command " + commandClassName + " was successfully added to the batch.");
                } catch (Exception e) {
                    System.out.println("Error creating command: " + e.getMessage());
                }
            } else {
                System.out.println("Unknown command: " + line);
                printHelp();
            }
        }
    }

    private void printHelp() {
        System.out.println("""
            ============= CONSOLE APPLICATION =============
            Command format:
              1) create <CommandClassName> arg1 arg2 ...
                 - creates a command and adds it to the batch
              2) commit
                 - executes all commands in the batch
              3) exit
                 - quits the application

            Example:
              create CreateBankAccountnr 1 "MainAcc" 1000.0
              create CreateCategory 10 "Food" EXPENSE
              commit

            """);
    }

    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();
        app.run();
    }
}
