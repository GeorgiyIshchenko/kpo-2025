package bank.console;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleAppTest {

    @Test
    void testConsoleAppScript() throws Exception {
        String inputScript = String.join("\n",
                "create CreateBankAccount 1 MainAcc 1000.0",
                "create CreateCategory 10 \"Food\" EXPENSE",
                "create CreateOperation 100 DEPOSIT 1 1000 1697046781234 \"Salary\" 10",
                "commit",
                "unknown UnknownCommand 123",
                "commit",
                "",
                "create CreateBankAccount",
                "commit",
                "create ExportAllDataToJson report.json",
                "create ExportAllDataToYaml report.yaml",
                "create ExportAllDataToCsv report.csv",
                "commit",
                "create DeleteBankAccount 1",
                "create DeleteCategory 10",
                "create DeleteOperation 100",
                "commit",
                "create ImportFromCsv report.csv",
                "create ImportFromYaml report.yaml",
                "commit",
                "exit"
        ) + "\n";

        ByteArrayInputStream in = new ByteArrayInputStream(inputScript.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        try {
            System.setIn(in);
            System.setOut(printStream);

            ConsoleApp app = new ConsoleApp();
            app.run();
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }

        String consoleOutput = out.toString();

        System.out.println(consoleOutput);

        assertTrue(consoleOutput.contains("Unknown command: unknown UnknownCommand 123"));
        assertTrue(consoleOutput.contains("Error creating command:"));
        assertTrue(consoleOutput.contains("Command CreateBankAccount was successfully added to the batch."));
        assertTrue(consoleOutput.contains("Command ExportAllDataToCsv was successfully added to the batch."));
        assertTrue(consoleOutput.contains("Command ExportAllDataToJson was successfully added to the batch."));
        assertTrue(consoleOutput.contains("Command ExportAllDataToYaml was successfully added to the batch."));
        assertTrue(consoleOutput.contains("Command ImportFromCsv was successfully added to the batch."));
        assertTrue(consoleOutput.contains("Command ImportFromYaml was successfully added to the batch."));

    }
}