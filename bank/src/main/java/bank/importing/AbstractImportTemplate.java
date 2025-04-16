package bank.importing;

import bank.services.FinanceService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public abstract class AbstractImportTemplate {

    protected final FinanceService financeService;

    protected AbstractImportTemplate(FinanceService financeService) {
        this.financeService = financeService;
    }

    public final void importData(String filePath) {
        try {
            String content = readFile(filePath);
            List<CommonDTO> rows = parseContent(content);
            persistRows(rows);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + filePath, e);
        }
    }

    protected String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    protected abstract List<CommonDTO> parseContent(String content);

    protected void persistRows(List<CommonDTO> rows) {
        for (CommonDTO row : rows) {
            switch (row.getEntityType()) {
                case CommonDTO.ENTITY_TYPE_ACCOUNT:
                    financeService.createBankAccount(
                            row.getId(),
                            row.getName(),
                            row.getBalance()
                    );
                    break;
                case CommonDTO.ENTITY_TYPE_CATEGORY:
                    financeService.createCategory(
                            row.getId(),
                            row.getName(),
                            row.getCategoryType()
                    );
                    break;
                case CommonDTO.ENTITY_TYPE_OPERATION:
                    financeService.createOperation(
                            row.getId(),
                            row.getOperationType(),
                            row.getBankAccountId(),
                            row.getAmount(),
                            row.getDate(),
                            row.getDescription(),
                            row.getCategoryId()
                    );
                    break;
                default:
                    log.warn("Неизвестный тип строки: " + row.getEntityType());
            }
        }
    }

}
