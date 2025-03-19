package bank.commands.impl.importing;

import bank.commands.Command;
import bank.importing.YamlImport;
import bank.services.FinanceService;

public class ImportFromYamlCommand implements Command<Void> {

    private final FinanceService financeService;
    private final String filePath;

    public ImportFromYamlCommand(FinanceService financeService, String filePath) {
        this.financeService = financeService;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        YamlImport yamlImport = new YamlImport(financeService);
        yamlImport.importData(filePath);

        System.out.println("Data successfully imported from YAML: " + filePath);
        return null;
    }
}
