package bank.commands.importing;

import bank.commands.Command;
import bank.importing.CsvImport;

public class ImportFromCsvCommand implements Command<Void> {

    private final CsvImport csvImport;
    private final String filePath;

    public ImportFromCsvCommand(CsvImport csvImport, String filePath) {
        this.csvImport = csvImport;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        csvImport.importData(filePath);
        return null;
    }
}
