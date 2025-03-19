package bank.commands.impl.importing;

import bank.commands.Command;
import bank.importing.JsonImport;
import bank.services.FinanceService;

public class ImportFromJsonCommand implements Command<Void> {

    private final FinanceService financeService;
    private final String filePath;

    public ImportFromJsonCommand(FinanceService financeService, String filePath) {
        this.financeService = financeService;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        JsonImport jsonImport = new JsonImport(financeService);
        jsonImport.importData(filePath);

        System.out.println("Data successfully imported from JSON: " + filePath);
        return null;
    }
}
